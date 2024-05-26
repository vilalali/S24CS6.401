package com.sismics.books.core.listener.async;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Strings;
import com.google.common.eventbus.Subscribe;
import com.sismics.books.core.dao.jpa.BookDao;
import com.sismics.books.core.dao.jpa.TagDao;
import com.sismics.books.core.dao.jpa.UserBookDao;
import com.sismics.books.core.dao.jpa.dto.TagDto;
import com.sismics.books.core.event.BookImportedEvent;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.Book;
import com.sismics.books.core.model.jpa.Tag;
import com.sismics.books.core.model.jpa.UserBook;
import com.sismics.books.core.util.TransactionUtil;
import com.sismics.books.core.util.math.MathUtil;

/**
 * Listener on books import request.
 *  
 * @author bgamard
 */
public class BookImportAsyncListener {
    private static final Logger log = LoggerFactory.getLogger(BookImportAsyncListener.class);
    private BookDao bookDao = new BookDao();
    private UserBookDao userBookDao = new UserBookDao();
    private TagDao tagDao = new TagDao();

    @Subscribe
    public void on(final BookImportedEvent bookImportedEvent) throws Exception {
        if (log.isInfoEnabled()) {
            log.info(MessageFormat.format("Books import requested event: {0}", bookImportedEvent.toString()));
        }
        TransactionUtil.handle(new Runnable() {
            @Override
            public void run() {
                processCsvLines(bookImportedEvent);
            }
        });
    }

    private void processCsvLines(BookImportedEvent bookImportedEvent) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(bookImportedEvent.getImportFile()));
            String[] line;
            while ((line = reader.readNext()) != null) {
                processLine(line, bookImportedEvent);
            }
        } catch (FileNotFoundException e) {
            log.error("Unable to read CSV file", e);
        } catch (IOException e) {
            log.error("Error reading from CSV file", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("Error closing CSV reader", e);
                }
            }
        }
    }

    private void processLine(String[] line, BookImportedEvent bookImportedEvent) {
        if (line[0].equals("Book Id")) {
            return; // Skip header
        }

        String isbn = Strings.isNullOrEmpty(line[6]) ? line[5] : line[6];
        if (Strings.isNullOrEmpty(isbn)) {
            log.warn("No ISBN number for Goodreads book ID: " + line[0]);
            return;
        }

        Book book = handleBook(isbn, bookImportedEvent);
        if(book == null) {
            log.error("Book is null after attempting to handle book");
            return;
        }
        UserBook userBook = handleUserBook(book, bookImportedEvent);
        handleTags(userBook, line[16], bookImportedEvent);
    }

    private Book handleBook(String isbn, BookImportedEvent bookImportedEvent) {
        Book book = bookDao.getByIsbn(isbn);
        if (book == null) {
            try {
                book = AppContext.getInstance().getBookDataService().searchBook(isbn);
                bookDao.create(book);
            } catch (Exception e) {
                log.error("Error fetching book data", e);
                return null;
            }
        }
        return book;
    }

    private UserBook handleUserBook(Book book, BookImportedEvent bookImportedEvent) {
        if (book == null || bookImportedEvent.getUser() == null) {
            log.error("Book or user is null in handleUserBook");
            return null;
        }
    
        UserBook userBook = userBookDao.getByBook(book.getId(), bookImportedEvent.getUser().getId());
        if (userBook == null) {
            userBook = createNewUserBook(book, bookImportedEvent);
            userBookDao.create(userBook);
        }
        return userBook;
    }

    private UserBook createNewUserBook(Book book, BookImportedEvent bookImportedEvent) {
        UserBook userBook = new UserBook();
        userBook.setUserId(bookImportedEvent.getUser().getId());
        userBook.setBookId(book.getId());
        userBook.setCreateDate(new Date());
        // ... other properties setting
        return userBook;
    }

    private void handleTags(UserBook userBook, String bookshelfColumn, BookImportedEvent bookImportedEvent) {
        String[] bookshelfArray = bookshelfColumn.split(",");
        Set<String> tagIdSet = new HashSet<>();
        for (String bookshelf : bookshelfArray) {
            bookshelf = bookshelf.trim();
            if (Strings.isNullOrEmpty(bookshelf)) {
                continue;
            }
            Tag tag = tagDao.getByName(bookImportedEvent.getUser().getId(), bookshelf);
            if (tag == null) {
                tag = createNewTag(bookshelf, bookImportedEvent);
                tagDao.create(tag);
            }
            tagIdSet.add(tag.getId());
        }
        updateUserBookWithTags(userBook, tagIdSet);
    }
    
    private Tag createNewTag(String name, BookImportedEvent bookImportedEvent) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setColor(MathUtil.randomHexColor());
        tag.setUserId(bookImportedEvent.getUser().getId());
        return tag;
    }
    
    private void updateUserBookWithTags(UserBook userBook, Set<String> tagIds) {
        List<TagDto> tagDtoList = tagDao.getByUserBookId(userBook.getId());
        for (TagDto tagDto : tagDtoList) {
            tagIds.add(tagDto.getId());
        }
        tagDao.updateTagList(userBook.getId(), tagIds);
    }
}