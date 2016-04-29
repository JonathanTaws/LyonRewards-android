package h4311.hexanome.insa.lyonrewards.di.module.image;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Pierre on 28/04/2016.
 */
@Module
public class ImageModule {

    private Context mContext;

    private boolean loaderInit = false;

    public ImageModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    ImageLoaderConfiguration provideImageLoaderConfiguration() {
        return new ImageLoaderConfiguration.Builder(mContext).build();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(ImageLoaderConfiguration config) {
        if (!loaderInit) {
            ImageLoader.getInstance().init(config);
            loaderInit = true;
        }

        return ImageLoader.getInstance();
    }

}
