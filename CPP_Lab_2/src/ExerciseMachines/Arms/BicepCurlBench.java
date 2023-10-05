package ExerciseMachines.Arms;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class BicepCurlBench extends AbstractArmExerciseMachine {
    public BicepCurlBench() {
        super();
        this.name = "Bicep Curl Bench";
        this.exerciseDuration = 5;
        this.exercises = new ArrayList<>(List.of(
            Exercises.BANDED_TRICEPS_EXTENSION,
            Exercises.BICEP_CURL,
            Exercises.DINNER_CURL)
        );
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.ARM));
    }
}
