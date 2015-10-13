package net.exkazuu.mimicdance.program;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Block extends Statement implements Iterable<Statement> {
    private final List<Statement> statements;

    public Block() {
        this.statements = new ArrayList<>();
    }

    public Block(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void unroll(UnrolledProgram program, boolean isNormal) {
        for (Statement statement : statements) {
            statement.unroll(program, isNormal);
        }
    }

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }
}
