package h4311.hexanome.insa.lyonrewards.di.module.auth;

import h4311.hexanome.insa.lyonrewards.business.User;

/**
 * Created by Pierre on 29/04/2016.
 */
public class ConnectionManager {
    private User mConnectedUser;

    public void setConnectedUser(User user) {
        mConnectedUser = user;
    }

    public User getConnectedUser() {
        return mConnectedUser;
    }
}
