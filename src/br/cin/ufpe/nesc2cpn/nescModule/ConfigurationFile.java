package br.cin.ufpe.nesc2cpn.nescModule;

import java.util.Map.Entry;

/**
 * Cria um objeto do tipo Configuration e atribuir os dados de um .NC
 * neste objeto.
 *
 * @author avld
 */
public class ConfigurationFile
{
    private Configuration conf;

    public ConfigurationFile()
    {
        
    }

    public Configuration getConfiguration()
    {
        return conf;
    }

    public void convertTo(String dados) throws Exception
    {
        conf = new Configuration();

        int confStart = dados.indexOf("configuration");
        int implStart =  dados.indexOf("implementation");

        tratarPrimeiraParte( dados.substring( confStart , implStart ) );

        tratarSegundaParte( dados.substring( implStart ) );

        //printConfiguration();
    }

    /**
     * Identificar as interfaces providas pelo arquivo de configuração
     *
     * @param dado
     */
    private void tratarPrimeiraParte(String dado)
    {
        dado = dado.replace( ';' , ' ' );
        dado = NescIdentifyFile.removerEspaceDuplo( dado.replace( '\n' , ' ' ) );

        String[] partes = dado.trim().split(" ");
        for(int i = 0; i < partes.length; i++ )
        {
            //Precisa Indicar a linha

            if( "configuration".equals( partes[i] ) )       // atribuir nome do Configuration
            {
                conf.setName( partes[++i] );
            }
            else if( "interface".equals( partes[i] ) )      // Identificou uma nova Interfaces que prove
            {
                String iName = partes[ ++i ];               // similar a: String iName = partes[ i + 1 ]; i = 1 + 1;
                String iNick = iName;
                
                if( "as".equals( partes[ i + 1 ] ) )        // Interface as Apelido
                {
                    i += 2;
                    iNick = partes[i];
                }

                conf.getInterfaceProviders().put( iNick , iName );
            }
        }
    }

    /**
     * Identificar quais são os interfaces e modulos foram utilizados
     *
     * @param dado
     */
    private void tratarSegundaParte(String dado)
    {
        dado = dado.replace( "," , " components " );
        dado = dado.replace( ';' , ' ' );
        dado = NescIdentifyFile.removerEspaceDuplo( dado.replace( '\n' , ' ' ) );

        String[] partes = dado.trim().split(" ");
        for(int i = 0; i < partes.length; i++ )
        {
            if( "components".equals( partes[i] ) )          // Identificou Componentes
            {
                String iName = partes[ ++i ];               // similar a: String iName = partes[ i + 1 ]; i = 1 + 1;
                if( "new".equals( iName ) ) iName = partes[ ++i ];

                String iNick = iName;

                if( "as".equals( partes[ i + 1 ] ) )
                {
                    i += 2;
                    iNick = partes[i];
                }

                conf.getModules().put( iNick , iName );
            }
            else if( partes[i].indexOf("=") >= 0 ||
                     partes[i].indexOf("->") >= 0 ||
                     partes[i].indexOf("<-") >= 0 )
            {
                String link = partes[i - 1];
                link += " " + partes[i];
                link += " " + partes[++i];
                
                conf.getLinks().add( new ConfigurationLink( link ) );
            }
            
        }
    }

    /**
     * Imprimir na tela um objeto de Configuration
     *
     */
    public void printConfiguration()
    {
        System.out.println( "configuration " + conf.getName() + "{" );

        for( Entry<String,String> entry : conf.getInterfaceProviders().entrySet() ){
            System.out.print( "  provides interface " + entry.getValue() );

            if( entry.getKey().equals( entry.getValue() ) ) System.out.println( ";");
            else System.out.println( " as " + entry.getKey() + ";" );
        }

        System.out.println( "}" );

        // --------------------------------------------------------

        System.out.println( "implemention {" );

        for( Entry<String,String> entry : conf.getModules().entrySet() ){
            System.out.print( "  components " );
            if( entry.getValue().indexOf("()") > 0 ) System.out.print( "new " );
            System.out.print( entry.getValue() );

            if( entry.getKey().equals( entry.getValue() ) ) System.out.println( ";");
            else System.out.println( " as " + entry.getKey() + ";" );
        }

        System.out.println();

        for( ConfigurationLink link : conf.getLinks() ){
            System.out.print( "  " + link.getPreModule() );
            System.out.print( " " + link.getSymbol() );
            System.out.print( " " + link.getPosModule() );
            System.out.println( ";" );
        }

        System.out.println( "}" );
    }
}
