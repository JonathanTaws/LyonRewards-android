package h4311.hexanome.insa.lyonrewards;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonObject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Pierre on 26/04/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ApiTest {

    @Inject
    LyonRewardsApi lyonRewardsApi;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        LyonRewardsApplication app = (LyonRewardsApplication) instrumentation.getTargetContext().getApplicationContext();
        app.getAppComponent();
        MockAppComponent component = (MockAppComponent) app.getAppComponent();
        component.inject(this);
    }

    @Test
    public void isApiReachable() {
        lyonRewardsApi.getHello(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Assert.assertNotNull(jsonObject);
                Assert.assertEquals("World", jsonObject.get("Hello").getAsString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Assert.fail();
            }
        });
    }
}
