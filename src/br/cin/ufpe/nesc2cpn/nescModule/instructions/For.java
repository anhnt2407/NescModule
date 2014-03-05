package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class For extends ComposedInstruction {
    private Instruction part01;
    private Instruction part02;
    private Instruction part03;

    private int interationNumber = 10;

    public For()
    {
        setType("for");
    }

    public int getInterationNumber() {
        return interationNumber;
    }

    public void setInterationNumber(int interationNumber) {
        this.interationNumber = interationNumber;
    }

    public Instruction getPart01()
    {
        return part01;
    }

    public void setPart01(Instruction part01)
    {
        this.part01 = part01;
    }

    public Instruction getPart02()
    {
        return part02;
    }

    public void setPart02(Instruction part02)
    {
        this.part02 = part02;
    }

    public Instruction getPart03()
    {
        return part03;
    }

    public void setPart03(Instruction part03)
    {
        this.part03 = part03;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "for( " );
        builder.append( getPart01() ).append( " ; " );
        builder.append( getPart02() ).append( " ; " );
        builder.append( getPart03() ).append( " ){\n" );

        builder.append( super.toString() );

        builder.append( "}" );

        return builder.toString();
    }
}
