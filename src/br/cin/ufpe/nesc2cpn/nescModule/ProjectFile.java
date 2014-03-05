package br.cin.ufpe.nesc2cpn.nescModule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class ProjectFile
{
    private List<ModuleFile> moduleFileList;
    private List<InterfaceFile> interfaceFileList;
    private List<ConfigurationFile> configurationFileList;

    private List<String> diretoryList;

    public ProjectFile()
    {
        moduleFileList        = new ArrayList<ModuleFile>();
        interfaceFileList     = new ArrayList<InterfaceFile>();
        configurationFileList = new ArrayList<ConfigurationFile>();

        diretoryList = new ArrayList<String>();
    }

    // --------------------------- //
    // --------------------------- //
    // --------------------------- //

    public List<String> getDiretory()
    {
        return diretoryList;
    }

    public void setDiretory( List<String> dirs )
    {
        diretoryList.clear();
        diretoryList.addAll( dirs );
    }

    public void addDiretory( String dir )
    {
        diretoryList.add( dir );
    }

    // --------------------------- //
    // --------------------------- //
    // --------------------------- //

    public Object processFile( String filename ) throws Exception
    {
        String name = (filename.indexOf(".nc") < 0) ? filename + ".nc" : filename;
        File file = null;

        for( String dir : diretoryList )
        {
            String all = dir + File.separatorChar + name;
            System.out.println("Checking if the file exists... " + all );

            file = new File( all );

            if( !file.exists() )
            {
                file = null;
            }
        }

        if( file == null )
        {
            throw new Exception("The file ("+ filename +") doesn't exist in your project.");
        }

        return processFile( file );
    }

    public Object processFile( File file ) throws Exception
    {
        System.out.println("openning the file... " + file.getName() );

        NescIdentifyFile nescUnkownFile = new NescIdentifyFile( file );
        nescUnkownFile.open();

        if( NescIdentifyFile.CONFIGURATION_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            ConfigurationFile confFile = new ConfigurationFile();
            confFile.convertTo( nescUnkownFile.getConteudo() );
            configurationFileList.add( confFile );
            
            processConfiguration( confFile.getConfiguration() );

            return confFile;
        }
        else if( NescIdentifyFile.INTERFACE_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            InterfaceFile interfFile = new InterfaceFile();
            interfFile.convertTo( nescUnkownFile.getConteudo() );
            interfaceFileList.add( interfFile );

            return interfFile;
        }
        else if( NescIdentifyFile.MODULE_FILE.equalsIgnoreCase( nescUnkownFile.getType() ) )
        {
            ModuleFile moduleFile = new ModuleFile();
            moduleFile.convertTo( nescUnkownFile.getConteudo() );
            //moduleFile.printModule();
            moduleFileList.add( moduleFile );

            return moduleFile;
        }

        return null;
    }

    public void processConfiguration( Configuration conf ) throws Exception
    {
        Map<String,List<ConfigurationLink>> nickMap = new HashMap<String, List<ConfigurationLink>>();
        
        for( ConfigurationLink link : conf.getLinks() )
        {
            String name = null;

            if( link.getSymbol().equals( ConfigurationLink.LEFT ) )
            {
                name = link.getPreModule();
            }
            else if( link.getSymbol().equals( ConfigurationLink.RIGHT ) )
            {
                name = link.getPosModule();
            }
            else
            {
                continue ;
            }

            if( !nickMap.containsKey( name ) )
            {
                nickMap.put( name , new ArrayList<ConfigurationLink>() );
            }

            nickMap.get( name ).add( link );
        }

        for( Entry<String,List<ConfigurationLink>> entry : nickMap.entrySet() )
        {
            String name = conf.getModules().get( entry.getKey() );

            try
            {
                Object obj = processFile( name );

                if( obj instanceof ModuleFile )
                {
                    ((ModuleFile) obj).processLink( entry.getKey()
                                                  , entry.getValue()
                                                  , conf.getModules() );
                }
            }
            catch(Exception err)
            {
                err.printStackTrace();
            }
        }
    }

    // --------------------------- //
    // --------------------------- //
    // --------------------------- //

    public List<Module> getModuleList()
    {
        List<Module> moduleList = new ArrayList<Module>();

        for( ModuleFile mf : moduleFileList )
        {
            moduleList.add( mf.getModule() );
        }

        return moduleList;
    }
}
