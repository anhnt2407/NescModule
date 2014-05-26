package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import java.io.File;

/**
 *
 * @author avld
 */
public class NescMain
{
    private static void process( String filename ) throws Exception
    {
        NescIdentifyFile nescUnkownFile = new NescIdentifyFile( filename );
        nescUnkownFile.open();

        if( NescIdentifyFile.CONFIGURATION_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            ConfigurationFile confFile = new ConfigurationFile();
            confFile.convertTo( nescUnkownFile.getConteudo() );
            confFile.printConfiguration();
        }
        else if( NescIdentifyFile.INTERFACE_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            InterfaceFile interfFile = new InterfaceFile();
            interfFile.convertTo( nescUnkownFile.getConteudo() );
            interfFile.printInterface();
        }
        else if( NescIdentifyFile.MODULE_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            ModuleFile moduleFile = new ModuleFile();
            moduleFile.convertTo( nescUnkownFile.getConteudo() );
            moduleFile.printModule();
        }
    }

    public static void main(String[] args) throws Exception
    {
        //String filename = "/media/pessoal/Doutorado/medicoes/SplitControl/SplitControl_start/RadioAppC.nc";
        //String filename = "/media/pessoal/Doutorado/medicoes/SplitControl/SplitControl_start/Radio.nc";
        //String filename = "/media/pessoal/Doutorado/medicoes/Exemplo/atribuir/ExampleM.nc";
        //String filename = "/opt/tinyos-2.x/apps/Blink/BlinkAppC.nc";
        String filename = "/opt/idea4wsn/1/Blink/BlinkAppC.nc";

        //process( filename );

        // ----------------------------
        // ----------------------------
        // ----------------------------

        File arquivo = new File( filename );

        ProjectFile projectFile = new ProjectFile();
        projectFile.addDiretory( arquivo.getParent() );
        projectFile.processFile( arquivo.getName() );

        System.out.println( "------------------------------" );

        for( Module module : projectFile.getModuleList() )
        {
            for( String nick : module.getInterfaceUses().keySet() )
            {
                System.out.println( "inteface: " + nick );

                for( Function method : module.getFunctions() )
                {
                    if( nick.equals( method.getInterfaceNick() ) )
                    {
                        System.out.println( " " + method.getFunctionName() );
                    }
                }
            }
            
            System.out.println( "name: " + module.getName() );
            
            for( Function method : module.getFunctions() )
            {
                System.out.print( " " + method.getInterfaceNick() );
                System.out.println( "." + method.getFunctionName() );
            }
        }
    }

}
