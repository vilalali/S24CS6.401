package com.sismics.books.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;

/**
 * BookRating entity.
 */
@Entity
@Table(name = "T_BOOK_RATING")
public class BookRating {

    /**
     * Rating ID.
     */
    @Id
    @Column(name = "BR_ID", updatable = false, nullable = false)
    private String id;

    /**
    * Book ID.
    */
    @Column(name = "BOK_ID_C", nullable = false, length = 36)
    private String bookId;
    
    /**
     * User ID.
    */
    @Column(name = "USR_ID_C", nullable = false, length = 36)
    private String userId;

    @Column(name = "BR_RATING")
    private Integer rating;

    // Default constructor
    public BookRating() {
    }

    // Constructor with all fields
    public BookRating(String b_id, String u_id, Integer rating) {
        this.bookId = b_id;
        this.userId = u_id;
        this.rating = rating;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String b_id) {
        this.bookId = b_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String u_id) {
        this.userId = u_id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
