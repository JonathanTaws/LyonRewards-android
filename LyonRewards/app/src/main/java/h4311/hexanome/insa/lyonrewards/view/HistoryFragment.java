package h4311.hexanome.insa.lyonrewards.view;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public class HistoryFragment {

    private String tag;
    private String title;
    private boolean previousIcon;
    private List<Object> args = null;

    public HistoryFragment(String tag, String title, boolean previousIcon, List<Object> args) {
        this.tag = tag;
        this.title = title;
        this.previousIcon = previousIcon;
        if (args != null) {
            this.args = new ArrayList<>(args);
        }
    }

    public HistoryFragment(String tag, String title, boolean previousIcon) {
        this.tag = tag;
        this.title = title;
        this.previousIcon = previousIcon;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public List<Object> getArgs() {
        return args;
    }

    public boolean isPreviousIcon() {
        return previousIcon;
    }
}
