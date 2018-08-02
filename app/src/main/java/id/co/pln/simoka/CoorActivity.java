package id.co.pln.simoka;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.bumptech.glide.Glide;

import id.co.pln.simoka.bottomnav.BottomBarAdapter;
import id.co.pln.simoka.bottomnav.DummyFragment;
import id.co.pln.simoka.bottomnav.MenuKetigaFragment;
import id.co.pln.simoka.bottomnav.MenuUtamaFragment;
import id.co.pln.simoka.bottomnav.MenuKeduaFragment;
import id.co.pln.simoka.bottomnav.NoSwipePager;
import id.co.pln.simoka.classumum.alertDialog;
import id.co.pln.simoka.classumum.config;

public class CoorActivity extends AppCompatActivity {

    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2};

    private Toolbar toolbar;
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;
    Menu _Menu;

    private boolean notificationVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar/action bar animation
        initCollapsingToolbar( );

        try {
            Glide.with(this).load(R.drawable.bg2).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace( );
        }

        setupViewPager( );

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setupBottomNavBehaviors( );
        setupBottomNavStyle( );

        createFakeNotification( );

        addBottomNavigationItems( );
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener( ) {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected)
                    viewPager.setCurrentItem(position);
                return true;
            }
        });

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener( ) {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange( );
                }
                if (scrollRange + verticalOffset == 0) {
                    _Menu.setGroupVisible(R.id.grup_tahun, true);
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    Typeface tf = Typeface.createFromAsset(getAssets( ), "fonts/pacifico.ttf");
                    collapsingToolbar.setCollapsedTitleTypeface(tf);
                    collapsingToolbar.setExpandedTitleTypeface(tf);
                    isShow = true;
                } else if (isShow) {
                    _Menu.setGroupVisible(R.id.grup_tahun, false);
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager( ));

        pagerAdapter.addFragments(createFragmentMenuUtama(R.color.bottomtab_0));
        pagerAdapter.addFragments(createFragmentMenuKedua(R.color.bottomtab_1));
        pagerAdapter.addFragments(createFragmentMenuKetiga(R.color.bottomtab_2));

        viewPager.setAdapter(pagerAdapter);
    }

    @NonNull
    private DummyFragment createFragment(int color) {
        DummyFragment fragment = new DummyFragment();
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }


    @NonNull
    private MenuUtamaFragment createFragmentMenuUtama(int color) {
        MenuUtamaFragment afragment = new MenuUtamaFragment( );
        afragment.setArguments(passFragmentArguments(fetchColor(color)));
        return afragment;
    }

    @NonNull
    private MenuKeduaFragment createFragmentMenuKedua(int color) {
        MenuKeduaFragment bfragment = new MenuKeduaFragment( );
        bfragment.setArguments(passFragmentArguments(fetchColor(color)));
        return bfragment;
    }

    @NonNull
    private MenuKetigaFragment createFragmentMenuKetiga(int color) {
        MenuKetigaFragment cfragment = new MenuKetigaFragment( );
        cfragment.setArguments(passFragmentArguments(fetchColor(color)));
        return cfragment;
    }

    @NonNull
    private Bundle passFragmentArguments(int color) {
        Bundle bundle = new Bundle( );
        bundle.putInt("color", color);
        return bundle;
    }

    private void createFakeNotification() {
        new Handler( ).postDelayed(new Runnable( ) {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder( )
                        .setText("1")
                        .setBackgroundColor(Color.YELLOW)
                        .setTextColor(Color.BLACK)
                        .build( );
                notificationVisible = true;
            }
        }, 1000);
    }


    public void setupBottomNavBehaviors() {
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    private void setupBottomNavStyle() {
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.bottomtab_0));
        bottomNavigation.setInactiveColor(fetchColor(R.color.bottomtab_item_resting));

        bottomNavigation.setColoredModeColors(Color.WHITE,
                fetchColor(R.color.bottomtab_item_resting));

        bottomNavigation.setColored(true);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }

    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_attach_money_black_24dp, colors[0]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_maps_local_menus, colors[1]);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_attach_money_black_24dp, colors[2]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }


    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate(R.menu.menu_main, menu);
        _Menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );

        if (id == R.id.logo) {
            return true;
        }else if(id == R.id.tahun_2017){
            config.data_tahun = "2017";
            alertDialog.showAlertDialog(this, "Menampilkan Data Tahun 2017");
        }else if(id == R.id.tahun_2018){
            config.data_tahun = "2018";
            alertDialog.showAlertDialog(this, "Menampilkan Data Tahun 2018");
        }else{
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
