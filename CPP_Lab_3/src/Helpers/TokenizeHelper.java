package Helpers;

import Entities.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizeHelper {

    private List<String> tokenizeString(String str, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        List<String> tokenList = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            tokenList.add(tokenizer.nextToken());
        }

        return tokenList;
    }
    public List<Employee> stringListToEmployeeList(List<String> stringList) {
        List<Employee> employees = new ArrayList<>();

        for (var str : stringList) {
            List<String> tokenizedString = tokenizeString(str, " .");

            employees.add(new Employee(
                tokenizedString.get(0),
                LocalDate.of(
                    Integer.parseInt(tokenizedString.get(3)),
                    Integer.parseInt(tokenizedString.get(2)),
                    Integer.parseInt(tokenizedString.get(1))),
                tokenizedString.get(4))
            );
        }

        return employees;
    }

}
