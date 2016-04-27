package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pierre on 26/04/2016.
 */
@Module
public class NetModule {

    private static String API_BASE_URL = "https://lyonrewards.antoine-chabert.fr/api/";

    public NetModule() {

    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        return client;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    LyonRewardsEndpoint provideLyonRewardsEndpoint(Retrofit retrofit) {
        return retrofit.create(LyonRewardsEndpoint.class);
    }

    @Provides
    @Singleton
    LyonRewardsApi provideLyonRewardsApi(LyonRewardsEndpoint endpoint) {
        return new LyonRewardsApi(endpoint);
    }
}
