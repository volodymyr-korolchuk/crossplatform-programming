package Helpers;

import Pizzeria.Pizzeria;

import java.util.Scanner;

public class InputHelper {
    public String getStringInput(String label) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(label);
        try {
            return scanner.nextLine();
        } catch (Exception exception) {
            System.out.println("Exception occurred: " + exception.getMessage());
            return null;
        } finally {
            System.out.println();
        }
    }

    public void start() {
        Pizzeria pizzeria = new Pizzeria();
        IOHelper helper = new IOHelper();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option;

        while (!exit) {
            System.out.println("\n -- Menu -- \n");
            System.out.printf("1 - %s %n2 - %s %n3 - %s %n4 - %s %n5 - %s %n6 - %s %n7 - %s %n8 - %s %n%n",
                "Sort all orders by delivery time (ascending)",
                "Get the addresses of customers that ordered more than 2 pizzas",
                "Get number of total orders by pizza name",
                "Get most ordered pizza by customer",
                "Get the list of all pizzas and their buyers",
                "Get delayed orders",
                "Place order",
                "Exit"
            );
            System.out.print("Choice: ");
            String choiceString = scanner.nextLine().trim();

            if (choiceString.isEmpty()) continue;
            if (choiceString.equals("exit")) break;

            try {
                option = Integer.parseInt(choiceString);
                switch (option) {
                    case 1 -> pizzeria.sortByDeliveryTime();
                    case 2 -> pizzeria.getMultiplePizzaOrderAddresses();
                    case 3 -> pizzeria.getOrderCountByPizzaName(getStringInput("Pizza name: "));
                    case 4 -> pizzeria.getMostOrderedPizzaByCustomer(getStringInput("Customer`s full name: "));
                    case 5 -> pizzeria.getPizzasAndTheirBuyers();
                    case 6 -> pizzeria.getDelayedOrders();
                    case 7 -> pizzeria.placeOrder();
                    case 8 -> exit = true;
                    default -> {
                        break;
                    }
                }
            } catch (Exception exception) {
                System.out.println("Exception occurred: " + exception.getMessage());
                return;
            }
        }

        helper.updateSerializedFiles(pizzeria.getPizzaMenu(), pizzeria.getOrders());
    }
}
