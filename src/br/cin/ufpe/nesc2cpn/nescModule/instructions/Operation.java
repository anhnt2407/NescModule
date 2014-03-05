package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Operation extends Instruction
{
    private Instruction left;
    private String operation;
    private Instruction right;

    public Operation()
    {
        setType( "operation" );
    }

    public Instruction getLeft()
    {
        return left;
    }

    public void setLeft(Instruction left)
    {
        this.left = left;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public Instruction getRight()
    {
        return right;
    }

    public void setRight(Instruction right)
    {
        this.right = right;
    }

    @Override
    public String toString()
    {
        return left.toString() + " "
             + getOperation() + " "
             + right.toString();
    }
}
