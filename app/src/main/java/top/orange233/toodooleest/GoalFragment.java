package top.orange233.toodooleest;

import android.app.Activity;
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
    private Activity mActivity;

    //这是在保存GoalFragment时需要保存的
    private String fragmentName;
    private List<BatModel> mGoals = new ArrayList<>();

    //测试时使用该Constructor
    public GoalFragment(String fragmentName, Activity mActivity) {
        this.fragmentName = fragmentName;
        this.mActivity = mActivity;
    }

    //读取时使用该Constructor
    public GoalFragment(String fragmentName, Activity mActivity, List<BatModel> mGoals) {
        this.fragmentName = fragmentName;
        this.mActivity = mActivity;
        this.mGoals = mGoals;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_fragment, container, false);
        initGoals();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        //需要extends AppCompatActivity才能使用，准备放在主Activity里
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

//        ((TextView) getActivity().findViewById(R.id.text_title)).setTypeface(TypefaceUtil.getAvenirTypeface(getActivity()));
//        ((TextView) getActivity().findViewById(R.id.text_title)).setText(fragmentName);

        mRecyclerView = (BatRecyclerView) view.findViewById(R.id.bat_recycler_view);
        mAnimator = new BatItemAnimator();

        mRecyclerView.getView().setLayoutManager(new LinearLayoutManager(getActivity()));
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
        Toast.makeText(getActivity(), item.getText(), Toast.LENGTH_SHORT).show();
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

    //测试用List<Goal>
    public void initGoals() {
        mGoals.add(new Goal("这是一个示例任务"));
        mGoals.add(new Goal("向左/右划删除任务"));
        mGoals.add(new Goal("点击左边圆圈标记完成"));
        mGoals.add(new Goal("标记完成的任务会沉底"));
        mGoals.add(new Goal("取消标记的任务会浮顶"));
        mGoals.add(new Goal("凑数任务一"));
        mGoals.add(new Goal("凑数任务二"));
        mGoals.add(new Goal("凑数任务三"));
        mGoals.add(new Goal("凑数任务四"));
        mGoals.add(new Goal("凑数任务五"));
        mGoals.add(new Goal("凑数任务六"));
        mGoals.add(new Goal("凑数任务七"));
        mGoals.add(new Goal("凑数任务八"));
        mGoals.add(new Goal("凑数任务九"));
        mGoals.add(new Goal("凑数任务十"));
    }
}
