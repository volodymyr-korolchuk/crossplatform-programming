package Helpers;

import Entities.Employee;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class FileHelper {
    private List<String> getStringList(List<Employee> list){
        var stringList = new ArrayList<String>();

        for (var employee : list) {
            stringList.add(String
                .format(
                    "%s %s %s",
                    employee.getLastName(),
                    employee.getEnrolmentDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    employee.getPosition()
                )
            );
        }

        return stringList;
    }

    private void writeListIntoFile(String path, List<String> dataList) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < dataList.size(); i++) {
                writer.write(dataList.get(i));

                if (i != dataList.size() - 1) {
                    writer.newLine();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                exit(1);
            }
        }
    }

    public void createAndPopulateFiles() {
        PopulationHelper populationHelper = new PopulationHelper();

        List<Employee> employeeList = null;
        List<String> output = null;

        employeeList = populationHelper.getRandomEmployeeList(1000);
        output = getStringList(employeeList);
        writeListIntoFile("input_1.txt", output);

        employeeList = populationHelper.getRandomEmployeeList(1000);
        output = getStringList(employeeList);
        writeListIntoFile("input_2.txt", output);
    }

    public List<String> getFileContent(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            List<String> stringList = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null) {
                stringList.add(line);
            }

            return stringList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
