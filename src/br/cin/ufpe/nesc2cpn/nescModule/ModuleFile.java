package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.creator.InvokeCreator;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.IfElse;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Invoke;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Struct;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Variable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class ModuleFile extends ProgramBodyFile
{
    private Module module;
    
    public ModuleFile()
    {
        // do nothing
    }

    public Module getModule()
    {
        return module;
    }

    public void convertTo(String dados) throws Exception
    {
        module = new Module();
        
        int confStart = dados.indexOf("module");
        int implStart =  dados.indexOf("implementation");
        theatHead( dados.substring( confStart , implStart ) );

        String impStr  =  dados.substring( implStart + "implementation".length() );
        int implStart2 =  impStr.indexOf("{") ;

        ProjectDate.getInstance().setLastModule( module.getName() );
        ProjectDate.getInstance().setLastFunction( null );
        
        treatBody( impStr.substring( implStart2 + 1 ) );

        setInterfaceNameInMethodAndInvoke();
        //printModule();
    }

    /**
     * identificar o nome do modulo e as interfaces que são usadas e providas por ele
     *
     * @param dado
     * @throws Exception
     */
    private void theatHead(String dado) throws Exception
    {
        dado = dado.replace( ';' , ' ' );
        dado = NescIdentifyFile.removerEspaceDuplo( dado.replace( '\n' , ' ' ) );

        module.setSafe( dado.indexOf("@safe") > 0 );

        String[] partes = dado.trim().split(" ");
        boolean provides = false;
        for(int i = 0; i < partes.length; i++ )
        {
            if( "module".equals( partes[i] ) )       // atribuir nome do Configuration
            {
                module.setName( partes[++i] );
            }
            else if( "interface".equals( partes[i] ) )      // Identificou uma nova Interfaces que prove
            {
                String iName = partes[ ++i ];               // similar a: String iName = partes[ i + 1 ]; i = 1 + 1;
                String iNick = iName;

                if( "as".equals( partes[ i + 1 ] ) )        // Interface as Apelido
                {
                    i += 2;
                    iNick = partes[i];
                }

                Interface itObj = InterfaceFile.tratar( iName );
                iNick = treatInterfaceName( iNick );

                if( provides )
                {
                    module.getInterfaceProviders().put( iNick , itObj );
                }
                else
                {
                    module.getInterfaceUses().put( iNick , itObj );
                }
            }
            else if( "uses".equals( partes[i] ) )
            {
                provides = false;
            }
            else if( "provides".equals( partes[i] ) )
            {
                provides = true;
            }
        }
    }

    private String treatInterfaceName( String nick )
    {
        if( nick.contains( "<" ) )
        {
            return nick.substring( 0 , nick.indexOf("<") ).trim();
        }
        else
        {
            return nick;
        }
    }
    
    @Override
    public void addVariable(Variable var)
    {
        module.getVariables().put( var.getVariableName() , var );
    }

    @Override
    public void addTypeDefined(Instruction instruction)
    {
        String name = null;

        if( instruction instanceof Struct )
        {
            name = ((Struct) instruction).getName();
        }
        else if( instruction instanceof br.cin.ufpe.nesc2cpn.nescModule.instructions.Enum )
        {
            //TODO: ve depois!
            //name = ((br.cin.ufpe.nesc2cpn.nescModule.instructions.Enum) instruction).getText();
            name = "enum";
        }

        module.getTypeDefined().put( name , instruction );
    }

    @Override
    public void addFunction(Function function)
    {
        module.getFunctions().add( function );
    }

    public void processLink( String nick , List<ConfigurationLink> linkList , Map<String,String> compMap )
    {
        for( ConfigurationLink link : linkList )
        {
            String moduleName = null;
            String interfaceName = null;

            if( !link.getPreModule().equals( nick ) )
            {
                moduleName = link.getPreModule();
                interfaceName = link.getPosInterface();
            }
            else if( !link.getPosModule().equals( nick ) )
            {
                moduleName = link.getPosModule();
                interfaceName = link.getPreInterface();
            }

            // ---------------------------------------------

            if( compMap.containsKey( moduleName ) )
            {
                moduleName = compMap.get( moduleName );
            }

            if( moduleName.indexOf('(') > 0 )
            {
                moduleName = moduleName.substring( 0 , moduleName.indexOf('(') );
            }

            if( moduleName.indexOf('<') > 0 )
            {
                moduleName = moduleName.substring( 0 , moduleName.indexOf('<') );
            }

            // ---------------------------------------------

//            System.out.println("\tmodule: " + moduleName + " | interface: " + interfaceName );
            setModuleNameInInvoke( moduleName , interfaceName );
        }
    }

    private void setModuleNameInInvoke( String moduleName , String interfaceName )
    {
        for( Function method : module.getFunctions() )                                              // get ALL methods
        {   
            if( Function.EVENT.equalsIgnoreCase( method.getFunctionType() ) )
            {
                if( method.getInterfaceNick().equals( interfaceName ) )
                {
                    method.setModuleName( moduleName );
                }
            }

            setModuleNameInInvoke( method , moduleName , interfaceName );
        }
    }

    private void setModuleNameInInvoke( ComposedInstruction comp, String moduleName , String interfaceName )
    {
        for( Instruction inst : comp.getInstructions() )                                        // each method, get ALL instuction
            {
                if( inst instanceof Invoke )                                                    // confirm if instruction is a Invoke
                {
                    Invoke invoke = (Invoke) inst;                                              // convert Instruction class to Invoke Class

                    if( invoke.getInvokeType() == null )
                    {
                        continue ;
                    }

                    if( InvokeCreator.COMMAND.equals( invoke.getInvokeType() ) )                       // Check if the invoke is to a command
                    {
                        //System.out.println( "\t[INVOKE] " + invoke.toString() );

                        if( invoke.getMethod().getInterfaceNick().equals( interfaceName ) )     // Check if the invoke is to a same interface
                        {
                            invoke.getMethod().setModuleName( moduleName );                     // Put module name in this Invoke
                        }
                    }
                }
                else if( inst instanceof IfElse )
                {
                    setModuleNameInInvoke( (ComposedInstruction) inst , moduleName , interfaceName );

                    for( IfElse elseIf : ((IfElse) inst).getElses().values() )
                    {
                        setModuleNameInInvoke( elseIf , moduleName , interfaceName );
                    }
                }
                else if( inst instanceof ComposedInstruction )
                {
                    setModuleNameInInvoke( (ComposedInstruction) inst , moduleName , interfaceName );
                }
            }
    }

    private void setInterfaceNameInMethodAndInvoke()
    {
        Map<String,Interface> interfaceMap = new HashMap<String,Interface>();
        interfaceMap.putAll( module.getInterfaceProviders() );
        interfaceMap.putAll( module.getInterfaceUses() );

        for( Function method : module.getFunctions() )                                              // get ALL methods
        {
            if( method == null )
            {
                continue ;
            }

            if( Function.EVENT.equalsIgnoreCase( method.getFunctionType() ) )
            {
                String name = interfaceMap.get( method.getInterfaceName() ).getName();

                method.setInterfaceName( name );
            }

            for( Instruction inst : method.getInstructions() )                                  // each method, get ALL instuction
            {
                if( inst instanceof Invoke )                                                    // confirm if instruction is a Invoke
                {
                    Invoke invoke = (Invoke) inst;                                              // convert Instruction class to Invoke Class

                    if( invoke.getInvokeType() == null )
                    {
                        continue ;
                    }

                    // Check if the invoke is to a command
                    if( InvokeCreator.COMMAND.equals( invoke.getInvokeType() )
                            || InvokeCreator.EVENT.equals( invoke.getInvokeType() ) )
                    {
                        String nick = invoke.getMethod().getInterfaceNick();
                        
                        if( !interfaceMap.containsKey( nick ) )
                        {
                            System.out.println( "try find: " + nick );
                            System.out.println();
                            System.out.println( " Interface Avaliable " );
                            for( String name : interfaceMap.keySet() )
                            {
                                System.out.println( "name: " + name );
                            }
                        }
                        
                        String name = interfaceMap.get( nick ).getName();       // Put interface name in this Invoke
                        invoke.getMethod().setInterfaceName( name );
                    }
                }
            }
        }
    }

    // ------------------------------- //
    // ------------------------------- //
    // ------------------------------- //

    public void printModule()
    {
        System.out.print( "module " + module.getName() + " " );
        System.out.println( ( module.isSafe() ? "@safe" : "" ) + "{" );

        if( !module.getInterfaceUses().isEmpty() )
        {
            System.out.println( "  uses{" );

            for( Entry<String,Interface> entry : module.getInterfaceUses().entrySet() )
            {
                System.out.print( "    interface " + entry.getValue().getName() );
                
                if( !entry.getKey().equals( entry.getValue().getName() ) )
                {
                    System.out.print( " as " + entry.getKey() );
                }

                System.out.println(";");
            }

            System.out.println( "  }" );
        }

        if( !module.getInterfaceProviders().isEmpty() )
        {
            System.out.println( "  provides{" );

            for( Entry<String,Interface> entry : module.getInterfaceProviders().entrySet() )
            {
                System.out.print( "  interface " + entry.getValue().getName() );

                if( !entry.getKey().equals( entry.getValue().getName() ) )
                {
                    System.out.print( " as " + entry.getKey() );
                }

                System.out.println(";");
            }

            System.out.println( "  }" );
        }

        System.out.println( "}" );
        System.out.println( "implementation{" );

        for( Instruction instruction : module.getVariables().values() )
        {
            System.out.println( "  " + instruction.toString() );
        }

        System.out.println();

        for( Function method : module.getFunctions() )
        {
            System.out.println( "  " + method.toString() + "\n" );
        }

        System.out.println( "}" );
    }
}
