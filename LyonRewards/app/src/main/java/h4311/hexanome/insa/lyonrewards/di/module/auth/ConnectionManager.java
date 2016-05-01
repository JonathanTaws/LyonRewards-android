package h4311.hexanome.insa.lyonrewards.di.module.auth;

import java.util.ArrayList;
import java.util.List;

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

    public interface ConnectedUserChangedListener {
        void updateConnectedUser(User user);
    }
}
