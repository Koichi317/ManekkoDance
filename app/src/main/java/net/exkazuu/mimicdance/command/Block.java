package net.exkazuu.mimicdance.command;

import java.util.ArrayList;
import java.util.List;

class Block implements Statement {
    public List<Statement> statements = new ArrayList<Statement>();

    public void createExpandedCommands(List<String> result, boolean isLeft) {
        for (Statement statement : statements) {
            statement.createExpandedCommands(result, isLeft);
        }
    }

    @Override
    public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
        for (Statement statement : statements) {
            statement.createExpandedLineNumbers(result, isLeft);
        }
    }
}
