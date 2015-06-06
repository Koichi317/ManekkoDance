package net.exkazuu.mimicdance.program;

import com.google.common.base.Joiner;

import net.exkazuu.mimicdance.interpreter.ActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnrolledProgram {
    final List<String> lines = new ArrayList<>();
    final List<Integer> lineIndexes = new ArrayList<>();

    public String getCode() {
        return Joiner.on('\n').join(lines);
    }

    public String getLine(int lineIndex) {
        return lines.get(lineIndex);
    }

    public int getOriginalLineIndex(int lineIndex) {
        return lineIndexes.get(lineIndex);
    }

    public boolean semanticallyEquals(UnrolledProgram otherProgram) {
        int size = lines.size();
        if (size != otherProgram.lines.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            Set<ActionType> actions = ActionType.parse(lines.get(i));
            Set<ActionType> otherActions = ActionType.parse(otherProgram.lines.get(i));
            if (!actions.equals(otherActions)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return lines.size();
    }
}
