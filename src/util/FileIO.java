package util;

import model.*;

import java.io.*;
import java.util.ArrayList;

public class FileIO {

    /**Location of the data folder with serialized files */
    private static final String FILE_NAME = "cars.ser";

    /**
     * Serializes the list of cars and saves in the disk
     * @param cars list of Cars
     * @throws Exception
     */
    public static void serialize(ArrayList<Car> cars) throws Exception {
        FileOutputStream file = new FileOutputStream(FILE_NAME);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(cars);
        out.close();
        file.close();
    }

    /**
     * Deserializes the cars.ser file into an ArrayList of Cars and returns it
     * @return Deserialized ArrayList of Cars
     * @throws Exception
     */
    public static ArrayList<Car> deserialize() throws Exception {
        FileInputStream file = new FileInputStream(FILE_NAME);
        ObjectInputStream in = new ObjectInputStream(file);
        ArrayList<Car> cars = (ArrayList<Car>) in.readObject();
        in.close();
        file.close();
        return cars;
    }

}
