package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.rewards.OfferDetailFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;

/**
 * Created by Pierre on 01/05/2016.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_tag_title)
        protected TextView mTag;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setTag(String tag) {
            mTag.setText(tag);
        }


    }

    protected List<String> tags;

    public TagsAdapter(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setTag(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

}