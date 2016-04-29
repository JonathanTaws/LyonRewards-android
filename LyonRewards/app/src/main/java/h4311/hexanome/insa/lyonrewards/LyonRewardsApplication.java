package h4311.hexanome.insa.lyonrewards;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.component.DaggerAppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;
import h4311.hexanome.insa.lyonrewards.di.module.auth.AuthModule;
import h4311.hexanome.insa.lyonrewards.di.module.image.ImageModule;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;

/**
 * Created by Pierre on 26/04/2016.
 */
public class LyonRewardsApplication extends Application {

    private AppComponent mAppComponent = createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .imageModule(new ImageModule(this))
                .authModule(new AuthModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
