package com.sismics.books.rest.resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import com.sismics.books.core.dao.jpa.BookRatingDao;
import com.sismics.books.core.dao.jpa.dto.BookRankingDto;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import javax.ws.rs.FormParam;

@Path("/bookranking")
public class BookRankingResource {
    private static final Logger log = LoggerFactory.getLogger(BookRankingResource.class); 

    private final BookRatingDao bookRatingDao = new BookRatingDao(); // Make sure this DAO is properly instantiated

    @POST
    @Path("/rank")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rankBooks(@FormParam("rankingType") String rankingType) {
        try {
            JSONArray rankedBooksJson = new JSONArray();
            
            log.debug(" line 28 bookresources Fetched rankingType:  ", rankingType);
           // rankingType="averageRating";
            // Assuming the DAO returns a list of DTOs or entities that include book title and ranking info
            List<BookRankingDto> rankedBooks;
            if ("averageRating".equals(rankingType)) {
                rankedBooks = bookRatingDao.rankBooksByAverageRating();
            } else if ("numberOfRatings".equals(rankingType)) {
                rankedBooks = bookRatingDao.rankBooksByNumberOfRatings();
            } else {
                log.debug(" line 36 bookresources Fetched rankingType: ", rankingType);
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ranking type").build();
            }
            
            for (BookRankingDto book : rankedBooks) {
                JSONObject bookJson = new JSONObject();
                bookJson.put("id", book.getBookId());
                bookJson.put("title", book.getTitle()); // Assuming the DTO includes the title
                bookJson.put("rankingValue", book.getMetric());
                rankedBooksJson.put(bookJson);
            }

            JSONObject response = new JSONObject();
            response.put("rankedBooks", rankedBooksJson);
            return Response.ok().entity(response.toString()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
