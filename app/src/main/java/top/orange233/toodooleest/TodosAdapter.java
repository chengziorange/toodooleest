package top.orange233.toodooleest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private List<Todos> mTodosList;

    public TodosAdapter(List<Todos> todosList) {
        mTodosList = todosList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout missionsInTodo;
        private CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            missionsInTodo = (LinearLayout) view.findViewById(R.id.missions_in_todo);
            //checkBox= (CheckBox)view.findViewById(R.id.check_box);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.todos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todos todos = mTodosList.get(position);
    }

    @Override
    public int getItemCount() {
        return mTodosList.size();
    }
}
