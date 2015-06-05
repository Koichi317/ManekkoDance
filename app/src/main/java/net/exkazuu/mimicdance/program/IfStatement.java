package net.exkazuu.mimicdance.program;

public class IfStatement extends Statement {
    private final Block trueBlock;
    private final Block falseBlock;
    private final boolean standard;

    public IfStatement(Block trueBlock, Block falseBlock, boolean standard) {
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
        this.standard = standard;
    }

    @Override
    public void unroll(UnrolledProgram program, boolean isNormal) {
        Block statements, others;
        if (this.standard == isNormal) {
            statements = trueBlock;
            others = falseBlock;
        } else {
            statements = falseBlock;
            others = trueBlock;
        }
        int initialSize = program.lines.size();
        for (Statement statement : statements) {
            statement.unroll(program, isNormal);
        }

        int lastLineIndex = 0;
        if (program.lines.size() > 0) {
            lastLineIndex = program.lineIndexes.get(program.lineIndexes.size() - 1);
        }

        int otherSize = getOtherSize(others, isNormal);
        for (int i = program.lines.size() - initialSize; i < otherSize; i++) {
            program.lines.add("");
            program.lineIndexes.add(lastLineIndex);
        }
    }

    private int getOtherSize(Block others, boolean standard) {
        UnrolledProgram program = new UnrolledProgram();
        for (Statement statement : others) {
            statement.unroll(program, standard);
        }
        return program.lines.size();
    }
}
