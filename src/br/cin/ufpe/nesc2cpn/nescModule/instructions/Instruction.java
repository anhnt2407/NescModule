package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Instruction {
    public final static char INSTRUCTION_FINAL = ';';
    public final static char BLOCK_START = '{';
    public final static char BLOCK_FINAL = '}';

    private int lineNumber;
    private String type;
    private String text;

    public Instruction()
    {
        // do nothing
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int line) {
        this.lineNumber = line;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
