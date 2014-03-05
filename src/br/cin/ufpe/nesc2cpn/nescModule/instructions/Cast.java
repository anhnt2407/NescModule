package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Cast extends Instruction
{
    private String castType;
    private Instruction inst;

    public Cast()
    {
        setType( "cast" );
    }

    public String getCastType()
    {
        return castType;
    }

    public void setCastType(String castType)
    {
        this.castType = castType;
    }

    public Instruction getInst()
    {
        return inst;
    }

    public void setInst(Instruction inst)
    {
        this.inst = inst;
    }

    @Override
    public String toString()
    {
        return "(" + getCastType() + ") " + inst.toString();
    }
}
