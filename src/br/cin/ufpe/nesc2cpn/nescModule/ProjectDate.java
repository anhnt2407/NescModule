package br.cin.ufpe.nesc2cpn.nescModule;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author avld
 */
public class ProjectDate
{
    private static ProjectDate instance;

    private Map<String , Map<String , String>> configurationMap;
    private String lastConfiguration;

    private Map<String , Map<String , Map<String , String>>> variableMap;
    private String lastModule;
    private String lastFunction;

    private ProjectDate()
    {
        configurationMap = new HashMap<String, Map<String, String>>();
        variableMap = new HashMap<String, Map<String, Map<String, String>>>();
    }

    public static ProjectDate getInstance()
    {
        if( instance == null )
        {
            instance = new ProjectDate();
        }

        return instance;
    }

    // ------------------------------------------
    // ------------------------------------------
    // ------------------------------------------

    public String getModuleName( String interfaceName )
    {
        Map<String,String> moduleMap = configurationMap.get( lastConfiguration );
        return moduleMap.get( interfaceName );
    }

    public void putModuleName( String interfaceName , String moduleName )
    {
        if( !configurationMap.containsKey( lastConfiguration ) )
        {
            configurationMap.put( lastConfiguration , new HashMap<String, String>() );
        }

        Map<String,String> moduleMap = configurationMap.get( lastConfiguration );
        moduleMap.put( interfaceName , moduleName );
    }

    public void setLastConfiguration(String conf)
    {
        this.lastConfiguration = conf;
    }

    // ------------------------------------------
    // ------------------------------------------
    // ------------------------------------------

    public String getVariableType( String variableName )
    {
        String name = clearVariableName( variableName );

        // --------------------------

        if( !variableMap.containsKey( lastModule ) )
        {
            variableMap.put( lastModule , new HashMap<String, Map<String, String>>() );
        }

        Map<String,Map<String , String>> moduleMap = variableMap.get( lastModule );

        if( !moduleMap.containsKey( lastFunction ) )
        {
            moduleMap.put( lastFunction , new HashMap<String, String>() );
        }

        Map<String , String> functionVariableMap = moduleMap.get( lastFunction );
        
        String type = functionVariableMap.get( name );

        if( type != null )
        {
            return type;
        }

        functionVariableMap = moduleMap.get( null );
        return functionVariableMap.get( name );
    }

    public void putVariableType( String variableName , String variableType )
    {
        String name = clearVariableName( variableName );

        if( !variableMap.containsKey( lastModule ) )
        {
            variableMap.put( lastModule , new HashMap<String, Map<String, String>>() );
        }

        Map<String,Map<String , String>> moduleMap = variableMap.get( lastModule );

        if( !moduleMap.containsKey( lastFunction ) )
        {
            moduleMap.put( lastFunction , new HashMap<String, String>() );
        }

        Map<String , String> functionVariableMap = moduleMap.get( lastFunction );
        functionVariableMap.put( name , variableType );
    }

    public void setLastModule(String module)
    {
        this.lastModule = module;
    }

    public void setLastFunction(String function)
    {
        this.lastFunction = function;
    }

    private String clearVariableName(String name)
    {
        if( name.indexOf( "*" ) >= 0 )
        {
            return name.replace( '*' , ' ' ).trim();
        }
        else if( name.indexOf("[") >= 0 )
        {
            int start = name.indexOf("[");
            return name.substring( 0 , start );
        }

        return name;
    }

}
