package ExerciseMachines.Back;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class LateralPulldownMachine extends AbstractBackExerciseMachine {
    public LateralPulldownMachine() {
        super();
        this.name = "Lateral Pulldown Machine";
        this.exerciseDuration = 6;
        this.exercises = new ArrayList<>(List.of(Exercises.WIDE_GRIP_LAT_PULLDOWNS));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.ARM));
    }

    @Override
    public void changeLoad(int difference) {
        if (difference == 0) return;

        if (currentLoad + difference < minLoad ||
                currentLoad + difference > maxLoad
        ) {
            System.out.println("[ INVALID DIFFERENCE VALUE ]");
            return;
        }

        currentLoad += difference;
    }
}
