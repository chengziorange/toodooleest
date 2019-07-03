package top.orange233.toodooleest;

import com.yalantis.beamazingtoday.interfaces.BatModel;

public class Goal implements BatModel {

    //这是在保存Goal时需要保存的成员
    private String name;
    private boolean isChecked;

    public Goal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String getText() {
        return getName();
    }

}
