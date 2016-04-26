package h4311.hexanome.insa.lyonrewards;

import android.app.Application;

import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.component.DaggerAppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;

/**
 * Created by Pierre on 26/04/2016.
 */
public class LyonRewardsApplication extends Application {

    protected static String API_BASE_URL = "https://lyonrewards.antoine-chabert.fr/api/";

    private AppComponent mAppComponent = createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule(API_BASE_URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
