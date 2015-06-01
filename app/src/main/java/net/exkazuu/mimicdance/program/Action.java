package net.exkazuu.mimicdance.program;

class Action implements Statement {
    private final String line;
    private final int lineIndex;

    public Action(String line, int lineIndex) {
        this.line = line;
        this.lineIndex = lineIndex;
    }

    @Override
    public void unrollProgram(UnrolledProgram program, boolean forStandard) {
        program.lines.add(line);
        program.lineIndexes.add(lineIndex);
    }
}
