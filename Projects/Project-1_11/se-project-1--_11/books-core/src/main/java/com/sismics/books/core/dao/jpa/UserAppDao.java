package com.sismics.books.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.dao.jpa.dto.UserAppDto;
import com.sismics.books.core.model.jpa.UserApp;
import com.sismics.util.context.ThreadLocalContext;

/**
 * User app DAO.
 * 
 * @author jtremeaux
 */
public class UserAppDao {
    /**
     * Create a new connection.
     * 
     * @param userApp User app
     * @return ID
     */
    public String create(UserApp userApp) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Create the user app's UUID
        userApp.setId(UUID.randomUUID().toString());
        
        Date now = new Date();
        userApp.setCreateDate(now);
        em.persist(userApp);
        
        return userApp.getId();
    }
    
    /**
     * Delete a connection.
     * 
     * @param id User app ID
     */
    public void delete(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the user app
        Query q = em.createQuery("select ua from UserApp ua where ua.id = :id and ua.deleteDate is null");
        q.setParameter("id", id);
        UserApp userAppFromDb = (UserApp) q.getSingleResult();

        // Delete the user app
        userAppFromDb.setDeleteDate(new Date());
    }

    /**
     * Deletes connection linked to a user and an application.
     * 
     * @param userId User ID
     * @param appId App ID
     */
    public void deleteByUserIdAndAppId(String userId, String appId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("update UserApp ua set ua.deleteDate = :deleteDate where ua.userId = :userId and ua.appId = :appId and ua.deleteDate is null");
        q.setParameter("deleteDate", new Date());
        q.setParameter("userId", userId);
        q.setParameter("appId", appId);
        q.executeUpdate();
    }

    /**
     * Search and returns a non-deleted connection.
     * 
     * @param id User app ID
     * @return User app
     */
    public UserApp getActiveById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ua from UserApp ua where ua.id = :id and ua.deleteDate is null");
        try {
            q.setParameter("id", id);
            return (UserApp) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Search and returns a non-deleted connection by user and app.
     * 
     * @param userId User ID
     * @param appId App ID
     * @return User app
     */
    public UserApp getActiveByUserIdAndAppId(String userId, String appId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select distinct ua from UserApp ua");
        sb.append(" where ua.userId = :userId and ua.appId = :appId ");
        sb.append(" and ua.deleteDate is null ");
        sb.append(" order by ua.createDate desc ");
        Query q = em.createQuery(sb.toString());
        try {
            q.setParameter("userId", userId);
            q.setParameter("appId", appId);
            return (UserApp) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Search and returns application list with connection status.
     * 
     * @param userId User ID
     * @return User app list
     */
    @SuppressWarnings("unchecked")
    public List<UserAppDto> findByUserId(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select a.APP_ID_C, ua.USA_ID_C, ua.USA_ACCESSTOKEN_C, ua.USA_USERNAME_C, ua.USA_SHARING_B ");
        sb.append(" from T_APP a");
        sb.append(" left join T_USER_APP ua on(a.APP_ID_C = ua.USA_IDAPP_C and ua.USA_IDUSER_C = :userId and ua.USA_DELETEDATE_D is null)");
        sb.append(" order by a.APP_ID_C ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("userId", userId);
        List<Object[]> l = q.getResultList();
        List<UserAppDto> userAppDtoList = new ArrayList<UserAppDto>();
        for (Object[] o : l) {
            int i = 0;
            UserAppDto userAppDto = new UserAppDto();
            userAppDto.setAppId((String) o[i++]);
            userAppDto.setId((String) o[i++]);
            userAppDto.setUserId(userId);
            userAppDto.setAccessToken((String) o[i++]);
            userAppDto.setUsername((String) o[i++]);
            Boolean sharing = (Boolean) o[i++];
            userAppDto.setSharing(sharing != null ? sharing : false);
            userAppDtoList.add(userAppDto);
        }
        
        return userAppDtoList;
    }

    /**
     * Search and returns user's connected applications.
     * 
     * @param userId User ID
     * @return User app list
     */
    @SuppressWarnings("unchecked")
    public List<UserAppDto> findConnectedByUserId(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select ua.USA_ID_C, ua.USA_IDAPP_C, ua.USA_ACCESSTOKEN_C, ua.USA_USERNAME_C, ua.USA_SHARING_B ");
        sb.append(" where ua.USA_IDUSER_C = :userId and ua.USA_ACCESSTOKEN_C is not null and ua.USA_DELETEDATE_D is null");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("userId", userId);
        List<Object[]> l = q.getResultList();
        List<UserAppDto> userAppDtoList = new ArrayList<UserAppDto>();
        for (Object[] o : l) {
            int i = 0;
            UserAppDto userAppDto = new UserAppDto();
            userAppDto.setId((String) o[i++]);
            userAppDto.setAppId((String) o[i++]);
            userAppDto.setUserId(userId);
            userAppDto.setAccessToken((String) o[i++]);
            userAppDto.setUsername((String) o[i++]);
            Boolean sharing = (Boolean) o[i++];
            userAppDto.setSharing(sharing != null ? sharing : false);
            userAppDtoList.add(userAppDto);
        }
        
        return userAppDtoList;
    }

    /**
     * Search and returns applications connected to a user.
     * 
     * @param appId Application ID
     * @return User app list
     */
    @SuppressWarnings("unchecked")
    public List<UserAppDto> findByAppId(String appId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select ua.USA_ID_C, ua.USA_IDUSER_C, ua.USA_IDAPP_C, ua.USA_ACCESSTOKEN_C, ua.USA_USERNAME_C, ua.USA_SHARING_B ");
        sb.append(" from T_USER_APP ua ");
        sb.append(" where ua.USA_IDAPP_C = :appId and ua.USA_DELETEDATE_D is null ");
        sb.append(" order by ua.USA_CREATEDATE_D ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("appId", appId);
        List<Object[]> l = q.getResultList();
        List<UserAppDto> userAppDtoList = new ArrayList<UserAppDto>();
        for (Object[] o : l) {
            int i = 0;
            UserAppDto userAppDto = new UserAppDto();
            userAppDto.setId((String) o[i++]);
            userAppDto.setUserId((String) o[i++]);
            userAppDto.setAppId((String) o[i++]);
            userAppDto.setAccessToken((String) o[i++]);
            userAppDto.setUsername((String) o[i++]);
            Boolean sharing = (Boolean) o[i++];
            userAppDto.setSharing(sharing != null ? sharing : false);
            userAppDtoList.add(userAppDto);
        }
        
        return userAppDtoList;
    }

    /**
     * Updates a connection.
     * 
     * @param userApp User app
     * @return Updated user app
     */
    public UserApp update(UserApp userApp) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        
        // Récupère la connexion
        Query q = em.createQuery("select ua from UserApp ua where ua.id = :id and ua.deleteDate is null");
        q.setParameter("id", userApp.getId());
        UserApp userAppFromDb = (UserApp) q.getSingleResult();

        // Modifie la connexion
        userAppFromDb.setExternalId(userApp.getExternalId());
        userAppFromDb.setUsername(userApp.getUsername());
        userAppFromDb.setEmail(userApp.getEmail());
        userAppFromDb.setSharing(userApp.isSharing());

        return userApp;
    }
}
