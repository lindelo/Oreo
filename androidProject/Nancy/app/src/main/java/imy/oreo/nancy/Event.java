package imy.oreo.nancy;

public class Event {

    private String task, date, time;

    public Event(String task_, String date_, String time_)
    {
        task = task_;
        date = date_;
        time = time_;
    }

    public String getTask(){
        return task;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }

    public void setTask(String newTask){
        task = newTask;
    }

    public void setDate(String newDate) {
        date = newDate;
    }

    public void setTime(String newTime){
        time = newTime;
    }
}