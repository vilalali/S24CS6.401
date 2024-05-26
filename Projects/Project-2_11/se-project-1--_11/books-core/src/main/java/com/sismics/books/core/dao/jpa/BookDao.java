package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.model.jpa.Book;
import com.sismics.util.context.ThreadLocalContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.TypedQuery;
import com.sismics.books.core.model.jpa.Book;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Path;
import javax.persistence.TypedQuery;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;



/**
 * Book DAO.
 * 
 * @author bgamard
 */
public class BookDao {
    /**
     * Creates a new book.
     * 
     * @param book Book
     * @return New ID
     * @throws Exception
     */
    public String create(Book book) {
        // Create the book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(book);
        return book.getId();
    }
    
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets a book by its ID.
     * 
     * @param id Book ID
     * @return Book
     */
    public Book getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(Book.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Returns a book by its ISBN number (10 or 13)
     * 
     * @param isbn ISBN Number (10 or 13)
     * @return Book
     */
    public Book getByIsbn(String isbn) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select b from Book b where b.isbn10 = :isbn or b.isbn13 = :isbn");
        q.setParameter("isbn", isbn);
        try {
            return (Book) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Updates an existing book entity in the database.
     *
     * @param book The book entity with updated fields.
     */
    public void update(Book book) {
        entityManager.merge(book);
    }

    public List<Book> findAllBooks(Integer limit, Integer offset, String sortColumn, Boolean asc) {
        EntityManager em = ThreadLocalContext.get().getEntityManager(); // Get EntityManager from ThreadLocal
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        cq.select(book);

        // Implement sorting
        if (sortColumn != null && !sortColumn.isEmpty() && asc != null) {
            if (asc) {
                cq.orderBy(cb.asc(book.get(sortColumn)));
            } else {
                cq.orderBy(cb.desc(book.get(sortColumn)));
            }
        }

        TypedQuery<Book> query = em.createQuery(cq);
        if (limit != null && limit > 0) {
            query.setMaxResults(limit);
        }
        if (offset != null && offset >= 0) {
            query.setFirstResult(offset);
        }

        return query.getResultList();
    }

    private boolean isSortableColumn(String columnName) {
        // List of fields allowed to sort by
        Set<String> sortableFields = new HashSet<>(Arrays.asList("title", "publishDate", "rating", "id"));
        return sortableFields.contains(columnName);
    }
}
