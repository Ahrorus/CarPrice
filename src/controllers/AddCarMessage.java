package controllers;

import app_info.CarInfo;

/**
 * Represents Message that is sent when the user clicks the Save Car button in the AddCarView.
 */
public class AddCarMessage implements Message {

    /**Represents the CarInfo instance */
    private CarInfo carInfo;

    /**Getter for the carInfo
     * @return Car's info
     */
    public CarInfo getCarInfo() {
        return carInfo;
    }

    /**Class constructor
     * @param carInfo Car's info
     */
    public AddCarMessage(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

}
