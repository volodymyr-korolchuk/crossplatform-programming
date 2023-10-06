package Entities;

import java.time.LocalDate;

public class Employee {
    private final String lastName;
    private String position;
    private LocalDate enrolmentDate;

    public Employee(String lastName, LocalDate enrolmentDate, String position) {
        this.lastName = lastName;
        this.enrolmentDate = enrolmentDate;
        this.position = position;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getEnrolmentDate() {
        return enrolmentDate;
    }
}
