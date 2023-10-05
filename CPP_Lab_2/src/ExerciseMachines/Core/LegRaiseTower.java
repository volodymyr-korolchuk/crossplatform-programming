package ExerciseMachines.Core;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class LegRaiseTower extends AbstractCoreExerciseMachine {
    public LegRaiseTower() {
        super();
        this.name = "Leg Raise Tower";
        this.exerciseDuration = 4;
        this.exercises = new ArrayList<>(List.of(Exercises.LEG_RAISES));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.CORE));
    }
}
