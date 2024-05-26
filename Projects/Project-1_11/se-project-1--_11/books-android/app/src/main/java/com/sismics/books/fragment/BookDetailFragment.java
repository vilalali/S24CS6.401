package com.sismics.books.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sismics.books.R;
import com.sismics.books.resource.BookResource;
import com.sismics.books.util.PreferenceUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link com.sismics.books.activity.BookListActivity}
 * in two-pane mode (on tablets) or a {@link com.sismics.books.activity.BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String bookId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        final AQuery aq = new AQuery(view);

        if (!getArguments().containsKey(ARG_ITEM_ID)) {
            return view;
        }

        bookId = getArguments().getString(ARG_ITEM_ID);

        BookResource.info(getActivity(), bookId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject book) {
                if (getActivity() == null) {
                    return;
                }

                aq.id(R.id.author).text(book.optString("author"));
                aq.id(R.id.title).text(book.optString("title") + " " + book.optString("subtitle", ""));
                long publishDate = book.optLong("publish_date", 0);
                if (publishDate != 0) {
                    aq.id(R.id.publish_date).text(new SimpleDateFormat("yyyy").format(new Date(publishDate)));
                }
                aq.id(R.id.isbn10).text(book.optString("isbn10"));
                aq.id(R.id.isbn13).text(book.optString("isbn13"));
                aq.id(R.id.page_count).text("" + book.optInt("page_count"));
                aq.id(R.id.language).text(book.optString("language"));

                String coverUrl = PreferenceUtil.getServerUrl(getActivity()) + "/api/book/" + book.optString("id") + "/cover";
                aq.id(R.id.imgCover).image(new BitmapAjaxCallback()
                        .url(coverUrl)
                        .animation(AQuery.FADE_IN_NETWORK)
                        .cookie("auth_token", PreferenceUtil.getAuthToken(getActivity()))
                );
            }
        });

        return view;
    }
}
