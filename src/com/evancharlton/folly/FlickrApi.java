
package com.evancharlton.folly;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.evancharlton.folly.api.FlickrResponse.Photos;
import com.evancharlton.folly.api.FlickrResponse.Photos.Photo;

public class FlickrApi {
    private final RequestQueue mQueue;

    public FlickrApi(RequestQueue queue) {
        mQueue = queue;
    }

    public Request<?> search(String query, int pageNum, Listener<Photos> listener,
            ErrorListener errorListener) {
        return mQueue.add(new FlickrSearch(query, pageNum, listener, errorListener));
    }

    public Request<?> thumbnail(Photo photo, int maxWidth, int maxHeight,
            Listener<Bitmap> listener, ErrorListener errorListener) {
        return mQueue.add(new ImageRequest(photo.getUrl(), listener, maxWidth, maxHeight, null,
                errorListener));
    }
}
