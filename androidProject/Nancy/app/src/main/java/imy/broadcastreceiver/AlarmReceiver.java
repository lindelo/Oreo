package imy.broadcastreceiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import imy.oero.adatpters.TaskActionsAdapter;
import imy.oreo.nancy.DueTasksActivity;
import imy.oreo.nancy.InitParse;
import imy.oreo.nancy.MainActivity;
import imy.oreo.nancy.R;
import imy.oreo.nancy.SendEmail;
import imy.oreo.nancy.TaskAction;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final Context innerContext = context;
        Calendar calendarNow = Calendar.getInstance();

        InitParse.initParse(context);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

        final int[] numberOfTaskDue = {0};

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    Calendar calendar = Calendar.getInstance();
                    Calendar calendar1 = Calendar.getInstance();
                    String date = calendar.getTime().getDay() + "/" + (calendar.getTime().getMonth() + 1) + "/" + calendar.getTime().getYear();
                    int timeH = calendar.getTime().getHours();
                    int timeM = calendar.getTime().getMinutes();
                    String tasks = null;
                    String emailTo = ParseUser.getCurrentUser().getUsername();

                    for (ParseObject post : postList) {

                        if (ParseUser.getCurrentUser().getUsername().equalsIgnoreCase(post.getString("Event_Owner"))) {

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

                            //Toast.makeText(innerContext, date + " " + timeH + "h" + timeM, Toast.LENGTH_LONG).show();
                            //numberOfTaskDue[0]++;

                            if(taskCalendar.compareTo(calendar) <= 0) {

                                numberOfTaskDue[0]++;

                            }
                            //if (date.equals(post.getString("Date"))) {

                               /* String[] timeparts = post.getString("Time").split("h");

                                if (Integer.parseInt(timeparts[0]) == timeH && Integer.parseInt(timeparts[1]) == timeM) {

                                    numberOfTaskDue[0]++;

                                    if (tasks == null) {

                                        tasks = post.getString("Task");

                                    } else {

                                        tasks = tasks.concat("\n" + post.get("Task"));
                                    }
                                }*/
                            //}
                        }
                    }

                    Toast.makeText(innerContext, "FFDSFDS", Toast.LENGTH_LONG).show();
                    Notify(innerContext, "You have " + numberOfTaskDue[0] + " task(s) due", "Click here to view");

                }
            }
        });



    }

    private void Notify(Context context, String notificationTitle, String notificationMessage){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.mipmap.notification,"Task(s) Due", System.currentTimeMillis());
        Intent notificationIntent = new Intent(context, DueTasksActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, notificationTitle,notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }
}
