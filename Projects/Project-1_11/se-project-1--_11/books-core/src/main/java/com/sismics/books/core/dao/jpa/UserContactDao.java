package com.sismics.books.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.sismics.books.core.dao.jpa.criteria.UserContactCriteria;
import com.sismics.books.core.dao.jpa.dto.UserContactDto;
import com.sismics.books.core.model.jpa.UserContact;
import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.util.jpa.PaginatedLists;
import com.sismics.books.core.util.jpa.QueryParam;
import com.sismics.util.context.ThreadLocalContext;

/**
 * User's contact DAO.
 * 
 * @author jtremeaux
 */
public class UserContactDao {
    /**
     * Create a new contact.
     * 
     * @param userContact UserContact
     * @return ID
     */
    public String create(UserContact userContact) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Create contact's UUID
        userContact.setId(UUID.randomUUID().toString());
        
        Date now = new Date();
        userContact.setCreateDate(now);
        em.persist(userContact);
        
        return userContact.getId();
    }
    
    /**
     * Returns users' contact.
     * 
     * @param userId User ID
     * @param appId Application ID
     * @return Users' contact
     */
    @SuppressWarnings("unchecked")
    public List<UserContactDto> findByUserIdAndAppId(String userId, String appId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select uc.USC_ID_C, uc.USC_EXTERNALID_C ");
        sb.append(" from T_USER_CONTACT uc");
        sb.append(" where uc.USC_IDUSER_C = :userId and uc.USC_IDAPP_C = :appId ");
        sb.append(" and uc.USC_DELETEDATE_D is null ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("userId", userId);
        q.setParameter("appId", appId);
        List<Object[]> l = q.getResultList();
        List<UserContactDto> userContactDtoList = new ArrayList<UserContactDto>();
        for (Object[] o : l) {
            int i = 0;
            UserContactDto userContactDto = new UserContactDto();
            userContactDto.setId((String) o[i++]);
            userContactDto.setExternalId((String) o[i++]);
            userContactDtoList.add(userContactDto);
        }
        
        return userContactDtoList;
    }

    /**
     * Updates last check user contact date.
     * 
     * @param userId User ID
     * @param appId Application ID
     */
    public void updateByUserIdAndAppId(String userId, String appId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("update T_USER_CONTACT uc");
        sb.append(" set uc.USC_UPDATEDATE_D = :updateDate ");
        sb.append(" where uc.USC_IDUSER_C = :userId and uc.USC_IDAPP_C = :appId ");
        sb.append(" and uc.USC_DELETEDATE_D is null ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("updateDate", new Date());
        q.setParameter("userId", userId);
        q.setParameter("appId", appId);
        q.executeUpdate();
    }

    /**
     * Delete a contact.
     * 
     * @param id User contact ID
     */
    public void delete(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the contact
        Query q = em.createQuery("select uc from UserContact uc where uc.id = :id and uc.deleteDate is null");
        q.setParameter("id", id);
        UserContact userContactFromDb = (UserContact) q.getSingleResult();

        // Delete the contact
        userContactFromDb.setDeleteDate(new Date());
    }

    /**
     * Search user's contacts.
     * 
     * @param criteria Search criteria
     * @param paginatedList Paginated list (filled by side effect)
     */
    public void findByCriteria(PaginatedList<UserContactDto> paginatedList, UserContactCriteria criteria) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        
        StringBuilder sb = new StringBuilder("select uc.USC_ID_C, uc.USC_EXTERNALID_C, uc.USC_FULLNAME_C ");
        sb.append(" from T_USER_CONTACT uc");
        
        // Add search criterias
        List<String> criteriaList = new ArrayList<String>();
        criteriaList.add("uc.USC_DELETEDATE_D is null");
        if (criteria.getAppId() != null) {
            criteriaList.add("uc.USC_IDAPP_C = :appId");
            parameterMap.put("appId", criteria.getAppId());
        }
        if (criteria.getUserId() != null) {
            criteriaList.add("uc.USC_IDUSER_C = :userId");
            parameterMap.put("userId", criteria.getUserId());
        }
        if (!Strings.isNullOrEmpty(criteria.getQuery())) {
            criteriaList.add("lower(uc.USC_FULLNAME_C) like :fullName");
            parameterMap.put("fullName", "%" + criteria.getQuery().toLowerCase() + "%");
        }
        
        if (!criteriaList.isEmpty()) {
            sb.append(" where ");
            sb.append(Joiner.on(" and ").join(criteriaList));
        }
        
        sb.append(" order by uc.USC_FULLNAME_C, uc.USC_ID_C");
        
        // Perform the search
        QueryParam queryParam = new QueryParam(sb.toString(), parameterMap);
        List<Object[]> l = PaginatedLists.executePaginatedQuery(paginatedList, queryParam);
        
        // Build results
        List<UserContactDto> userContactDtoList = new ArrayList<UserContactDto>();
        for (Object[] o : l) {
            int i = 0;
            UserContactDto userContactDto = new UserContactDto();
            userContactDto.setId((String) o[i++]);
            userContactDto.setExternalId((String) o[i++]);
            userContactDto.setFullName((String) o[i++]);
            userContactDtoList.add(userContactDto);
        }
        paginatedList.setResultList(userContactDtoList);
    }
}
