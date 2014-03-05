package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Exemplo .:
 *      struct name { ATT ; ATT; }
 * or
 *      struct { ATT ; ATT; } name;
 *
 *
 * @author avld
 */
public class Struct extends Instruction
{
    private String name;
    private boolean isNxType;
    private Map<String,String> attributesMap;

    public Struct()
    {
        attributesMap = new HashMap<String, String>();
    }

    public Map<String, String> getAttributes() {
        return attributesMap;
    }

    public void setAttributes(Map<String, String> attributesMap) {
        this.attributesMap = attributesMap;
    }

    public boolean isIsNxType() {
        return isNxType;
    }

    public void setIsNxType(boolean isNxType) {
        this.isNxType = isNxType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ------------------------------------- //
    // ------------------------------------- //
    // ------------------------------------- //

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( isIsNxType() ? "nx_struct " : "struct " );

        if( getName() == null ? false : !getName().isEmpty() )
        {
            builder.append( getName() );
            builder.append( " " );
        }

        builder.append( "{ \n" );

        Iterator<Map.Entry<String,String>> it = getAttributes().entrySet().iterator();
        while( it.hasNext() )
        {
            Map.Entry<String,String> entry = it.next();

            builder.append( entry.getValue() );
            builder.append( " " );
            builder.append( entry.getKey() );
            builder.append( ";\n" );
        }

        builder.append( "}" );

        return builder.toString();
    }
    
}
