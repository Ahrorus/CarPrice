import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

import java.util.ArrayList;

class CarTest {

    @Test
    void testGetOptionSetSize() {
        // Case 1
        Car car = new Car("", 0);
        assertEquals(car.getOptionSetSize(), 0,
                "OptionSet list size for the new Car should be 0.");
        // Case 2
        car.addOptionSet("Color");
        assertEquals(car.getOptionSetSize(), 1,
                "OptionSet lis size for the Car with 1 OptionSet should be 1.");
    }

    @Test
    void testGetOptionSize() {
        // Case 1
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        assertEquals(car.getOptionSize(0), 0, "Option list size should be 0.");
        // Case 2
        car.addOption(0, "Green", 1000);
        assertEquals(car.getOptionSize(0), 1, "Option list size should be 1.");
    }

    @Test
    void testAddOptionSet() {
        // Case 1
        Car car = new Car("", 0);
        car.addOptionSet("Engine Type");
        assertEquals(car.getOptionSetSize(), 1,
                "OptionSet list size for the Car should increase by 1.");
        assertEquals(car.getOptionSetName(0), "Engine Type",
                "There should be a new OptionSet with name 'Engine Type'");
        // Case 2
        car.addOptionSet("Moon Roof");
        assertEquals(car.getOptionSetSize(), 2, "OptionSetSize should now be 2");
        assertEquals(car.getOptionSetName(1), "Moon Roof", "There should be a new optionSet named 'Moon Roof' ");

    }

    @Test
    void testAddOption() {
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        // Case 1
        car.addOption(0, "White", 0.0);
        assertEquals(car.getOptionSize(0), 1, "The optionSize should be 1");
        assertEquals(car.getOptionPrice(0,0), 0, "The optionPrice should be 0.0");
        assertEquals(car.getOptionName(0,0),"White", "The OptionName should be 'White' ");
        // Case 2
        car.addOption(0,"Gray",770);
        assertEquals(car.getOptionSize(0),2, "the optionSize should be 2");
    }

    @Test
    void testGetOptionSetName() {
    }

    @Test
    void testGetOptionName() {
    }

    @Test
    void testGetOptionPrice() {
    }

    @Test
    void testEditOptionSetName() {
        Car car = new Car("",0);
        car.addOptionSet("Color");
        car.editOptionSetName(0,"colorsssss");
        assertEquals(car.getOptionSetName(0), "colorsssss", "The new OptionSetName should be 'colorsssss' ");

    }

    @Test
    void testEditOptionName() {
    }

    @Test
    void testEditOptionPrice() {
        Car car = new Car ("", 0);
        car.addOptionSet("Color");
        car.addOption(0,"white",0);
        car.editOptionPrice(0,0,999);
        assertEquals(car.getOptionPrice(0,0), 999, "The new OptionPrice should be 999");
        //case 2
        //assertEquals(car.getTotalPrice(),999,"The new total price should increase by 999");

    }

    @Test
    void testDeleteOption() {
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        car.addOption(0,"purple", 660);
        car.deleteOption(0,0);
        //OptionSize
        assertEquals(car.getOptionSize(0), 0, "There OptionSize should be 0 after the delete");
        //case 2
        //assertEquals(car.getOptionPrice(0,0),0,
         //       "The optionPrice after deleting the particular option at that index should be 0");
    }

    @Test
    void testDeleteOptionSet() {
        //OptionSetSize
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        car.deleteOptionSet(0);
        assertEquals(car.getOptionSetSize(),0,"The optionSetSize should be 0 after delete");
    }

    @Test
    void testCalculateTotalPrice() {
        Car car = new Car("", 20000);
        car.addOptionSet("Fuel Type");
        car.addOption(0, "Gas", 0);
        car.addOption(0, "Hybrid", 3000);
        car.addOptionSet("Color");
        car.addOption(1, "White", 0);
        car.addOption(1, "Black", 1000);
        try {
            car.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Case 1
        car.calculateTotalPrice();
        assertEquals(car.getTotalPrice(), 20000,
                "Total price of this new car should be 20000.");
        // Case 2
        car.choose(0, 1);
        car.choose(1, 1);
        car.calculateTotalPrice();
        assertEquals(car.getTotalPrice(), 24000,
                "Total price of this new car should be 24000.");
    }

}