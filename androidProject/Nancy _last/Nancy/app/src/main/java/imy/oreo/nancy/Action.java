package imy.oreo.nancy;

import android.graphics.drawable.Drawable;

public class Action {

    private String title;
    private String subtitle;
    private int src;

    public Action(String title, String subtitle, int src) {

        this.title = title;
        this.subtitle = subtitle;
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getSrc() {
        return src;
    }
}
