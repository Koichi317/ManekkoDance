package net.exkazuu.mimicdance.program;

import com.google.common.base.Joiner;

import net.exkazuu.mimicdance.interpreter.ActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class UnrolledProgram {
    final List<Set<ActionType>> actionSets = new ArrayList<>();
    final List<Integer> lineIndexes = new ArrayList<>();

    public String getCode() {
        StringBuilder code = new StringBuilder();
        String delimiter = "";

        for (Set<ActionType> actionSet : actionSets) {
            code.append(delimiter);
            code.append(Joiner.on("").join(new TreeSet<>(actionSet)));
            delimiter = "\n";
        }

        return code.toString();
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
