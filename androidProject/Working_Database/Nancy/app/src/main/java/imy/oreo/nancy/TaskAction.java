package imy.oreo.nancy;

public class TaskAction {
    private String title;
    private String date;
    private String time;
    private int src;

    public TaskAction(String title, String date, String time, int src) {

        this.title = title;
        this.date = date;
        this.time = time;
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public int getSrc() {
        return src;
    }
}
