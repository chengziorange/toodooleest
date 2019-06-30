package top.orange233.toodooleest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        private TextView todosName;

        public ViewHolder(View view) {
            super(view);
            todosName = (TextView) view.findViewById(R.id.todos_name);
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
        holder.todosName.setText(todos.getName());
    }

    @Override
    public int getItemCount() {
        return mTodosList.size();
    }
}
