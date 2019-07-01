package top.orange233.toodooleest;

import java.util.List;

public class Todos {
    private String title;
    private List<String> missionList;
    //构造器
    public Todos(String title,List<String> missionList) {
        this.title = title;
        this.missionList=missionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMissionList() {
        return missionList;
    }

    public void setMissionList(List<String> missionList) {
        this.missionList = missionList;
    }
}
