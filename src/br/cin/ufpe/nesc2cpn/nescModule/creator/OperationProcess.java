package br.cin.ufpe.nesc2cpn.nescModule.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author avld
 */
public class OperationProcess
{
    private List<String> partList;

    private List<String> logicList;
    private List<String> relacionalList;
    private List<String> otherList;

    private int opIndex;

    public OperationProcess(String line)
    {
        logicList = Arrays.asList( OperationCreator.OPERATION_LOGICO );
        relacionalList = Arrays.asList( OperationCreator.OPERATION_RELACIONAL );

        otherList = new ArrayList<String>();
        otherList.addAll( Arrays.asList( OperationCreator.OPERATION_MATH ) );
        otherList.addAll( Arrays.asList( OperationCreator.OPERATION_BIT )  );

        init( line );
    }

    private void init(String line)
    {
        partList = new LinkedList<String>();
        partList.add( new String() );

        int counter = 0;
        for( int i = 0; i < line.length(); i++ )
        {
            char c = line.charAt( i );

            if( c == '(' )
            {
                String subLine = line.substring( i );
                int finish = Creator.finishParentese( subLine );

                if( finish > 0 )
                {
                    String part = partList.get( counter );
                    part += subLine.subSequence( 0 , finish + 1 );
                    partList.set( counter , part );

                    i += finish;

                    partList.add( new String() );
                    counter++;

                    continue ;
                }
            }
            if( c == ' ' )
            {
                if( !partList.get( counter ).isEmpty() )
                {
                    partList.add( new String() );
                    counter++;
                }

                continue ;
            }

            String part = partList.get( counter );
            part += c;
            partList.set( counter , part );
        }

        opIndex = foundOperation();
    }

    public List<String> getList()
    {
        return partList;
    }

    public String get(int i)
    {
        return partList.get( i );
    }

    // --------------------------------- //

    public boolean haveOperation()
    {
        return opIndex != -1;
    }

    public String getOperation()
    {
        return get( opIndex );
    }

    public String getLeft()
    {
        return remount( 0 , opIndex );
    }

    public String getRigth()
    {
        return remount( opIndex + 1 , partList.size() );
    }

    // --------------------------------- //

    private int foundOperation()
    {
        int result = foundOperation( logicList );
        if( result > 0 ) return result;

        result = foundOperation( relacionalList );
        if( result > 0 ) return result;

        result = foundOperation( otherList );
        return result;
    }

    private int foundOperation(List<String> opList)
    {
        for( int i = 0; i < partList.size(); i++ )
        {
            String part = partList.get( i );

            if( opList.contains( part ) )
            {
                return i;
            }
        }

        return -1;
    }

    private String remount( int start , int end )
    {
        StringBuilder builder = new StringBuilder();

        for(int i = start; i < end; i++)
        {
            builder.append( partList.get( i ) );
            builder.append( " " );
        }

        return builder.toString().trim();
    }
}
