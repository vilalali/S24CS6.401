package com.sismics.books.resource;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Access to /book API.
 * 
 * @author bgamard
 */
public class BookResource extends BaseResource {
    /**
     * List all books
     * @param context Context
     * @param responseHandler Response handler
     */
    public static void list(Context context, JsonHttpResponseHandler responseHandler) {
        init(context);

        client.get(getApiUrl(context) + "/book/list?asc=true&offset=0&limit=100&sort_column=3", responseHandler);
    }

    /**
     * Get the book data.
     * @param context Context
     * @param id Book ID
     * @param responseHandler Response handler
     */
    public static void info(Context context, String id, JsonHttpResponseHandler responseHandler) {
        init(context);

        client.get(getApiUrl(context) + "/book/" + id, responseHandler);
    }

    /**
     * Add a book by ISBN.
     * @param context Context
     * @param isbn ISBN
     * @param responseHandler Response handler
     */
    public static void add(Context context, String isbn, JsonHttpResponseHandler responseHandler) {
        init(context);

        RequestParams params = new RequestParams();
        params.put("isbn", isbn);
        client.put(context, getApiUrl(context) + "/book", params, responseHandler);
    }
}
