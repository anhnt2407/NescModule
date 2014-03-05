package br.cin.ufpe.nesc2cpn.nescModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author avld
 */
public class NescIdentifyFile
{
    public static String GENERIC = "generic";

    public static String CONFIGURATION_FILE = "configuration";
    public static String MODULE_FILE = "module";
    public static String INTERFACE_FILE = "interface";
    public static String HEADER_FILE = "header";
    
    public static String UNKNOWN_FILE = "-";

    public static String PROBABILITY_NOTATION = "//@";
    public static char LINE_NUMBER = 1;


    // ---------------------------------------- //

    private File file;

    private String filename;
    private String conteudo;
    private String type;

    public NescIdentifyFile(String filename)
    {
        this.filename = filename;
    }

    public NescIdentifyFile(File arquivo)
    {
        this.file = arquivo;
        filename = arquivo.getName();
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getType() {
        return type;
    }

    public void open() throws Exception
    {
        //Primeiro..: tirar todos os comentarios //
        //Segundo...: tirar todos os \t \r \n \v \f
        //Terceiro..: substituir todos os espaços duplos ("  ") por espaço simples (" ")
        //Quarto....: tirar todos os comentarios /**/
        validarExtensao();

        if( file == null )
        {
            file = new File( filename );
        }

        conteudo = getConteudoInArquivo( file );

        //System.out.println( "tipo: " + type  + "\n\n");
        //System.out.println( conteudo );
    }

    private void validarExtensao() throws Exception
    {
        if( "Makefile".equalsIgnoreCase( filename ) )
        {
            openMakefile();
        }
        else if( filename.indexOf(".nc") <= 0 )
        {
            throw new Exception("Formato invalido ["+ filename +"].");
        }
    }

    private void openMakefile()
    {

    }

    private String getConteudoInArquivo(File file) throws Exception
    {
        BufferedReader in = new BufferedReader( new FileReader( file ) );
        StringBuilder builder = new StringBuilder();
        String str = "";
        boolean foundType = false;
        boolean comment = false;
        long line = 0;
        
        while ((str = in.readLine()) != null)
        {
            line++;
            String linha = limparLinha( str );
            
            if( !comment )
            {
                if( linha.indexOf("/*") >= 0 && linha.indexOf("*/") >= 0 )
                {
                    linha = linha.substring( 0 , linha.indexOf("/*") );
                }
                else if(linha.indexOf("/*") >= 0 )
                {
                    linha = linha.substring( 0 , linha.indexOf("/*") );
                    comment = true;
                }
            }
            else
            {
                if( linha.indexOf("*/") >= 0 )
                {
                    linha = linha.substring( linha.indexOf("*/") + 2 );
                    comment = false;
                }
                else
                {
                    linha = "";
                }
            }

            if( linha == null ? false : !linha.trim().isEmpty() )
            {
                builder.append( LINE_NUMBER ).append( line ).append( " " );
                builder.append( linha ).append( "\n" );
            }
            
            if( !foundType && !comment )
            {
                int pos = 0;
                String[] partes = linha.split(" ");

                if( partes == null ? true : partes.length < 1 )
                {
                    continue ;
                }

                if( partes[ pos ].equalsIgnoreCase("generic") )
                {
                    pos++;
                }

                if( partes[ pos ].equalsIgnoreCase(CONFIGURATION_FILE) )
                {
                    type = CONFIGURATION_FILE;
                    foundType = true;
                }
                else if( partes[ pos ].equalsIgnoreCase(INTERFACE_FILE) )
                {
                    type = INTERFACE_FILE;
                    foundType = true;
                }
                else if( partes[ pos ].equalsIgnoreCase(MODULE_FILE) )
                {
                    type = MODULE_FILE;
                    foundType = true;
                }
                else
                {
                    type = UNKNOWN_FILE;
                    foundType = false;
                }
            }
        }

        in.close();

        if( !foundType )
        {
            throw new Exception( "Arquivo desconhecido." );
        }

        return builder.toString();
    }

    /**
     * Tirar comentarios e espacos duplos,
     * Deixa apenas dados importantes [LINHA].
     *
     */
    public static String limparLinha(String linha)
    {
        if( linha.indexOf("//@") == -1 )
        {
            //Tirar comentario do tipo barra-barra [//]
            String sp[] = linha.split("//");
            linha = sp[0];
        }

        //Tirar caracteres especiais
        linha = linha.replaceAll( "[\t\r\f]" , " " );

        //Tirar espacos duplos
        return removerEspaceDuplo( linha );
    }

    /**
     * Substitui todos os espaço duplos ("  ") por espaço simples (" ").
     *
     * @param linha     linha para ser limpa
     * @return          linha sem espaço duplo, apenas com espaço simples
     */
    public static String removerEspaceDuplo(String linha)
    {
        String newLinha = "";
        
        boolean isFistLetter = true;
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c != ' ' && c != '\t') {
                isFistLetter = false;
                newLinha += c;
            } else if (i + 1 >= linha.length()) {
                newLinha += c;
            } else if (c == ' ' && linha.charAt(i + 1) != ' ' && !isFistLetter) {
                newLinha += c;
            }
        }

        return newLinha;
    }
    
}
