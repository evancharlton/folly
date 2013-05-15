
package com.evancharlton.folly;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class FollyApp extends Application {
    private static FollyApp sInstance;

    public static FollyApp get() {
        return sInstance;
    }

    private final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);

    private FlickrApi mApi;

    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        RequestQueue queue = Volley.newRequestQueue(this);
        mApi = new FlickrApi(queue);

        ImageCache imageCache = new ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mImageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return mImageCache.get(key);
            }
        };

        mImageLoader = new ImageLoader(queue, imageCache);
    }

    public FlickrApi getApi() {
        return mApi;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
