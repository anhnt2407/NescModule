package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Struct;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Variable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Arquivos com extensão ".h"
 *
 * @author avld
 */
public class HeaderFile extends ProgramBodyFile
{
    private Module module;

    public HeaderFile()
    {
        // do nothing
    }

    public Module getModule()
    {
        return module;
    }

    public void convertTo(String dados) throws Exception
    {
        module = new Module();
        treatBody( dados );
    }

    @Override
    public void addVariable(Variable var)
    {
        module.getVariables().put( var.getVariableName() , var );
    }

    @Override
    public void addTypeDefined(Instruction instruction)
    {
        String name = null;

        if( instruction instanceof Struct )
        {
            name = ((Struct) instruction).getName();
        }
        else if( instruction instanceof br.cin.ufpe.nesc2cpn.nescModule.instructions.Enum )
        {
            //TODO: ve depois!
            //name = ((br.cin.ufpe.nesc2cpn.nescModule.instructions.Enum) instruction).getText();
            name = "enum";
        }

        module.getTypeDefined().put( name , instruction );
    }

    @Override
    public void addFunction(Function function)
    {
        module.getFunctions().add( function );
    }

    private static String getConteudoInArquivo(File file) throws Exception
    {
        BufferedReader in = new BufferedReader( new FileReader( file ) );
        StringBuilder builder = new StringBuilder();
        String str = "";
        boolean foundType = false;
        boolean comment = false;

        while ((str = in.readLine()) != null)
        {
            String linha = NescIdentifyFile.limparLinha( str );

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

            builder.append( linha ).append("\n");
        }

        in.close();

        return builder.toString();
    }
    
}
