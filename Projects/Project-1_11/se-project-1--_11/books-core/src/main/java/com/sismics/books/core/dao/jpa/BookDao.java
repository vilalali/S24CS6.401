package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.model.jpa.Book;
import com.sismics.util.context.ThreadLocalContext;



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
}
