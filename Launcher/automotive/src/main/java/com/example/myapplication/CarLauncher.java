/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.car.carlauncher.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;

/**
 * Note by Wolf Lickefett: the ActivityView does not seem to be documented
 * but should exist in the android project
 */

/**
 * Basic Launcher for Android Automotive which demonstrates the use of {@link ActivityView} to host
 * maps content.
 *
 * <p>Note: On some devices, the ActivityView may render with a width, height, and/or aspect
 * ratio that does not meet Android compatibility definitions. Developers should work with content
 * owners to ensure content renders correctly when extending or emulating this class.
 *
 * <p>Note: Since the hosted maps Activity in ActivityView is currently in a virtual display, the
 * system considers the Activity to always be in front. Launching the maps Activity with a direct
 * Intent will not work. To start the maps Activity on the real display, send the Intent to the
 * Launcher with the {@link Intent#CATEGORY_APP_MAPS} category, and the launcher will start the
 * Activity on the real display.
 *
 * <p>Note: The state of the virtual display in the ActivityView is nondeterministic when
 * switching away from and back to the current user. To avoid a crash, this Activity will finish
 * when switching users.
 */

public class CarLauncher extends FragmentActivity {

    private static final String TAG = "CarLauncher";

    private ActivityView mActivityView;
    private boolean mActivityViewReady = false;
    private boolean mIsStarted = false;
//    private LinearLayout tripCompLayout;





    private final ActivityView.StateCallback mActivityViewCallback =
            new ActivityView.StateCallback() {
                @Override
                public void onActivityViewReady(ActivityView view) {
                    System.out.println("yoooooo asuh");
                    Log.d("asdf","fuckinhell");
                    mActivityViewReady = true;
//                    ActivityView myActivityView = findViewById(R.id.maps);//.startActivity(launchIntent,null);
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.contacts");

                    mActivityView.startActivity(launchIntent,android.os.Process.myUserHandle());


                    //startMapsInActivityView();
                }

                @Override
                public void onActivityViewDestroyed(ActivityView view) {
                    mActivityViewReady = false;
                }



                public void onTaskMovedToFront(int taskId) {
                    try {
                        if (mIsStarted) {
                            ActivityManager am =
                                    (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                            am.moveTaskToFront(CarLauncher.this.getTaskId(), /* flags= */ 0);
                        }
                    } catch (RuntimeException e) {
                        Log.w(TAG, "Failed to move CarLauncher to front.");
                    }
                }
            };

    public void sendMessage(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_launcher);
        mActivityView  = findViewById(R.id.maps);
        // Don't show the maps panel in multi window mode.
        // NOTE: CTS tests for split screen are not compatible with activity views on the default
        // activity of the launcher
//        if (isInMultiWindowMode() || isInPictureInPictureMode()) {
//            setContentView(R.layout.car_launcher_multiwindow);
//        } else {
//
//        }

        EditText typeText = findViewById(R.id.nameOfAppRN);
        TextView filledInName = findViewById(R.id.filledInName);
        typeText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    boolean foundOne = false;
                    List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
                    for (PackageInfo packageInfo : apps) {
                        if (packageInfo.packageName.contains(s)){
                            filledInName.setText(packageInfo.packageName);
                            foundOne = true;
                            break;
                        }


                    }
                    if (!foundOne){
                        filledInName.setText("N/A");
                    }else{
                        try
                        {
                            ImageView iconView = findViewById(R.id.iconOfApp);
                            Drawable icon = getPackageManager().getApplicationIcon((String)filledInName.getText());
                            iconView.setImageDrawable(icon);
                        }
                        catch (PackageManager.NameNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.TripComp).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("woah there boris");
                return true;
            }
        });


        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
        Log.d("first lard","then lard");
//get a list of installed apps.
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : apps) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
//            Log.d(TAG, "Source dir : " + packageInfo.sou);
            Log.d(TAG, "Launch Activity :" + getPackageManager().getLaunchIntentForPackage(packageInfo.packageName));
        }
        Log.d("lard over","then lard");


        ActivityInfo tp = pkgAppsList.get(pkgAppsList.size() - 1).activityInfo;


        ComponentName name=new ComponentName(tp.applicationInfo.packageName,
                tp.name);
        Intent i=new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(name);
        startActivity(i);


        initializeFragments();









        if (mActivityView != null) {

            mActivityView.setCallback(mActivityViewCallback);
            System.out.println("seting callback");
        }
//        ActivityView myActivityView = findViewById(R.id.maps);//.startActivity(launchIntent,null);

//        myActivityView.setCallback(mActivityViewCallback);

//        try{
//            Context c = myActivityView.getContext();
//            System.out.println("lol");
//            System.out.println(c);
//            System.out.println(launchIntent);
//            c.startActivity(launchIntent);
//            System.out.println("diddly borf");
//        }catch (Exception e){
//            System.out.println("fuck!!!! "+e.toString());
//        }




    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Set<String> categories = intent.getCategories();
        if (categories != null && categories.size() == 1 && categories.contains(
                Intent.CATEGORY_APP_MAPS)) {
      //      launchMapsActivity();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // startMapsInActivityView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: Hello World");
        System.out.println("hello again");
        mIsStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsStarted = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mActivityView != null && mActivityViewReady) {
            //mActivityView.release();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initializeFragments();
    }

    private void initializeFragments() {
       // PlaybackSupportFragment playbackFragment = new PlaybackSupportFragment();
        ContextualFragment contextualFragment = null;

       FrameLayout contextual = findViewById(R.id.contextual);

        if(contextual != null) {
            contextualFragment = new ContextualFragment();
        }
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        if(contextual != null) {
            fragmentTransaction.replace(R.id.contextual, contextualFragment);
        }

        fragmentTransaction.commitNow();
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.TripComp:
                Log.d("pressed clickity cliek","click");
                TextView filledInName = findViewById(R.id.filledInName);
                Intent launchIntent;
                if (filledInName.getText().equals("N/A")){
                    launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.automotive");
                }else{
                    launchIntent = getPackageManager().getLaunchIntentForPackage((String) filledInName.getText());
                }

                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }else{
                    System.out.println("fuck");
                }

        }
    }







//
//
//    private void startMapsInActivityView() {
//        // If we happen to be be resurfaced into a multi display mode we skip launching content
//        // in the activity view as we will get recreated anyway.
//        if (!mActivityViewReady || isInMultiWindowMode() || isInPictureInPictureMode()) {
//            return;
//        }
//        if (mActivityView != null) {
//            mActivityView.startActivity(getMapsIntent(), null);
//        }
//    }
//
//    private void launchMapsActivity() {
//        // Make sure the Activity launches on the current display instead of in the ActivityView
//        // virtual display.
//        final ActivityOptions options = ActivityOptions.makeBasic();
//        //options.setLaunchDisplayId(getDisplay().getDisplayId());
//        options.setLaunchDisplayId(0);
//        startActivity(getMapsIntent(), options.toBundle());
//    }
//
//    private Intent getMapsIntent() {
//        return Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MAPS);
//    }
//    private void startTripCompInActivityView() {
//        // If we happen to be be resurfaced into a multi display mode we skip launching content
//        // in the activity view as we will get recreated anyway.
//        if (!mActivityViewReady || isInMultiWindowMode() || isInPictureInPictureMode()) {
//            return;
//        }
//        if (mActivityView != null) {
//            mActivityView.startActivity(getTripCompIntent(), null);
//        }
//    }
//
//    private void launchTripCompActivity() {
//        // Make sure the Activity launches on the current display instead of in the ActivityView
//        // virtual display.
//        final ActivityOptions options = ActivityOptions.makeBasic();
//        //options.setLaunchDisplayId(getDisplay().getDisplayId());
//        options.setLaunchDisplayId(0);
//        startActivity(getTripCompIntent(), options.toBundle());
//    }
//
//    private Intent getTripCompIntent() {
//        //return null;
//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setComponent(new ComponentName("com.example.",
//                "com.qz.test.StartUpActivity"));
//    }



}
