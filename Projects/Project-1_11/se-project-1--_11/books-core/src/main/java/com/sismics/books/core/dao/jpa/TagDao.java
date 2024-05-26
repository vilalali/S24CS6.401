package com.sismics.books.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.dao.jpa.dto.TagDto;
import com.sismics.books.core.model.jpa.UserBookTag;
import com.sismics.books.core.model.jpa.Tag;
import com.sismics.util.context.ThreadLocalContext;

/**
 * Tag DAO.
 * 
 * @author bgamard
 */
public class TagDao {
    /**
     * Gets a tag by its ID.
     * 
     * @param id Tag ID
     * @return Tag
     */
    public Tag getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(Tag.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Returns the list of all tags.
     * 
     * @return List of tags
     */
    @SuppressWarnings("unchecked")
    public List<Tag> getByUserId(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from Tag t where t.userId = :userId and t.deleteDate is null order by t.name");
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    /**
     * Update tags on a user book.
     * 
     * @param userBookId
     * @param tagIdSet
     */
    public void updateTagList(String userBookId, Set<String> tagIdSet) {
        // Delete old tag links
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("delete UserBookTag bt where bt.userBookId = :userBookId");
        q.setParameter("userBookId", userBookId);
        q.executeUpdate();
        
        // Create new tag links
        for (String tagId : tagIdSet) {
            UserBookTag userBookTag = new UserBookTag();
            userBookTag.setId(UUID.randomUUID().toString());
            userBookTag.setUserBookId(userBookId);
            userBookTag.setTagId(tagId);
            em.persist(userBookTag);
        }

    }

    /**
     * Returns tag list on a user book.
     * @param userBookId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TagDto> getByUserBookId(String userBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select t.TAG_ID_C, t.TAG_NAME_C, t.TAG_COLOR_C from T_USER_BOOK_TAG bt ");
        sb.append(" join T_TAG t on t.TAG_ID_C = bt.BOT_IDTAG_C ");
        sb.append(" where bt.BOT_IDUSERBOOK_C = :userBookId and t.TAG_DELETEDATE_D is null ");
        sb.append(" order by t.TAG_NAME_C ");
        
        // Perform the query
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("userBookId", userBookId);
        List<Object[]> l = q.getResultList();
        
        // Assemble results
        List<TagDto> tagDtoList = new ArrayList<TagDto>();
        for (Object[] o : l) {
            int i = 0;
            TagDto tagDto = new TagDto();
            tagDto.setId((String) o[i++]);
            tagDto.setName((String) o[i++]);
            tagDto.setColor((String) o[i++]);
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }
    
    /**
     * Creates a new tag.
     * 
     * @param tag Tag
     * @return New ID
     * @throws Exception
     */
    public String create(Tag tag) {
        // Create the UUID
        tag.setId(UUID.randomUUID().toString());
        
        // Create the tag
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        tag.setCreateDate(new Date());
        em.persist(tag);
        
        return tag.getId();
    }

    /**
     * Returns a tag by name.
     * @param userId User ID
     * @param name Name
     * @return Tag
     */
    public Tag getByName(String userId, String name) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from Tag t where t.name = :name and t.userId = :userId and t.deleteDate is null");
        q.setParameter("userId", userId);
        q.setParameter("name", name);
        try {
            return (Tag) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Returns a tag by ID.
     * @param userId User ID
     * @param tagId Tag ID
     * @return Tag
     */
    public Tag getByTagId(String userId, String tagId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from Tag t where t.id = :tagId and t.userId = :userId and t.deleteDate is null");
        q.setParameter("userId", userId);
        q.setParameter("tagId", tagId);
        try {
            return (Tag) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Deletes a tag.
     * 
     * @param tagId Tag ID
     */
    public void delete(String tagId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the tag
        Query q = em.createQuery("select t from Tag t where t.id = :id and t.deleteDate is null");
        q.setParameter("id", tagId);
        Tag tagDb = (Tag) q.getSingleResult();
        
        // Delete the tag
        Date dateNow = new Date();
        tagDb.setDeleteDate(dateNow);

        // Delete linked data
        q = em.createQuery("delete UserBookTag bt where bt.tagId = :tagId");
        q.setParameter("tagId", tagId);
        q.executeUpdate();
    }

    /**
     * Search tags by name.
     * 
     * @param name Tag name
     * @return List of found tags
     */
    @SuppressWarnings("unchecked")
    public List<Tag> findByName(String userId, String name) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from Tag t where t.name like :name and t.userId = :userId and t.deleteDate is null");
        q.setParameter("userId", userId);
        q.setParameter("name", "%" + name + "%");
        return q.getResultList();
    }
}
