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
    private List<Object> args = null;

    public HistoryFragment(String tag, String title, List<Object> args) {
        this.tag = tag;
        this.title = title;
        if (args != null) {
            this.args = new ArrayList<>(args);
        }
    }

    public HistoryFragment(String tag, String title) {
        this.tag = tag;
        this.title = title;
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
}
