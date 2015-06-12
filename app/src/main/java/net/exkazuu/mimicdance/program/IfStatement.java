package net.exkazuu.mimicdance.program;

import net.exkazuu.mimicdance.interpreter.ActionType;

import java.util.HashSet;

public class IfStatement extends Statement {
    private final Block trueBlock;
    private final Block falseBlock;
    private final boolean isNormal;

    public IfStatement(Block trueBlock, Block falseBlock, boolean isNormal) {
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
        this.isNormal = isNormal;
    }

    @Override
    public void unroll(UnrolledProgram program, boolean isNormal) {
        Block statements, others;
        if (this.isNormal == isNormal) {
            statements = trueBlock;
            others = falseBlock;
        } else {
            statements = falseBlock;
            others = trueBlock;
        }
        int initialSize = program.actionSets.size();
        for (Statement statement : statements) {
            statement.unroll(program, isNormal);
        }

        int lastLineIndex = 0;
        if (program.actionSets.size() > 0) {
            lastLineIndex = program.lineIndexes.get(program.lineIndexes.size() - 1);
        }

        int otherSize = getOtherSize(others, isNormal);
        for (int i = program.actionSets.size() - initialSize; i < otherSize; i++) {
            program.actionSets.add(new HashSet<ActionType>());
            program.lineIndexes.add(lastLineIndex);
        }
    }

    private int getOtherSize(Block others, boolean isNormal) {
        UnrolledProgram program = new UnrolledProgram();
        for (Statement statement : others) {
            statement.unroll(program, isNormal);
        }
        return program.actionSets.size();
    }
}
