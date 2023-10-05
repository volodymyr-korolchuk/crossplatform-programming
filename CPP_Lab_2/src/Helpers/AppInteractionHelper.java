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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AppInteractionHelper {
    public static void displayAllMachines(ArrayList<AbstractExerciseMachine> machines) {
        int iter = 1;

        System.out.println("\n    -- Exercise Machines --    \n");
        for (var machine: machines) {
            System.out.printf("%s. %s\n", iter, machine.getName());
            iter++;
        }
    }

    public static void displayAllExercises(){
        EnumPrintHelper printHelper = new EnumPrintHelper();

        System.out.println("\n    -- Exercises --    \n");
        for (var exercise: Exercises.values()) {
            System.out.printf("%s. %s\n", exercise.ordinal() + 1,
                    printHelper.getFormattedEnumName(exercise.name())
            );
        }
    }

    public static void exerciseProgramEditor() {
        Scanner scanner = new Scanner(System.in);

        int option;
        boolean exit = false;
        ExerciseProgramHelper programHelper = new ExerciseProgramHelper();

        while(!exit) {
            for (String s : Arrays.asList(
                "\n -- Exercise Program Editor -- \n",
                "1 - List Available exercises",
                "2 - Add exercise to the program",
                "3 - Remove exercise from the program",
                "4 - List current program",
                "5 - Clear program",
                "6 - Sort",
                "7 - Exit"
            )) {
                System.out.println(s);
            }

            System.out.print("\nChoice: ");

            var data = scanner.nextLine().trim();
            if (data.isEmpty()) continue;

            int choice;
            try {
                choice = Integer.parseInt(data);
            } catch (Exception e) {
                System.out.println("\n- INVALID OPTION -\n");
                continue;
            }

            switch (choice) {
                case 1:
                    programHelper.listAvailableExercises();
                    break;
                case 2:
                    programHelper.addExercise();
                    break;
                case 3:
                    programHelper.removeExercise();
                    break;
                case 4:
                    programHelper.listWholeProgram();
                    break;
                case 5:
                    programHelper.clearProgram();
                    break;
                case 6:
                    programHelper.sortMenu();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        ArrayList<AbstractExerciseMachine> machines = new ArrayList<>(List.of(
                new BicepCurlBench(),
                new SeatedDipMachine(),
                new CableRowMachine(),
                new LateralPulldownMachine(),
                new LegRaiseTower(),
                new RotaryTorsoMachine(),
                new LegCurlMachine(),
                new LegExtensionMachine()
        ));

        boolean exit = false;
        while (!exit) {
            for (String s : Arrays.asList(
                    "\n     -- MENU --    \n",
                    "1 - list all machines\n",
                    "2 - list all exercises\n",
                    "3 - exercise program editor\n",
                    "4 - exit\n\n",
                    "Choice: "))
            {
                System.out.print(s);
            }

            var data = scanner.nextLine().trim();
            if (data.isEmpty()) continue;

            int choice;
            try {
                choice = Integer.parseInt(data);
            } catch (Exception e) {
                System.out.println("\n-INVALID OPTION-");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllMachines(machines);
                    break;
                case 2:
                    displayAllExercises();
                    break;
                case 3:
                    exerciseProgramEditor();
                    break;
                case 4:
                    exit = true;
                    break;
            }
        }
    }
}
