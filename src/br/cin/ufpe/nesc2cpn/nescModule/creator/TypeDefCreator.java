package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.TypeDef;

/**
 *
 * @author avld
 */
public class TypeDefCreator extends Creator
{

    public TypeDefCreator()
    {
        
    }

    @Override
    public boolean identify(String line)
    {
        return line.startsWith("typedef");
    }

    @Override
    public Instruction convertTo(String line)
    {
        int nameStart = line.lastIndexOf( " " );

        String inst = line.substring( "typedef".length() , nameStart );
        String name = line.substring( nameStart + 1 , line.length() - 1 );

        Instruction instruction = CreatorFactory.getInstance().convertTo( inst );

        TypeDef typedef = new TypeDef();
        typedef.setInstruction( instruction );
        typedef.setName( name );

        return typedef;
    }

    @Override
    public String getType()
    {
        return "typedef";
    }


    public static void main(String args[]) throws Exception
    {
        String text = "typedef nx_struct radio_msg { nx_uint8_t err; nx_uint16_t res; nx_uint8_t counter[25]; } radio_msg_t;";

        TypeDefCreator creator = new TypeDefCreator();
        boolean result = creator.identify( text );
        Instruction inst= creator.convertTo( text );

        System.out.println( "result: " + result );
        System.out.println( "inst: " + inst.toString() );
    }
}
