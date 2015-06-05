package net.exkazuu.mimicdance.program;

public class Action extends Statement {
    private final String line;
    private final int lineIndex;

    public Action(String line, int lineIndex) {
        this.line = line;
        this.lineIndex = lineIndex;
    }

    @Override
    public void unroll(UnrolledProgram program, boolean isNormal) {
        program.lines.add(line);
        program.lineIndexes.add(lineIndex);
    }
}
