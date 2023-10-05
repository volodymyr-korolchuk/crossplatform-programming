package ExerciseMachines.Back;

import Enums.Exercises;
import Enums.Parameters;
import ExerciseMachines.AbstractExerciseMachine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBackExerciseMachine extends AbstractExerciseMachine {
    protected int currentLoad;
    protected int minLoad;
    protected int maxLoad;

    public AbstractBackExerciseMachine() {
        super();
        this.minLoad = 0;
        this.maxLoad = 250;
        this.currentLoad = 0;
        this.parametersImproved = new ArrayList<>(List.of(Parameters.ENDURANCE, Parameters.STRENGTH));
    }

    @Override
    public void perform(Exercises exercise) {
        System.out.printf("Performing %s on a %s...%n", exercise.name(), this.name);
    }

    public abstract void changeLoad(int difference);

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public int getMinLoad() {
        return minLoad;
    }
}
