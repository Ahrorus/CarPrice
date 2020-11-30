package model;

import java.util.ArrayList;

public class SingleCarList {

    private static SingleCarList instance = new SingleCarList();
    private ArrayList<Car> cars;

    private SingleCarList() {
        cars = new ArrayList<>();
    }

    public static SingleCarList getInstance() {
        return instance;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

}
