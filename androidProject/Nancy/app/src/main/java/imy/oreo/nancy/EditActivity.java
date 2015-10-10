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
import com.parse.*;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import imy.oero.adatpters.ActionsAdapter;


public class EditActivity extends ActionBarActivity
        implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private ActionsAdapter actionsAdapter;
    private List<Action> actionList;
    private ListView listView;
    private String mDate = "Today";
    private String mTime = "23h59";
    private String mTask;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        InitParse.initParse(this);

        Intent intent = getIntent();

        eventId = intent.getStringExtra("Event.id");
        String title = intent.getStringExtra("Event.title");
        String date = intent.getStringExtra("Event.date");
        String time = intent.getStringExtra("Event.time");

        listView = (ListView) findViewById(R.id.actions_list);
        actionList = new ArrayList<>();

        TextInputLayout taskWrapper = (TextInputLayout)  findViewById(R.id.taskWrapper);
        taskWrapper.getEditText().setText(title);

        Action duedate = new Action("Due Date", date, R.mipmap.ic_action_go_to_today);
        Action alarm = new Action("Reminder", time, R.mipmap.ic_action_alarms);

        actionList.add(duedate);
        actionList.add(alarm);

        actionsAdapter = new ActionsAdapter(getApplicationContext(), actionList);

        listView.setAdapter(actionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Action action = (Action) actionsAdapter.getItem(position);

                if (position == 0) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(EditActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }

                if (position == 1) {
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(EditActivity.this,
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
        action.setSubtitle(hourOfDay + "h" + zero + minute);
        mTime = hourOfDay+"h"+zero+minute;

        actionList.set(1, action);
        actionsAdapter = new ActionsAdapter(getApplicationContext(), actionList);
        listView.setAdapter(actionsAdapter);

    }

    public void editEvent(String task, String time, String date) {

        ParseObject myEvent = ParseObject.createWithoutData("Events", eventId);

        myEvent.put("Task", task);
        myEvent.put("Date", date);
        myEvent.put("Time", time);
        myEvent.put("Event_Owner", ParseUser.getCurrentUser().getUsername());

        myEvent.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    // The save failed.
                }
            }
        });

    }

    public void onEditTaskClicked(View view) {

        TextInputLayout taskWrapper = (TextInputLayout)  findViewById(R.id.taskWrapper);
        mTask = taskWrapper.getEditText().getText().toString();
        editEvent(mTask, mTime, mDate);
    }

}
