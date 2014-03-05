package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class InterfaceFile
{
    private Interface interf;

    public InterfaceFile()
    {
        // do nothing
    }

    public Interface getInterface()
    {
        return interf;
    }

    public void convertTo(String dados) throws Exception
    {
        interf = new Interface();

        dados = NescIdentifyFile.removerEspaceDuplo( dados.replace( '\n' , ';' ) );

        int interfStart = dados.indexOf("interface");
        int methodStart = dados.indexOf("{");
        tratarInterfaceName( dados.substring( interfStart , methodStart ) );
        
        setInterfaceLineNumber( dados.substring( 0 , interfStart) );
        
        int methodEnd  = dados.indexOf("}");
        tratarFunction( dados.substring( methodStart + 1 , methodEnd ) );

        //printInterface();
    }

    private void setInterfaceLineNumber(String dado)
    {
        int ln = dado.lastIndexOf( NescIdentifyFile.LINE_NUMBER );

        if( ln != -1  )
        {
            int firstSpace = dado.lastIndexOf( ' ' );
            int lineNumber = Integer.parseInt( dado.substring( ln + 1 , firstSpace ) );

            interf.setLineNumber( lineNumber );
        }
    }

    private void tratarInterfaceName(String dado)
    {
        String name = dado;
        String param = null;

        //System.out.println("\tinterface: " + dado );

        if( dado.indexOf("<") > 0 )
        {
            name  = dado.substring( 0 , dado.indexOf("<") ).trim();
            param = dado.substring( dado.indexOf("<") + 1 ).trim();
        }

        String[] partes = name.split( " " );
        interf.setName( partes[1] );
        
        if( param != null )
        {
            tratarParameter( param );
        }
    }

    private void tratarParameter( String param )
    {
        param = param.substring( 0 , param.indexOf(">") ).trim();

        if( param.indexOf(",") < 0 )
        {
            interf.getParameter().add( param );
            return ;
        }

        for( String p : param.split(",") )
        {
            interf.getParameter().add( param.trim() );
        }
    }

    private void tratarFunction(String dado)
    {
        String[] partes = dado.split( ";" );
        int lineNumber = 0;

        for(int i = 0; i < partes.length; i++)
        {
            if( partes[i] == null ? true : partes[i].isEmpty() )
            {
                continue ;
            }

            if( partes[i].charAt( 0 ) == NescIdentifyFile.LINE_NUMBER )
            {
                int firstSpace = partes[i].indexOf( ' ' );
                lineNumber = Integer.parseInt( partes[i].substring( 1 , firstSpace ) );

                partes[i] = partes[i].substring( firstSpace );
            }

            if( partes[i].length() < 3 )
            {
                continue ;
            }

            Function function = convertStringToFunction( partes[i] );
            function.setLineNumber( lineNumber );

            interf.getMethods().add( function );

            //System.out.println();
        }
    }

    public Function convertStringToFunction(String text)
    {
        Function function = new Function();
        function.setInstructions( null );

        int first = text.indexOf("(");
        tratarFunctionPrimeiraParte( text.substring( 0 , first ) , function );

        int second = text.indexOf(")");
        tratarFunctionSegundaParte( text.substring( first + 1 , second ) , function );

        return function;
    }

    private void tratarFunctionPrimeiraParte(String dado, Function method)
    {
        int pos = 0;
        String[] partes = dado.split(" ");

        if( partes[ pos ] == null ? true : "".equals( partes[ pos  ]  ) )
            pos++;

        if( "async".equals( partes[ pos ] ) )
        {
            pos++;
            method.setAsync( true );
        }

        method.setFunctionType( partes[ pos++ ] );
        
        if( partes[ pos - 1 ] == null ? true : "".equals( partes[ pos - 1 ]  ) )
            method.setType( partes[ pos++ ] );

        method.setReturnType( partes[ pos++ ] );
        method.setFunctionName( partes[ pos++ ] );
    }

    private void tratarFunctionSegundaParte(String dado, Function method)
    {
        if( dado == null ? true : dado.isEmpty() )
        {
            return ;
        }

        String[] partes = dado.split(",");

        for(int i = 0; i < partes.length; i++)
        {
            int pos = 0;
            String[] subPartes = partes[i].split(" ");

            String type = subPartes[ pos++ ];
            if( type == null ? true : type.isEmpty() )
                type = subPartes[ pos++ ];

            String name = subPartes[ pos++ ];

            method.getArguments().put( name , type );
        }
    }

    // ----------------------------

    public static Interface tratar(String dado) throws Exception
    {
        String newDado = "interface " + dado + "{ }";

        InterfaceFile inf = new InterfaceFile();
        inf.convertTo( newDado );

        return inf.getInterface();
    }

    // ----------------------------

    public void printInterface()
    {
        System.out.println( "interface " + interf.getName() + " {" );

        for( Function method : interf.getMethods() )
        {
            System.out.print( " " );

            if( method.isAsync() )
                System.out.print( "async " );

            System.out.print( method.getFunctionType() + " " );
            System.out.print( method.getReturnType() + " " );
            System.out.print( method.getFunctionName() + " ( " );

            Iterator<Entry<String,String>> it = method.getArguments().entrySet().iterator();
            while( it.hasNext() ){
                Entry<String,String> entry = it.next();

                System.out.print( entry.getValue() + " " + entry.getKey() );
                
                if( it.hasNext() ) System.out.print(" , ");
                else System.out.print(" ");
            }

            System.out.println(");");
        }

        System.out.println( "}" );
    }

}