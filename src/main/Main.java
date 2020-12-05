package main;

import app_info.CarInfo;
import controllers.*;
import model.*;
import views.MainView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents the main class that launches the entire application.
 */
public class Main {

    /**Represents a static blocking queue of Messages */
    private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    /**Represents MainView */
    private static MainView mainView;

    /**
     * The main method
     * @param args some unused arguments
     */
    public static void main(String[] args) {

        mainView = new MainView(queue);
        SingleCarList.getInstance();
        CarController controller = new CarController(mainView, queue);

        controller.mainLoop();
        queue.clear();
        System.out.println("That's it, folks!");
        System.exit(0);
    }

}
