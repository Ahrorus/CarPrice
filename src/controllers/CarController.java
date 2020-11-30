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

public class CarController {

    private BlockingQueue<Message> queue;
    private List<Valve> valves = new LinkedList<Valve>();
    public ArrayList<Car> cars;
    private MainView mainView;
    private AddCarView addCarView;
    private EditCarView editCarView;
    private CheckoutCarView checkoutCarView;

    private DefaultListModel listModel;
    private int carChoice;

    public CarController(MainView mainView, BlockingQueue<Message> queue) {
        this.cars = SingleCarList.getInstance().getCars();
        this.mainView = mainView;
        this.queue = queue;
        listModel = new DefaultListModel();
        valves.add(new DisplayCarListValve());
        valves.add(new ChooseCarValve());
        valves.add(new DeleteCarValve());
        valves.add(new DisposeMainViewValve());
        valves.add(new CheckoutCarValve());
        valves.add(new ChooseOptionValve());
        valves.add(new CheckoutValve());
        valves.add(new DisposeCheckoutCarViewValve());
    }

    public int getCarChoice() {
        return carChoice;
    }

    public void setCarChoice(int carChoice) {
        this.carChoice = carChoice;
    }

    public void refreshList() {
        listModel.removeAllElements();
        for (int i = 0; i < cars.size(); i++) {
            listModel.addElement(cars.get(i).getName());
        }
    }

    // Creates first test Cars
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

    private void updateGameInfo() {
        //
    }

    public void saveCars() {
        try {
            FileIO.serialize(cars);
            System.out.println("Cars are saved.");
        } catch (Exception e) {
            mainView.showSerializationErrorDialog();
            e.printStackTrace();
        }
    }

    // Main Loop
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

    private interface Valve {
        /**Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }

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

    private class CheckoutCarValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != CheckoutCarMessage.class) {
                return ValveResponse.MISS;
            }

            CarInfo carInfo = CarInfo.getInfoFromCar(cars.get(carChoice));
            mainView.disableView();
            checkoutCarView = new CheckoutCarView(queue, carInfo);

            return ValveResponse.EXECUTED;
        }
    }

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

    private class DisposeCheckoutCarViewValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != DisposeCheckoutCarViewMessage.class) {
                return ValveResponse.MISS;
            }

            mainView.enableView();

            return ValveResponse.EXECUTED;
        }
    }

}
