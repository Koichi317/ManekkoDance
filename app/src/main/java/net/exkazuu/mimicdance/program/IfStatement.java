package net.exkazuu.mimicdance.program;

import java.util.ArrayList;
import java.util.List;

class IfStatement implements Statement {
    final List<Statement> trueStatements = new ArrayList<>();
    final List<Statement> falseStatements = new ArrayList<>();
    private final boolean forPiyo;

    public IfStatement(boolean forPiyo) {
        this.forPiyo = forPiyo;
    }

    @Override
    public void unrollProgram(UnrolledProgram program, boolean forStandard) {
        List<Statement> statements, others;
        if (this.forPiyo == forStandard) {
            statements = trueStatements;
            others =  falseStatements;
        }
        else {
            statements = falseStatements;
            others =  trueStatements;
        }
        int initialSize = program.lines.size();
        for (Statement statement : statements) {
            statement.unrollProgram(program, forStandard);
        }

        int lastLineIndex = 0;
        if (program.lines.size() > 0) {
            lastLineIndex = program.lineIndexes.get(program.lineIndexes.size() - 1);
        }

        int otherSize = getOtherSize(others, forStandard);
        for (int i = program.lines.size() - initialSize; i < otherSize; i++) {
            program.lines.add("");
            program.lineIndexes.add(lastLineIndex);
        }
    }

    private int getOtherSize(List<Statement> others, boolean isLeft) {
        UnrolledProgram program = new UnrolledProgram();
        for (Statement statement : others) {
            statement.unrollProgram(program, isLeft);
        }
        return program.lines.size();
    }
}
