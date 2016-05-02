package h4311.hexanome.insa.lyonrewards.di.module.auth;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;

/**
 * Created by Pierre on 29/04/2016.
 */
public class ConnectionManager {
    private User mConnectedUser;

    private List<ConnectedUserChangedListener> subscribers = new ArrayList<>();

    public void addSubscriber(ConnectedUserChangedListener subscriber) {
        subscribers.add(subscriber);
    }

    public void addSubscriberAndNotify(ConnectedUserChangedListener subscriber) {
        subscribers.add(subscriber);
        subscriber.updateConnectedUser(mConnectedUser);
    }

    public void setConnectedUser(User user) {
        mConnectedUser = user;
        notifySubscribers();
    }

    public void removeSubscriber(ConnectedUserChangedListener subscriber) {
        subscribers.remove(subscriber);
    }

    private void notifySubscribers() {
        for (ConnectedUserChangedListener subscriber : subscribers) {
            if (subscriber != null) {
                subscriber.updateConnectedUser(mConnectedUser);
            }

        }
    }

    public User getConnectedUser() {
        return mConnectedUser;
    }

    public void disconnect(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_user), Context.MODE_PRIVATE);

        String noUser = context.getString(R.string.no_user);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getString(R.string.current_user), noUser);

        editor.apply();

        mConnectedUser = null;
    }

    public void debitCredit(int nbPoints) {
        if (mConnectedUser != null) {
            mConnectedUser.setCurrentPoints(mConnectedUser.getCurrentPoints() - nbPoints);
            notifySubscribers();
        }
    }

    public interface ConnectedUserChangedListener {
        void updateConnectedUser(User user);
    }
}
