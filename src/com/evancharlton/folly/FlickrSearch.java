
package com.evancharlton.folly;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.evancharlton.folly.api.FlickrResponse;
import com.evancharlton.folly.api.FlickrResponse.Photos;
import com.google.gson.Gson;

public class FlickrSearch extends Request<Photos> {
    public static final String API_KEY = "96e3cd93b482b82c5acacff05d77fdab";

    private final Listener<Photos> mListener;

    private final Gson mGson = new Gson();

    public FlickrSearch(String query, int page, Listener<Photos> listener,
            ErrorListener errorListener) {
        super(Method.GET, getUrl(query, page), errorListener);
        mListener = listener;
    }

    @Override
    protected void deliverResponse(Photos response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<Photos> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        FlickrResponse flickrResponse = mGson.fromJson(jsonString, FlickrResponse.class);
        return Response.success(flickrResponse.getPhotos(), getCacheEntry());
    }

    private static String getUrl(String query, int page) {
        return "http://api.flickr.com/services/rest/?method=flickr.photos.search"
                + "&api_key=" + API_KEY
                + "&text=" + query
                + "&page=" + page
                + "&per_page=" + ResultsAdapter.PER_PAGE
                + "&format=json&nojsoncallback=1";
    }
}
