package com.sismics.books.rest.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.util.stream.Collectors;
import org.codehaus.jettison.json.JSONArray;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sismics.books.core.dao.jpa.BookDao;
import com.sismics.books.core.dao.jpa.BookRatingDao;
import com.sismics.books.core.dao.jpa.GenreDao;
import com.sismics.books.core.dao.jpa.TagDao;
import com.sismics.books.core.dao.jpa.UserBookDao;
import com.sismics.books.core.dao.jpa.UserDao;
import com.sismics.books.core.dao.jpa.criteria.UserBookCriteria;
import com.sismics.books.core.dao.jpa.dto.TagDto;
import com.sismics.books.core.dao.jpa.dto.UserBookDto;
import com.sismics.books.core.event.BookImportedEvent;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.Book;
import com.sismics.books.core.model.jpa.Genre; 
import com.sismics.books.core.model.jpa.Tag;
import com.sismics.books.core.model.jpa.User;
import com.sismics.books.core.model.jpa.UserBook;
import com.sismics.books.core.model.jpa.BookRating;
import com.sismics.books.core.util.DirectoryUtil;
import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.util.jpa.PaginatedLists;
import com.sismics.books.core.util.jpa.SortCriteria;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.exception.ServerException;
import com.sismics.rest.util.ValidationUtil;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * Book REST resources.
 * 
 * @author bgamard
 */
@Path("/book")
public class BookResource extends BaseResource {
    private static final Logger log = LoggerFactory.getLogger(BaseResource.class); 
    private GenreDao genreDao = new GenreDao();
    /**
     * Creates a new book.
     * 
     * @param isbn ISBN Number
     * @return Response
     * @throws JSONException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@FormParam("isbn") String isbn) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
    
        ValidationUtil.validateRequired(isbn, "isbn");
    
        Book book = fetchOrCreateBookFromPublicApi(isbn);
        UserBook userBook = createUserBookIfNeeded(book);
    
        JSONObject response = new JSONObject();
        response.put("id", userBook.getId());
        return Response.ok().entity(response).build();
    }
    
    private Book fetchOrCreateBookFromPublicApi(String isbn) throws JSONException {
        BookDao bookDao = new BookDao();
        Book book = bookDao.getByIsbn(isbn);
        if (book == null) {
            try {
                book = AppContext.getInstance().getBookDataService().searchBook(isbn);
                bookDao.create(book);
            } catch (Exception e) {
                throw new ClientException("BookNotFound", e.getCause().getMessage(), e);
            }
        }
        return book;
    }
    
    private UserBook createUserBookIfNeeded(Book book) throws JSONException {
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = userBookDao.getByBook(book.getId(), principal.getId());
        if (userBook == null) {
            userBook = new UserBook();
            userBook.setUserId(principal.getId());
            userBook.setBookId(book.getId());
            userBook.setCreateDate(new Date());
            userBookDao.create(userBook);
        } else {
            throw new ClientException("BookAlreadyAdded", "Book already added");
        }
        return userBook;
    }

    /**
     * Deletes a book.
     * 
     * @param userBookId User book ID
     * @return Response
     * @throws JSONException
     */
    @DELETE
    @Path("{id: [a-z0-9\\-]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @PathParam("id") String userBookId) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Get the user book
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = userBookDao.getUserBook(userBookId, principal.getId());
        if (userBook == null) {
            throw new ClientException("BookNotFound", "Book not found with id " + userBookId);
        }
        
        // Delete the user book
        userBookDao.delete(userBook.getId());
        
        // Always return ok
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok().entity(response).build();
    }
    
    /**
     * Add a book book manually.
     * 
     * @param title Title
     * @param description Description
     * @return Response
     * @throws JSONException
     */
    @PUT
    @Path("manual")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(
            @FormParam("title") String title,
            @FormParam("subtitle") String subtitle,
            @FormParam("authors") String authors,
            @FormParam("selectedGenres") List<String> selectedGenres,
            @FormParam("description") String description,
            @FormParam("rating") Float rating,
            @FormParam("isbn10") String isbn10,
            @FormParam("isbn13") String isbn13,
            @FormParam("page_count") Long pageCount,
            @FormParam("language") String language,
            @FormParam("publish_date") String publishDateStr,
            @FormParam("thumbnailImageUrl") String thumbnailImageUrl,
            @FormParam("tags") List<String> tagList) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        title = ValidationUtil.validateLength(title, "title", 1, 255, false);
        subtitle = ValidationUtil.validateLength(subtitle, "subtitle", 1, 255, true);
        authors = ValidationUtil.validateLength(authors, "authors", 1, 1000, false);
        description = ValidationUtil.validateLength(description, "description", 1, 4000, true);
        isbn10 = ValidationUtil.validateLength(isbn10, "isbn10", 10, 10, true);
        isbn13 = ValidationUtil.validateLength(isbn13, "isbn13", 13, 13, true);
        language = ValidationUtil.validateLength(language, "language", 2, 2, true);
        Date publishDate = ValidationUtil.validateDate(publishDateStr, "publish_date", false);
        
        if (Strings.isNullOrEmpty(isbn10) && Strings.isNullOrEmpty(isbn13)) {
            throw new ClientException("ValidationError", "At least one ISBN number is mandatory");
        }
        
        // Check if this book is not already in database
        BookDao bookDao = new BookDao();
        Book bookIsbn10 = bookDao.getByIsbn(isbn10);
        Book bookIsbn13 = bookDao.getByIsbn(isbn13);
        if (bookIsbn10 != null || bookIsbn13 != null) {
            throw new ClientException("BookAlreadyAdded", "Book already added");
        }
        
        // Convert the list of genre IDs into a Set<Genre> 
        Set<Genre> genres = new HashSet<>();
        if (selectedGenres != null && !selectedGenres.isEmpty()) {
            for (String genreIdStr : selectedGenres) {
                Long genreId = Long.parseLong(genreIdStr.trim()); 
                Genre genre = genreDao.getById(genreId); // Assuming you have a method to load Genre by ID 
                if (genre != null) {
                    genres.add(genre);
                } 
            }
        }        
 
        // Create the book
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        
        if (title != null) {
            book.setTitle(title);
        }
        if (subtitle != null) {
            book.setSubtitle(subtitle);
        }
        if (authors != null) {
            book.setAuthors(authors);
        }
        
        if (genres != null) {            
            book.setGenres(genres);
        }
        if (description != null) {
            book.setDescription(description);
        }
        if (isbn10 != null) {
            book.setIsbn10(isbn10);
        }
        if (isbn13 != null) {
            book.setIsbn13(isbn13);
        }
        if (pageCount != null) {
            book.setPageCount(pageCount);
        }
        if (language != null) {
            book.setLanguage(language);
        }
        if (publishDate != null) {
            book.setPublishDate(publishDate);
        }
        if (thumbnailImageUrl != null) {
            book.setThumbnailImageUrl(thumbnailImageUrl);
        }
        bookDao.create(book);
        
        // Create the user book
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = new UserBook();
        userBook.setUserId(principal.getId());
        userBook.setBookId(book.getId());
        userBook.setCreateDate(new Date());
        userBookDao.create(userBook);
        
        // Update tags
        if (tagList != null) {
            TagDao tagDao = new TagDao();
            Set<String> tagSet = new HashSet<>();
            Set<String> tagIdSet = new HashSet<>();
            List<Tag> tagDbList = tagDao.getByUserId(principal.getId());
            for (Tag tagDb : tagDbList) {
                tagIdSet.add(tagDb.getId());
            }
            for (String tagId : tagList) {
                if (!tagIdSet.contains(tagId)) {
                    throw new ClientException("TagNotFound", MessageFormat.format("Tag not found: {0}", tagId));
                }
                tagSet.add(tagId);
            }
            tagDao.updateTagList(userBook.getId(), tagSet);
        }
        
        // Returns the book ID
        JSONObject response = new JSONObject();
        response.put("id", userBook.getId());
        return Response.ok().entity(response).build();
    }
    
    /**
     * Updates the book.
     * 
     * @param title Title
     * @param description Description
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z0-9\\-]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(
            @PathParam("id") String userBookId,
            @FormParam("title") String title,
            @FormParam("subtitle") String subtitle,
            @FormParam("authors") String authors,
            @FormParam("genres") Set<Genre> genres,
            @FormParam("rating") Float rating,
            @FormParam("description") String description,
            @FormParam("isbn10") String isbn10,
            @FormParam("isbn13") String isbn13,
            @FormParam("page_count") Long pageCount,
            @FormParam("language") String language,
            @FormParam("publish_date") String publishDateStr,
            @FormParam("thumbnailImageUrl") String thumbnailImageUrl,
            @FormParam("tags") List<String> tagList) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        title = ValidationUtil.validateLength(title, "title", 1, 255, true);
        subtitle = ValidationUtil.validateLength(subtitle, "subtitle", 1, 255, true);
        authors = ValidationUtil.validateLength(authors, "authors", 1, 255, true);
        description = ValidationUtil.validateLength(description, "description", 1, 4000, true);
        isbn10 = ValidationUtil.validateLength(isbn10, "isbn10", 10, 10, true);
        isbn13 = ValidationUtil.validateLength(isbn13, "isbn13", 13, 13, true);
        language = ValidationUtil.validateLength(language, "language", 2, 2, true);
        Date publishDate = ValidationUtil.validateDate(publishDateStr, "publish_date", true);
        
        // Get the user book
        UserBookDao userBookDao = new UserBookDao();
        BookDao bookDao = new BookDao();
        UserBook userBook = userBookDao.getUserBook(userBookId, principal.getId());
        if (userBook == null) {
            throw new ClientException("BookNotFound", "Book not found with id " + userBookId);
        }
        
        // Get the book
        Book book = bookDao.getById(userBook.getBookId());
        
        // Check that new ISBN number are not already in database
        if (!Strings.isNullOrEmpty(isbn10) && book.getIsbn10() != null && !book.getIsbn10().equals(isbn10)) {
            Book bookIsbn10 = bookDao.getByIsbn(isbn10);
            if (bookIsbn10 != null) {
                throw new ClientException("BookAlreadyAdded", "Book already added");
            }
        }
        
        if (!Strings.isNullOrEmpty(isbn13) && book.getIsbn13() != null && !book.getIsbn13().equals(isbn13)) {
            Book bookIsbn13 = bookDao.getByIsbn(isbn13);
            if (bookIsbn13 != null) {
                throw new ClientException("BookAlreadyAdded", "Book already added");
            }
        }

        
        // Update the book
        if (title != null) {
            book.setTitle(title);
        }
        if (subtitle != null) {
            book.setSubtitle(subtitle);
        }
        if (authors != null) {
            book.setAuthors(authors);
        }
        if (genres != null) {
            book.setGenres(genres);
        }
        if (description != null) {
            book.setDescription(description);
        }
        if (isbn10 != null) {
            book.setIsbn10(isbn10);
        }
        if (isbn13 != null) {
            book.setIsbn13(isbn13);
        }
        if (pageCount != null) {
            book.setPageCount(pageCount);
        }
        if (language != null) {
            book.setLanguage(language);
        }
        if (publishDate != null) {
            book.setPublishDate(publishDate);
        }
        if (thumbnailImageUrl != null) {
            book.setThumbnailImageUrl(thumbnailImageUrl);
        }
        
        // Update tags
        if (tagList != null) {
            TagDao tagDao = new TagDao();
            Set<String> tagSet = new HashSet<>();
            Set<String> tagIdSet = new HashSet<>();
            List<Tag> tagDbList = tagDao.getByUserId(principal.getId());
            for (Tag tagDb : tagDbList) {
                tagIdSet.add(tagDb.getId());
            }
            for (String tagId : tagList) {
                if (!tagIdSet.contains(tagId)) {
                    throw new ClientException("TagNotFound", MessageFormat.format("Tag not found: {0}", tagId));
                }
                tagSet.add(tagId);
            }
            tagDao.updateTagList(userBookId, tagSet);
        }
        
        // Returns the book ID
        JSONObject response = new JSONObject();
        response.put("id", userBookId);
        return Response.ok().entity(response).build();
    }
    
    /**
     * Get a book.
     * 
     * @param id User book ID
     * @return Response
     * @throws JSONException
     */ 
    @GET
    @Path("{id: [a-z0-9\\-]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @PathParam("id") String userBookId) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Fetch the user book
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = userBookDao.getUserBook(userBookId);
        if (userBook == null) {
            throw new ClientException("BookNotFound", "Book not found with id " + userBookId);
        }
        
        // Fetch the book
        BookDao bookDao = new BookDao();
        Book bookDb = bookDao.getById(userBook.getBookId());

        //calculation of the average rating 

        BookRatingDao BookRatingDao= new BookRatingDao();
        Double  averageRating =  BookRatingDao.getAverageRating(bookDb.getId());  
        // Transform Set<Genre> to List<String> of genre names
        Set<Genre> genres = bookDb.getGenres();
        List<String> genreNames = new ArrayList<String>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }

        // Now 'genreNames' is a List<String> of genre names
        // Convert the list of genre names to a JSON array to be put in the response
        JSONArray genresJsonArray = new JSONArray();
        for (String genreName : genreNames) {
            genresJsonArray.put(genreName);
        }
        // Return book data
        JSONObject book = new JSONObject();
        book.put("id", userBook.getId());
        book.put("title", bookDb.getTitle());
        book.put("subtitle", bookDb.getSubtitle());
        book.put("authors", bookDb.getAuthors()); 
 //       book.put("genres", bookDb.getGenres());
        book.put("genres", genresJsonArray); 
        book.put("rating", averageRating);
        book.put("page_count", bookDb.getPageCount());
        book.put("description", bookDb.getDescription());
        book.put("isbn10", bookDb.getIsbn10());
        book.put("isbn13", bookDb.getIsbn13());
        book.put("language", bookDb.getLanguage());
        book.put("thumbnailImageUrl", bookDb.getThumbnailImageUrl());
        if (bookDb.getPublishDate() != null) {
            book.put("publish_date", bookDb.getPublishDate().getTime());
        }
        book.put("create_date", userBook.getCreateDate().getTime());
        if (userBook.getReadDate() != null) {
            book.put("read_date", userBook.getReadDate().getTime());
        }
        

        // Add tags
        TagDao tagDao = new TagDao();
        List<TagDto> tagDtoList = tagDao.getByUserBookId(userBookId);
        List<JSONObject> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtoList) {
            JSONObject tag = new JSONObject();
            tag.put("id", tagDto.getId());
            tag.put("name", tagDto.getName());
            tag.put("color", tagDto.getColor());
            tags.add(tag);
        }
        book.put("tags", tags);
        
        return Response.ok().entity(book).build();
    }
    
    /**
     * Returns a book cover.
     * 
     * @param id User book ID
     * @return Response
     * @throws JSONException
     */
    @GET
    @Path("{id: [a-z0-9\\-]+}/cover")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response cover(
            @PathParam("id") final String userBookId) throws JSONException {
        // Get the user book
        UserBookDao userBookDao = new UserBookDao(); 
        UserBook userBook = userBookDao.getUserBook(userBookId);
        if(userBook == null) { 
            userBook = userBookDao.getByBookid(userBookId);  
        } else {
            System.out.println(userBook + "Null value JSON value ");
        }
         
          
        // Get the cover image
        File file = Paths.get(DirectoryUtil.getBookDirectory().getPath(), userBook.getBookId()).toFile(); 
        InputStream inputStream = null;
        try {
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                inputStream = new FileInputStream(new File(getClass().getResource("/dummy.png").getFile()));
            }
        } catch (FileNotFoundException e) {
            throw new ServerException("FileNotFound", "Cover file not found", e);
        }

        return Response.ok(inputStream)
                .header("Content-Type", "image/jpeg")
                .header("Expires", new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").format(new Date().getTime() + 3600000))
                .build();
    }
    
    /**
     * Updates a book cover.
     * 
     * @param id User book ID
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z0-9\\-]+}/cover")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCover(
            @PathParam("id") String userBookId,
            @FormParam("url") String imageUrl) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get the user book
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = userBookDao.getUserBook(userBookId, principal.getId());
        if (userBook == null) {
            throw new ClientException("BookNotFound", "Book not found with id " + userBookId);
        }
        
        // Get the book
        BookDao bookDao = new BookDao();
        Book book = bookDao.getById(userBook.getBookId());

        // Download the new cover
        try {
            AppContext.getInstance().getBookDataService().downloadThumbnail(book, imageUrl);
        } catch (Exception e) {
            throw new ClientException("DownloadCoverError", "Error downloading the cover image");
        }
        
        // Always return ok
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok(response).build();
    }
    
    /**
     * Returns all books.
     * 
     * @param limit Page limit
     * @param offset Page offset
     * @return Response
     * @throws JSONException
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("sort_column") Integer sortColumn,
            @QueryParam("asc") Boolean asc,
            @QueryParam("search") String search,
            @QueryParam("read") Boolean read,
            @QueryParam("tag") String tagName) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        JSONObject response = new JSONObject();
        List<JSONObject> books = new ArrayList<>();
        
        UserBookDao userBookDao = new UserBookDao();
        TagDao tagDao = new TagDao();
        PaginatedList<UserBookDto> paginatedList = PaginatedLists.create(limit, offset);
        SortCriteria sortCriteria = new SortCriteria(sortColumn, asc);
        UserBookCriteria criteria = new UserBookCriteria();
        criteria.setSearch(search);
        criteria.setRead(read);
        criteria.setUserId(principal.getId());
        if (!Strings.isNullOrEmpty(tagName)) {
            Tag tag = tagDao.getByName(principal.getId(), tagName);
            if (tag != null) {
                criteria.setTagIdList(Lists.newArrayList(tag.getId()));
            }
        }
        try {
            userBookDao.findByCriteria(paginatedList, criteria, sortCriteria);
        } catch (Exception e) {
            throw new ServerException("SearchError", "Error searching in books", e);
        }

        for (UserBookDto userBookDto : paginatedList.getResultList()) {
            JSONObject book = new JSONObject();
            book.put("id", userBookDto.getId());
            book.put("title", userBookDto.getTitle());
            book.put("subtitle", userBookDto.getSubtitle());
            book.put("authors", userBookDto.getAuthors());
            book.put("generes", userBookDto.getGenres());
            book.put("rating", userBookDto.getRating());            
            book.put("language", userBookDto.getLanguage());
            book.put("publish_date", userBookDto.getPublishTimestamp());
            book.put("create_date", userBookDto.getCreateTimestamp());
            book.put("read_date", userBookDto.getReadTimestamp());
            book.put("thumbnailImageUrl", userBookDto.getThumbnailImageUrl());
            
            // Get tags
            List<TagDto> tagDtoList = tagDao.getByUserBookId(userBookDto.getId());
            List<JSONObject> tags = new ArrayList<>();
            for (TagDto tagDto : tagDtoList) {
                JSONObject tag = new JSONObject();
                tag.put("id", tagDto.getId());
                tag.put("name", tagDto.getName());
                tag.put("color", tagDto.getColor());
                tags.add(tag);
            }
            book.put("tags", tags);
            
            books.add(book);
        }


        response.put("total", paginatedList.getResultCount());
        response.put("books", books);
        
        return Response.ok().entity(response).build();
    }
    
    @GET
    @Path("listAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("sort_column") Integer sortColumn,
            @QueryParam("asc") Boolean asc) throws JSONException {

        JSONObject response = new JSONObject();
        List<JSONObject> booksJson = new ArrayList<>();
        
        // Assuming BookDao is properly instantiated or injected elsewhere with a valid EntityManager
        BookDao bookDao = new BookDao();
        List<Book> allBooks = bookDao.findAllBooks(limit, offset, getSortColumnName(sortColumn), asc);
        
        for (Book book : allBooks) {
            
            JSONObject bookJson = new JSONObject();
            
            UserBookDao userBookDao = new UserBookDao();
            UserBook userBook = userBookDao.getByBookid(book.getId());
            
            
            bookJson.put("id", userBook.getId());
            bookJson.put("title", book.getTitle());
            bookJson.put("subtitle", book.getSubtitle());
            bookJson.put("authors", book.getAuthors());
            // Omitting conversion of genres and tags to JSON-friendly format for brevity
            BookRatingDao BookRatingDao= new BookRatingDao();
            Double  averageRating =  BookRatingDao.getAverageRating(book.getId()); 
            
            bookJson.put("average_rating", averageRating != null ? averageRating : "No ratings yet"); // Include average rating
            bookJson.put("language", book.getLanguage());
            if (book.getPublishDate() != null) {
                bookJson.put("publish_date", book.getPublishDate().getTime()); // Adjust as per your date formatting needs
            }
            // Additional fields as necessary
            
            booksJson.add(bookJson);
        }
        
        response.put("total", booksJson.size());
        response.put("books", booksJson);
        
        return Response.ok().entity(response).build();
    }

    private String getSortColumnName(Integer sortColumn) {
        if (sortColumn == null) return "id"; // Default sort column
        
        switch (sortColumn) {
            case 1: return "title";
            case 2: return "publishDate";
            // Add more cases as necessary
            default: return "id"; // Fallback to default sorting
        }
    }

    /**
     * Imports books.
     * 
     * @param fileBodyPart File to import
     * @return Response
     * @throws JSONException
     */
    @PUT
    @Consumes("multipart/form-data") 
    @Path("import")
    public Response importFile(
            @FormDataParam("file") FormDataBodyPart fileBodyPart) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        ValidationUtil.validateRequired(fileBodyPart, "file");

        UserDao userDao = new UserDao();
        User user = userDao.getById(principal.getId());
        
        InputStream in = fileBodyPart.getValueAs(InputStream.class);
        File importFile = null;
        try {
            // Copy the incoming stream content into a temporary file
            importFile = File.createTempFile("books_import", null);
            IOUtils.copy(in, new FileOutputStream(importFile));
            
            BookImportedEvent event = new BookImportedEvent();
            event.setUser(user);
            event.setImportFile(importFile);
            AppContext.getInstance().getImportEventBus().post(event);
            
            // Always return ok
            JSONObject response = new JSONObject();
            response.put("status", "ok");
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            if (importFile != null) {
                try {
                    importFile.delete();
                } catch (SecurityException e2) {
                    // NOP
                }
            }
            throw new ServerException("ImportError", "Error importing books", e);
        }
    }
    
    /**
     * Set a book as read/unread.
     * 
     * @param id User book ID
     * @param read Read state
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("{id: [a-z0-9\\-]+}/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(
            @PathParam("id") final String userBookId,
            @FormParam("read") boolean read) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get the user book
        UserBookDao userBookDao = new UserBookDao();
        UserBook userBook = userBookDao.getUserBook(userBookId, principal.getId());
        
        // Update the read date
        userBook.setReadDate(read ? new Date() : null);
        
        // Always return ok
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("{id}/rate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateBook(
            @PathParam("id") String bookIdStr,
            @FormParam("rating") Integer rating) throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        
        // Validate the rating value
        if (rating == null || rating < 1 || rating > 10) {
            throw new ClientException("InvalidRating", "Rating must be between 1 and 10.");
        }

        try {
            // Fetch the current logged-in user's ID
            String userId = principal.getId();
            BookDao bookDao = new BookDao();
            UserDao userDao = new UserDao();
            BookRatingDao bookRatingDao = new BookRatingDao();

            UserBookDao userBookDao = new UserBookDao();
            UserBook userBook1 = userBookDao.getUserBook(bookIdStr); 

            // Fetch the book and user entities by their IDs
            Book book = bookDao.getById(userBook1.getBookId()); 

            if (book == null  ) {
                throw new ClientException("NotFound", "Book  not found.");
            }
 
            // Check if a rating already exists for this user and book
            String b_idx=userBook1.getBookId(); 
            BookRating existingRating = bookRatingDao.getRatingByBAndUr(b_idx, userId); 
           // BookRating existingRating  =null;              
           

            if (existingRating == null) { 
                // Create a new rating if none exists
                  BookRating newRating = new BookRating(userBook1.getBookId(), userId, rating); 
                  bookRatingDao.addRating(newRating);
            } else {
                // Update existing rating
                existingRating.setRating(rating);
                bookRatingDao.updateRating(existingRating);
            }

            // Return success response
            JSONObject response = new JSONObject();
            response.put("status", "ok");
            response.put("message", "Rating submitted successfully.");
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors, etc.  
            throw new ServerException("ErrorSubmittingRating", "An error occurred while submitting the rating.");
        }
    }


}