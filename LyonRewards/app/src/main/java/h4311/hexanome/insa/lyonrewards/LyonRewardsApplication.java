package h4311.hexanome.insa.lyonrewards;

import android.app.Application;

import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.component.DaggerAppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;

/**
 * Created by Pierre on 26/04/2016.
 */
public class LyonRewardsApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
            // list of modules that are part of this component need to be created here too
                        .appModule(new AppModule(this))
                        .build();

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
