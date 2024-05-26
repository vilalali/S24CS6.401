package com.sismics.books.core.event;

import com.google.common.base.Objects;
import com.sismics.books.core.model.jpa.UserApp;

/**
 * User app created event.
 *
 * @author jtremeaux 
 */
public class UserAppCreatedEvent {
    /**
     * User app created.
     */
    private UserApp userApp;

    /**
     * Getter of userApp.
     *
     * @return userApp
     */
    public UserApp getUserApp() {
        return userApp;
    }

    /**
     * Setter of userApp.
     *
     * @param userApp userApp
     */
    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userApp", userApp)
                .toString();
    }
}
