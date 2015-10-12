package imy.oreo.nancy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import imy.broadcastreceiver.AlarmReceiver;
import imy.oero.adatpters.TaskActionsAdapter;


public class DueTasksActivity extends ActionBarActivity {

    private TaskActionsAdapter taskActionsAdapter;
    private List<TaskAction> actionList;
    private SwipeMenuListView mListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_tasks);

        InitParse.initParse(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);;

        mListView = (SwipeMenuListView) findViewById(R.id.due_task_list);
        actionList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    actionList.clear();

                    Calendar calendar = Calendar.getInstance();

                    for (ParseObject post : postList) {
                        if(ParseUser.getCurrentUser().getUsername().equalsIgnoreCase(post.getString("Event_Owner"))) {

                            String[] timeparts = post.getString("Time").split("h");
                            Calendar taskCalendar = (Calendar) calendar.clone();
                            taskCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeparts[0]));
                            taskCalendar.set(Calendar.MINUTE, Integer.parseInt(timeparts[1]));
                            taskCalendar.set(Calendar.SECOND, 0);
                            taskCalendar.set(Calendar.MILLISECOND, 0);

                            if(!post.getString("Date").equals("Today")) {

                                String[] dateparts = post.getString("Date").split("/");
                                taskCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateparts[0]));
                                taskCalendar.set(Calendar.MONTH, Integer.parseInt(dateparts[1]));
                                taskCalendar.set(Calendar.YEAR, Integer.parseInt(dateparts[2]));
                            }

                            if(taskCalendar.compareTo(calendar) <= 0) {


                                TaskAction note = new TaskAction(post.getObjectId(), post.getString("Task"), post.getString("Date"), post.getString("Time"), R.mipmap.ic_action_expand);
                                actionList.add(note);
                            }


                        }
                    }

                    progressBar.setVisibility(View.GONE);
                    taskActionsAdapter = new TaskActionsAdapter(getApplicationContext(),actionList);
                    mListView.setAdapter(taskActionsAdapter);

                }
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(255, 193, 7)));
                // set item width
                editItem.setWidth(dp2px(90));
                // set a icon
                editItem.setIcon(R.mipmap.ic_action_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(244,67,54)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_action_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        edit(position);
                        break;
                    case 1:
                        delete(position);
                        break;
                }
                return false;
            }
        });

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // Left
        //mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_due_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           updateList();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doneTask(View view) {

        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void edit(int position) {

        TaskAction action = (TaskAction) taskActionsAdapter.getItem(position);

        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("Event.title", action.getTitle());
        intent.putExtra("Event.date", action.getDate());
        intent.putExtra("Event.time", action.getTime());
        intent.putExtra("Event.id", action.getId());
        startActivity(intent);
    }

    private void delete(int position) {


        TaskAction taskAction  = (TaskAction) taskActionsAdapter.getItem(position);
        ParseObject.createWithoutData("Events", taskAction.getId()).deleteEventually();
        taskActionsAdapter.remove(position);
        taskActionsAdapter.notifyDataSetChanged();
    }


    void updateList() {

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    actionList.clear();

                    Calendar calendar = Calendar.getInstance();

                    for (ParseObject post : postList) {
                        if(ParseUser.getCurrentUser().getUsername().equalsIgnoreCase(post.getString("Event_Owner"))) {

                            String[] timeparts = post.getString("Time").split("h");
                            Calendar taskCalendar = (Calendar) calendar.clone();
                            taskCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeparts[0]));
                            taskCalendar.set(Calendar.MINUTE, Integer.parseInt(timeparts[1]));
                            taskCalendar.set(Calendar.SECOND, 0);
                            taskCalendar.set(Calendar.MILLISECOND, 0);

                            if(!post.getString("Date").equals("Today")) {

                                String[] dateparts = post.getString("Date").split("/");
                                taskCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateparts[0]));
                                taskCalendar.set(Calendar.MONTH, Integer.parseInt(dateparts[1]));
                                taskCalendar.set(Calendar.YEAR, Integer.parseInt(dateparts[2]));
                            }

                            if(taskCalendar.compareTo(calendar) <= 0) {


                                TaskAction note = new TaskAction(post.getObjectId(), post.getString("Task"), post.getString("Date"), post.getString("Time"), R.mipmap.ic_action_expand);
                                actionList.add(note);
                            }


                        }
                    }

                    progressBar.setVisibility(View.GONE);
                    taskActionsAdapter = new TaskActionsAdapter(getApplicationContext(),actionList);
                    mListView.setAdapter(taskActionsAdapter);

                }
            }
        });
    }
}
