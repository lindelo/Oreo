package imy.oero.adatpters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import imy.oreo.nancy.Action;
import imy.oreo.nancy.R;
import imy.oreo.nancy.TaskAction;

public class TaskActionsAdapter extends BaseAdapter {
    private List<TaskAction> actionList;
    private Context context;

    public TaskActionsAdapter(Context context, List<TaskAction> actionList) {

        this.context = context;
        this.actionList = actionList;
    }

    public void remove(int position) {

        actionList.remove(position);
    }

    public boolean isEmpty() {

        return  actionList.isEmpty();
    }

    @Override
    public int getCount() {
        return actionList.size();
    }

    @Override
    public Object getItem(int position) {
        return actionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TaskAction action = actionList.get(position);

        convertView = layoutInflater.inflate(R.layout.task_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.task_content);
        TextView date = (TextView) convertView.findViewById(R.id.task_date);
        TextView time = (TextView) convertView.findViewById(R.id.task_time);
        //ImageView src = (ImageView) convertView.findViewById(R.id.task_src);

        title.setText(action.getTitle());
        date.setText(action.getDate());
        time.setText(action.getTime());
        //src.setImageResource(action.getSrc());

        return convertView;
    }
}
