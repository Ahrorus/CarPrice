package app_info;

import java.io.Serializable;

/**
 * OptionInfo class represents information about an Option, shared across the application.
 */
public class OptionInfo implements Serializable {

    private String name;    // OptionInfo name
    private double price;   // OptionInfo price

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OptionInfo(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
