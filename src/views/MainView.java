package views;

import controllers.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

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

    public JList getCarList() { return carList; }

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

        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });


        editCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        checkoutCarButton.addActionListener(e -> {
            try {
                queue.put(new CheckoutCarMessage());
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

    public void enableView() {
        frame.setEnabled(true);
    }

    public void disableView() {
        frame.setEnabled(false);
    }

    private void setupFrame() {
        frame = new JFrame("Car Price Application");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void showDeserializationErrorDialog() {
        JOptionPane.showMessageDialog(frame,
                "Unable to load the list of cars from the disk. " +
                        "Dropping the current list and loading new test cars. " +
                        "\nClick OK and restart the application.",
                "Deserialization Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSerializationErrorDialog() {
        JOptionPane.showMessageDialog(frame,
                "Unable to save list of cars.",
                "Serialization Error",
                JOptionPane.ERROR_MESSAGE);
    }

}
