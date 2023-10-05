package ExerciseMachines.Core;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class RotaryTorsoMachine extends AbstractCoreExerciseMachine {
    public RotaryTorsoMachine() {
        super();
        this.name = "Rotary Torso Machine";
        this.exerciseDuration = 4;
        this.exercises = new ArrayList<>(List.of(Exercises.TORSO_ROTATION));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.CORE));
    }
}
