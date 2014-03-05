package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.SpecialWord;

/**
 *
 * @author avld
 */
public class SpecialWordCreator extends Creator
{
    public final static String[] SPECIAL_WORD = new String[]{"continue","break","return"};

    public boolean identify(String line)
    {
        if( line.indexOf(";") < 0 );

        String partes[] = line.split(";");
        partes[0] = partes[0].split(" ")[0].trim();

        for( String word : SPECIAL_WORD )
        {
            if( word.equals( partes[0].trim() ) )
            {
                return true;
            }
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        SpecialWord specialWord = new SpecialWord();
        specialWord.setText( line );
        specialWord.setWord( line );

        return specialWord;
    }

    public String getType()
    {
        return "specialWord";
    }

}
