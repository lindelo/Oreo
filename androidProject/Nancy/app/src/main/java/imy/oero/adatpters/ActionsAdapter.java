package imy.oero.adatpters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import imy.oreo.nancy.Action;
import imy.oreo.nancy.R;

public class ActionsAdapter extends BaseAdapter {

    private List<Action> actionList;
    private Context context;

    public ActionsAdapter(Context context, List<Action> actionList) {

        this.context = context;
        this.actionList = actionList;
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
        Action action = actionList.get(position);

        convertView = layoutInflater.inflate(R.layout.action_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.action_title);
        TextView subtitle = (TextView) convertView.findViewById(R.id.action_subtitle);
        ImageView src = (ImageView) convertView.findViewById(R.id.action_src);

        title.setText(action.getTitle());
        subtitle.setText(action.getSubtitle());
        src.setImageResource(action.getSrc());

        return convertView;
    }

    public void setItem(int position, Action item) {

        actionList.set(position, item);
    }
}
