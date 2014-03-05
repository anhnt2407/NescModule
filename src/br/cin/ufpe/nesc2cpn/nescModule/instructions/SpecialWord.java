package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class SpecialWord extends Instruction {
    private String word;

    public SpecialWord()
    {
        setType("special_word");
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString()
    {
        return getWord();
    }
}
