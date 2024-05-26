package com.sismics.books.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Genre entity.
 */
@Entity
@Table(name = "T_GENRE")
public class Genre {
    /**
     * Genre ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GEN_ID_C")
    private Long id;
    
    /**
     * Genre name.
     */
    @Column(name = "GEN_NAME_C", nullable = false, length = 255)
    private String name;

    /**
     * Books associated with this genre.
     * The 'mappedBy' attribute points to the 'genres' field in the Book class.
     */
    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    // Default constructor
    public Genre() {}

    // Constructor with name parameter
    public Genre(String name) {
        this.name = name;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    // toString, hashCode, and equals methods can be implemented as needed
}
