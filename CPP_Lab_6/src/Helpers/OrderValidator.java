package Helpers;

import java.util.List;

public class OrderValidator {
    public static boolean validateOrderData(String number, String fullName, String address, List<String> pizzas) {
        if (!number.matches("\\d*")) return false;
        System.out.println("number matches");

        String[] parsedFullName = fullName.split(" ");

        if (parsedFullName.length != 2) return false;
        System.out.println("fullname proper len");

        if (!parsedFullName[0].matches("[A-Z]([a-z]+|\\.)*") ||
            !parsedFullName[1].matches("[A-Z]([a-z]+|\\.)")
        ) return false;
        System.out.println("fullname matches regex");

        return true;
    }
}
