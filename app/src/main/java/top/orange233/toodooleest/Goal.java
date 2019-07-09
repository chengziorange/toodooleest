package top.orange233.toodooleest;

import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.crud.LitePalSupport;

public class Goal extends LitePalSupport implements BatModel {

    //这是在保存Goal时需要保存的成员
    private String name;
    private boolean isChecked;

    //数据库相关
    private int data_id;

    //构造器
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

    public void setData_id(int data_id) {
        this.data_id = data_id;
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
