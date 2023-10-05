package Managers;

import ExerciseMachines.AbstractExerciseMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExerciseProgramManager {
    private static ArrayList<AbstractExerciseMachine> selectedMachines;

    public ExerciseProgramManager(ArrayList<AbstractExerciseMachine> machines) {
        selectedMachines = machines;
    }

    private static class StaticCustomComparator implements Comparator<AbstractExerciseMachine> {
        @Override
        public int compare(AbstractExerciseMachine first, AbstractExerciseMachine second) {
            return first.getName().compareTo(second.getName());
        }
    }

    private class CustomComparator implements Comparator<AbstractExerciseMachine> {
        @Override
        public int compare(AbstractExerciseMachine first, AbstractExerciseMachine second) {
            return first.getExercises().get(0).name().compareTo(second.getExercises().get(0).name());
        }
    }

    public void sortByNameAscending() {
        Collections.sort(selectedMachines, new StaticCustomComparator());
    }

    public void sortByNameDescending() {
        Collections.sort(selectedMachines, new StaticCustomComparator().reversed());
    }

    public void sortByExerciseNameAscending() {
        Collections.sort(selectedMachines, new CustomComparator());
    }

    public void sortByExerciseNameDescending() {
        Collections.sort(selectedMachines, new CustomComparator().reversed());
    }

    public void sortByMuscleGroupAscending() {
        Collections.sort(selectedMachines, new Comparator<AbstractExerciseMachine>() {
            @Override
            public int compare(AbstractExerciseMachine first, AbstractExerciseMachine second) {
                return first.getMusclesBenefited().get(0).ordinal() - second.getMusclesBenefited().get(0).ordinal();
            }
        });
    }

    public void sortByMuscleGroupDescending() {
        Collections.sort(selectedMachines, new Comparator<AbstractExerciseMachine>() {
            @Override
            public int compare(AbstractExerciseMachine first, AbstractExerciseMachine second) {
                return first.getMusclesBenefited().get(0).ordinal() - second.getMusclesBenefited().get(0).ordinal();
            }
        }.reversed());
    }

    public void sortByParametersAscending() {
        Collections.sort(selectedMachines, (first, second) ->
            first.getMusclesBenefited().get(0).ordinal() - second.getMusclesBenefited().get(0).ordinal()
        );
    }

    public void sortByParametersDescending() {
        Collections.sort(selectedMachines, (first, second) ->
            first.getMusclesBenefited().get(0).ordinal() - second.getMusclesBenefited().get(0).ordinal()
        );
        Collections.reverse(selectedMachines);
    }

    public ArrayList<AbstractExerciseMachine> getSelectedMachines() {
        return selectedMachines;
    }
}
