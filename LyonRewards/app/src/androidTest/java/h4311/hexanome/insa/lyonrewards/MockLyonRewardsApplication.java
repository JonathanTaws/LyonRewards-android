package h4311.hexanome.insa.lyonrewards;

import android.app.Application;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;

/**
 * Created by Pierre on 26/04/2016.
 */
public class MockLyonRewardsApplication extends LyonRewardsApplication {
    @Override
    protected AppComponent createAppComponent() {
        Log.d("TEST", "MockLyonRewardsApplication");
        return DaggerMockAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }
}
