package main;

import app_info.CarInfo;
import controllers.*;
import model.*;
import views.MainView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private static MainView mainView;

    public static void main(String[] args) {
        mainView = new MainView(queue);
        SingleCarList.getInstance();
        CarController controller = new CarController(mainView, queue);

        controller.mainLoop();
        queue.clear();
        System.out.println("That's it, folks!");
    }

}
