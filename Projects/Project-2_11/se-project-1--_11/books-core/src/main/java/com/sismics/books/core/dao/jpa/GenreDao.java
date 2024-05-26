package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.sismics.books.core.model.jpa.Genre;
import com.sismics.util.context.ThreadLocalContext; 
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
/**
 * Genre DAO.
 */
public class GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates a new genre.
     * 
     * @param genre Genre
     * @return New ID
     */
    public Long create(Genre genre) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(genre);
        return genre.getId();
    }
    
    /**
     * Gets a genre by its ID.
     * 
     * @param id Genre ID
     * @return Genre
     */
    public Genre getById(Long id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(Genre.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds a genre by its name.
     * 
     * @param name Genre name
     * @return Genre
     */
    public Genre getByName(String name) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("SELECT g FROM Genre g WHERE g.name = :name");
        q.setParameter("name", name);
        try {
            return (Genre) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds all genres in the database.
     *
     * @return A list of all Genre entities.
     */
    public List<Genre> findAll() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("SELECT g FROM Genre g", Genre.class);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Finds genres by a list of IDs.
     *
     * @param genreIds The IDs of genres to find.
     * @return A set of Genre entities.
     */
    public Set<Genre> findByIds(List<Long> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }
        List<Genre> genres = entityManager.createQuery("SELECT g FROM Genre g WHERE g.id IN :ids", Genre.class)
                                          .setParameter("ids", genreIds)
                                          .getResultList();
        return new HashSet<>(genres);
    }
}
