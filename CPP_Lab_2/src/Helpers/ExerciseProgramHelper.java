package Helpers;

import Enums.Exercises;
import ExerciseMachines.AbstractExerciseMachine;
import ExerciseMachines.Arms.BicepCurlBench;
import ExerciseMachines.Arms.SeatedDipMachine;
import ExerciseMachines.Back.CableRowMachine;
import ExerciseMachines.Back.LateralPulldownMachine;
import ExerciseMachines.Core.LegRaiseTower;
import ExerciseMachines.Core.RotaryTorsoMachine;
import ExerciseMachines.Leg.LegCurlMachine;
import ExerciseMachines.Leg.LegExtensionMachine;
import Managers.ExerciseProgramManager;

import java.util.*;

public class ExerciseProgramHelper {
    private final ExerciseProgramManager manager;
    private final ArrayList<Exercises> programExercises;
    private final ArrayList<Exercises> availableExercises;
    private final ArrayList<AbstractExerciseMachine> programMachines;
    private final ArrayList<AbstractExerciseMachine> availableMachines;

    public ExerciseProgramHelper() {
        programExercises = new ArrayList<>();
        programMachines = new ArrayList<>();
        availableExercises = new ArrayList<>(List.of(Exercises.values()));
        availableMachines = new ArrayList<>(List.of(
            new BicepCurlBench(),
            new SeatedDipMachine(),
            new CableRowMachine(),
            new LateralPulldownMachine(),
            new LegRaiseTower(),
            new RotaryTorsoMachine(),
            new LegCurlMachine(),
            new LegExtensionMachine()
        ));
        manager = new ExerciseProgramManager(programMachines);
    }

    private int optionInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choice: ");
        var data = scanner.nextLine().trim();

        if (data.isEmpty()) {
            System.out.println("No option specified");
            return -1;
        }

        int choice;
        try {
            choice = Integer.parseInt(data);
        } catch (Exception e) {
            System.out.println("\n- INVALID OPTION -\n");
            return -1;
        }

        return --choice;
    }

    public void addExercise() {
        listAvailableExercises();

        int choice = optionInput();
        if (choice == -1) return;

        if (choice < 0 || choice >= availableExercises.size()) {
            System.out.println("\n- INVALID OPTION -\n");
            return;
        }

        programExercises.add(availableExercises.get(choice));

        for (var machine : availableMachines) {
            if (machine.getExercises().contains(availableExercises.get(choice))) {
                programMachines.add(machine);
                availableMachines.remove(machine);
                break;
            }
        }
        availableExercises.remove(choice);
    }

    public void removeExercise() {
        listProgramExercises();

        int choice = optionInput();
        if (choice == -1) return;

        if (choice < 0 || choice >= programExercises.size()) {
            System.out.println("\n- INVALID OPTION -\n");
            return;
        }

        availableExercises.add(programExercises.get(choice));

        for (var machine : programMachines) {
            if (machine.getExercises().contains(programExercises.get(choice))) {
                availableMachines.add(machine);
                programMachines.remove(machine);
                break;
            }
        }
        programExercises.remove(choice);
    }

    public void listAvailableExercises() {
        EnumPrintHelper printHelper = new EnumPrintHelper();

        System.out.println("\n    -- Available Exercises --    \n");

        if (availableExercises.isEmpty()) {
            System.out.println("\nNo exercises available\n");
            return;
        }

        int iter = 1;
        for (Exercises exercise : availableExercises) {
            System.out.printf("%s - %s\n",
                iter++,
                printHelper.getFormattedEnumName(exercise.name())
            );
        }
        System.out.println();
    }

    public void listProgramExercises() {
        EnumPrintHelper printHelper = new EnumPrintHelper();

        int iter = 1;
        for (var exercise : programExercises) {
            System.out.printf("%s - %s\n",
                iter++,
                printHelper.getFormattedEnumName(exercise.name())
            );
        }
        System.out.println();
    }

    public void listWholeProgram() {
        EnumPrintHelper printHelper = new EnumPrintHelper();

        if (programExercises.isEmpty() || programMachines.isEmpty()) {
            System.out.println("\n -- Your exercise program is empty -- ");
            return;
        }

        System.out.println("\n -- Your exercise program -- \n");
        for (var machine : programMachines) {
            for (var exercise : programExercises) {
                if (machine.getExercises().contains(exercise)) {
                    System.out.printf(" - %s on %s for %s minutes\n",
                        printHelper.getFormattedEnumName(
                            exercise.name()),
                            machine.getName(),
                            machine.getExerciseDuration()
                    );
                }
            }
        }
        System.out.println();
    }

    public void clearProgram() {
        availableExercises.addAll(programExercises);
        availableMachines.addAll(programMachines);
        programExercises.clear();
        programMachines.clear();
    }

    public void sortMenu() {
        System.out.println();

        boolean exit = false;
        while(!exit) {
            for (String s : Arrays.asList(
                "\n -- Sort Menu -- \n",
                "1 - By machines` names ascending",
                "2 - By machines` names descending",
                "3 - By exercises` names ascending",
                "4 - By exercises` names descending",
                "5 - By parameters that are improved ascending",
                "6 - By parameters that are improved descending",
                "7 - By muscle groups ascending",
                "8 - By muscle groups descending",
                "9 - Exit\n"
            )) {
                System.out.println(s);
            }

            int choice = optionInput();
            ++choice;
            if (choice == -1) return;

            switch (choice){
                case 1:
                    byMachineNameAscending();
                    break;
                case 2:
                    byMachineNameDescending();
                    break;
                case 3:
                    byExerciseNameAscending();
                    break;
                case 4:
                    byExerciseNameDescending();
                    break;
                case 5:
                    byParametersAscending();
                    break;
                case 6:
                    byParametersDescending();
                    break;
                case 7:
                    byMuscleGroupAscending();
                    break;
                case 8:
                    byMuscleGroupDescending();
                    break;
                case 9:
                    exit = true;
                default:
                    break;
            }
            int iter = 1;
            for (var machine : manager.getSelectedMachines()) {
                System.out.printf("%s. %s %s %s %s%n",
                    iter++,
                    machine.getName(),
                    machine.getExercises(),
                    machine.getParametersImproved(),
                    machine.getMusclesBenefited()
                );
            }
        }

    }

    // sorts
    public void byMachineNameAscending() {
        manager.sortByNameAscending();

    }

    public void byMachineNameDescending() {
        manager.sortByNameDescending();
    }

    public void byExerciseNameAscending() {
        manager.sortByExerciseNameAscending();
    }

    public void byExerciseNameDescending() {
        manager.sortByExerciseNameDescending();
    }

    public void byParametersAscending() {
        manager.sortByParametersAscending();
    }

    public void byParametersDescending() {
        manager.sortByParametersDescending();
    }

    public void byMuscleGroupAscending() {
        manager.sortByMuscleGroupAscending();
    }

    public void byMuscleGroupDescending() {
        manager.sortByMuscleGroupDescending();
    }

    public ArrayList<AbstractExerciseMachine> getProgramMachines() {
        return programMachines;
    }

    public ArrayList<Exercises> getProgramExercises() {
        return programExercises;
    }
}