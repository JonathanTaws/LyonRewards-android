package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pierre on 28/04/2016.
 */
public class EventDetailPointsFragmentTabViewAdapter extends RecyclerView.Adapter<EventDetailPointsFragmentTabViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_detail_success_nb_done)
        protected TextView mSuccessNbDone;

        @BindView(R.id.event_detail_success_nb_total)
        protected TextView mSuccessNbTotal;

        @BindView(R.id.event_detail_success_percentage_done)
        protected TextView mSuccessPercentageDone;

        @BindView(R.id.event_detail_success_progressbar_done)
        protected View mViewSuccessProgressDone;

        @BindView(R.id.event_detail_success_progressbar_todo)
        protected View mViewSuccessProgressTodo;

        private Event mEvent = null;
        private FragmentActivity mActivity;
        private FragmentManager mChildFragmentManager;

        @BindView(R.id.event_detail_success_recyclerview)
        protected RecyclerView mRecyclerViewDone;

        @BindView(R.id.event_detail_success_recyclerview_unknown)
        protected RecyclerView mRecyclerViewUnknown;

        @BindView(R.id.event_detail_success_no_success_container)
        protected View mNoSuccessContainer;

        @BindView(R.id.event_detail_success_container)
        protected View mSuccessContainer;

        @BindView(R.id.event_detail_success_unlocked_empty)
        protected View mSuccessDoneEmpty;

        @BindView(R.id.event_detail_success_locked_empty)
        protected View mSuccessUnknownEmpty;

        private RecyclerView.Adapter mAdapterDone;
        private RecyclerView.Adapter mAdapterUnknown;

        private List<QRCodeCitizenAct> mContentSuccessDone = new ArrayList<>();
        private List<QRCodeCitizenAct> mContentSuccessUnknown = new ArrayList<>();

        @Inject
        protected LyonRewardsApi mLyonRewardsApi;

        @Inject
        protected ConnectionManager mConnectionManager;

        public ViewHolder(View view, FragmentActivity activity, FragmentManager childFragmentManager) {
            super(view);
            mActivity = activity;
            this.mChildFragmentManager = childFragmentManager;
            ButterKnife.bind(this, view);
            ((LyonRewardsApplication) mActivity.getApplication()).getAppComponent().inject(this);
        }

        public void setEvent(Event event) {
            mEvent = event;

            // RecyclerView binding
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            mRecyclerViewDone.setLayoutManager(layoutManager);
            mRecyclerViewDone.setHasFixedSize(true);
            mRecyclerViewDone.setNestedScrollingEnabled(false);

            mAdapterDone = new EventSuccessAdapter(mContentSuccessDone);
            mRecyclerViewDone.setAdapter(mAdapterDone);

            MaterialViewPagerHelper.registerRecyclerView(mActivity, mRecyclerViewDone, null);

            RecyclerView.LayoutManager layoutManagerUnknown = new LinearLayoutManager(mActivity);
            mRecyclerViewUnknown.setLayoutManager(layoutManagerUnknown);
            mRecyclerViewUnknown.setHasFixedSize(true);
            mRecyclerViewUnknown.setNestedScrollingEnabled(false);

            mAdapterUnknown = new EventSuccessAdapter(mContentSuccessUnknown);
            mRecyclerViewUnknown.setAdapter(mAdapterUnknown);

            MaterialViewPagerHelper.registerRecyclerView(mActivity, mRecyclerViewUnknown, null);

            mLyonRewardsApi.getQrCodesFromEvent(mEvent.getId(), mConnectionManager.getConnectedUser().getId(), new Callback<List<QRCodeCitizenAct>>() {
                @Override
                public void onResponse(Call<List<QRCodeCitizenAct>> call, Response<List<QRCodeCitizenAct>> response) {
                    if (response.isSuccessful()) {

                        List<QRCodeCitizenAct> listQrCode = response.body();

                        if (listQrCode.isEmpty()) {
                            mNoSuccessContainer.setVisibility(View.VISIBLE);
                        } else {
                            mSuccessContainer.setVisibility(View.VISIBLE);
                            for (QRCodeCitizenAct qrCodeCitizenAct : listQrCode) {
                                if (qrCodeCitizenAct.isCompleted()) {
                                    mContentSuccessDone.add(qrCodeCitizenAct);
                                } else {
                                    mContentSuccessUnknown.add(qrCodeCitizenAct);
                                }
                            }

                            if (mContentSuccessDone.isEmpty()) {
                                mRecyclerViewDone.setVisibility(View.GONE);
                                mSuccessDoneEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mAdapterDone.notifyDataSetChanged();
                            }

                            if (mContentSuccessUnknown.isEmpty()) {
                                mRecyclerViewUnknown.setVisibility(View.GONE);
                                mSuccessUnknownEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mAdapterUnknown.notifyDataSetChanged();
                            }

                            // Update ui in main fragment
                            int nbSuccess = mContentSuccessDone.size() + mContentSuccessUnknown.size();
                            int nbDone = (int) (nbSuccess * mEvent.getUserProgression());
                            float progress = mEvent.getUserProgression() * 100.0f;
                            float progressTodo = 100 - progress;
                            mSuccessNbTotal.setText(String.valueOf(nbSuccess));
                            mSuccessNbDone.setText(String.valueOf(nbDone));
                            mSuccessPercentageDone.setText(String.format("%.0f", progress));
                            mViewSuccessProgressDone.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, progress));
                            mViewSuccessProgressTodo.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, progressTodo));
                        }
                    } else {
                        // todo : handle error
                    }
                }

                @Override
                public void onFailure(Call<List<QRCodeCitizenAct>> call, Throwable t) {
                    // TODO : Error message
                    Log.d("API", "error : " + t.getMessage());
                }
            });
        }
    }

    protected List<Event> events;
    private FragmentActivity mActivity;
    private FragmentManager mChildFragmentManager;

    public EventDetailPointsFragmentTabViewAdapter(List<Event> events, FragmentActivity activity, FragmentManager childFragmentManager) {
        this.events = events;
        this.mActivity = activity;
        this.mChildFragmentManager = childFragmentManager;
    }

    @Override
    public EventDetailPointsFragmentTabViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_detail_points, parent, false);
        return new ViewHolder(view, mActivity, mChildFragmentManager);
    }

    @Override
    public void onBindViewHolder(EventDetailPointsFragmentTabViewAdapter.ViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}
