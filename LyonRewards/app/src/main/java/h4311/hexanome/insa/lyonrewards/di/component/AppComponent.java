package h4311.hexanome.insa.lyonrewards.di.component;

import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;
import h4311.hexanome.insa.lyonrewards.di.module.auth.AuthModule;
import h4311.hexanome.insa.lyonrewards.di.module.image.ImageModule;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailDescriptionFragmentTabViewAdapter;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailPointsFragment;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailPointsFragmentTabViewAdapter;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTab;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentMyEventsTab;
import h4311.hexanome.insa.lyonrewards.view.login.LoginActivity;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeFoundActivity;
import h4311.hexanome.insa.lyonrewards.view.rewards.OfferDetailFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragmentOffersAdapter;

/**
 * Created by Pierre on 26/04/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class, ImageModule.class, AuthModule.class})
public interface AppComponent {
    void inject(MainActivity activity);

    void inject(EventsFragmentGrandLyonTab fragment);

    void inject(EventDetailActivity activity);

    void inject(QrCodeFoundActivity activity);

    void inject(RewardsFragment rewardsFragment);

    void inject(OfferDetailFragment offerDetailActivity);

    void inject(RewardsFragmentOffersAdapter.ViewHolder viewHolder);

    void inject(LoginActivity loginActivity);

    void inject(EventDetailPointsFragment eventDetailPointsFragment);

    void inject(EventDetailDescriptionFragmentTabViewAdapter.ViewHolder viewHolder);

    void inject(EventDetailPointsFragmentTabViewAdapter.ViewHolder viewHolder);

    void inject(EventsFragmentMyEventsTab eventsFragmentMyEventsTab);
}
