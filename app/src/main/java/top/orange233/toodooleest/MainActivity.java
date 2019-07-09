package top.orange233.toodooleest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<GoalFragment> mFragments = new ArrayList<>();
    private Activity mActivity = this;
    private FragAdapter mAdapter;
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private int mTheme = 1;
    private Drawer mDrawer;
    private MyReceiver mMyReceiver;
    private AccountHeader mAccountHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LitePal.initialize(this);
        View view = View.inflate(mActivity, R.layout.activity_main, null);
        setContentView(view);
        switch (mTheme) {
            case 1:
                getWindow().setStatusBarColor(getResources().getColor(R.color.themeBlue400));
                Log.d("TAG", "古天乐蓝了！");
                view.setBackground(getDrawable(R.drawable.gradient_blue));
                break;
            case 2:
                getWindow().setStatusBarColor(getResources().getColor(R.color.themeGreen400));
                view.setBackground(getDrawable(R.drawable.gradient_green));
                break;
            case 3:
                getWindow().setStatusBarColor(getResources().getColor(R.color.themeYellow400));
                view.setBackground(getDrawable(R.drawable.gradient_yellow));
                break;
            case 4:
                getWindow().setStatusBarColor(getResources().getColor(R.color.themeAmber400));
                view.setBackground(getDrawable(R.drawable.gradient_amber));
                break;
            case 5:
                getWindow().setStatusBarColor(getResources().getColor(R.color.themeOrange400));
                view.setBackground(getDrawable(R.drawable.gradient_orange));
                break;
        }

//        File dateBaseFile = mActivity.getDatabasePath("AllData.db");
//        if (dateBaseFile.exists()) {
//            mFragments = DataBase.ReadFragment(mActivity);
//        } else {
//            initFragments();
//        }

        initFragments();

        //初始化适配器、ViewPager，并将适配器传入ViewPager
        mAdapter = new FragAdapter(getSupportFragmentManager(), mFragments);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);

        //初始化小圆点指示器
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        switch (mTheme) {
            case 1:
                mCircleIndicator.setSelectedColor(getResources().getColor(R.color.themeBlue100));
                mCircleIndicator.setNormalColor(getResources().getColor(R.color.themeBlue400));
                break;
            case 2:
                mCircleIndicator.setSelectedColor(getResources().getColor(R.color.themeGreen100));
                mCircleIndicator.setNormalColor(getResources().getColor(R.color.themeGreen400));
                break;
            case 3:
                mCircleIndicator.setSelectedColor(getResources().getColor(R.color.themeYellow100));
                mCircleIndicator.setNormalColor(getResources().getColor(R.color.themeYellow400));
                break;
            case 4:
                mCircleIndicator.setSelectedColor(getResources().getColor(R.color.themeAmber100));
                mCircleIndicator.setNormalColor(getResources().getColor(R.color.themeAmber400));
                break;
            case 5:
                mCircleIndicator.setSelectedColor(getResources().getColor(R.color.themeOrange100));
                mCircleIndicator.setNormalColor(getResources().getColor(R.color.themeOrange400));
                break;
        }
        notifyIndicatorChange();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCircleIndicator.setPosition(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //初始化按钮，监听点击响应
        FloatingActionButton openMenu = findViewById(R.id.open_menu);
        openMenu.setOnClickListener(this);
        FloatingActionButton deleteFragment = findViewById(R.id.delete_frag);
        deleteFragment.setOnClickListener(this);

        //初始化左侧菜单
        initDrawer(mActivity);

    }

    //销毁前保存状态
//    @Override
//    protected void onDestroy() {
//        DataBase.storeList(mFragments);
//        super.onDestroy();
//    }

    //响应按键
    @Override
    public void onClick(View view) {
        int currentPage = mViewPager.getCurrentItem(); //为index，从0开始
        int totalPage = mFragments.size();

        int viewId = view.getId();
        switch (viewId) {
            case R.id.open_menu:
                mDrawer.openDrawer();
                break;
            case R.id.delete_frag:
                if (totalPage <= 1) {
                    Toast.makeText(mActivity, "你不能删除最后一个页面！", Toast.LENGTH_SHORT).show();
                } else {
                    mFragments.remove(currentPage);
                    mAdapter = new FragAdapter(getSupportFragmentManager(), mFragments);
                    mViewPager.setAdapter(mAdapter);
                    notifyIndicatorChange();
                    if (currentPage == (totalPage - 1)) {
                        mViewPager.setCurrentItem(currentPage - 1, true);
                    } else {
                        mViewPager.setCurrentItem(currentPage, true);
                    }
                    Log.d("TAG", "DD没了");
                    break;
                }
        }
    }

    //初始化Drawer菜单
    private void initDrawer(Activity activity) {
        //创建头像栏
        switch (mTheme) {
            case 1:
                mAccountHeader = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.gradient_blue)
                        .addProfiles(
                                new ProfileDrawerItem()
                                        .withName("TooDooLeest")
                                        .withIcon(android.R.drawable.ic_menu_edit)
                        )
                        .build();
                break;
            case 2:
                mAccountHeader = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.gradient_green)
                        .addProfiles(
                                new ProfileDrawerItem().withName("TooDooLeest")
                        )
                        .build();
                break;
            case 3:
                mAccountHeader = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.gradient_yellow)
                        .addProfiles(
                                new ProfileDrawerItem().withName("TooDooLeest")
                        )
                        .build();
                break;
            case 4:
                mAccountHeader = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.gradient_amber)
                        .addProfiles(
                                new ProfileDrawerItem().withName("TooDooLeest")
                        )
                        .build();
                break;
            case 5:
                mAccountHeader = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.gradient_orange)
                        .addProfiles(
                                new ProfileDrawerItem().withName("TooDooLeest")
                        )
                        .build();
                break;
        }

        //创建菜单列表
        mDrawer = new DrawerBuilder(activity)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new SecondaryDrawerItem()
                                .withIdentifier(1)
                                .withName("添加页面")
                                .withIcon(android.R.drawable.ic_menu_add),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withIdentifier(2)
                                .withName("设置提醒时间"),
                        new PrimaryDrawerItem()
                                .withIdentifier(3)
                                .withName("更换皮肤"),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName("导出数据"),
                        new PrimaryDrawerItem()
                                .withIdentifier(5)
                                .withName("导入数据")
                )//响应点击
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {

                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    final EditText editText=new EditText(mActivity);
                                    new AlertDialog.Builder(mActivity)
                                            .setTitle("输入新页面名")
                                            .setView(editText)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String input=editText.getText().toString();
                                                    mFragments.add(new GoalFragment(input, mActivity, new ArrayList<BatModel>(), mTheme));
                                                    mAdapter = new FragAdapter(getSupportFragmentManager(), mFragments);
                                                    mViewPager.setAdapter(mAdapter);
                                                    notifyIndicatorChange();
                                                    mViewPager.setCurrentItem(mFragments.size(), true);
                                                }
                                            })
                                            .setNegativeButton("取消",null)
                                            .show();
                                    break;
                                case 2:
                                    mDrawer.closeDrawer();
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.MaterialBaseTheme_Light_AlertDialog, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                            //创建通知栏提醒
                                            //unregisterReceiver(mMyReceiver);
                                            mMyReceiver = new MyReceiver(true, hourOfDay, minute);
                                            IntentFilter filter = new IntentFilter();
                                            filter.addAction(Intent.ACTION_TIME_TICK);
                                            registerReceiver(mMyReceiver, filter);
                                        }
                                    }, 17, 10, true);
                                    timePickerDialog.show();
                                    break;
                                case 3:
                                    //mDrawer.closeDrawer();
                                    final String[] theme = new String[]{"蓝色", "绿色", "黄色", "琥珀", "橙色"};
                                    AlertDialog alertDialog = null;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                                    alertDialog = builder.setTitle("选择皮肤(重启生效)")
                                            .setItems(theme, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    switch (i + 1) {
                                                        case 1:
                                                            mTheme = 1;
                                                            break;
                                                        case 2:
                                                            mTheme = 2;
                                                            break;
                                                        case 3:
                                                            mTheme = 3;
                                                            break;
                                                        case 4:
                                                            mTheme = 4;
                                                            break;
                                                        case 5:
                                                            mTheme = 5;
                                                            break;
                                                    }
                                                }
                                            })
                                            .create();
                                    alertDialog.show();
                                    break;
                            }
                        }
                        return false;
                    }
                })
                .withSelectedItem(-1)
                .build();
        mDrawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawer.getDrawerLayout().addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                mDrawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                mDrawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //更新小圆点指示器状态
    private void notifyIndicatorChange() {
        mCircleIndicator.setCount(mFragments.size());
    }

    //测试用List<GoalFragment>
    private void initFragments() {
        mFragments.add(new GoalFragment("随便生成的第一类任务", mActivity, initGoals(), mTheme));
        mFragments.add(new GoalFragment("随便生成的第二类任务", mActivity, initGoals(), mTheme));
        mFragments.add(new GoalFragment("随便生成的第三类任务", mActivity, initGoals(), mTheme));
    }

    //测试用List<Goal>
    public List<BatModel> initGoals() {
        List<BatModel> goals = new ArrayList<>();
        goals.add(new Goal("这是一个示例任务"));
        goals.add(new Goal("向左/右划删除任务"));
        goals.add(new Goal("点击左边圆圈标记完成"));
        goals.add(new Goal("标记完成的任务会沉底"));
        goals.add(new Goal("取消标记的任务会浮顶"));
        goals.add(new Goal("凑数任务一"));
        goals.add(new Goal("凑数任务二"));
        goals.add(new Goal("凑数任务三"));
        goals.add(new Goal("凑数任务四"));
        goals.add(new Goal("凑数任务五"));
        goals.add(new Goal("凑数任务六"));
        goals.add(new Goal("凑数任务七"));
        goals.add(new Goal("凑数任务八"));
        goals.add(new Goal("凑数任务九"));
        goals.add(new Goal("凑数任务十"));
        return goals;
    }
}
