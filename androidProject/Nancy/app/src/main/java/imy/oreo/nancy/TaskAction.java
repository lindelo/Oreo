package imy.oreo.nancy;

public class TaskAction {
    private String title;
    private String date;
    private String time;
    private String id;
    private int src;

    public TaskAction(String id, String title, String date, String time, int src) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.src = src;
    }

    public String getId() { return id;}
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
