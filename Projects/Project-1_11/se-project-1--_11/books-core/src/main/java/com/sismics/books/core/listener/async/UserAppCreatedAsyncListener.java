package com.sismics.books.core.listener.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;
import com.sismics.books.core.constant.AppId;
import com.sismics.books.core.event.UserAppCreatedEvent;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.UserApp;
import com.sismics.books.core.service.FacebookService;
import com.sismics.books.core.util.TransactionUtil;

/**
 * User app created listener.
 *
 * @author jtremeaux 
 */
public class UserAppCreatedAsyncListener {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserAppCreatedAsyncListener.class);

    /**
     * Process event.
     * 
     * @param userAppCreatedEvent Event
     */
    @Subscribe
    public void onUserCreated(final UserAppCreatedEvent userAppCreatedEvent) {
        if (log.isInfoEnabled()) {
            log.info("UserApp created event: " + userAppCreatedEvent.toString());
        }
        
        final UserApp userApp = userAppCreatedEvent.getUserApp();
        
        TransactionUtil.handle(new Runnable() {
            @Override
            public void run() {
                try {
                    AppId appId = AppId.valueOf(userApp.getAppId());
                    switch (appId) {
                    case FACEBOOK:
                        // Synchronize friends contact
                        final FacebookService facebookService = AppContext.getInstance().getFacebookService();
                        String facebookAccessToken = userApp.getAccessToken();
                        facebookService.synchronizeContact(facebookAccessToken, userApp.getUserId());
                        break;
                    }
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("Error synchronizing App contacts", userAppCreatedEvent, e);
                    }
                }
            }
        });
    }
}
