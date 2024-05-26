package com.sismics.books.core.strategy.impl;

import com.sismics.books.core.dao.jpa.BookRatingDao;
import com.sismics.books.core.dao.jpa.dto.BookRankingDto;
import com.sismics.books.core.strategy.BookRankingStrategy;

import java.util.List;

public class NumberOfRatingsRankingStrategy implements BookRankingStrategy {
    private final BookRatingDao bookRatingDao;

    public NumberOfRatingsRankingStrategy(BookRatingDao bookRatingDao) {
        this.bookRatingDao = bookRatingDao;
    }

    @Override
    public List<BookRankingDto> rankBooks() {
        return bookRatingDao.rankBooksByNumberOfRatings();
    }
}
