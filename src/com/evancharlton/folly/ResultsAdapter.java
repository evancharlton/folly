
package com.evancharlton.folly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.evancharlton.folly.api.FlickrResponse.Photos;
import com.evancharlton.folly.api.FlickrResponse.Photos.Photo;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends BaseAdapter implements Listener<Photos>, ErrorListener {
    public static final int PER_PAGE = 100;

    private final List<Photo> mPhotos = new ArrayList<Photo>();

    private final Context mContext;

    private final ImageLoader mImageLoader;

    private String mQuery = "android";

    private Request<?> mInFlightRequest;

    public ResultsAdapter(Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
        loadNextPage();
    }

    public void setQuery(String query) {
        mQuery = query;
        if (mInFlightRequest != null)
            mInFlightRequest.cancel();
        mInFlightRequest = null;
        mPhotos.clear();
        notifyDataSetChanged();
        loadNextPage();
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public Photo getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.thumbnail, parent, false);
        }

        if (position > (getCount() - 10)) {
            loadNextPage();
        }

        Holder h = Holder.get(convertView);
        h.image.setImageDrawable(null);
        h.image.setImageUrl(getItem(position).getUrl(), mImageLoader);

        return convertView;
    }

    private void loadNextPage() {
        if (mInFlightRequest != null) {
            return;
        }

        int page = (int) (mPhotos.size() / (double) PER_PAGE);
        mInFlightRequest = FollyApp.get().getApi().search(mQuery, page + 1, this, this);
        Toast.makeText(mContext, "Loading page: " + (page + 1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Photos photos) {
        Toast.makeText(mContext, "Loaded", Toast.LENGTH_SHORT).show();
        mInFlightRequest = null;
        mPhotos.addAll(photos.getPhotos());
        notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mInFlightRequest = null;
        FollyLog.e(error, "Unable to load photos");
    }

    private static final class Holder {
        public final NetworkImageView image;

        private Holder(View v) {
            image = (NetworkImageView) v.findViewById(R.id.thumbnail);
            v.setTag(this);
        }

        public static Holder get(View v) {
            if (v.getTag() instanceof Holder) {
                return (Holder) v.getTag();
            }
            return new Holder(v);
        }
    }
}
