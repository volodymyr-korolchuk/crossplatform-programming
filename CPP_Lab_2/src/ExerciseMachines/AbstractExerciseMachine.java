package ExerciseMachines;

import Enums.*;
import java.util.ArrayList;

public abstract class AbstractExerciseMachine {

    protected String name;

    protected ArrayList<MuscleGroups> musclesBenefited;

    protected ArrayList<Parameters> parametersImproved;

    protected ArrayList<Exercises> exercises;

    protected int exerciseDuration;

    public ArrayList<Exercises> getExercises() {
        return exercises;
    }

    public abstract void perform(Exercises exercise);

    public void setExerciseDuration(int exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Parameters> getParametersImproved() {
        return parametersImproved;
    }

    public ArrayList<MuscleGroups> getMusclesBenefited() {
        return musclesBenefited;
    }
}

