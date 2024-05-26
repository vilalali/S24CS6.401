package com.sismics.books.rest.resource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sismics.books.core.constant.AppId;
import com.sismics.books.core.dao.jpa.UserAppDao;
import com.sismics.books.core.dao.jpa.UserContactDao;
import com.sismics.books.core.dao.jpa.criteria.UserContactCriteria;
import com.sismics.books.core.dao.jpa.dto.UserAppDto;
import com.sismics.books.core.dao.jpa.dto.UserContactDto;
import com.sismics.books.core.event.UserAppCreatedEvent;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.UserApp;
import com.sismics.books.core.service.FacebookService;
import com.sismics.books.core.service.facebook.AuthenticationException;
import com.sismics.books.core.service.facebook.PermissionException;
import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.util.jpa.PaginatedLists;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

/**
 * Connected application REST resource.
 * 
 * @author jtremeaux
 */
@Path("/connect")
public class ConnectResource extends BaseResource {
    /**
     * Returns current user's connected applications.
     * 
     * @return Response
     * @throws JSONException
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Search connected applications
        UserAppDao userAppDao = new UserAppDao();
        List<UserAppDto> userAppList = userAppDao.findByUserId(principal.getId());
        
        List<JSONObject> items = new ArrayList<JSONObject>();
        for (UserAppDto userAppDto : userAppList) {
            JSONObject userApp = new JSONObject();
            userApp.put("id", userAppDto.getAppId());
            userApp.put("connected", userAppDto.getId() != null && userAppDto.getAccessToken() != null);
            userApp.put("username", userAppDto.getUsername());
            userApp.put("sharing", userAppDto.isSharing());
            items.add(userApp);
        }
        
        JSONObject response = new JSONObject();
        response.put("apps", items);
        return Response.ok().entity(response).build();
    }

    /**
     * Add a connected application.
     * 
     * @param appId App ID
     * @param authToken OAuth authorization token
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z]+}/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(
            @PathParam("id") String appIdString,
            @FormParam("access_token") String accessToken) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        accessToken = ValidationUtil.validateStringNotBlank(accessToken, "access_token");

        // Get application to add
        AppId appId = getAppId(appIdString);
        
        UserAppDao userAppDao = new UserAppDao();
        UserApp userApp = null;
        switch (appId) {
        case FACEBOOK:
            // Delete old connection to this application
            userAppDao.deleteByUserIdAndAppId(principal.getId(), appId.name());

            // Exchange the short lived token (2h) for a long lived one (60j)
            final FacebookService facebookService = AppContext.getInstance().getFacebookService();
            String extendedAccessToken = null;
            try {
                extendedAccessToken = facebookService.getExtendedAccessToken(accessToken);
            } catch (AuthenticationException e) {
                throw new ClientException("InvalidAuthenticationToken", "Error validating authentication token", e);
            }

            // Check permissions
            try {
                facebookService.validatePermission(extendedAccessToken);
            } catch (PermissionException e) {
                throw new ClientException("PermissionNotFound", e.getMessage(), e);
            }
            
            // Create the connection to the application
            userApp = new UserApp();
            userApp.setAppId(appId.name());
            userApp.setAccessToken(extendedAccessToken);
            userApp.setUserId(principal.getId());
            userApp.setSharing(true);
            
            // Get user's personnal informations
            facebookService.updateUserData(extendedAccessToken, userApp);

            userAppDao.create(userApp);
            
            break;
        }

        // Raise a user app created event
        UserAppCreatedEvent userAppCreatedEvent = new UserAppCreatedEvent();
        userAppCreatedEvent.setUserApp(userApp);
        AppContext.getInstance().getAsyncEventBus().post(userAppCreatedEvent);
        
        // Always return OK
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok().entity(response).build();
    }

    /**
     * Remove a connected application.
     * 
     * @param appIdString App ID
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z0-9\\-]+}/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(
            @PathParam("id") String appIdString) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get application to remove
        AppId appId = getAppId(appIdString);
        
        // Delete user app for this application
        UserAppDao userAppDao = new UserAppDao();
        userAppDao.deleteByUserIdAndAppId(principal.getId(), appId.name());

        // Always return OK
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok().entity(response).build();
    }

    /**
     * Updates connected application.
     * 
     * @param appIdString App ID
     * @param sharing If true, share on this application
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z0-9\\-]+}/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(
            @PathParam("id") String appIdString,
            @FormParam("sharing") boolean sharing) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get application to update
        AppId appId = getAppId(appIdString);
        
        // Check if the user is connected to this application
        UserAppDao userAppDao = new UserAppDao();
        UserApp userApp = userAppDao.getActiveByUserIdAndAppId(principal.getId(), appId.name());
        if (userApp == null) {
            throw new ClientException("AppNotConnected", MessageFormat.format("You are not connected to the app {0}", appId.name()));
        }
        
        // Update the user app
        userApp.setSharing(sharing);
        userAppDao.update(userApp);

        // Always return OK
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok().entity(response).build();
    }

    /**
     * Returns contact list on a connected application.
     * 
     * @param appIdString App ID
     * @param limit Page limit
     * @param offset Page offset
     * @return Response
     * @throws JSONException
     */
    @GET
    @Path("{id: [a-z0-9\\-]+}/contact/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response contactList(
            @PathParam("id") String appIdString,
            @QueryParam("query") String query,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get application
        AppId appId = getAppId(appIdString);
        
        // Check if the user is connected to the application
        UserAppDao userAppDao = new UserAppDao();
        UserApp userApp = userAppDao.getActiveByUserIdAndAppId(principal.getId(), appId.name());
        if (userApp == null) {
            throw new ClientException("AppNotConnected", MessageFormat.format("You are not connected to the app {0}", appId.name()));
        }

        JSONObject response = new JSONObject();
        List<JSONObject> contacts = new ArrayList<JSONObject>();

        // Search contacts
        PaginatedList<UserContactDto> paginatedList = PaginatedLists.create(limit, offset);
        UserContactCriteria criteria = new UserContactCriteria();
        criteria.setAppId(appId.name());
        criteria.setUserId(principal.getId());
        criteria.setQuery(query);
        UserContactDao userContactDao = new UserContactDao();
        userContactDao.findByCriteria(paginatedList, criteria);
        for (UserContactDto userContactDto : paginatedList.getResultList()) {
            JSONObject userContact = new JSONObject();
            userContact.put("id", userContactDto.getId());
            userContact.put("external_id", userContactDto.getExternalId());
            userContact.put("full_name", userContactDto.getFullName());
            contacts.add(userContact);
        }

        response.put("total", paginatedList.getResultCount());
        response.put("contacts", contacts);
        return Response.ok().entity(response).build();
    }

    /**
     * Get application ID.
     * 
     * @param appIdString Application ID (string format)
     * @return App ID
     * @throws JSONException
     */
    private AppId getAppId(String appIdString) throws JSONException {
        AppId appId = null;
        try {
            appId = AppId.valueOf(StringUtils.upperCase(appIdString));
        } catch (Exception e) {
            throw new ClientException("AppNotFound", MessageFormat.format("Application not found: {0}", appIdString));
        }
        return appId;
    }
}
