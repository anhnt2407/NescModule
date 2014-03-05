package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.NescIdentifyFile;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author avld
 */
public class CreatorFactory
{
    private static CreatorFactory instance;             //Singleton
    private Map<String,Creator> creatorMap;             //Creator's
    
    private CreatorFactory()
    {
        init();
    }

    public static CreatorFactory getInstance()
    {
        if( instance == null )
        {
            instance = new CreatorFactory();
        }

        return instance;
    }

    private void init()
    {
        creatorMap = new LinkedHashMap<String, Creator>();

        addCreator( PreDeployCreator.class );
        addCreator( StructCreator.class );
        addCreator( EnumCreator.class );
        addCreator( ParenteseCreator.class );
        addCreator( CastCreator.class );
        addCreator( SpecialWordCreator.class );
        addCreator( AtomicCreator.class );
        addCreator( WhileCreator.class );
        addCreator( DoWhileCreator.class );
        addCreator( ForCreator.class );
        addCreator( SwitchCreator.class );
        addCreator( CaseCreator.class );
        addCreator( DefaultCreator.class );
        addCreator( IfElseCreator.class );
        addCreator( InvokeCreator.class );
        addCreator( IncrementalCreator.class );
        addCreator( DecrementalCreator.class );
        addCreator( AssignCreator.class );
        addCreator( VariableCreator.class );
        addCreator( ValueCreator.class );
    }

    private void addCreator(Class creatorClass)
    {
        try
        {
            Creator creatorObj = (Creator) creatorClass.newInstance();
            creatorMap.put( creatorObj.getType().toLowerCase() , creatorObj );
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }
    }

    public Instruction convertTo(String line)
    {
        if( line == null ? true : line.isEmpty() )
        {
            //throw new Exception("Line invalid, it is null or empty.");
            System.out.println("Line invalid, it is null or empty.");
            return null;
        }

        //line = line.trim();

        int lineNumber = CreatorFactory.getInstance().getLineNumber( line );
        String tempSLN = CreatorFactory.getInstance().removeLineNumber( line );


        for( Creator creator : creatorMap.values() )
        {
            if( creator.identify( tempSLN ) )
            {
                Instruction inst = creator.convertTo( tempSLN );
                inst.setLineNumber( lineNumber );

                return inst;
            }
        }

        //throw new Exception("Instruction is not identify.");
        System.out.println("Instruction is not identify.");
        return null;
    }

    public Instruction convertToSimple(String line)
    {
        if( line == null ? true : line.isEmpty() )
        {
            //throw new Exception("Line invalid, it is null or empty.");
            return null;
        }

        //line = line.trim();
        
        int lineNumber = CreatorFactory.getInstance().getLineNumber( line );
        String tempSLN = CreatorFactory.getInstance().removeLineNumber( line );

        if( tempSLN.endsWith(";") )
        {
            tempSLN = tempSLN.substring( 0 , tempSLN.length() - 1 );
        }

        OperationCreator opCreator = new OperationCreator();
        if( opCreator.identify( tempSLN ) )
        {
            Instruction inst = opCreator.convertTo( tempSLN );
            inst.setLineNumber( lineNumber );

            return inst;
        }

        for( Creator creator : creatorMap.values() )
        {
            if( creator instanceof ComposedCreator )
            {
                continue ;
            }
            else if( creator.identify( tempSLN ) )
            {
                Instruction inst = creator.convertTo( tempSLN );
                inst.setLineNumber( lineNumber );

                return inst;
            }
        }

        //throw new Exception("Instruction is not identify.");
        System.out.println( "Instruction is not identify ["+ tempSLN +"]." );
        return null;
    }
    
    public void addInstruction( ComposedInstruction parent , String line )
    {
        if( line == null ? true : line.isEmpty() )
        {
            //throw new Exception("Line invalid, it is null or empty.");
            return ;
        }

        int lineNumber = CreatorFactory.getInstance().getLineNumber( line );
        String tempSLN = CreatorFactory.getInstance().removeLineNumber( line );

        for( Creator creator : creatorMap.values() )
        {
            if( creator.identify( tempSLN ) )
            {
                Instruction inst = creator.addInstruction( parent , tempSLN );
                inst.setLineNumber( lineNumber );

                return ;
            }
        }

        //throw new Exception("Instruction is not identify.");
    }

    public Creator get(String type)
    {
        return creatorMap.get( type );
    }

    public int getLineNumber(String dado)
    {
        if( dado.charAt( 0 ) == NescIdentifyFile.LINE_NUMBER )
        {
            int firstSpace = dado.indexOf( ' ' );
            int lineNumber = Integer.parseInt( dado.substring( 1 , firstSpace ) );

            return lineNumber;
        }

        return 0;
    }

    public String removeLineNumber(String dado)
    {
        if( dado == null ? true : dado.isEmpty() )
        {
            return dado;
        }

        if( dado.charAt( 0 ) == NescIdentifyFile.LINE_NUMBER )
        {
            int firstSpace = dado.indexOf( ' ' );

            if( firstSpace == -1 )
            {
                return dado;
            }

            return dado.substring( firstSpace ).trim();
        }

        return dado.trim();
    }
}
