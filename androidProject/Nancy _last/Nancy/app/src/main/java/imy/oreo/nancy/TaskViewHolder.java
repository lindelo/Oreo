package imy.oreo.nancy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView vTask;
    public TextView vDate;
    public TextView vTime;

    public TaskViewHolder(View view) {
        super(view);

        vDate = (TextView) view.findViewById(R.id.task_date);
        vTask = (TextView) view.findViewById(R.id.task_content);
        vTime = (TextView) view.findViewById(R.id.task_time);
    }
}
