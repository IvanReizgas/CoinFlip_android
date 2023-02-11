package com.kruegerapps.coinflip;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        ImageButton myButton = findViewById(R.id.coin);
        //myButton.setAnimation(myAnim);
        myButton.setOnTouchListener(new OnSwipeListener(this, myButton) {
            @Override
            public void onSwipeDown() {
                setAnimation(myButton, R.anim.rotate_y, DynamicAnimation.ROTATION_X, -200f);
            }

            @Override
            public void onSwipeLeft() {
                setAnimation(myButton, R.anim.rotate_x_l, DynamicAnimation.ROTATION_Y, -200f);
            }

            @Override
            public void onSwipeUp() {
                setAnimation(myButton, R.anim.rotate_y, DynamicAnimation.ROTATION_X, 200f);
            }

            @Override
            public void onSwipeRight() {
                setAnimation(myButton, R.anim.rotate_x_r, DynamicAnimation.ROTATION_Y, 200f);
            }
        });
//        myButton.setOnClickListener(v -> {
//            SpringAnimation anim = new SpringAnimation(v, DynamicAnimation.ROTATION_Y, 720);
//            anim.setStartValue(0);
//            anim.setStartVelocity(50);
//            anim.getSpring()
//                    .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//            anim.getSpring()
//                    .setStiffness(800f);
//            anim.start();
//           // startLoadingAnimations();
//        });


    }

    private void setAnimation(ImageButton v, int directionStart, DynamicAnimation.ViewProperty directionEnd, float finalPos) {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), directionStart);
//        anim.setRepeatMode(Animation.INFINITE);
//        anim.setRepeatCount(4);
        AtomicBoolean b = new AtomicBoolean(true);
        Runnable run = new Runnable() {
            int i = 0;
            public void run() {
                while (b.get()) {
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
                    v.setBackgroundResource(i % 2 == 0 ? R.drawable.euro_new : R.drawable.euro_back);
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(new Runnable() {
            public void run() {

                MainActivity.this.runOnUiThread(new Runnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        while (b.get()) {
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
                            v.setBackgroundResource(i % 2 == 0 ? R.drawable.euro_new : R.drawable.euro_back);
                            i++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                //v.post(run);
            }
        });
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //thread.start();
                //v.post(run);
//                int i = 0;
//                while (b.get()) {
//                    v.setBackgroundResource(i % 2 == 0 ? R.drawable.euro_new : R.drawable.euro_back);
//                    i++;
//                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //v.removeCallbacks(run);
                //b.set(false);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX1  " + b.get());
                SpringAnimation anim2 = new SpringAnimation(v, directionEnd, 0);
                anim2.setStartValue(-10);
                anim2.setStartVelocity(10);
                anim2.getSpring()
                        .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                anim2.getSpring()
                        .setStiffness(SpringForce.STIFFNESS_LOW);
                //anim2.animateToFinalPosition(finalPos);
                anim2.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX2  " + b.get());
                v.setBackgroundResource(b.getAndSet(!b.get()) ? R.drawable.euro_new : R.drawable.euro_back);
                onAnimationRepeat(animation);
            }
        });
        v.startAnimation(anim);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}