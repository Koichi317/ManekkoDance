package net.exkazuu.mimicdance.program;

public abstract class Statement {
    protected abstract void unroll(UnrolledProgram program, boolean isNormal);

    public UnrolledProgram unroll(boolean isNormal) {
        UnrolledProgram program = new UnrolledProgram();
        unroll(program, isNormal);
        return program;
    }
}
