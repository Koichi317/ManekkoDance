package net.exkazuu.mimicdance.command;

import java.util.ArrayList;
import java.util.List;

class IfStatement implements Statement {
    public List<Statement> trueStatements = new ArrayList<>();
    public List<Statement> falseStatements = new ArrayList<>();
    public boolean isLeftCondition;

    public IfStatement(boolean isLeftCondition) {
        this.isLeftCondition = isLeftCondition;
    }

    public void createExpandedCommands(List<String> result, boolean isLeft) {
        List<Statement> statements, others;
        if (isLeftCondition == isLeft) {
            statements = trueStatements;
            others =  falseStatements;
        }
        else {
            statements = falseStatements;
            others =  trueStatements;
        }
        int initialSize = result.size();
        for (Statement statement : statements) {
            statement.createExpandedCommands(result, isLeft);
        }
        int otherSize = getOtherSize(others, isLeft);
        for (int i = result.size() - initialSize; i < otherSize; i++) {
            result.add("");
        }
    }

    @Override
    public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
        List<Statement> statements, others;
        if (isLeftCondition == isLeft) {
            statements = trueStatements;
            others =  falseStatements;
        }
        else {
            statements = falseStatements;
            others =  trueStatements;
        }
        int initialSize = result.size();
        for (Statement statement : statements) {
            statement.createExpandedLineNumbers(result, isLeft);
        }

        int lastNumber = 0;
        if (result.size() > 0) {
            lastNumber = result.get(result.size() - 1);
        }

        int otherSize = getOtherSize(others, isLeft);
        for (int i = result.size() -initialSize; i < otherSize; i++) {
            result.add(lastNumber);
        }
    }

    private int getOtherSize(List<Statement> others, boolean isLeft) {
        List<String> ignoredResult = new ArrayList<>();
        for (Statement statement : others) {
            statement.createExpandedCommands(ignoredResult, isLeft);
        }
        return ignoredResult.size();
    }
}
