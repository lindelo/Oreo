package imy.oreo.nancy;

import android.content.Intent;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import imy.oero.adatpters.ActionsAdapter;


public class EventActivity extends ActionBarActivity
        implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private ActionsAdapter actionsAdapter;
    private List<Action> actionList;
    private ListView listView;
    private String mDate = "Today";
    private String mTime = null;
    private String mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        listView = (ListView) findViewById(R.id.actions_list);
        actionList = new ArrayList<>();

        Action duedate = new Action("Due Date", "Today", R.mipmap.ic_action_go_to_today);
        Action alarm = new Action("Reminder", "No reminder", R.mipmap.ic_action_alarms);

        actionList.add(duedate);
        actionList.add(alarm);

        actionsAdapter = new ActionsAdapter(getApplicationContext(), actionList);

        listView.setAdapter(actionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Action action = (Action) actionsAdapter.getItem(position);

                if(position == 0)
                {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(EventActivity.this ,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }

                if(position == 1)
                {
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(EventActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            true
                    );

                    tpd.show(getFragmentManager(), "Timepickerdialog");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {

        Action action = (Action) actionsAdapter.getItem(0);
        action.setSubtitle(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

        mDate = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        actionList.set(0, action);
        actionsAdapter = new ActionsAdapter(getApplicationContext(), actionList);
        listView.setAdapter(actionsAdapter);

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        String zero = minute > 9 ? "" : "0";

        Action action = (Action) actionsAdapter.getItem(1);
        action.setSubtitle(hourOfDay+"h"+zero+minute);
        mTime = hourOfDay+"h"+zero+minute;

        actionList.set(1, action);
        actionsAdapter = new ActionsAdapter(getApplicationContext(), actionList);
        listView.setAdapter(actionsAdapter);

    }

    public void addEvent(String task, String time, String date) {

        Toast.makeText(getApplicationContext(), task + " " + time + " " + date, Toast.LENGTH_LONG).show();
        //push to database
//added here

        ParseObject myEvent = new ParseObject("Events");
        myEvent.put("Task", task);
        myEvent.put("Date", date);
        myEvent.put("Time", time);
        myEvent.saveInBackground();
//end here
    }

    public void onCreateTaskClicked(View view) {

        TextInputLayout taskWrapper = (TextInputLayout)  findViewById(R.id.taskWrapper);
        mTask = taskWrapper.getEditText().getText().toString();
        addEvent(mTask, mTime, mDate);
    }
}
