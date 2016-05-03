package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.view.tracker.TravelCardView;

/**
 * Created by Pierre on 03/05/2016.
 */
public class InfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_username)
    protected TextView mUsername;

    @BindView(R.id.profile_name)
    protected TextView mName;

    @BindView(R.id.profile_email)
    protected TextView mEmail;

    public InfoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    public void setUser(User user) {
        mUsername.setText(user.getUsername());
        mName.setText(user.getFirstName() + " " + user.getLastName());
        mEmail.setText(user.getEmail());
    }
}
