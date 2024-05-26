package com.sismics.books.core.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.User;
import com.sismics.books.core.constant.AppId;
import com.sismics.books.core.dao.jpa.UserAppDao;
import com.sismics.books.core.dao.jpa.UserContactDao;
import com.sismics.books.core.dao.jpa.dto.UserAppDto;
import com.sismics.books.core.dao.jpa.dto.UserContactDto;
import com.sismics.books.core.model.jpa.UserApp;
import com.sismics.books.core.model.jpa.UserBook;
import com.sismics.books.core.model.jpa.UserContact;
import com.sismics.books.core.service.facebook.AuthenticationException;
import com.sismics.books.core.service.facebook.PermissionException;
import com.sismics.books.core.util.ConfigUtil;
import com.sismics.books.core.util.TransactionUtil;

/**
 * Facebook interaction service.
 *
 * @author jtremeaux 
 */
public class FacebookService extends AbstractScheduledService {
    
    private String facebookAppId;
    
    private String facebookAppSecret;
    
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(FacebookService.class);

    @Override
    protected void startUp() throws Exception {
        ResourceBundle configBundle = ConfigUtil.getConfigBundle();
        facebookAppId = configBundle.getString("app_key.facebook.id");
        facebookAppSecret = configBundle.getString("app_key.facebook.secret");
    }

    @Override
    protected void shutDown() throws Exception {
    }

    @Override
    protected void runOneIteration() throws Exception {
        TransactionUtil.handle(new Runnable() {
            @Override
            public void run() {
                // Synchronize Facebook contacts
                synchronizeAllContact();
            }
        });
    }

    /**
     * Synchronize all Facebook contacts.
     */
    private void synchronizeAllContact() {
        if (log.isInfoEnabled()) {
            log.info("Synchronizing all Facebook contacts...");
        }
        
        UserAppDao userAppDao = new UserAppDao();
        List<UserAppDto> userAppList = userAppDao.findByAppId(AppId.FACEBOOK.name());
        for (UserAppDto userApp : userAppList) {
            try {
                synchronizeContact(userApp.getAccessToken(), userApp.getUserId());
            } catch (Throwable t) {
                log.error(MessageFormat.format("Error synchronizing Facebook contacts for user {0}", userApp.getUserId()), t);
            }
        }
        
        if (log.isInfoEnabled()) {
            log.info("Synchronizing all Facebook contacts : done!");
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(0, 1, TimeUnit.DAYS);
    }

    /**
     * Exchange the short lived token (2h) for a long lived one (60j).
     * 
     * @param accessToken Short lived token
     * @return Long lived token
     * @throws AuthenticationException Invalid token
     */
    public String getExtendedAccessToken(String accessToken) throws AuthenticationException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        AccessToken extendedAccessToken = null;
        try {
            extendedAccessToken = facebookClient.obtainExtendedAccessToken(facebookAppId, facebookAppSecret, accessToken);

            if (log.isDebugEnabled()) {
                log.debug(MessageFormat.format("Got long lived session token {0} for token {1}", extendedAccessToken, accessToken));
            }
        } catch (FacebookException e) {
            if (e.getMessage().contains("Error validating access token")) {
                throw new AuthenticationException("Error validating access token");
            }
                
            throw new RuntimeException("Error exchanging short lived token for long lived token", e);
        }
        return extendedAccessToken.getAccessToken();
    }
    
    /**
     * Check user's permissions.
     * 
     * @param accessToken Access token
     * @throws PermissionException Permission not found
     */
    public void validatePermission(String accessToken) throws PermissionException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Connection<JsonObject> dataList = facebookClient.fetchConnection("me/permissions", JsonObject.class);
        boolean installed = false;
        boolean email = false;
        boolean publishStream = false;
        boolean readStream = false;
        for (JsonObject data : dataList.getData()) {
            if (data.optInt("installed") == 1) {
                installed = true;
            }
            if (data.optInt("email") == 1) {
                email = true;
            }
            if (data.optInt("publish_stream") == 1) {
                publishStream = true;
            }
            if (data.optInt("read_stream") == 1) {
                readStream = true;
            }
        }
        if (!installed) {
            throw new PermissionException("Permission not found: installed");
        }
        if (!email) {
            throw new PermissionException("Permission not found: email");
        }
        if (!publishStream) {
            throw new PermissionException("Permission not found: publish_stream");
        }
        if (!readStream) {
            throw new PermissionException("Permission not found: read_stream");
        }
    }
    
    /**
     * Synchronize user's contact.
     * 
     * @param accessToken Access token
     * @param userId User ID
     */
    public void synchronizeContact(String accessToken, String userId) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Synchronizing Facebook contacts for user {0}", userId));
        }
        
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Connection<User> connection = null;
        connection = facebookClient.fetchConnection("me/friends", User.class);
        Map<String, String> newFriendMap = new HashMap<String, String>();
        for (List<User> friendList : connection) {
            for (User friend : friendList) {
                String friendName = friend.getName();
                String externalId = friend.getId();
                newFriendMap.put(externalId, friendName);
            }
        }
        
        // Load current contacts
        UserContactDao userContactDao = new UserContactDao();
        List<UserContactDto> currentUserContactList = userContactDao.findByUserIdAndAppId(userId, AppId.FACEBOOK.name());
        Set<String> currentFriendSet = new HashSet<String>();
        for (UserContactDto userContact : currentUserContactList) {
            currentFriendSet.add(userContact.getExternalId());
        }
        
        // Update contact updated date
        userContactDao.updateByUserIdAndAppId(userId, AppId.FACEBOOK.name());
        
        // Delete unfriended contacts
        for (UserContactDto userContact : currentUserContactList) {
            if (!newFriendMap.containsKey(userContact.getExternalId())) {
                userContactDao.delete(userContact.getId());
            }
        }
        
        // Add new contacts
        for (Entry<String, String> entry : newFriendMap.entrySet()) {
            if (!currentFriendSet.contains(entry.getKey())) {
                UserContact userContact = new UserContact();
                userContact.setAppId(AppId.FACEBOOK.name());
                userContact.setExternalId(entry.getKey());
                userContact.setFullName(StringUtils.mid(entry.getValue(), 0, 100));
                userContact.setUserId(userId);
                userContactDao.create(userContact);
            }
        }
    }

    /**
     * Updates user's personal informations.
     * 
     * @param accessToken Access token
     * @param userApp User app (updated by side effect)
     */
    public void updateUserData(String accessToken, UserApp userApp) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        User user = facebookClient.fetchObject("me", User.class);
        userApp.setExternalId(user.getId());
        userApp.setEmail(user.getEmail());
        userApp.setUsername(user.getUsername());
    }

    /**
     * Publish a book on Facebook.
     * 
     * @param userBook User book to publish
     */
    public void publishAction(final UserBook userBook) {
//        FacebookClient facebookClient = new DefaultFacebookClient();

        // TODO Publish a user book
//        final String trackUrl = UrlUtil.getTrackUrl(track.getId());
//        String activityId = track.getActivity().getId();
//        String connection = "me/fitness.runs";
//        if (ActivityId.RUNNING.name().equals(activityId)) {
//            connection = "me/fitness.runs";
//        } else if (ActivityId.CYCLING.name().equals(activityId)) {
//            connection = "me/fitness.bikes";
//        } else if (ActivityId.WALKING.name().equals(activityId) || ActivityId.HIKING.name().equals(activityId)) {
//            connection = "me/fitness.walks";
//        } 
//        try {
//            FacebookType publishResponse = facebookClient.publish(connection, FacebookType.class, Parameter.with("course", trackUrl));
//
//            if (log.isInfoEnabled()) {
//                log.info(MessageFormat.format("Published Facebook action: {0} for user {1}, item id = {2}", connection, user.getUsername(), publishResponse.getId()));
//            }
//        } catch (FacebookException e) {
//            log.error("Error publishing Facebook action", e);
//        }
    }
}
