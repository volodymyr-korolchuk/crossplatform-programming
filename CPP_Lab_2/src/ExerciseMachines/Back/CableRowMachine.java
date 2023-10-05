package ExerciseMachines.Back;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class CableRowMachine extends AbstractBackExerciseMachine {
    public CableRowMachine() {
        super();
        this.name = "Cable Row Machine";
        this.exerciseDuration = 10;
        this.exercises = new ArrayList<>(List.of(Exercises.SEATED_CABLE_ROW));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.ARM, MuscleGroups.BACK));
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
