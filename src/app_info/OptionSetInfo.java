package app_info;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionSetInfo implements Serializable {

    private String name;  // OptionSetInfo name
    private ArrayList<OptionInfo> optionInfos;  // List of OptionInfos

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OptionInfo> getOptionInfos() {
        return optionInfos;
    }

    public void setOptionInfos(ArrayList<OptionInfo> options) {
        this.optionInfos = options;
    }

    public OptionSetInfo(String name) {
        this.name = name;
        optionInfos = new ArrayList<>();
    }

}
