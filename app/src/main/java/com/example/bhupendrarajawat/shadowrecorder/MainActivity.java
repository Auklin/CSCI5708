package com.example.bhupendrarajawat.shadowrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.Toast;
import java.io.IOException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Button play,stop;
    ImageButton record,play,stop;
    int count=0;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        play=(ImageButton)findViewById(R.id.playimage);
        stop=(ImageButton)findViewById(R.id.stopimage);
        //record=(Button)findViewById(R.id.record);
        record=(ImageButton)findViewById(R.id.recimage);
        stop.setVisibility(View.INVISIBLE);
        play.setVisibility(View.INVISIBLE);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.anim_bounce);
        final Animation animrotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        final Animation animalpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        record.startAnimation(myAnim);




        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    count++;
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+count+".amr";
                    myAudioRecorder=new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                record.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                stop.startAnimation(myAnim);
                stop.startAnimation(animrotate);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        myAudioRecorder.stop();
                        myAudioRecorder.release();
                        myAudioRecorder  = null;
                        Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                        if(record.getVisibility()!=View.VISIBLE)
                        {
                            record.performClick();
                            record.setPressed(true);
                        }
                        if(count==4)
                        {
                            count = 0;
                        }

                    }
                }, 10000);

            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAudioRecorder.stop();
                //myAudioRecorder.release();
                //myAudioRecorder  = null;

                stop.setVisibility(View.INVISIBLE);
                record.setVisibility(View.VISIBLE);
                record.startAnimation(myAnim);
                play.setVisibility(View.VISIBLE);
                play.startAnimation(myAnim);
                //Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                play.startAnimation(animalpha);
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                record.setVisibility(View.VISIBLE);
                record.startAnimation(myAnim);
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else */if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
