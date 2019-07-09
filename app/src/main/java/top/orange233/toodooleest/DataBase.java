package top.orange233.toodooleest;

import android.app.Activity;
import android.widget.FrameLayout;

import com.yalantis.beamazingtoday.interfaces.BatModel;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.Operator.findAll;
import static org.litepal.Operator.where;

public class DataBase {
    public static void storeList(List<GoalFragment> goalFragments){
        for (int i = 0; i < goalFragments.size(); i++) {
            GoalFragment goalFragment=goalFragments.get(i);
            Data data=new Data();
            data.setFragmentname(goalFragment.getFragmentName());
            data.setThemeNumber(goalFragment.getThemeNumber());
            data.save();
            List<BatModel> goalList=goalFragment.getmGoals();
            for (int j = 0; j < goalList.size(); j++) {
                Goal goal= (Goal) goalList.get(j);
                goal.setData_id(data.getId());
                goal.save();
            }
        }
    }

    public static List<GoalFragment> readList(Activity mActivity){
        List<GoalFragment> list=new ArrayList<>();
        List<Data> dataList=findAll(Data.class);
//        List<BatModel> resultList=new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            List<BatModel> resultList=new ArrayList<>();
            Data data=dataList.get(i);
            List<Goal> goalList=where("data_id=?",Integer.toString(data.getId())).find(Goal.class);
            if (goalList==null||goalList.size()<1) continue;
            resultList.addAll(goalList);
            GoalFragment goalFragment=new GoalFragment(data.getFragmentname(),mActivity,resultList,data.getThemeNumber());
            list.add(goalFragment);
        }
        return list;
    }
}
