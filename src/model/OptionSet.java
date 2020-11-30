package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * OptionSet class represents a type of menu of Options for the car.
 * It contains a list of Options.
 * Examples for the the OptionSet objects are: Engine Type, Fuel Type, Color, etc.
 */
public class OptionSet implements Serializable {

    /** Represents OptionSet's name */
    private String name;
    /** Represents OptionSet's list of Options */
    private ArrayList<Option> options;

    /**
     * Getter for OptionSet name
     * @return OptionSet name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for OptionSet name
     * @param name new OptionSet name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for OptionSet's list of Options
     * @return ArrayList of Options
     */
    public ArrayList<Option> getOptions() {
        return options;
    }

    /**
     * Setter for OptionSet's list of Options'
     * @param options new list of Options
     */
    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    /**
     * OptionSet constructor
     * @param name OptionSet name
     */
    public OptionSet(String name) {
        this.name = name;
        options = new ArrayList<>();
    }

}
