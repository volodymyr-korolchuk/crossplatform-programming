package ExerciseMachines.Arms;

import Enums.Exercises;
import Enums.Parameters;
import ExerciseMachines.AbstractExerciseMachine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArmExerciseMachine extends AbstractExerciseMachine {
    public AbstractArmExerciseMachine() {
        super();
        this.parametersImproved = new ArrayList<>(List.of(Parameters.FLEXIBILITY, Parameters.STRENGTH));
    }

    @Override
    public void perform(Exercises exercise) {
        System.out.printf("Performing %s on a %s...%n", exercise.name(), this.name);
    }
}


