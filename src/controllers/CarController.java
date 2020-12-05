package controllers;

import app_info.CarInfo;
import model.*;
import util.FileIO;
import views.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Application's Controller class.
 * Handles Messages, received from the Views in the corresponding Valves.
 */
public class CarController {
    /**Represents a blocking queue of Messages */
    private BlockingQueue<Message> queue;
    /**Represents a list of Valves */
    private List<Valve> valves = new LinkedList<Valve>();
    /**Represents an array list of Cars */
    public ArrayList<Car> cars;
    /**Represents a default list model for the car list */
    private DefaultListModel listModel;
    /**Represents an id of the chosen Car */
    private int carChoice;

    /**Represents MainView */
    private MainView mainView;
    /**Represents AddCarView */
    private AddCarView addCarView;
    /**Represents EditCarView */
    private EditCarView editCarView;
    /**Represents CheckoutCarView */
    private CheckoutCarView checkoutCarView;

    /**Class constructor
     * @param mainView MainView
     * @param queue blocking queue of Messages
     */
    public CarController(MainView mainView, BlockingQueue<Message> queue) {
        this.cars = SingleCarList.getInstance().getCars();
        this.mainView = mainView;
        this.queue = queue;
        listModel = new DefaultListModel();
        valves.add(new DisplayCarListValve());
        valves.add(new ChooseCarValve());
        valves.add(new DeleteCarValve());
        valves.add(new DisposeMainViewValve());
        valves.add(new EnableMainViewValve());
        valves.add(new DisplayCheckoutViewValve());
        valves.add(new ChooseOptionValve());
        valves.add(new CheckoutValve());
        valves.add(new DisplayAddViewValve());
        valves.add(new AddCarValve());
        valves.add(new DisplayEditViewValve());
        valves.add(new EditCarValve());
    }

    /**
     * Refreshes the car list
     */
    public void refreshList() {
        listModel.removeAllElements();
        for (int i = 0; i < cars.size(); i++) {
            listModel.addElement(cars.get(i).getName());
        }
    }

    /**
     * Generates and stores a list 2 initial test cars
     * @throws Exception
     */
    public void makeTestCars() throws Exception {
        // Car 1
        Car car1 = new Car("Honda Civic", 40000);
        car1.addOptionSet("Type");
        car1.addOptionSet("Fuel kind");
        car1.addOptionSet("Color");
        car1.editOptionSetName(1, "Fuel Type");
        car1.addOption(0, "RX", 0);
        car1.addOption(0, "LX", 4000);
        car1.addOption(0, "EX", 9999);
        car1.deleteOption(0, 2);
        car1.addOption(1, "Gas", 0);
        car1.addOption(1, "Hybrid", 3000);
        car1.addOption(1, "CNG", -4000);
        car1.addOption(2, "Gray Metallic", 0);
        car1.addOption(2, "Gray Clear Coat", 1000);
        car1.addOption(2, "White Metallic", 1000);
        car1.addOption(2, "White Clear Coat", 1500);
        car1.addOption(2, "Dark Metallic", 9999);
        car1.editOptionName(2, 4, "Black Metallic");
        car1.editOptionPrice(2, 4, 1500);
        car1.finish();
        cars.add(car1);
        // Car 2
        Car car2 = new Car("Tesla Model S", 70000);
        car2.addOptionSet("Drive Type");
        car2.addOptionSet("Moonroof");
        car2.addOptionSet("Color");
        car2.addOption(0, "fwd", 0);
        car2.addOption(0, "rwd", 2000);
        car2.addOption(0, "4wd", 5000);
        car2.addOption(1, "No", 0);
        car2.addOption(1, "Yes", 1000);
        car2.addOption(2, "White Metallic", 0);
        car2.addOption(2, "Black Metallic", 1500);
        car2.addOption(2, "Silver Coat", 1500);
        car2.addOption(2, "Red Metallic", 1500);
        car2.finish();
        cars.add(car2);
        // Save initial test cars
        saveCars();
    }

    /**
     * Saves the car list in the disk
     */
    public void saveCars() {
        try {
            FileIO.serialize(cars);
            System.out.println("Cars are saved.");
        } catch (Exception e) {
            mainView.showSerializationErrorDialog();
            e.printStackTrace();
        }
    }

    /**
     * Directs Messages from the Views them to their corresponding Valves
     */
    public void mainLoop() {
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;
        while (response != ValveResponse.FINISH) {
            try {
                message = queue.take(); // <--- take next message from the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Look for a Valve that can process a message
            for (Valve valve : valves) {
                response = valve.execute(message);
                // if successfully processed or game over, leave the loop
                if (response != ValveResponse.MISS) {
                    break;
                }
            }
        }
    }

    /**Valve interface class represents a template for the Valve classes that handle Messages.
     * Every Message has its corresponding Valve.
     */
    private interface Valve {
        /**Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }

    /**Responds to the DisplayCarListMessage.
     * Displays Car List in the MainView.
     */
    private class DisplayCarListValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisplayCarListMessage.class) {
                return ValveResponse.MISS;
            }
            mainView.getCarList().setModel(listModel);
            try {
                cars = FileIO.deserialize();
                refreshList();
            } catch (Exception ex1) {
                mainView.showDeserializationErrorDialog();
                try {
                    makeTestCars();
                } catch (Exception ex2) {
                    mainView.showSerializationErrorDialog();
                }
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the ChooseCarMessage.
     * Chooses a car in the car list when the car is clicked.
     */
    private class ChooseCarValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != ChooseCarMessage.class) {
                return ValveResponse.MISS;
            }
            carChoice = ((ChooseCarMessage) message).getChoice();
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DeleteCarMessage.
     * Deletes the chosen Car from the list.
     */
    private class DeleteCarValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DeleteCarMessage.class) {
                return ValveResponse.MISS;
            }
            cars.remove(carChoice);
            refreshList();
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DisposeMainViewMessage.
     * Saves the car list in the disk.
     */
    private class DisposeMainViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisposeMainViewMessage.class) {
                return ValveResponse.MISS;
            }
            saveCars();
            return ValveResponse.FINISH;
        }
    }

    /**Responds to the EnableMainViewMessage.
     * Enables the MainView.
     */
    private class EnableMainViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != EnableMainViewMessage.class) {
                return ValveResponse.MISS;
            }
            mainView.enableView();
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DisplayCheckoutViewMessage.
     * Displays the CheckoutView.
     */
    private class DisplayCheckoutViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisplayCheckoutViewMessage.class) {
                return ValveResponse.MISS;
            }
            CarInfo carInfo = CarInfo.getInfoFromCar(cars.get(carChoice));
            mainView.disableView();
            checkoutCarView = new CheckoutCarView(queue, carInfo);
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the ChooseOptionMessage.
     * Stores the user's option choice in the Car's choices[] array.
     */
    private class ChooseOptionValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != ChooseOptionMessage.class) {
                return ValveResponse.MISS;
            }
            int i = ((ChooseOptionMessage) message).getOptionSetId();
            int j = ((ChooseOptionMessage) message).getOptionId();
            cars.get(carChoice).choose(i, j);
            checkoutCarView.updateOptionPrice(i, j);
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the CheckoutMessage.
     * Calculates and displays the total price of the Car.
     */
    private class CheckoutValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != CheckoutMessage.class) {
                return ValveResponse.MISS;
            }
            cars.get(carChoice).calculateTotalPrice();
            checkoutCarView.updateTotalPrice(cars.get(carChoice).getTotalPrice());
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DisplayAddViewMessage.
     * Displays the AddCarView.
     */
    private class DisplayAddViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisplayAddViewMessage.class) {
                return ValveResponse.MISS;
            }
            mainView.disableView();
            addCarView = new AddCarView(queue);
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the AddCarMessage.
     * Adds the newly configured Car to the list of cars.
     */
    private class AddCarValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != AddCarMessage.class) {
                return ValveResponse.MISS;
            }
            Car car = ((AddCarMessage) message).getCarInfo().getCar();
            try {
                car.finish();
                cars.add(car);
                refreshList();
                System.out.println("Successfully added a new Car.");
            } catch (Exception e) {
                addCarView.displayError(e.getMessage());
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DisplayEditViewMessage.
     * Displays the EditCarView.
     */
    private class DisplayEditViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisplayEditViewMessage.class) {
                return ValveResponse.MISS;
            }
            CarInfo carInfo = CarInfo.getInfoFromCar(cars.get(carChoice));
            mainView.disableView();
            editCarView = new EditCarView(queue, carInfo);
            return ValveResponse.EXECUTED;
        }
    }

    /**Responds to the DisplayEditViewMessage.
     * Updates the current car in the list.
     */
    private class EditCarValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != EditCarMessage.class) {
                return ValveResponse.MISS;
            }
            Car car = ((EditCarMessage) message).getCarInfo().getCar();
            try {
                car.finish();
                cars.set(carChoice, car);
                refreshList();
                System.out.println("Successfully edited existing Car.");
            } catch (Exception e) {
                editCarView.displayError(e.getMessage());
            }
            return ValveResponse.EXECUTED;
        }
    }

}
