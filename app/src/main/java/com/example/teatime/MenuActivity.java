package com.example.teatime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.teatime.IdlingResource.SimpleIdlingResource;
import com.example.teatime.model.Tea;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements ImageDownloader.DelayerCallback {

    Intent mTeaIntent;

    public final static String EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME";

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(getString(R.string.menu_title));

        // Get the IdlingResource instance
        getIdlingResource();
    }

    /**
     * We call ImageDownloader.downloadImage from onStart or onResume instead of in onCreate
     * to ensure there is enougth time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */
    @Override
    protected void onStart() {
        super.onStart();
        ImageDownloader.downloadImage(this, MenuActivity.this, mIdlingResource);
    }

    /**
     * When the thread in {@link ImageDownloader} is finished, it will return an ArrayList of Tea
     * objects via the callback's onDone().
     */
    @Override
    public void onDone(ArrayList<Tea> teas) {

        //TextView testing =(TextView)findViewById(R.id.textView);
        //testing.setText("Changed");

        // Create a {@link TeaAdapter}, whose data source is a list of {@link Tea}s.
        // The adapter know how to create grid items for each item in the list.
        GridView gridview = (GridView) findViewById(R.id.tea_grid_view);
        TeaMenuAdapter adapter = new TeaMenuAdapter(this, R.layout.grid_item_layout, teas);
        gridview.setAdapter(adapter);

        // Set a click listener on that View
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Tea item = (Tea) adapterView.getItemAtPosition(position);
                // Set the intent to open the {@link OrderActivity}
                mTeaIntent = new Intent(MenuActivity.this, OrderActivity.class);
                String teaName = item.getTeaName();
                mTeaIntent.putExtra(EXTRA_TEA_NAME, teaName);
                startActivity(mTeaIntent);
            }
        });
    }
}