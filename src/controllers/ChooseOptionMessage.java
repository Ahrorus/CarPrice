package controllers;

public class ChooseOptionMessage implements Message {

    private int optionSetId;
    private int optionId;

    public int getOptionSetId() {
        return optionSetId;
    }

    public void setOptionSetId(int optionSetId) {
        this.optionSetId = optionSetId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public ChooseOptionMessage(int optionSetId, int optionId) {
        this.optionSetId = optionSetId;
        this.optionId = optionId;
    }

}
