package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.res.ObbInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventsFragmentGrandLyonTabViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Todo : use business object
    protected List<Object> events;

    public EventsFragmentGrandLyonTabViewAdapter(List<Object> events) {
        this.events = events;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event_layout, parent, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
