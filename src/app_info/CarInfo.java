package app_info;

import model.Car;
import model.Option;
import model.OptionSet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CarInfo class represents information about Car, shared across the application.
 * It contains a list of OptionSetInfos, and each of them contains a list of OptionInfos.
 */
public class CarInfo implements Serializable {

    /** Represents Car's name */
    private String name;
    /** Represents Car's base price */
    private double basePrice;
    /** Represents Car's total price */
    private double totalPrice;
    /** Represents list of OptionSets */
    private ArrayList<OptionSetInfo> optionSetInfos;

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
    public CarInfo(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        optionSetInfos = new ArrayList<>();
    }

    /**
     * Returns size of the optionSetInfos list
     * @return Size of the optionSetInfos list
     */
    public int getOptionSetInfosSize() {
        return optionSetInfos.size();
    }

    /**
     * Returns size of optionInfos list of the specified OptionSetInfo
     * @param i OptionSetInfo id
     * @return Specified OptionSetInfo's optionInfos list size
     */
    public int getOptionInfosSize(int i) {
        return optionSetInfos.get(i).getOptionInfos().size();
    }

    /**
     * Adds a new OptionSetInfo
     * @param name specific OptionSetInfo name
     */
    public void addOptionSetInfo(String name) {
        optionSetInfos.add(new OptionSetInfo(name));
    }

    /**
     * Adds a new OptionInfo into the specified OptionSetInfo
     * @param i OptionSetInfo id
     * @param name specific OptionSetInfo name
     * @param price specific OptionInfo price
     */
    public void addOptionInfo(int i, String name, double price) {
        optionSetInfos.get(i).getOptionInfos().add(new OptionInfo(name, price));
    }

    /**
     * Returns the name of the specified OptionSetInfo
     * @param i OptionSetInfo id
     * @return Name of the OptionSetInfo
     */
    public String getOptionSetInfoName(int i) {
        return optionSetInfos.get(i).getName();
    }

    /**
     * Returns the name of the specified OptionInfo
     * @param i OptionSetInfo id
     * @param j OptionInfo id
     * @return Name of the OptionInfo
     */
    public String getOptionInfoName(int i, int j) {
        return optionSetInfos.get(i).getOptionInfos().get(j).getName();
    }

    /**
     * @param i OptionSetInfo id
     * @param j OptionInfo id
     * @return Specified OptionInfo's price
     */
    public double getOptionInfoPrice(int i, int j) {
        return optionSetInfos.get(i).getOptionInfos().get(j).getPrice();
    }

    /**
     * Edits the specified OptionSetInfo's name
     * @param i OptionSetInfo id
     * @param newName OptionSetInfo's new name
     */
    public void editOptionSetInfoName(int i, String newName) {
        optionSetInfos.get(i).setName(newName);
    }

    /**
     * Edits the specified OptionSetInfo's name
     * @param i OptionSetInfo id
     * @param j OptionInfo id
     * @param newName new OptionSetInfo name
     */
    public void editOptionInfoName(int i, int j, String newName) {
        optionSetInfos.get(i).getOptionInfos().get(j).setName(newName);
    }

    /**
     * Edits the specified OptionInfo's price
     * @param i OptionSetInfo id
     * @param j OptionInfo id
     * @param newPrice new OptionInfo price
     */
    public void editOptionInfoPrice(int i, int j, double newPrice) {
        optionSetInfos.get(i).getOptionInfos().get(j).setPrice(newPrice);
    }

    /**
     * Deletes the specified OptionInfo
     * @param i OptionSet id
     * @param j Option id
     */
    public void deleteOptionInfo(int i, int j){
        optionSetInfos.get(i).getOptionInfos().remove(j);
    }

    /**
     * Deletes the specified OptionSetInfo
     * @param i OptionSetInfo id
     */
    public void deleteOptionSetInfo(int i) {
        optionSetInfos.remove(i);
    }

    /**
     * toString method for the CarInfo
     * @return CarInfo String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CarInfo:\n").append(name).append("\n").append(basePrice);
        for (OptionSetInfo optionSetInfo : optionSetInfos) {
            sb.append("\n-----\n").append(optionSetInfo.getName());
            for (OptionInfo optionInfo : optionSetInfo.getOptionInfos()) {
                sb.append("\n").append(optionInfo.getName())
                        .append("\n").append(optionInfo.getPrice());
            }
        }
        return sb.toString();
    }

    public static CarInfo getInfoFromCar(Car car) {
        CarInfo carInfo = new CarInfo(car.getName(), car.getBasePrice());
        for (int i = 0; i < car.getOptionSetSize(); i++) {
            carInfo.addOptionSetInfo(car.getOptionSetName(i));
            for (int j = 0; j < car.getOptionSize(i); j++) {
                carInfo.addOptionInfo(i, car.getOptionName(i, j), car.getOptionPrice(i, j));
            }
        }
        return carInfo;
    }

}
