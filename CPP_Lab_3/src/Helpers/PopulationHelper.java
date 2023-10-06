package Helpers;

import Entities.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopulationHelper {

    private final List<String> lastNames = new ArrayList<>(List.of("Smith", "Well", "Williams", "Cole", "Dawson"));

    private final List<String> positions = new ArrayList<>(List.of("Worker", "Manager", "CEO"));

    private int getDateBound(int month) {
        int bound = switch (month) {
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };

        return bound + 1;
    }

    public Employee generateRandomEmployee() {
        Random rd = new Random();

        int year = rd.nextInt(2002, LocalDate.now().getYear());
        int month = rd.nextInt(11, 12);
        int day = rd.nextInt(1, getDateBound(month));

        return new Employee(
            lastNames.get(rd.nextInt(0, lastNames.size())),
            LocalDate.of(year, month, day),
            positions.get(rd.nextInt(0, positions.size()))
        );
    }

    public void populateWithRandomEmployees(List<Employee> employeeList, int number) {
        for (int i = 0; i < number; i++) {
            employeeList.add(generateRandomEmployee());
        }
    }

    public List<Employee> getRandomEmployeeList(int number) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            employees.add(generateRandomEmployee());
        }

        return employees;
    }
}
