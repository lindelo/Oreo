package imy.oreo.nancy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.parse.*;

import imy.oero.adatpters.ActionsAdapter;
import imy.oero.adatpters.TaskActionsAdapter;


public class MainActivity extends ActionBarActivity {
    private TaskActionsAdapter taskActionsAdapter;
    private List<TaskAction> actionList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(this, "kklfSNNgmK7kCdlIxzYzMHK7f6PZXbWhDkU0I2Q8", "zShNGOBu2cMzoiqhEWOW8PSue4K8pXNkgtm140Ow");
        ParseObject myEvent = new ParseObject("Events");
        myEvent.put("Task", "Maria's surprise birthday party.");
        myEvent.put("Date", "2025-03-25");
        myEvent.put("Time", "17:09");
        myEvent.saveInBackground();

        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.task_list);
        actionList = new ArrayList<>();

        //TaskAction duedate = new TaskAction("gfhcfgcgfcgfcgfgfgffgfdgfd", "23 Jun","13:38", R.mipmap.ic_action_expand);
       // TaskAction alarm = new TaskAction("Reminderhjhjvb", "21 Jul","13:30",R.mipmap.ic_action_expand);

       // actionList.add(duedate);
       // actionList.add(alarm);


        if(actionList.isEmpty()) {
            listView.setVisibility(View.GONE);
        }else
        {
            LinearLayout empty = (LinearLayout)findViewById(R.id.empty);
            TextView title = (TextView)findViewById(R.id.empty_title);
            ImageView src = (ImageView)findViewById(R.id.empty_src);
            title.setVisibility(View.GONE);
            src.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);

            taskActionsAdapter = new TaskActionsAdapter(getApplicationContext(), actionList);
            listView.setAdapter(taskActionsAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void toCreateTask(View view) {

        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
        startActivity(intent);
    }
}
