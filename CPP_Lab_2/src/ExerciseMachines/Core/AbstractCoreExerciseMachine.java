package ExerciseMachines.Core;

import Enums.Exercises;
import Enums.Parameters;
import ExerciseMachines.AbstractExerciseMachine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCoreExerciseMachine extends AbstractExerciseMachine {
    public AbstractCoreExerciseMachine() {
        super();
        this.parametersImproved = new ArrayList<>(List.of(Parameters.WEIGHT_LOSS, Parameters.STRENGTH));
    }

    @Override
    public void perform(Exercises exercise) {
        System.out.printf("Performing %s on a %s...%n", exercise.name(), this.name);
    }

}
