package h4311.hexanome.insa.lyonrewards.di.component;

import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTab;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeFoundActivity;

/**
 * Created by Pierre on 26/04/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(MainActivity activity);

    void inject(EventsFragmentGrandLyonTab fragment);

    void inject(EventDetailActivity activity);

    void inject(QrCodeFoundActivity activity);
}
