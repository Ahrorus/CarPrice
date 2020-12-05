import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

import java.util.ArrayList;

/**
 * Test class has test methods for the Car class.
 */
class CarTest {

    /**
     * Testing the getOptionSetSize method
     */
    @Test
    void testGetOptionSetSize() {
        // Case 1 - testing the size of the optionSet array list of a new car
        Car car = new Car("", 0);
        assertEquals(car.getOptionSetSize(), 0,
                "OptionSet list size for the new Car should be 0.");
        // Case 2 - testing the size of the optionSet array list of the same car but with 1 optionSet
        car.addOptionSet("Color");
        assertEquals(car.getOptionSetSize(), 1,
                "OptionSet list size for the Car with 1 OptionSet should be 1.");
    }

    /**
     * This method tests the OptionSize
     */
    @Test
    void testGetOptionSize() {
        // Case 1 - testing the size of the option array list inside optionSet of a new car with only 1 optionSet
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        assertEquals(car.getOptionSize(0), 0, "Option list size should be 0.");
        // Case 2 - Car with 1 OptionSet and 1 Option
        car.addOption(0, "Green", 1000);
        assertEquals(car.getOptionSize(0), 1, "Option list size should be 1.");
        // Case 3 - Checking Options list size of the OptionSet that does not exist
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.getOptionSize(1);
        });
    }

    /**
     * testsuit for testing AddOptionSet method
     */
    @Test
    void testAddOptionSet() {
        // Case 1 - testing the size of the OptionSet after adding a new OptionSet
        Car car = new Car("", 0);
        car.addOptionSet("Engine Type");
        assertEquals(car.getOptionSetSize(), 1,
                "OptionSet list size for the Car should increase by 1.");
        assertEquals(car.getOptionSetName(0), "Engine Type",
                "There should be a new OptionSet with name 'Engine Type'");
        // Case 2 - Testing the addition of a new optionSet
        car.addOptionSet("Moon Roof");
        assertEquals(car.getOptionSetSize(), 2, "OptionSetSize should now be 2");
        assertEquals(car.getOptionSetName(1), "Moon Roof", "There should be a new optionSet named 'Moon Roof' ");

    }

    /**
     * testsuit testing the AddOption method
     */
    @Test
    void testAddOption() {
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        // Case 1 - Testing the addition of new color Options, cheching the optionsize and also optionPrice
        car.addOption(0, "White", 0.0);
        assertEquals(car.getOptionSize(0), 1, "The optionSize should be 1");
        assertEquals(car.getOptionPrice(0,0), 0, "The optionPrice should be 0.0");
        assertEquals(car.getOptionName(0,0),"White", "The OptionName should be 'White' ");
        // Case 2 - Checking the the optionSize after adding another color option.
        car.addOption(0,"Gray",770);
        assertEquals(car.getOptionSize(0),2, "the optionSize should be 2");
    }



    /**
     * testsuit testing EditOptionSetName method
     */
    @Test
    void testEditOptionSetName() {
        // Creating a new Car with 1 optionSet
        Car car = new Car("",0);
        car.addOptionSet("Color");
        // Testing that 1 optionSet after editing it
        car.editOptionSetName(0,"colorsssss");
        assertEquals(car.getOptionSetName(0), "colorsssss", "The new OptionSetName should be 'colorsssss' ");
        // Testing editing a non-existing optionSet
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.editOptionSetName(1, "Fuel typeeee");
        });
    }

    /**
     * testsuit testing EditOptionName method
     */
    @Test
    void testEditOptionName() {
        // Creating a new Car with some options
        Car car = new Car ("", 0);
        car.addOptionSet("Color");
        car.addOption(0,"white",0);
        // Case 1 - Testing option name after changing it
        car.editOptionName(0,0,"Black");
        assertEquals(car.getOptionName(0,0), "Black", "The new option name should be Black");
        // Case 2 - Testing editing non-existing option's name
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.editOptionPrice(0,1,999);
        });
    }

    /**
     * testsuit for EditOptionPrice method
     */
    @Test
    void testEditOptionPrice() {
        // Creating a new Car with some options
        Car car = new Car ("", 0);
        car.addOptionSet("Color");
        car.addOption(0,"white",0);
        // Case 1 - Testing option price after changing it
        car.editOptionPrice(0,0,999);
        assertEquals(car.getOptionPrice(0,0), 999, "The new OptionPrice should be 999");
        // Case 2 - Testing editing non-existing option's price
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.editOptionPrice(0,1,999);
        });
    }

    /**
     * testsuit for DeleteOption method
     */
    @Test
    void testDeleteOption() {
        // Creating a new Car with some options
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        car.addOption(0,"Purple", 660);
        car.addOption(0, "Gray", 1000);
        car.deleteOption(0,0);
        // Case 1 - Testing option list inside the optionSet after deleting an option
        assertEquals(car.getOptionSize(0), 1, "There OptionSize should be 1 after the delete");
        assertEquals(car.getOptionName(0, 0), "Gray", "The first Color option should be Gray");
        // Case 2 - Testing deleting non-existing option
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.deleteOption(0, 1);
        });
    }

    /**
     * testsuit for DeleteOptionSet method
     */
    @Test
    void testDeleteOptionSet() {
        // Case 1 - Checking the optionSet list after deleting an optionSet
        Car car = new Car("", 0);
        car.addOptionSet("Color");
        car.addOptionSet("Fuel Type");
        car.deleteOptionSet(0);
        assertEquals(car.getOptionSetSize(),1,"The optionSetSize should be 1 after delete");
        assertEquals(car.getOptionSetName(0), "Fuel Type", "The first OptionSet should be Color");
        // Case 2 - Testing deleting non-existing optionSet
        assertThrows(IndexOutOfBoundsException.class, () -> {
            car.deleteOptionSet(1);
        });
    }

    /**
     * Testing the finish method.
     */
    @Test
    void testFinish() {
        // Creating a new Car with some options in it
        Car car = new Car("", 20000);
        car.addOptionSet("Fuel Type");
        car.addOption(0, "Gas", 0);
        car.addOption(0, "Hybrid", 3000);
        car.addOptionSet("Color");
        // Case 1 - testing finish method for a Car with an empty optionSet
        assertThrows(Exception.class, car::finish);
        // Case 2 - testing finish method for a Car with no empty options or optionSets
        car.addOption(1, "White", 0);
        car.addOption(1, "Black", 1000);
        assertDoesNotThrow(car::finish);
    }

    /**
     * testing the TotalPrice Method
     */
    @Test
    void testCalculateTotalPrice() {
        // Creating a new Car with some options in it
        Car car = new Car("", 20000);
        car.addOptionSet("Fuel Type");
        car.addOption(0, "Gas", 0);
        car.addOption(0, "Hybrid", 3000);
        car.addOptionSet("Color");
        car.addOption(1, "White", 0);
        car.addOption(1, "Black", 1000);
        assertDoesNotThrow(car::finish);
        // Case 1 - Testing total price of the Car with default option choices
        car.calculateTotalPrice();
        assertEquals(car.getTotalPrice(), 20000,
                "Total price of this car should be 20000.");
        // Case 2 - Testing total price of the Car with other options choices
        car.choose(0, 1);
        car.choose(1, 1);
        car.calculateTotalPrice();
        assertEquals(car.getTotalPrice(), 24000,
                "Total price of this car should be 24000.");
    }

}