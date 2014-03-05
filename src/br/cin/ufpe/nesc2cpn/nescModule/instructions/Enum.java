package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 *
 * exemplo .:
 *      enum { ATT_1 = 0 , ATT_1 = 1 }
 *
 * @author avld
 */
public class Enum extends Instruction
{
    private Map<String,String> attributesMap;

    public Enum()
    {
        attributesMap = new HashMap<String, String>();
        setType( "enum" );
    }

    public Map<String, String> getAttributes() {
        return attributesMap;
    }

    public void setAttributes(Map<String, String> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "enum {\n" );

        Iterator<Map.Entry<String,String>> it = getAttributes().entrySet().iterator();
        while( it.hasNext() )
        {
            Map.Entry<String,String> entry = it.next();

            builder.append( entry.getKey() );
            builder.append( " = " );
            builder.append( entry.getValue() );

            String fim = "\n";
            if( it.hasNext() )
            {
                fim = ",\n";
            }

            builder.append( fim );
        }

        builder.append( "};" );
        return builder.toString();
    }

}
