package views;

import app_info.CarInfo;
import controllers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.BlockingQueue;

public class CheckoutCarView {

    BlockingQueue<Message> queue;

    private CarInfo carInfo;
    private JFrame frame;
    private JPanel outerPanel;
    private JButton checkoutButton;
    private JLabel carNameLabel;
    private JLabel basePriceLabel;
    private JLabel totalPriceLabel;
    private JPanel innerPanel;
    private JPanel[] panels;
    private JComboBox<String>[] comboBoxes;
    private JLabel[] priceLabels;

    public CheckoutCarView(BlockingQueue<Message> queue, CarInfo carInfo) {

        this.queue = queue;
        this.carInfo = carInfo;

        setupOuterComponents();
        setupInnerComponents();
        setupFrame();

        for (int i = 0; i < carInfo.getOptionSetInfosSize(); i++) {
            int optionSetId = i;
            comboBoxes[i].addItemListener(e -> {
                int optionId = comboBoxes[optionSetId].getSelectedIndex();
                try {
                    queue.put(new ChooseOptionMessage(optionSetId, optionId));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        }

        checkoutButton.addActionListener(e -> {
            try {
                queue.put(new CheckoutMessage());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    queue.put(new DisposeCheckoutCarViewMessage());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                super.windowClosing(e);
            }
        });

    }

    public JFrame getFrame() {
        return frame;
    }

    private void setupOuterComponents() {
        carNameLabel.setText(carInfo.getName());
        basePriceLabel.setText(Double.toString(carInfo.getBasePrice()));
        totalPriceLabel.setText("0");
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
    }

    private void setupInnerComponents() {
        int n = carInfo.getOptionSetInfosSize();
        panels = new JPanel[n];
        comboBoxes = new JComboBox[n];
        priceLabels = new JLabel[n];
        for (int i = 0; i < n; i++) {
            panels[i] = new JPanel();
            comboBoxes[i] = new JComboBox<>();
            priceLabels[i] = new JLabel("0");
            for (int j = 0; j < carInfo.getOptionInfosSize(i); j++) {
                comboBoxes[i].addItem(carInfo.getOptionInfoName(i, j));
            }
            innerPanel.add(panels[i]);
            panels[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            panels[i].add(new JLabel(carInfo.getOptionSetInfoName(i)));
            panels[i].add(comboBoxes[i]);
            panels[i].add(priceLabels[i]);
        }
    }

    private void setupFrame() {
        frame = new JFrame("Add New Car");
        frame.setContentPane(outerPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 500);
    }

    public void updateOptionPrice(int i, int j) {
        priceLabels[i].setText(Double.toString(carInfo.getOptionInfoPrice(i, j)));
    }

    public void updateTotalPrice(Double totalPrice) {
        totalPriceLabel.setText(Double.toString(totalPrice));
    }

}
