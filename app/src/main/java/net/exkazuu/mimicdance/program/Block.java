package net.exkazuu.mimicdance.program;

import java.util.ArrayList;
import java.util.List;

class Block implements Statement {
    final List<Statement> statements = new ArrayList<>();

    @Override
    public void unrollProgram(UnrolledProgram program, boolean forStandard) {
        for (Statement statement : this.statements) {
            statement.unrollProgram(program, forStandard);
        }
    }
}
