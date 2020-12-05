package controllers;

/**
 * Represents Message that is sent when the user clicks at some car in the car list.
 */
public class ChooseCarMessage implements Message {

    /**Represents the chosen car id */
    private int choice;

    /**Getter for the chosen car
     * @return chosen car id
     */
    public int getChoice() {
        return choice;
    }

    /**Class constructor */
    public ChooseCarMessage(int choice) {
        this.choice = choice;
    }

}
