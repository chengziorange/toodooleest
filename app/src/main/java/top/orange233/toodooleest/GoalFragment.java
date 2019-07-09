package top.orange233.toodooleest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GoalFragment extends Fragment implements BatListener, OnItemClickListener, OnOutsideClickedListener {
    private BatRecyclerView mRecyclerView;
    private BatAdapter mAdapter;
    private BatItemAnimator mAnimator;

    //这是在保存GoalFragment时需要保存的
    private Activity mActivity;
    private String fragmentName;
    private List<BatModel> mGoals = new ArrayList<>();
    private int mTheme;

    //读取时使用该Constructor。新增参数theme
    public GoalFragment(String fragmentName, Activity mActivity, List<BatModel> mGoals, int mTheme) {
        this.fragmentName = fragmentName;
        this.mActivity = mActivity;
        this.mGoals = mGoals;
        this.mTheme = mTheme;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public List<BatModel> getmGoals() {
        return mGoals;
    }

    public int getThemeNumber() {
        return mTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mActivity = getActivity();

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        //需要extends AppCompatActivity才能使用，准备放在主Activity里
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

//        ((TextView) getActivity().findViewById(R.id.text_title)).setTypeface(TypefaceUtil.getAvenirTypeface(getActivity()));
//        ((TextView) getActivity().findViewById(R.id.text_title)).setText(fragmentName);

        mRecyclerView = (BatRecyclerView) view.findViewById(R.id.bat_recycler_view);
        mAnimator = new BatItemAnimator();

        mRecyclerView.getView().setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.getView().setAdapter(mAdapter = new BatAdapter(mGoals, this, mAnimator)
                .setOnItemClickListener(this).setOnOutsideClickListener(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new BatCallback(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView.getView());
        mRecyclerView.getView().setItemAnimator(mAnimator);
        mRecyclerView.setAddItemListener(this);

        view.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.revertAnimation();
            }
        });

        //主题颜色设置
        setTheme(mTheme);
    }

    @Override
    public void add(String string) {
        mGoals.add(0, new Goal(string));
        mAdapter.notify(AnimationType.ADD, 0);
    }

    @Override
    public void delete(int position) {
        mGoals.remove(position);
        mAdapter.notify(AnimationType.REMOVE, position);
    }

    @Override
    public void move(int from, int to) {
        if (from >= 0 && to >= 0) {
            mAnimator.setPosition(to);
            BatModel model = mGoals.get(from);
            mGoals.remove(model);
            mGoals.add(to, model);
            mAdapter.notify(AnimationType.MOVE, from, to);

            if (from == 0 || to == 0) {
                mRecyclerView.getView().scrollToPosition(Math.min(from, to));
            }
        }
    }

    @Override
    public void onClick(BatModel item, int position) {
        Toast.makeText(mActivity, item.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClicked() {
        mRecyclerView.revertAnimation();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((TextView) mActivity.findViewById(R.id.text_title)).setText(fragmentName);
            Log.d("TAG", fragmentName + "is visible now");
        }
    }

    private void setTheme(int theme) {
        switch (theme) {
            case 1:
                mRecyclerView.setListBackgroundColor(getResources().getColor(R.color.themeBlue100));
                mRecyclerView.setPlusColor(getResources().getColor(R.color.themeBlue100));
                mRecyclerView.setCursorColor(getResources().getColor(R.color.themeBlue100));
                mRecyclerView.setAddButtonColor(Color.WHITE);
                break;
            case 2:
                mRecyclerView.setListBackgroundColor(getResources().getColor(R.color.themeGreen100));
                mRecyclerView.setPlusColor(getResources().getColor(R.color.themeGreen100));
                mRecyclerView.setCursorColor(getResources().getColor(R.color.themeGreen100));
                mRecyclerView.setAddButtonColor(Color.WHITE);
                break;
            case 3:
                mRecyclerView.setListBackgroundColor(getResources().getColor(R.color.themeYellow100));
                mRecyclerView.setPlusColor(getResources().getColor(R.color.themeYellow100));
                mRecyclerView.setCursorColor(getResources().getColor(R.color.themeYellow100));
                mRecyclerView.setAddButtonColor(Color.WHITE);
                break;
            case 4:
                mRecyclerView.setListBackgroundColor(getResources().getColor(R.color.themeAmber100));
                mRecyclerView.setPlusColor(getResources().getColor(R.color.themeAmber100));
                mRecyclerView.setCursorColor(getResources().getColor(R.color.themeAmber100));
                mRecyclerView.setAddButtonColor(Color.WHITE);
                break;
            case 5:
                mRecyclerView.setListBackgroundColor(getResources().getColor(R.color.themeOrange100));
                mRecyclerView.setPlusColor(getResources().getColor(R.color.themeOrange100));
                mRecyclerView.setCursorColor(getResources().getColor(R.color.themeOrange100));
                mRecyclerView.setAddButtonColor(Color.WHITE);
                break;
        }
    }
}
