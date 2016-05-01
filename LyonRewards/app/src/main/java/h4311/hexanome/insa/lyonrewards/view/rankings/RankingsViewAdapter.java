package h4311.hexanome.insa.lyonrewards.view.rankings;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;

/**
 * Created by Jonathan on 30/04/2016.
 */
public class RankingsViewAdapter extends RecyclerView.Adapter<RankingsViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected MainActivity mMainActivity;

        @BindView(R.id.ranking_position)
        protected TextView mRankingPosition;

        @BindView(R.id.ranking_username)
        protected TextView mRankingUsername;

        @BindView(R.id.ranking_nb_points)
        protected TextView mRankingNbPoints;

        private User user;

        private User currentUser;

        private int ranking;

        public ViewHolder(View view, MainActivity mainActivity, User currentUser) {
            super(view);
            ButterKnife.bind(this, view);

            mMainActivity = mainActivity;
            this.currentUser = currentUser;
        }

        public void setUser(User user, int ranking) {
            this.user = user;
            this.ranking = ranking;
            displayUserRanking();
        }

        private void displayUserRanking() {
            if(user.equals(currentUser)) {
                mRankingPosition.setTypeface(null, Typeface.BOLD);
                mRankingUsername.setTypeface(null, Typeface.BOLD);
                mRankingNbPoints.setTypeface(null, Typeface.BOLD);
            }
            mRankingPosition.setText(String.valueOf(ranking));
            mRankingUsername.setText(user.getUsername());
            mRankingNbPoints.setText(String.valueOf(user.getGlobalPoints()));
        }
    }

    protected List<User> users;

    protected MainActivity mainActivity;

    @Inject
    protected ConnectionManager connectionManager;

    public RankingsViewAdapter(List<User> users, MainActivity mainActivity) {
        this.users = users;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_layout, parent, false);

        ((LyonRewardsApplication) mainActivity.getApplication()).getAppComponent().inject(this);

        return new ViewHolder(view, mainActivity, connectionManager.getConnectedUser());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setUser(users.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
