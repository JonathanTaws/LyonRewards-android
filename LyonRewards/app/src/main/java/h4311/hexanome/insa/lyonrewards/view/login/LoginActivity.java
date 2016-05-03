package h4311.hexanome.insa.lyonrewards.view.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_pseudo)
    protected EditText mLogin;

    @BindView(R.id.login_password)
    protected EditText mPassword;

    @BindView(R.id.login_btn_login)
    protected Button mBtnLogin;

    @BindView(R.id.login_form)
    protected View mLoginFormView;

    @BindView(R.id.login_progress)
    protected View mProgressView;


    @Inject
    protected ConnectionManager mConnectionManager;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    @Inject
    protected LyonRewardsApi mLyonRewardsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Connexion");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getApplicationContext().getString(R.string.preference_file_user), Context.MODE_PRIVATE
        );

        String noUserString = getApplicationContext().getString(R.string.no_user);
        String userString = sharedPreferences.getString(getApplicationContext().getString(R.string.current_user), noUserString);



        if(!userString.equals(noUserString)) {

            final User storedUser = new Gson().fromJson(userString, User.class);
            mLyonRewardsApi.getUserById(storedUser.getId(), new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = storedUser;
                    if(response.code() == 200) {
                        user = response.body();
                        saveUserToSharedPrefs(user);
                    }
                    proceedToMainActivity(user);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // TODO
                    Log.d("API", "Error : " + t.getMessage());
                    proceedToMainActivity(storedUser);
                }
            });
        }
        else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }

    }

    @OnClick(R.id.login_btn_login)
    public void loginBtnOnClick() {
        if (mAuthTask != null) {
            return;
        }

        // Hide keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if(currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }


        mLogin.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String login = mLogin.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid login
        if (TextUtils.isEmpty(login)) {
            mLogin.setError(getString(R.string.error_field_required));
            focusView = mLogin;
            cancel = true;
        } else if (!isEmailValid(login)) {
            mLogin.setError(getString(R.string.error_invalid_login));
            focusView = mLogin;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(this, login, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    private void proceedToMainActivity(User user) {
        mConnectionManager.setConnectedUser(user);

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    private void saveUserToSharedPrefs(User user) {
        Context context = getApplicationContext();

        String userString = new Gson().toJson(user,
                User.class);

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_user), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getString(R.string.current_user), userString);

        editor.apply();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final Activity mActivity;
        private final String mLoginString;
        private final String mPasswordString;
        private User mUser;

        UserLoginTask(Activity activity, String login, String password) {
            mActivity = activity;
            mLoginString = login;
            mPasswordString = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                User user = mLyonRewardsApi.loginUser(mLoginString, mPasswordString);
                if (user == null) {
                    return false;
                } else {
                    mUser = user;
                }
            } catch (IOException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                saveUserToSharedPrefs(mUser);
                proceedToMainActivity(mUser);
                finish();
            } else {
                showProgress(false);
                mPassword.setError(getString(R.string.error_incorrect_password));
                mPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

