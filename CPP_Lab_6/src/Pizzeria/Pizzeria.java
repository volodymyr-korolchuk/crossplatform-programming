package Pizzeria;

import Helpers.FileHelper;
import Helpers.IOHelper;
import Helpers.OrderValidator;
import Helpers.SerializationHelper;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pizzeria {
    private List<Pizza> pizzaMenu;

    private List<Order> orders;

    public Pizzeria() {
        File serializedPizzaFile = new File("serializedPizzaMenu");
        File serializedOrdersFile = new File("serializedOrders");
        IOHelper helper = new IOHelper();

        pizzaMenu = new ArrayList<>();
        orders = new ArrayList<>();

        if (serializedPizzaFile.exists() && serializedOrdersFile.exists()) {
            helper.readSerializedDataFiles(pizzaMenu, orders);
        } else {
            helper.readTextDataFiles(pizzaMenu, orders);
            helper.updateSerializedFiles(pizzaMenu, orders);
        }

        adaptDeliveryTimes();
    }

    public void placeOrder() {
        try {
            System.out.println("\nEnter order data: \n");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Order number: ");
            String number = scanner.nextLine();

            System.out.println("Customer`s full name: ");
            String fullName = scanner.nextLine();

            System.out.println("Address: ");
            String address = scanner.nextLine();

            System.out.println("Pizzas: ");

            String data;
            List<String> orderItemList = new ArrayList<>();
            while(true) {
                for (int i = 0; i < pizzaMenu.size(); i++) {
                    System.out.printf("%s. ", i + 1);
                    pizzaMenu.get(i).print();
                }

                System.out.print("\n\nAdd: ");

                data = scanner.nextLine();
                if (data.equalsIgnoreCase("exit")) {
                    if (orderItemList.isEmpty()) {
                        System.out.println("\n Order list can`t be empty! \n");
                        continue;
                    }
                    break;
                }

                int pizzaNumber = Integer.parseInt(data);

                orderItemList.add(pizzaMenu.get(pizzaNumber - 1).getName());
            }

            if (OrderValidator.validateOrderData(number, fullName, address, orderItemList)) {
                orders.add(new Order(
                    Integer.parseInt(number),
                    address,
                    fullName,
                    generateRandomDateTime(
                            LocalDateTime.now().minusHours(2),
                            LocalDateTime.now().plusHours(2)),
                        orderItemList
                    )
                );
                return;
            }

            System.out.println("wrong data format, try again");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private static LocalDateTime generateRandomDateTime(LocalDateTime lowerBound, LocalDateTime upperBound) {
        Random random = new Random();

        long minSeconds = lowerBound.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = upperBound.toEpochSecond(ZoneOffset.UTC);
        long randomSeconds = minSeconds + random.nextInt((int) (maxSeconds - minSeconds + 1));

        return LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC);
    }

    private void adaptDeliveryTimes() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime lowerBound = currentDateTime.minusHours(2);
        LocalDateTime upperBound = currentDateTime.plusHours(2);

        for (var order : orders) {
            order.setDeliveryTime(generateRandomDateTime(lowerBound, upperBound));
        }
    }

    // Tasks
    public void sortByDeliveryTime() {
        List<Order> ordersCopy = orders
            .stream()
            .sorted(Comparator.comparing((Order a) -> a.getDeliveryTime()))
            .toList();

        for (var order : ordersCopy) order.print();
    }

    public void getMultiplePizzaOrderAddresses() {
        List<Order> ordersCopy = orders
            .stream()
            .filter(a -> a.getItemList().size() > 2)
            .toList();

        for (var order : ordersCopy) {
            System.out.printf(
                "%nCustomer: %s %nAddress: %s %nItem List: %s %n",
                order.getCustomer(),
                order.getAddress(),
                order.getItemList()
            );
        }
    }

    public void getOrderCountByPizzaName(String pizzaName) {
        List<Order> ordersCopy = orders
            .stream()
            .filter(a -> a.getItemList().contains(pizzaName))
            .toList();

        System.out.printf(
            "-> There are %s customers that ordered %s%n%n",
            ordersCopy.size(),
            pizzaName
        );

        for (var order : ordersCopy) {
            order.print();
        }
    }

    public void getMostOrderedPizzaByCustomer(String customerFullName) {
        var maxItemOrder = orders
            .stream()
            .filter(a -> a.getCustomer().equals(customerFullName))
            .max(Comparator.comparingInt((Order a) -> a.getItemList().size()))
            .stream()
            .toList();

        System.out.printf(
            "Max number of pizzas ordered by %s is %s %nOrder #%s: %s%n",
            maxItemOrder.get(0).getCustomer(),
            maxItemOrder.get(0).getItemList().size(),
            maxItemOrder.get(0).getNumber(),
            maxItemOrder.get(0).getItemList()
        );
    }

    public void getPizzasAndTheirBuyers() {
        Map<String, List<String>> pizzaCustomerMap = new HashMap<>();

        for (var pizza : pizzaMenu) {
            var customersThatOrdered = orders
                .stream()
                .filter(a -> a.getItemList().contains(pizza.getName()))
                .map(a -> a.getCustomer())
                .toList();

            pizzaCustomerMap.put(pizza.getName(), new ArrayList<>(customersThatOrdered));
        }


        System.out.printf("%n%-28s %s%n%n", "Pizza:", "Ordered by:");

        for (var item : pizzaCustomerMap.keySet()) {
            System.out.printf("%-25s -> %s%n", item, pizzaCustomerMap.get(item));
        }
    }

    public void getDelayedOrders() {
        var delayedOrders = orders
            .stream()
            .filter(a -> a.getDeliveryTime().isBefore(LocalDateTime.now()))
            .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        System.out.println(" -- Delayed orders -- ");

        for (var order : delayedOrders) {
            var delayDuration = Duration.between(order.getDeliveryTime(), LocalDateTime.now());
            System.out.printf(
                "Order #%s: %nItem List: %s %nTill: %s %nDelayed by: %s%n%n",
                order.getNumber(),
                order.getItemList(),
                order.getDeliveryTime().format(formatter),
                String.format("%02d:%02d", delayDuration.toHours(), delayDuration.toMinutesPart())
            );
        }
    }

    // Getters

    public List<Order> getOrders() {
        return orders;
    }

    public List<Pizza> getPizzaMenu() {
        return pizzaMenu;
    }
}
