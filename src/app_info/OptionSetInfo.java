package app_info;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * OptionSetInfo class represents a type of menu of Options for the car.
 * It contains a list of Options.
 * Examples for the the OptionSet objects are: Engine Type, Fuel Type, Color, etc.
 */
public class OptionSetInfo implements Serializable {

    /** Represents OptionSet's name */
    private String name;
    /** Represents OptionSet's list of Options */
    private ArrayList<OptionInfo> optionInfos;

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
     * Getter for OptionInfos list of Options
     * @return ArrayList of Options
     */
    public ArrayList<OptionInfo> getOptionInfos() {
        return optionInfos;
    }

    /**
     * Setter for OptionSetInfo's list of Options'
     * @param options new list of Options
     */
    public void setOptionInfos(ArrayList<OptionInfo> options) {
        this.optionInfos = options;
    }

    /**
     * OptionSet constructor
     * @param name OptionSet name
     */
    public OptionSetInfo(String name) {
        this.name = name;
        optionInfos = new ArrayList<>();
    }

}
