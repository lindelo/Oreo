package imy.oero.adatpters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import imy.oreo.nancy.R;
import imy.oreo.nancy.TaskAction;
import imy.oreo.nancy.TaskViewHolder;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{

    private List<TaskAction> taskList;

    public TaskAdapter(List<TaskAction> tasks) {

        this.taskList = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        TaskAction task = taskList.get(position);
        holder.vTime.setText(task.getTime());
        holder.vDate.setText(task.getDate());
        holder.vTask.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
