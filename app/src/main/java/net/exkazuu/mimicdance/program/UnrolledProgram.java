package net.exkazuu.mimicdance.program;

import com.google.common.base.Joiner;

import net.exkazuu.mimicdance.interpreter.ActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnrolledProgram {
    final List<Set<ActionType>> actionSets = new ArrayList<>();
    final List<Integer> lineIndexes = new ArrayList<>();

    public String getCode() {
        return Joiner.on('\n').join(actionSets);
    }

    public Set<ActionType> getActionSet(int lineIndex) {
        return actionSets.get(lineIndex);
    }

    public int getOriginalLineIndex(int lineIndex) {
        return lineIndexes.get(lineIndex);
    }

    public int countDifferences(UnrolledProgram otherProgram) {
        int size = Math.min(actionSets.size(), otherProgram.actionSets.size());
        int diffCount = 0;
        for (int i = 0; i < size; i++) {
            Set<ActionType> actions = actionSets.get(i);
            Set<ActionType> otherActions = otherProgram.actionSets.get(i);
            if (!actions.equals(otherActions)) {
                diffCount++;
            }
        }
        return diffCount + Math.max(actionSets.size(), otherProgram.actionSets.size()) - size;
    }

    public int size() {
        return actionSets.size();
    }
}
