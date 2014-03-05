package br.cin.ufpe.nesc2cpn.nescModule;

/**
 *
 * @author avld
 */
public class ConfigurationLink {
    public static final String EQUAL = "=";
    public static final String LEFT = "->";
    public static final String RIGHT = "<-";

    private String preModule;
    private String preInterface;
    private String symbol;
    private String posModule;
    private String posInterface;

    public ConfigurationLink()
    {
        // do nothing
    }

    public ConfigurationLink(String link)
    {
        init( link );
    }

    // ----------------------------------
    // ----------------------------------
    // ----------------------------------
    // ----------------------------------

    private void init(String link)
    {
        String[] parts = link.split(" ");
        setSymbol( parts[1] );
        
        String[] pre = processPart( parts[0] );
        setPreModule( pre[0] );
        setPreInterface( pre[1] );
        
        String[] pos = processPart( parts[2] );
        setPosModule( pos[0] );
        setPosInterface( pos[1] );

        // --------------- //

        if( getPreInterface() == null ? true : getPreInterface().isEmpty() )
        {
            setPreInterface( getPosInterface() );
        }
        else if(getPosInterface() == null ? true : getPosInterface().isEmpty() )
        {
            setPosInterface( getPreInterface() );
        }
    }

    private String[] processPart(String part)
    {
        String result[] = new String[2];
        result[0] = part;
        result[1] = null;

        if( part.indexOf(".") > 0 )
        {
            result[0] = part.substring( 0 , part.indexOf(".") );
            result[1] = part.substring( part.indexOf(".") + 1 );
        }

        return result;
    }

    // ----------------------------------
    // ----------------------------------
    // ----------------------------------
    // ----------------------------------

    public String getPosModule() {
        return posModule;
    }

    public void setPosModule(String posModule) {
        this.posModule = posModule;
    }

    public String getPosInterface() {
        return posInterface;
    }

    public void setPosInterface(String posInterface) {
        this.posInterface = posInterface;
    }

    public String getPreModule() {
        return preModule;
    }

    public void setPreModule(String preModule) {
        this.preModule = preModule;
    }

    public String getPreInterface() {
        return preInterface;
    }

    public void setPreInterface(String preInterface) {
        this.preInterface = preInterface;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
}
