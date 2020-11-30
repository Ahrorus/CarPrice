package model;

import util.FileIO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Car class represents a Car object.
 * It contains a list of OptionSets, and each of them contains a list of Options.
 */
public class Car implements Serializable {

    /** Represents Car's name */
    private String name;
    /** Represents Car's base price */
    private double basePrice;
    /** Represents Car's total price */
    private double totalPrice;
    /** Represents list of OptionSets */
    private ArrayList<OptionSet> optionSets;
    /** Represents user's Option choices */
    private int[] choices;

    /**
     * Getter for car's name
     * @return Car's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for Car's basePrice
     * @return Car's basePrice
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Getter for Car's totalPrice
     * @return Car's totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Setter for Car's name
     * @param name Car's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for Car's base price
     * @param basePrice Car's new base price
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Setter for Car's total price
     * @param totalPrice Car's new total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Car Constructor
     * @param name Car's name
     * @param basePrice Car's base price
     */
    public Car(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        optionSets = new ArrayList<>();
    }

    /**
     * Returns size of the optionSets list
     * @return Size of the optionSets list
     */
    public int getOptionSetSize() {
        return optionSets.size();
    }

    /**
     * Returns size of options list of the specified OptionSet
     * @param i OptionSet id
     * @return Specified OptionSet's options list size
     */
    public int getOptionSize(int i) {
        return optionSets.get(i).getOptions().size();
    }

    /**
     * Adds a new OptionSet
     * @param name specific OptionSet name
     */
    public void addOptionSet(String name) {
        optionSets.add(new OptionSet(name));
    }

    /**
     * Adds a new Option into the specified OptionSet
     * @param i OptionSet id
     * @param name specific OptionSet name
     * @param price specific Option price
     */
    public void addOption(int i, String name, double price) {
        optionSets.get(i).getOptions().add(new Option(name, price));
    }

    /**
     * Returns the name of the specified OptionSet
     * @param i OptionSet id
     * @return Name of the OptionSet
     */
    public String getOptionSetName(int i) {
        return optionSets.get(i).getName();
    }

    /**
     * Returns the name of the specified Option
     * @param i OptionSet id
     * @param j Option id
     * @return Name of the Option
     */
    public String getOptionName(int i, int j) {
        return optionSets.get(i).getOptions().get(j).getName();
    }

    /**
     * @param i OptionSet id
     * @param j Option id
     * @return Specified Option's price
     */
    public double getOptionPrice(int i, int j) {
        return optionSets.get(i).getOptions().get(j).getPrice();
    }

    /**
     * Edits the specified OptionSet's name
     * @param i OptionSet id
     * @param newName OptionSet's new name
     */
    public void editOptionSetName(int i, String newName) {
        optionSets.get(i).setName(newName);
    }

    /**
     * Edits the specified OptionSet's name
     * @param i OptionSet id
     * @param j Option id
     * @param newName new OptionSet name
     */
    public void editOptionName(int i, int j, String newName) {
        optionSets.get(i).getOptions().get(j).setName(newName);
    }

    /**
     * Edits the specified Option's price
     * @param i OptionSet id
     * @param j Option id
     * @param newPrice new Option price
     */
    public void editOptionPrice(int i, int j, double newPrice) {
        optionSets.get(i).getOptions().get(j).setPrice(newPrice);
    }

    /**
     * Deletes the specified Option
     * @param i OptionSet id
     * @param j Option id
     */
    public void deleteOption(int i, int j){
        optionSets.get(i).getOptions().remove(j);
    }

    /**
     * Deletes the specified OptionSet
     * @param i OptionSet id
     */
    public void deleteOptionSet(int i) {
        optionSets.remove(i);
    }

    /**
     * Edits the specified element in the choices[] array
     * @param i OptionSet id
     * @param n Option id
     */
    public void choose(int i, int n){
        choices[i] = n;
    }

    /**
     * Calculates the total price for the Car
     */
    public void calculateTotalPrice() {
        totalPrice = basePrice;
        for (int i = 0; i < optionSets.size(); i++) {
            int j = choices[i];
            totalPrice += getOptionPrice(i, j);
        }
    }

    /**
     * Checks if the Car is good to go
     * @throws Exception
     */
    public void finish() throws Exception {
        if (optionSets.isEmpty())
            throw new Exception("Cannot leave optionSets list empty.");
        for (OptionSet optionSet : optionSets)
            if (optionSet.getOptions().isEmpty())
                throw new Exception("Cannot leave options list empty.");
        choices = new int[optionSets.size()];
    }

    /**
     * toString method for the Car
     * @return Car String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car:\n").append(name).append("\n").append(basePrice);
        for (OptionSet optionSet : optionSets) {
            sb.append("\n-----\n").append(optionSet.getName());
            for (Option option : optionSet.getOptions()) {
                sb.append("\n").append(option.getName())
                        .append("\n").append(option.getPrice());
            }
        }
        return sb.toString();
    }

}
