package edu.nku.csc456.fall2015.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.Csc456Application;
import edu.nku.csc456.fall2015.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Tracker tracker;

    @Bind(R.id.app_toolbar)
    Toolbar toolbar;

    @Bind(R.id.nav_drawer)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Csc456Application application = (Csc456Application) getApplication();
        tracker = application.getDefaultTracker();

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ListContentFragment.newInstance(ListContentFragment.CHAPTERS))
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder()
                .setCategory("Navigating");

        int id = menuItem.getItemId();
        if( id == R.id.nav_chapters ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListContentFragment.newInstance(ListContentFragment.CHAPTERS))
                    .commit();
            getSupportActionBar().setTitle(R.string.chapters);
            eventBuilder.setAction("chatpers");
        } else if( id == R.id.nav_adventure ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListContentFragment.newInstance(ListContentFragment.ADVENTURES))
                    .commit();
            getSupportActionBar().setTitle(R.string.adventures);
            eventBuilder.setAction("adventure");
        } else if( id == R.id.nav_main_quest ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListContentFragment.newInstance(ListContentFragment.ADVENTURES))
                    .commit();
            getSupportActionBar().setTitle(R.string.main_quest);
            eventBuilder.setAction("main quest");
        } else if( id == R.id.nav_badges ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListContentFragment.newInstance(ListContentFragment.BADGES))
                    .commit();
            getSupportActionBar().setTitle(R.string.badges);
            eventBuilder.setAction("badges");
        } else if( id == R.id.nav_website ) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.website_url)));
            startActivity(intent);

            eventBuilder.setAction("website");
        }
        tracker.send(eventBuilder.build());
        drawerLayout.closeDrawers();
        return false;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
