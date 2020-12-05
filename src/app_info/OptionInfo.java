package app_info;

import java.io.Serializable;

/**
 * OptionInfo class represents information about an Option, shared across the application.
 */
public class OptionInfo implements Serializable {

    /**Represents Option's name */
    private String name;
    /**Represents Option's price */
    private double price;

    /**
     * Getter for Option's name
     * @return Option's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Option's name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for Option's price
     * @return Option's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter of Option's price
     * @param price new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Option constructor
     * @param name Option name
     * @param price Option price
     */
    public OptionInfo(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
