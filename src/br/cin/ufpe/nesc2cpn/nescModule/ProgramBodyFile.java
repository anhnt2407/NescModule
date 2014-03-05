package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.creator.CreatorFactory;
import br.cin.ufpe.nesc2cpn.nescModule.creator.EnumCreator;
import br.cin.ufpe.nesc2cpn.nescModule.creator.FunctionCreator;
import br.cin.ufpe.nesc2cpn.nescModule.creator.PreDeployCreator;
import br.cin.ufpe.nesc2cpn.nescModule.creator.StructCreator;
import br.cin.ufpe.nesc2cpn.nescModule.creator.VariableCreator;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Struct;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Variable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avld
 */
public abstract class ProgramBodyFile
{
    private VariableCreator variableCreator;
    private EnumCreator enumCreator;
    private StructCreator structCreator;
    private PreDeployCreator preDeployCreator;
    private FunctionCreator functionCreator;

    private String[] partesGlobal;

    public ProgramBodyFile()
    {
        variableCreator = new VariableCreator();
        enumCreator = new EnumCreator();
        structCreator = new StructCreator();
        preDeployCreator = new PreDeployCreator();
        functionCreator = new FunctionCreator();
    }

    protected void treatBody(String dado)
    {
        partesGlobal = dado.split("\n");

        List<String> variablesList = new ArrayList<String>();
        List<String> functionList    = new ArrayList<String>();

        foundVariableAndMethod( variablesList , functionList );

        //System.out.println();
        //System.out.println( "Variaveis encontradas..." );
        for( String text : variablesList )
        {
            String tempSLN = CreatorFactory.getInstance().removeLineNumber( text );
            int lineNumber = CreatorFactory.getInstance().getLineNumber( text );

            if( variableCreator.identify( tempSLN ) )
            {
                Variable var = (Variable) variableCreator.convertTo( tempSLN );
                var.setLineNumber( lineNumber );

                //module.getVariables().put( var.getVariableName() , var );
                addVariable( var );
            }
            else if( enumCreator.identify( tempSLN ) )
            {
                Instruction enum_ = enumCreator.convertTo( tempSLN );
                enum_.setLineNumber( lineNumber );

                //module.getTypeDefined().put( struct.getName() , struct );
                addTypeDefined( enum_ );
            }
            else if( structCreator.identify( tempSLN ) )
            {
                Struct struct = (Struct) structCreator.convertTo( tempSLN );
                struct.setLineNumber( lineNumber );

                //module.getTypeDefined().put( struct.getName() , struct );
                addTypeDefined( struct );
            }
        }

        //System.out.println();
        //System.out.println( "Metodos encontrados..." );
        for( String text : functionList )
        {
            //System.out.println( "text: " + text );

            int lineNumber = CreatorFactory.getInstance().getLineNumber( text );
            String tempSLN = CreatorFactory.getInstance().removeLineNumber( text );
            
            Function function = (Function) functionCreator.convertTo( tempSLN );
            function.setLineNumber( lineNumber );

            //System.out.println( method.toString() + "\n" );

            addFunction( function );
            //System.out.println( text );
        }
    }

    private void foundVariableAndMethod(List<String> variablesList, List<String> functionList)
    {
        for( int i = 0; i < partesGlobal.length; i++ )
        {
            int blockStartCounter = 0;
            String temp = "";

            String line = partesGlobal[i];
            
            if( line == null ? true : "".equals( line.trim() ) )
            {
                continue ;
            }

            String tempSLN = CreatorFactory.getInstance().removeLineNumber( line );
            if( preDeployCreator.identify( tempSLN ) )
            {
                variablesList.add( line );
                continue ;
            }

            for( int i2 = 0; i2 < line.length(); i2++ )
            {
                char c = line.charAt( i2 );
                temp += c;

                if( c == Instruction.INSTRUCTION_FINAL && blockStartCounter == 0 )
                {
                    temp = NescIdentifyFile.removerEspaceDuplo( temp );
                    tempSLN = CreatorFactory.getInstance().removeLineNumber( temp );

                    if( variableCreator.identify( tempSLN )
                            || enumCreator.identify( tempSLN )
                            || structCreator.identify( tempSLN )
                            || preDeployCreator.identify( tempSLN ) )
                    {
                        variablesList.add( temp );
                        temp = "";
                    }
                    else
                    {
                        functionList.add( temp );
                        temp = "";
                    }

                    continue ;
                }
                else if( c == Instruction.BLOCK_START )
                {
                    //System.err.println( temp + "\tblock size: " + blockStartCounter );
                    blockStartCounter++;
                }
                else if( c == Instruction.BLOCK_FINAL )
                {
                    blockStartCounter--;

                    if( blockStartCounter == 0 )
                    {
                        temp = NescIdentifyFile.removerEspaceDuplo( temp );
                        tempSLN = CreatorFactory.getInstance().removeLineNumber( temp );

                        //System.out.println( temp );

                        if( !variableCreator.identify( tempSLN )
                            && !enumCreator.identify( tempSLN )
                            && !structCreator.identify( tempSLN )
                            && !preDeployCreator.identify( tempSLN ) )
                        {
                            functionList.add( temp );
                            temp = "";
                            
                            continue ;
                        }
                        //else
                            //System.out.println("ignored\t" + temp);
                    }
                }

                if( i2 + 1 >= line.length() )              // continue in next part
                {
                    if( i + 1 >= partesGlobal.length )  // have next part ?!
                    {
                        //System.out.println("break!");
                        break ;                         // if no exist, finish process.
                    }

                    i2 = -1;

                    do                                  // get next part avaliable
                    {
                        line = partesGlobal[ ++i ];        // get next part
                    }
                    while( line == null ? true : "".equals( line ) ); // is avaliable ?!

                    temp += " ";
                }
            }
        }
    }

    public abstract void addVariable(Variable variable);
    public abstract void addTypeDefined(Instruction instruction);
    public abstract void addFunction(Function function);
}
