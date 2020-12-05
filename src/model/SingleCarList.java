package model;

import java.util.ArrayList;

/**
 * SingleCarList class represents a Singleton that contains an arraylist of Cars.
 */
public class SingleCarList {

    /**Represents the single instance of the class */
    private static SingleCarList instance = new SingleCarList();
    /**Represents an arraylist of Cars */
    private ArrayList<Car> cars;

    /**Default class constructor
     */
    private SingleCarList() {
        cars = new ArrayList<>();
    }


    /**Getter for the singleton
     * @return SingleCarList instance
     */
    public static SingleCarList getInstance() {
        return instance;
    }

    /**Getter for the arraylist of Cars
     * @return ArrayList of Cars
     */
    public ArrayList<Car> getCars() {
        return cars;
    }

}
