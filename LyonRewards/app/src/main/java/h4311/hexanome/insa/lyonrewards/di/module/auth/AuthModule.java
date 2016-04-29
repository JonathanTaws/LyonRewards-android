package h4311.hexanome.insa.lyonrewards.di.module.auth;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pierre on 29/04/2016.
 */
@Module
public class AuthModule {

    @Provides
    @Singleton
    ConnectionManager provideConnectionManager() {
        return new ConnectionManager();
    }

}
