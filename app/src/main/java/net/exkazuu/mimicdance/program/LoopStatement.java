package net.exkazuu.mimicdance.program;

import java.util.ArrayList;
import java.util.List;

class LoopStatement implements Statement {
    final List<Statement> statements = new ArrayList<>();
    private final int loopCount;

    public LoopStatement(int loopCount) {
        this.loopCount = loopCount;
    }

    @Override
    public void unrollProgram(UnrolledProgram program, boolean forStandard) {
        for (int i = 0; i < loopCount; i++) {
            for (Statement statement : statements) {
                statement.unrollProgram(program, forStandard);
            }
        }
    }
}
