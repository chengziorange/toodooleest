package top.orange233.toodooleest;

import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Data extends LitePalSupport {
    private int id;
    private String fragmentname;
    private int ThemeNumber;
    private List<BatModel> goalList=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFragmentname() {
        return fragmentname;
    }

    public void setFragmentname(String fragmentname) {
        this.fragmentname = fragmentname;
    }

    public int getThemeNumber() {
        return ThemeNumber;
    }

    public void setThemeNumber(int themeNumber) {
        ThemeNumber = themeNumber;
    }

    public List<BatModel> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<BatModel> goalList) {
        this.goalList = goalList;
    }
}
