package br.cin.ufpe.nesc2cpn.nescModule.creator;

/**
 *
 * @author avld
 */
public class DefaultCreator extends CaseCreator
{
    public final static String DEFAULT  = "default";

    public DefaultCreator()
    {
        DefaultCreator.CASE = DEFAULT;
    }

    @Override
    public String getType()
    {
        return "default";
    }

}
