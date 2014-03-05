/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.NescIdentifyFile;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import java.util.List;

/**
 *
 * @author avld
 */
public abstract class ComposedCreator extends Creator
{

    public ComposedCreator()
    {
        
    }

    public abstract boolean identify(String line);
    public abstract Instruction convertTo(String line);
    public abstract String getType();

    protected void theatBody(ComposedInstruction parent, String texto)
    {
//        if( getType().indexOf("if") >= 0 )
//        {
//            System.out.println( "body: " + texto );
//        }

        texto = getBody( texto );

        //String tempSLN = CreatorFactory.getInstance().removeLineNumber( instruction );

        String[] partes = texto.split(";");
        DoWhileCreator doWhileCreator = new DoWhileCreator();

        for( int i = 0 ; i < partes.length; i++ )
        {
            int paramStartCounter = 0;
            int blockStartCounter = 0;
            String instruction = "";

            String sp = partes[i] + ";";

            for( int i2 = 0 ; i2 < sp.length() ; i2++ )
            {
                char c = sp.charAt( i2 );

                if( !( instruction.isEmpty() && (c == '{' || c == '}' || c == ';' ) ) )
                {
                    instruction += c;
                }

                if( c == Instruction.INSTRUCTION_FINAL && blockStartCounter <= 0 && paramStartCounter <= 0 )
                {
                    instruction = NescIdentifyFile.removerEspaceDuplo( instruction );
                    CreatorFactory.getInstance().addInstruction( parent , instruction );

                    blockStartCounter = 0;
                    instruction = "";
                }
                else if( c == Instruction.BLOCK_START && !instruction.isEmpty() )
                {
                    blockStartCounter++;
                }
                else if( c == Instruction.BLOCK_FINAL && !instruction.isEmpty() )
                {
                    blockStartCounter--;

                    if( blockStartCounter == 0 && !doWhileCreator.identify( instruction ) )
                    {
                        instruction = NescIdentifyFile.removerEspaceDuplo( instruction );
                        CreatorFactory.getInstance().addInstruction( parent , instruction );

                        paramStartCounter = 0;
                        blockStartCounter = 0;
                        instruction = "";
                    }
                }
                else if( c == '(' )
                {
                    paramStartCounter++;
                }
                else if( c == ')' )
                {
                    paramStartCounter--;
                }

                if( i2 + 1 >= sp.length() )              // continue in next part
                {
                    if( i + 1 >= partes.length )        // have next part ?!
                    {
                        //System.out.println("break!");
                        break ;                         // if no exist, finish process.
                    }

                    i2 = -1;

                    do                                  // get next part avaliable
                    {
                        sp = partes[ ++i ] + ";";       // get next part
                    }
                    while( sp == null ? true : "".equals( sp ) ); // is avaliable ?!

                    instruction += " ";
                }
            }

            //System.out.println( "param.: " + paramStartCounter + " ...... block: " + blockStartCounter );
            //System.out.println( "inst..: \"" + instruction.trim() + "\"" );
        }
    }

    private String getBody(String bodyStr)
    {
        boolean lnFound = false;
        
        for( int i = 0; i < bodyStr.length(); i++ )
        {
            char c = bodyStr.charAt( i );

            if( c == NescIdentifyFile.LINE_NUMBER )
            {
                lnFound = true;
            }
            else if(c == ' ' && lnFound)
            {
                if( bodyStr.charAt( i + 1 ) == '{' )
                {
                    return bodyStr.substring( i + 1 );
                }
            }
            else if( c == ' ' && !lnFound )
            {
                continue ;
            }
            else if( Character.isLetter( c ) )
            {
                break ;
            }
        }

        return bodyStr;
    }

    public List<Instruction> getInstructionChildren( String text )
    {
        int conditionLength = -1;

        if( getType().indexOf("if") >= 0 )
        {
            System.out.println( "body: " + text );
        }

        try
        {
            String condition = getCondition( text );
            conditionLength = text.indexOf( condition );
            conditionLength += condition.length();
        }
        catch(StringIndexOutOfBoundsException err)
        {
            conditionLength = -1;
        }

        String textCopy = text;
        String prob = getProbabilityString( text );

        if( prob != null )
        {
            textCopy = text.replaceFirst( prob , " " );
        }

        return getInstructionChildren( textCopy , conditionLength );
    }

    public List<Instruction> getInstructionChildren( String text , int conditionLength )
    {
        ComposedInstruction composed = new ComposedInstruction();

        int start = 0;

        CaseCreator caseCreator = new CaseCreator();
        DefaultCreator defaultCreator = new DefaultCreator();
        IfElseCreator ifElseCreator = new IfElseCreator();

        if( ifElseCreator.isIfElse( text ) == IfElseCreator.RETURN_ELSE )
        {
            start = IfElseCreator.ELSE.length();
        }
        else if( caseCreator.identify( text ) || defaultCreator.identify( text ) )
        {
            start = text.indexOf(":");
        }
        else if( conditionLength + 3 > text.indexOf("{") && text.indexOf("{") != -1)
        {
            start = text.indexOf("{");
        }
        else
        {
            start = conditionLength;
        }

        start++;
        theatBody( composed , text.substring( start ) );

        return composed.getInstructions();
    }

    public static String getCondition(String text)
    {
        int counter = 0;
        String texto = "";

        for( char c : text.toCharArray() )
        {
            if( counter > 0 || c == '(' )
            {
                texto += c;
            }

            if( c == '(' )
            {
                counter++;
            }
            else if( c == ')' )
            {
                counter--;

                if( counter <= 0 )
                {
                    break;
                }
            }
        }

        return texto.substring( 1 , texto.length() - 1 );
    }

    public Instruction getConditionInstruction(String text)
    {
        String condition = getCondition( text );
        return CreatorFactory.getInstance().convertToSimple( condition );
    }

    protected static double foundAndGetProbability(String inst)
    {
        String cond = getCondition( inst );

        int condEnd = inst.indexOf( cond ) + cond.length();
        String str = inst.substring( condEnd ).trim();

        for( char c : str.toCharArray() )
        {
            if( c == '/' )
            {
                return getProbability( str );
            }
            else if( c == '{'
                    || c == ')'
                    || c == ' ' )
            {
                continue ;
            }
            else
            {
                return -1;
            }
        }

        return -1;
    }

    /**
     * ex. if( A == B ) //@0.20
     *
     * @param line
     * @return
     */
    protected static double getProbability(String line)
    {
        int start = line.indexOf( NescIdentifyFile.PROBABILITY_NOTATION );
        if( start >= 0 )
        {
            String probStr = line.substring( start );                   //remove String before //@
            int end = probStr.indexOf( " " );                           //found a space after prob. ' '

            if( end <= -1 )                                             //Case no exist space after prob.
            {
                end = line.length();                                    //set end position like last char
            }
            else
            {
                end += start;
            }

            int size = NescIdentifyFile.PROBABILITY_NOTATION.length();
            probStr = line.substring( start + size , end );

            try
            {
                return Double.parseDouble( probStr );
            }
            catch(Exception err)
            {
                System.err.println( "Sintax Error in probability: " + line );
                return -1;
            }
        }

        return -1;
    }

    protected static String getProbabilityString(String line)
    {
        String cond = "else";

        if( !line.startsWith("else {")
                && !line.startsWith("else{")
                && !line.startsWith("else //") )
        {
            cond = getCondition( line );
        }

        int condEnd = line.indexOf( cond ) + cond.length();
        String str = line.substring( condEnd ).trim();

        for( int i = 0; i < str.length(); i++ )
        {
            char c = str.charAt( i );
            
            if( c == '/' )
            {
                int end = i + str.substring( i ).indexOf( " " );
                if( end < i ) end = str.length();

                return str.substring( i , end );
            }
            else if( c == '{'
                    || c == ')'
                    || c == ' ' )
            {
                continue ;
            }
            else
            {
                return null;
            }
        }

        return null;
    }
}
