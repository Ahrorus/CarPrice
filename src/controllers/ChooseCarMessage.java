package controllers;

public class ChooseCarMessage implements Message {

    private int choice;

    public int getChoice() {
        return choice;
    }

    public ChooseCarMessage(int choice) {
        this.choice = choice;
    }

}
