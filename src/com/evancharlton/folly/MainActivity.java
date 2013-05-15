
package com.evancharlton.folly;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private AdapterView<ResultsAdapter> mGridView;
    private EditText mQuery;
    private Button mSearch;

    private ResultsAdapter mAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuery = (EditText) findViewById(R.id.query);
        mSearch = (Button) findViewById(R.id.search);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setQuery(mQuery.getText().toString());
            }
        });

        mGridView = (AdapterView<ResultsAdapter>) findViewById(R.id.grid);
        mAdapter = new ResultsAdapter(this, FollyApp.get().getImageLoader());
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
