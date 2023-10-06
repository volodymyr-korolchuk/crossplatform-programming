package Helpers;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Entities.Employee;

public class CollectionsHelper {
    private List<Employee> employeeList;
    private Map<LocalDate, List<String>> task1Map = new HashMap<>();
    private Map<LocalDate, List<String>> task2Map = new HashMap<>();
    private PopulationHelper populationHelper = new PopulationHelper();

    public CollectionsHelper() {
        FileHelper fileHelper = new FileHelper();
        TokenizeHelper tokenizeHelper = new TokenizeHelper();
        employeeList = new ArrayList<>();

        var dataList = fileHelper.getFileContent("employees.txt");
        System.out.println(dataList);
        employeeList.addAll(tokenizeHelper.stringListToEmployeeList(dataList));
    }

    public void start() {
        int choice;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while(!exit) {
            System.out.println("\nPerform: \n");
            System.out.printf("1-%s %n2-%s %n3-%s %n4-%s %n%n", "Task #1", "Task #2", "Task #3", "Exit");
            System.out.print("Choice: ");

            String choiceString = scanner.nextLine().trim();
            if (choiceString.isEmpty()) continue;
            if (choiceString.equals("exit")) break;

            try {
                choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 1:
                        task1();
                        break;
                    case 2:
                        task2();
                        break;
                    case 3:
                        task3();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception exception) {
                System.out.println("Exception occurred: " + exception.getMessage());
                return;
            }
        }
    }

    public void task1() {
        // storing all dates in a set to avoid duplicates
        for (var employee: employeeList) {
            System.out.printf(
                "%-10s %-10s %-10s%n",
                employee.getLastName(),
                employee.getEnrolmentDate().toString(),
                employee.getPosition()
            );
        }
        HashSet<LocalDate> dates = new HashSet<>();

        for (var employee: employeeList) {
            dates.add(employee.getEnrolmentDate());
        }

        // traversing the employee list while checking a certain date
        // and picking all the corresponding surnames
        for (var date : dates) {
            HashSet<Employee> filteredEmployees = new HashSet<>();
            for (var employee : employeeList) {
                if (employee.getEnrolmentDate().equals(date)) {
                    filteredEmployees.add(employee);
                }
            }

            var filteredEmployeeList = new ArrayList<>(filteredEmployees);
            filteredEmployeeList.sort((a, b) -> a.getPosition().compareTo(b.getPosition()));

            List<String> surnameList = filteredEmployeeList
                .stream()
                .map(Employee::getLastName)
                .distinct()
                .toList();

            task1Map.put(
                date,
                surnameList
            );
        }

        System.out.println("\n[ Task 1 ] Map of employees: \n");
        var keys = task1Map.keySet().stream().toList();

        System.out.printf("| %-11s| %-11s %n", "Date: ", "Employees: ");
        for (int i = 0; i < task1Map.size(); i++) {
            System.out.printf("| %-11s| ", keys.get(i));
            for (var item : task1Map.get(keys.get(i))) {
                System.out.printf("%-11s", item);
            }
            System.out.println();
        }
    }

    public void task2() {
        task2Map = task1Map
            .entrySet()
            .stream()
            .filter(x -> (LocalDate.now().getYear() - x.getKey().getYear() > 10))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
        );

        System.out.println("\n[ Task 2 ] Map of employees: \n");
        var keys = task2Map.keySet().stream().toList();

        System.out.printf("| %-11s| %-11s %n", "Date: ", "Employees: ");
        for (int i = 0; i < task2Map.size(); i++) {
            System.out.printf("| %-11s| ", keys.get(i));
            for (var item : task2Map.get(keys.get(i))) {
                System.out.printf("%-11s", item);
            }
            System.out.println();
        }
    }

    public void task3() {
        var dataLists = handleFileOperations();

        var employees_1 = dataLists.get(0);
        var employees_2 = dataLists.get(1);

        HashMap<String, Employee> employees = new HashMap<>();

        for (var employee : employees_1) {
            employees.put(employee.getLastName(), employee);
        }

        for (var employee : employees_2) {
            employees.put(employee.getLastName(), employee);
        }

        System.out.println("\n[ Task 3 ] Set of employees: \n");
        for (var employee : employees.values()) {
            System.out.printf(
                "%-10s %-10s %-10s %n",
                employee.getLastName(),
                employee.getEnrolmentDate()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                employee.getPosition()
            );
        }

        task3Addition(employees_1, employees_2);
    }

    private int countFrequencyByPosition(String position, List<Employee> employeeList1, List<Employee> employeeList2) {
        int frequency = 0;

        frequency = employeeList1.stream().filter(x -> x.getPosition().equals(position)).toList().size();
        frequency += employeeList2.stream().filter(x -> x.getPosition().equals(position)).toList().size();

        return frequency;
    }

    private void task3Addition(List<Employee> employees_1, List<Employee> employees_2) {
        int choice;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while(!exit) {
            System.out.println("\nDisplay workers frequency of: \n");
            System.out.printf("1-%s %n2-%s %n3-%s %n4-%s %n5-%s %n%n", "CEOs", "Managers", "Workers", "All", "Exit");
            System.out.print("Choice: ");

            String choiceString = scanner.nextLine().trim();
            if (choiceString.isEmpty()) continue;
            if (choiceString.equals("exit")) break;

            try {
                choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 1:
                        System.out.printf(
                                "%n%s: %s%n", "CEOs frequency",
                                countFrequencyByPosition("CEO", employees_1, employees_2)
                        );
                        break;
                    case 2:
                        System.out.printf(
                                "%n%s: %s%n", "Managers frequency",
                                countFrequencyByPosition("Manager", employees_1, employees_2)
                        );
                        break;
                    case 3:
                        System.out.printf(
                                "%n%s: %s%n", "Workers frequency",
                                countFrequencyByPosition("Worker", employees_1, employees_2)
                        );
                        break;
                    case 4:
                        System.out.printf(
                                "%n| %-11s| %-11s| %-11s| %n| %-11s| %-11s| %-11s|%n",
                                "CEOs",
                                "Managers",
                                "Workers",
                                countFrequencyByPosition("CEO", employees_1, employees_2),
                                countFrequencyByPosition("Manager", employees_1, employees_2),
                                countFrequencyByPosition("Worker", employees_1, employees_2)
                        );
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception exception) {
                System.out.println("Exception occurred: " + exception.getMessage());
                return;
            }
        }
    }

    private List<List<Employee>> handleFileOperations() {
        FileHelper fileHelper = new FileHelper();
        TokenizeHelper tokenizeHelper = new TokenizeHelper();

        File file_1 = new File("input_1.txt");
        File file_2 = new File("input_2.txt");

        // Check if files exist. Create and populate if not
        if (!(file_1.exists() && file_2.exists())) {
            fileHelper.createAndPopulateFiles();
        }

        var input_1 = fileHelper.getFileContent("input_1.txt");
        var input_2 = fileHelper.getFileContent("input_2.txt");

        var employees_1 = tokenizeHelper.stringListToEmployeeList(input_1);
        var employees_2 = tokenizeHelper.stringListToEmployeeList(input_2);

        return new ArrayList<>(List.of(employees_1, employees_2));
    }
}
