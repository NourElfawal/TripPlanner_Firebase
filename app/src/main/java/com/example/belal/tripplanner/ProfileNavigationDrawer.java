package com.example.belal.tripplanner;

import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileNavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //  v,end_point varchar,date text,time text,notes varchar,trip_type varchar,status varchar

    static  int i2 =0;
    static  int flag=0;
    TextView  userNameNav;
    CircleImageView imageViewNav;
    static ImageView imageViewDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userNameNav = (TextView) headerView.findViewById(R.id.textViewProfile);
        userNameNav.setText(LoginScreen.userName);

        imageViewNav = (CircleImageView) headerView.findViewById(R.id.imageViewProfile);

        imageViewDefault = (ImageView) findViewById(R.id.imageDefault);

//        imageViewNav.setImageBitmap(DBAdapter.bmp);

     //   txtViewTest = (TextView) findViewById(R.id.txtViewTest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            //System.exit(0);
        } else {
            //super.onBackPressed();
           // System.exit(0);



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcoming) {
         //   txtViewTest.setText("Upcoming Trips");
            imageViewDefault.setVisibility(View.INVISIBLE);
         UpcomingFragment upcomingFragment1=new UpcomingFragment();
            android.app.FragmentManager fmgr=getFragmentManager();
            FragmentTransaction ftr=fmgr.beginTransaction();
         //   ftr.add(R.id.RelativeContainner,upcomingFragment1);
            ftr.replace(R.id.RelativeContainner,upcomingFragment1);
            ftr.commit();

        } else if (id == R.id.nav_past) {
//            txtViewTest.setText("Past Trips");
            imageViewDefault.setVisibility(View.INVISIBLE);
            PastFragment pastFragment1=new PastFragment();
            android.app.FragmentManager fmgr=getFragmentManager();
            FragmentTransaction ftr=fmgr.beginTransaction();
            ftr.replace(R.id.RelativeContainner,pastFragment1);
            ftr.commit();
           flag=1;

        } else if (id == R.id.nav_add) {
           // txtViewTest.setText("Add New Trip");
            imageViewDefault.setVisibility(View.INVISIBLE);
            AddTripFragment Fragment1=new AddTripFragment();
            android.app.FragmentManager fmgr=getFragmentManager();
            FragmentTransaction ftr=fmgr.beginTransaction();
            ftr.replace(R.id.RelativeContainner,Fragment1,"Fragment1");
            ftr.commit();
            i2=1;

        } else if (id == R.id.nav_edit) {
            imageViewDefault.setVisibility(View.INVISIBLE);
            EditProfileFragment F=new EditProfileFragment();
            android.app.FragmentManager fmgr=getFragmentManager();
            FragmentTransaction ftr=fmgr.beginTransaction();
            ftr.replace(R.id.RelativeContainner,F,"Fragment1");
            ftr.commit();

        }
        else if (id == R.id.nav_rate) {
            imageViewDefault.setVisibility(View.INVISIBLE);

            android.app.AlertDialog.Builder      builder = new android.app.AlertDialog.Builder(ProfileNavigationDrawer.this);
            builder.setIcon(R.drawable.start);
            builder.setTitle("Alarm of My Trip ");
            builder.setMessage("Name Of Trip ");
            builder.setNegativeButton("start", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String country="cairo";
                    String city="alexandria";

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+country+","+country+"&daddr="+city+","+city));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });

builder.setPositiveButton("Later", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
//----------------------------//

        NotificationCompat.Builder   mNotification = new NotificationCompat.Builder(ProfileNavigationDrawer.this);
        mNotification.setSmallIcon(R.drawable.start);

        mNotification.setTicker("Trip Name");
        mNotification.setContentTitle("Click to Start your Trip");
        mNotification.setContentText("Click to Appear");


        mNotification.setDefaults(Notification.DEFAULT_VIBRATE);

        //here when user click on the notification .. it will go to  Notification Activity
        String country="cairo";
        String city="alexandria";

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+country+","+country+"&daddr="+city+","+city));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");


        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(ProfileNavigationDrawer.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotification.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); notificationManager.notify(0, mNotification.build());

           // startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(ProfileNavigationDrawer.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }

            Toast.makeText(ProfileNavigationDrawer.this, "Later Clicked ", Toast.LENGTH_SHORT).show();


    }
});

builder.show();

        }
        else
        if (id == R.id.nav_logOut)
        {

            imageViewDefault.setVisibility(View.INVISIBLE);
            RegisterScreen.sharedPreferences=getSharedPreferences("myTxt",0);
            RegisterScreen.sharedPreferences.edit().clear().commit();
            startActivity(new Intent(ProfileNavigationDrawer.this,LoginScreen.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
