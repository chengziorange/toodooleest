package top.orange233.toodooleest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<GoalFragment> goalFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), goalFragmentList);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
    }

    //测试用List<GoalFragment>
    private void initFragments() {
        goalFragmentList.add(new GoalFragment("随便生成的第一类任务",this));
        goalFragmentList.add(new GoalFragment("随便生成的第二类任务",this));
        goalFragmentList.add(new GoalFragment("随便生成的第三类任务",this));
    }

}
