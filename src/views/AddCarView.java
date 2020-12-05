package views;

import app_info.CarInfo;
import controllers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * AddCarView class represents the GUI frame for adding a car
 */
public class AddCarView {

    BlockingQueue<Message> queue;

    private JFrame frame;
    private JPanel outerPanel;
    private JTextField carNameField;
    private JTextField basePriceField;
    private JButton saveCarButton;
    private JScrollPane scrollPane;
    private JButton addOptionSetButton;
    private JPanel innerPanel;
    private ArrayList<JPanel> optionSetPanels;
    private ArrayList<JTextField> optionSetNameFields;
    private ArrayList<JTextArea> optionAreas;
    private ArrayList<JButton> deleteButtons;

    /**
     * Class constructor
     * @param queue Blocking queue of Messages
     */
    public AddCarView(BlockingQueue<Message> queue) {

        this.queue = queue;
        optionSetPanels = new ArrayList<>();
        optionSetNameFields = new ArrayList<>();
        optionAreas = new ArrayList<>();
        deleteButtons = new ArrayList<>();
        setupFrame();

        addOptionSetButton.addActionListener(e -> {
            createOptionSet();
        });

        saveCarButton.addActionListener(e -> {
            try {
                queue.put(new AddCarMessage(readCarInfo()));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    queue.put(new EnableMainViewMessage());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                super.windowClosing(e);
            }
        });
    }

    /**
     * Creates a new OptionSet panel with fields
     */
    public void createOptionSet() {
        JPanel optionSetPanel = new JPanel();
        optionSetPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        optionSetPanel.setLayout(new BoxLayout(optionSetPanel, BoxLayout.PAGE_AXIS));
        JTextField optionSetNameField = new JTextField();
        JTextArea optionArea = new JTextArea();
        JButton deleteButton = new JButton("Delete OptionSet");
        optionSetPanels.add(optionSetPanel);
        optionSetNameFields.add(optionSetNameField);
        optionAreas.add(optionArea);
        deleteButtons.add(deleteButton);
        optionSetPanel.add(new JLabel("OptionSet name:"));
        optionSetPanel.add(optionSetNameField);
        optionSetPanel.add(new JLabel("Options:"));
        optionSetPanel.add(optionArea);
        optionSetPanel.add(deleteButton);
        innerPanel.add(optionSetPanel);
        deleteButton.addActionListener(e -> {
            optionSetNameFields.remove(optionSetNameField);
            optionAreas.remove(optionArea);
            deleteButtons.remove(deleteButton);
            optionSetPanels.remove(optionSetPanel);
            innerPanel.remove(optionSetPanel);
            innerPanel.revalidate();
            innerPanel.repaint();
        });
        optionSetPanel.setMinimumSize(new Dimension(400, 400));
        optionSetPanel.setMaximumSize(new Dimension(400, 400));
        optionSetNameField.setMinimumSize(new Dimension(300, 20));
        optionSetNameField.setMaximumSize(new Dimension(300, 20));
        optionArea.setMinimumSize(new Dimension(400, 200));
        deleteButton.setMinimumSize(new Dimension(300, 20));
        innerPanel.revalidate();
    }

    /**
     * Initializes the frame
     */
    private void setupFrame() {
        frame = new JFrame("Add New Car");
        frame.setContentPane(outerPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(1000, 300);
        frame.setSize(420, 500);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
    }

    /**
     * Reads info from the text fields and saves in a CarInfo instance
     * @return CarInfo instance
     */
    private CarInfo readCarInfo() {
        CarInfo carInfo = new CarInfo(
                carNameField.getText(),
                Double.parseDouble(basePriceField.getText())
        );
        for (int i = 0; i < optionSetPanels.size(); i++) {
            carInfo.addOptionSetInfo(optionSetNameFields.get(i).getText());
            readOptions(carInfo, i, optionAreas.get(i));
        }
        return carInfo;
    }

    /**
     * Reads info from the option text areas and saves in the given carInfo
     * @param carInfo CarInfo
     * @param i optionSet id
     * @param optionArea optionArea where we read from
     */
    private void readOptions(CarInfo carInfo, int i, JTextArea optionArea) {
        Scanner scanner = new Scanner(optionArea.getText());
        while (scanner.hasNextLine()) {
            String optionName = scanner.nextLine();
            Double optionPrice = Double.parseDouble(scanner.nextLine());
            carInfo.addOptionInfo(i, optionName, optionPrice);
        }
        scanner.close();
    }

    /**
     * Displays error message for saving the car
     */
    public void displayError(String msg) {
        JOptionPane.showMessageDialog(frame, msg,
                "SaveCar Error",
                JOptionPane.ERROR_MESSAGE);
    }

}
