package h4311.hexanome.insa.lyonrewards.view.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Connexion");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);


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
                mConnectionManager.setConnectedUser(mUser);
                Intent intent = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();
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

