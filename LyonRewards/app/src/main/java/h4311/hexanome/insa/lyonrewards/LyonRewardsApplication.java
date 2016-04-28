package h4311.hexanome.insa.lyonrewards;

import android.app.Application;

import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.component.DaggerAppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;
import h4311.hexanome.insa.lyonrewards.di.module.image.ImageModule;

/**
 * Created by Pierre on 26/04/2016.
 */
public class LyonRewardsApplication extends Application {

    private AppComponent mAppComponent = createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .imageModule(new ImageModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
