package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class Function extends ComposedInstruction
{
    public final static String COMMAND = "command";
    public final static String EVENT = "event";
    public final static String TASK = "task";
    
    private boolean async;
    private String functionType;
    private String returnType;
    private String moduleName;
    private String interfaceName;
    private String interfaceNick;
    private String functionName;
    private Map<String,String> arguments;
    private String comments;

    public Function(){
        arguments = new LinkedHashMap<String, String>();
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceNick() {
        return interfaceNick;
    }

    public void setInterfaceNick(String interfaceNick) {
        this.interfaceNick = interfaceNick;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String name) {
        this.functionName = name;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String type) {
        this.functionType = type;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        if( isAsync() )
        {
            builder.append( "async " );
        }

        if( getFunctionType() == null ? false : !getFunctionType().isEmpty() )
        {
            builder.append( getFunctionType() ).append( " " );
        }

        builder.append( getReturnType() ).append( " " );

        if( getInterfaceName() == null ? false : !getInterfaceName().isEmpty() )
        {
            builder.append( getInterfaceName() ).append( "." );
        }

        builder.append( getFunctionName() ).append( " ( " );
        
        Iterator<Entry<String,String>> it = getArguments().entrySet().iterator();
        while( it.hasNext() ){
            Entry<String,String> entry = it.next();

            builder.append( entry.getValue() ).append(" ").append( entry.getKey() );

            if( it.hasNext() ) builder.append(" , ");
            else builder.append(" ");
        }

        if( getInstructions() == null )
        {
            builder.append( ");" );
            return builder.toString();
        }

        builder.append( ") {\n" );

        for( Instruction inst : getInstructions() )
        {
            if( inst == null ) continue;
            
            builder.append( "   [" ).append( inst.getType() ).append( "]" );
            builder.append( inst.toString() );

            if( !(inst instanceof ComposedInstruction) )
            {
                builder.append( ";" );
            }

            builder.append( "\n" );
        }

        builder.append( "  }" );

        return builder.toString();
    }
}
