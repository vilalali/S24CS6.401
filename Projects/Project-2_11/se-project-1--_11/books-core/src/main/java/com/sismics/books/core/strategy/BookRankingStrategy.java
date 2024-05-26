package com.sismics.books.core.strategy;

import com.sismics.books.core.dao.jpa.dto.BookRankingDto;

import java.util.List;

public interface BookRankingStrategy {
    List<BookRankingDto> rankBooks();
}
