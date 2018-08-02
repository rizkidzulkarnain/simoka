package id.co.pln.simoka;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.fragment.NotaDinasDetailFragment;
import id.co.pln.simoka.fragment.NotaDinasFragment;

/**
 * Created by 4741G on 28/02/2018.
 */

public class NotaDinasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeActionBarFont( );
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        if (savedInstanceState == null) {
            createFragmentNotaDinasDetail( );
        }

    }

    private void createFragmentNotaDinasDetail() {
        FragmentTransaction ft = getSupportFragmentManager( ).beginTransaction( );
        NotaDinasDetailFragment adetfrag = new NotaDinasDetailFragment( );
        ft.replace(R.id.frag_container, adetfrag);
        ft.commit( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate(R.menu.menu_search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView( );
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (NotaDinasDetailFragment._Adapter != null)
                    NotaDinasDetailFragment._Adapter.getFilter( ).filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed( );
        return true;
    }

    public void changeActionBarFont() {
        TextView tv = new TextView(getApplicationContext( ));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Rencana/Nota Dinas " + config.data_tahun);
        tv.setTextSize(14);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(getAssets( ), "fonts/pacifico.ttf");
        tv.setTypeface(tf);
        getSupportActionBar( ).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar( ).setCustomView(tv);
        getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar( ).setDisplayShowHomeEnabled(true);
    }
}
