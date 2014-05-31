package net.exkazuu.ManekkoDance.command;

class ParseState {
	public StateType type;
	public Statement statement;

	public ParseState(StateType type, Statement statement) {
		this.type = type;
		this.statement = statement;
	}

	public void addStatement(Statement added) {
		switch (type) {
		case Block:
			((Block) statement).statements.add(added);
			break;
		case Else:
			((IfStatement) statement).falseStatements.add(added);
			break;
		case If:
			((IfStatement) statement).trueStatements.add(added);
			break;
		case Loop:
			((LoopStatement) statement).statements.add(added);
			break;
		default:
			break;
		}
	}
}