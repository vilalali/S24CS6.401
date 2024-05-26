package com.sismics.books.core.dao.jpa;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.UUID;
import java.util.List;
import com.sismics.books.core.dao.jpa.dto.BookRankingDto;
import java.util.ArrayList;


import com.sismics.books.core.model.jpa.BookRating;
import com.sismics.util.context.ThreadLocalContext;

public class BookRatingDao {

    // Method to add a new rating
    public void addRating(BookRating rating) { 
        // Create the UUID
        rating.setId(UUID.randomUUID().toString());
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(rating);
    }

    // Method to update an existing rating
    public void updateRating(BookRating rating) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.merge(rating);
    }

    // Method to find a rating by its ID
    public BookRating findRatingById(Long ratingId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        return em.find(BookRating.class, ratingId);
    }

    //calculation of the average rating 
    public Double getAverageRating(String bookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        
        String qlString = "SELECT AVG(r.rating) FROM BookRating r WHERE r.bookId = :bookId";
        Query q = em.createQuery(qlString);
        q.setParameter("bookId", bookId);
        try {
            return (Double) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    
    // Method to find a rating by book and user
    public BookRating getRatingByBAndUr(String b_idx, String uId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();        
        
        try {
            String qlString = "SELECT r FROM BookRating r WHERE r.bookId = :b_idx AND r.userId = :uId";
            Query q = em.createQuery(qlString);             
            q.setParameter("b_idx", b_idx);
            q.setParameter("uId", uId);
            List<BookRating> results = q.getResultList();
            System.out.println(results + " 4535 rating "); 
            return results.isEmpty() ? null : results.get(0); 
        } catch (NoResultException e) {
            return null;
        } catch (Exception ex1){
            System.out.println( "Exception  "+ex1.getMessage()); 
            throw ex1;
        }
    }

    // Method to delete a rating
    public void deleteRating(Long ratingId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        BookRating rating = em.find(BookRating.class, ratingId);
        if (rating != null) {
            em.remove(rating);
        }
    }

    public List<BookRankingDto> rankBooksByAverageRating() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        System.out.println(" r ankBooksByAverageRating "); 
        String qlString = "SELECT br.bookId, AVG(br.rating) as avgRating FROM BookRating br GROUP BY br.bookId ORDER BY avgRating DESC";
        Query q = em.createQuery(qlString);
        List<Object[]> results = q.getResultList();
        
        List<BookRankingDto> rankings = new ArrayList<>();
        for (Object[] result : results) {
            String bookId = (String) result[0];
            Double avgRating = (Double) result[1];
            String title = fetchTitleForBookId(bookId, em); // Fetch the title for the bookId
            
            rankings.add(new BookRankingDto(bookId, title, avgRating));
        }

        return rankings;
    }

    public List<BookRankingDto> rankBooksByNumberOfRatings() {
        System.out.println(" r rankBooksByNumberOfRatings "); 
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        String qlString = "SELECT br.bookId, COUNT(br) as numRatings FROM BookRating br GROUP BY br.bookId ORDER BY numRatings DESC";
        Query q = em.createQuery(qlString);
        List<Object[]> results = q.getResultList();

        List<BookRankingDto> rankings = new ArrayList<>();
        for (Object[] result : results) {
            String bookId = (String) result[0];
            Double numRatings = ((Long) result[1]).doubleValue();
            String title = fetchTitleForBookId(bookId, em); // Fetch the title for the bookId
            
            rankings.add(new BookRankingDto(bookId, title, numRatings));
        }

        return rankings;
    }

    // Utility method to fetch the title for a given bookId
    private String fetchTitleForBookId(String bookId, EntityManager em) {
        Query query = em.createQuery("SELECT b.title FROM Book b WHERE b.id = :bookId");
        query.setParameter("bookId", bookId);
        try {
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // or some default value
        }
    }
    
}
