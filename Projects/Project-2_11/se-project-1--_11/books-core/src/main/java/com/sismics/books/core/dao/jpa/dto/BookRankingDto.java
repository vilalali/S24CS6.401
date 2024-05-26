package com.sismics.books.core.dao.jpa.dto;
import com.sismics.books.core.dao.jpa.BookRatingDao;


public class BookRankingDto {
    private String bookId;
    private String title;
    private double metric; // This can be average rating or number of ratings

    // Constructor, getters, and setters
    public BookRankingDto(String bookId, String title, double metric) {
        this.bookId = bookId;
        this.title = title;
        this.metric = metric;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMetric() {
        return metric;
    }

    public void setMetric(double metric) {
        this.metric = metric;
    }
}
