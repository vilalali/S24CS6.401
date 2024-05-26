package com.sismics.books.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.sismics.books.R;
import com.sismics.books.util.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Adapter for books list.
 * 
 * @author bgamard
 */
public class BooksAdapter extends BaseAdapter {

    private AQuery aq;
    private Activity activity;
    private JSONArray books;
    private String authToken;
    private String serverUrl;

    /**
     * Constructor.
     * @param activity Context activity
     */
    public BooksAdapter(Activity activity, JSONArray books) {
        this.activity = activity;
        this.books = books;
        this.aq = new AQuery(activity);
        this.authToken = PreferenceUtil.getAuthToken(activity);
        this.serverUrl = PreferenceUtil.getServerUrl(activity);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item_book, null);
            aq.recycle(view);
            holder = new ViewHolder();
            holder.author = aq.id(R.id.author).getTextView();
            holder.title = aq.id(R.id.title).getTextView();
            holder.imgCover = aq.id(R.id.imgCover).getImageView();
            view.setTag(holder);
        } else {
            aq.recycle(view);
            holder = (ViewHolder) view.getTag();
        }

        JSONObject book = getItem(position);

        // Book cover
        String bookId = book.optString("id");
        String coverUrl = serverUrl + "/api/book/" + bookId + "/cover";
        if (aq.shouldDelay(position, view, parent, coverUrl)) {
            aq.id(holder.imgCover).image((Bitmap) null);
        } else {
            aq.id(holder.imgCover).image(new BitmapAjaxCallback()
                    .url(coverUrl)
                    .animation(AQuery.FADE_IN_NETWORK)
                    .cookie("auth_token", authToken)
            );
        }

        // Filling book data
        holder.author.setText(book.optString("author"));
        holder.title.setText(book.optString("title"));

        return view;
    }

    @Override
    public int getCount() {
        return books.length();
    }

    @Override
    public JSONObject getItem(int position) {
        return books.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Book ViewHolder.
     * 
     * @author bgamard
     */
    private static class ViewHolder {
        TextView author;
        TextView title;
        ImageView imgCover;
    }
}
