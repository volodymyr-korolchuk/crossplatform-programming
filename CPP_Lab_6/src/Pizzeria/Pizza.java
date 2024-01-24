package Pizzeria;

import java.io.Serializable;
import java.util.List;

public class Pizza implements DataDisplayer, Serializable {
    private String name;
    private Integer weight;
    private Integer price;
    private List<String> ingredients;

    public Pizza(String name, Integer weight, Integer price, List<String> ingredients) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getPrice() {
        return price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public void print() {
        System.out.printf("-- %s -- %nWeight: %sg %nPrice: %s₴  %nIngredients: %s%n%n",
            name,
            weight,
            price,
            ingredients
                .stream()
                .reduce("", (a, b) -> ingredients.get(0).equals(b)
                    ? a + b
                    : a + ", " + b
                )
        );
    }
}
