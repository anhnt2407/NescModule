package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 * Esta classe representa todos os valores atribuindos
 * em variaveis ou em métodos.
 * 
 * ex. 
 * (1) var = "processo";
 * (2) call sqrt( 123 , 2 );
 *
 * @author avld
 */
public class Value extends Instruction
{
    private String valueType;
    private String value;
    
    public Value()
    {
        setType( "value" );
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public boolean isPointer()
    {
        if( value == null )
        {
            return false;
        }
        else if(value.indexOf("[") > 0)
        {
            return true;
        }
        else if(value.indexOf("*") >= 0)
        {
            return true;
        }

        return false;
    }

    public boolean isNumber()
    {
        // cria um array de char
        char[] c = getValue().trim().toCharArray();
        boolean d = true;

        for ( int i = 0; i < c.length; i++ )
        {
            // verifica se o char não é um dígito
            if ( !Character.isDigit( c[ i ] ) )
            {
                d = false;
                break ;
            }
        }
    
        return d;
    }

    public boolean isVariable()
    {
        return Character.isLetter( getValue().trim().charAt( 0 ) );
    }

    public void setValueType(String type)
    {
        this.valueType = type;
    }

    public String getValueType()
    {
        return valueType;
    }

    public String defineValueType()
    {
        if( getValue() == null )
        {
            return "";
        }
        else if( getValue().charAt(0) == '"' )
        {
            return "char*";
        }
        else if( getValue().charAt(0) == '\'' )
        {
            return "char";
        }
        else if( getValue().indexOf( "f" ) > 0 )
        {
            return "float";
        }
        else if( getValue().indexOf( "." ) > 0 )
        {
            return "double";
        }
        else
        {
            return "int";
        }
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}