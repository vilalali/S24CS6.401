package com.sismics.books.rest.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sismics.books.core.dao.jpa.GenreDao;
import com.sismics.books.core.model.jpa.Genre;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Genre REST resources.
 */
@Path("/genre")
public class GenreResource extends BaseResource {
    /**
     * Returns the list of all genres.
     * 
     * @return Response
     * @throws JSONException
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws JSONException {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        GenreDao genreDao = new GenreDao();
        List<Genre> genreList = genreDao.findAll();
        JSONObject response = new JSONObject();
        List<JSONObject> items = new ArrayList<>();
        for (Genre genre : genreList) {
            JSONObject item = new JSONObject();
            item.put("id", genre.getId());
            item.put("name", genre.getName());
            items.add(item);
        }
        response.put("genres", items);
        return Response.ok().entity(response).build();
    }

    // Implement other methods (add, update, delete) as needed based on your requirements
}
