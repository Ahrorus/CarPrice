package views;

import controllers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

/**
 * AddCarView class represents the GUI frame for the main screen
 */
public class MainView {

    private BlockingQueue<Message> queue;

    private JFrame frame;
    private JPanel panel;
    private JButton addCarButton;
    private JButton editCarButton;
    private JButton checkoutCarButton;
    private JButton deleteCarButton;
    private JScrollPane scrollPane;
    private JList carList;

    /**Getter for the JList of cars
     * @return JList of cars
     */
    public JList getCarList() { return carList; }

    /**
     * Class constructor
     * @param queue blocking queue of Messages
     */
    public MainView(BlockingQueue<Message> queue) {

        this.queue = queue;
        setupFrame();

        try {
            this.queue.put(new DisplayCarListMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        carList.addListSelectionListener(e -> {
            try {
                queue.put(new ChooseCarMessage(carList.getSelectedIndex()));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        addCarButton.addActionListener(e -> {
            try {
                queue.put(new DisplayAddViewMessage());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });


        editCarButton.addActionListener(e -> {
            try {
                queue.put(new DisplayEditViewMessage());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        checkoutCarButton.addActionListener(e -> {
            try {
                queue.put(new DisplayCheckoutViewMessage());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        deleteCarButton.addActionListener(e -> {
            try {
                queue.put(new DeleteCarMessage());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    queue.put(new DisposeMainViewMessage());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                super.windowClosing(e);
            }
        });

    }

    /**
     * Enables the MainView
     */
    public void enableView() {
        frame.setEnabled(true);
    }

    /**
     * Disables the MainView
     */
    public void disableView() {
        frame.setEnabled(false);
    }

    /**
     * Initializes the frame
     */
    private void setupFrame() {
        frame = new JFrame("Car Price Application");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setFont(new Font("", Font.PLAIN, 16));
        frame.setLocation(400, 300);
    }

    /**
     * Displays deserialization error message
     */
    public void showDeserializationErrorDialog() {
        JOptionPane.showMessageDialog(frame,
                "Unable to load the list of cars from the disk. " +
                        "Dropping the current list and loading new test cars. " +
                        "\nClick OK and restart the application.",
                "Deserialization Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays serialization error message
     */
    public void showSerializationErrorDialog() {
        JOptionPane.showMessageDialog(frame,
                "Unable to save list of cars.",
                "Serialization Error",
                JOptionPane.ERROR_MESSAGE);
    }

}
