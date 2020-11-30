package views;

import controllers.CarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCarView {

    private CarController controller;

    private JFrame frame;
    private JPanel outerPanel;
    private JTextField carNameField;
    private JTextField basePriceField;
    private JButton saveCarButton;
    private JScrollPane scrollPane;
    private JButton addOptionSetButton;
    private JPanel innerPanel;

    public AddCarView(CarController controller) {
        this.controller = controller;
        launch();



    }

    public JFrame getFrame() {
        return frame;
    }

    private void launch() {
        frame = new JFrame("Add New Car");
        frame.setContentPane(outerPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 500);
//        controller.disableMainView();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                controller.enableMainView();
                super.windowClosing(e);
            }
        });
    }

}
