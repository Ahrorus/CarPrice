package controllers;

/**
 * Represents Message that is sent when the user chooses an option from the menus of choices in the CheckoutCarView.
 */
public class ChooseOptionMessage implements Message {

    /**Represents id of the optionSet. */
    private int optionSetId;
    /**Represents id of the chose option. */
    private int optionId;

    /**Getter for the option set id
     * @return id of the option set
     */
    public int getOptionSetId() {
        return optionSetId;
    }

    /**Setter for the option set id
     * @param optionSetId option set id
     */
    public void setOptionSetId(int optionSetId) {
        this.optionSetId = optionSetId;
    }

    /**Getter for the option id
     * @return id of the option
     */
    public int getOptionId() {
        return optionId;
    }

    /**Setter for the option id
     * @param optionId option id
     */
    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    /**Class constructor
     * @param optionSetId option set id
     * @param optionId option id
     */
    public ChooseOptionMessage(int optionSetId, int optionId) {
        this.optionSetId = optionSetId;
        this.optionId = optionId;
    }

}
