package Helpers;

import Pizzeria.Order;
import Pizzeria.Pizza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOHelper {

    private static Pizza parseLineAsPizza(String line) {
        String[] splitStr = line.split(" ");
        List<String> ingredientsList = List.of(Arrays.copyOfRange(splitStr, 3, splitStr.length));

        return new Pizza(
            splitStr[0],
            Integer.parseInt(splitStr[1]),
            Integer.parseInt(splitStr[2]),
            new ArrayList<>(ingredientsList)
        );
    }

    private static Order parseLineAsOrder(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            int orderNumber = Integer.parseInt(matcher.group(1));
            String address = matcher.group(2);
            String firstName = matcher.group(3);
            String lastName = matcher.group(4);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(5), formatter);

            List<String> pizzas = Arrays.asList(matcher.group(6).split(" "));

            return new Order(
                orderNumber,
                address,
                firstName + " " + lastName,
                dateTime,
                new ArrayList<>(pizzas)
            );
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void readSerializedDataFiles(List<Pizza> pizzaMenu, List<Order> orders) {
        List<Pizza> pizzaList = new ArrayList<>(SerializationHelper.deserialize("serializedPizzaMenu"));
        List<Order> ordersList = new ArrayList<>(SerializationHelper.deserialize("serializedOrders"));
        pizzaMenu.addAll(pizzaList);
        orders.addAll(ordersList);
    }

    public void updateSerializedFiles(List<Pizza> pizzaMenu, List<Order> orders) {
        List<Object> pizzaListObj = new ArrayList<>(pizzaMenu);
        List<Object> orderListObj = new ArrayList<>(orders);
        SerializationHelper.serialize(pizzaListObj, "serializedPizzaMenu");
        SerializationHelper.serialize(orderListObj, "serializedOrders");
    }

    public void readTextDataFiles(List<Pizza> pizzaMenu, List<Order> orders) {
        String regex = "(\\d+) \"(.*?)\" (\\S+) (\\S+) (\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}) (.*)";
        Pattern pattern = Pattern.compile(regex);

        try {
            List<Pizza>  pizzaList = FileHelper.ReadFile("pizzas.txt")
                .stream()
                .map(IOHelper::parseLineAsPizza)
                .toList();

            List<Order> orderList = FileHelper.ReadFile("orders.txt")
                .stream()
                .map(line -> parseLineAsOrder(line, pattern))
                .toList();

            pizzaMenu.addAll(pizzaList);
            orders.addAll(orderList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
