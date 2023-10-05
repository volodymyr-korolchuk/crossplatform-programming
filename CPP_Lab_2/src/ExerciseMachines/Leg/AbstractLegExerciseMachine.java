package ExerciseMachines.Leg;

import Enums.*;
import ExerciseMachines.AbstractExerciseMachine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLegExerciseMachine extends AbstractExerciseMachine {
    public AbstractLegExerciseMachine() {
        super();
        this.parametersImproved = new ArrayList<>(List.of(Parameters.FLEXIBILITY, Parameters.ENDURANCE));
    }

}
