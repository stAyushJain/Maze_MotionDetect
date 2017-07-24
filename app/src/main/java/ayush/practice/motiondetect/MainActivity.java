package ayush.practice.motiondetect;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView tv;
    private SensorManager sm;
    private LinearLayout linearRotatingView;
    private int maxX, maxY;
    private float originalX, originalY;
    private Disposable disposal;



//    added new


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.textView1);
        linearRotatingView = (LinearLayout)findViewById(R.id.linearRotatingView);
        RelativeLayout mRootView = (RelativeLayout)findViewById(R.id.mRootView);
        GameView gameView = new GameView(this, DrawMaze.getMaze(1));

        mRootView.addView(gameView);

        if(linearRotatingView != null) {
            originalX = linearRotatingView.getX();
            originalY = linearRotatingView.getY();

            Log.d("View Cord ", originalX+" , "+originalY);
        }




        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;





            }

    @Override
    protected void onResume() {
        super.onResume();
        //get sensor service
        sm=(SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        //Tell which sensor you are going to use
        //And declare delay of sensor
        //Register all to your sensor object to use
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);


    }
//
//    @Override
//    public void onSensorChanged(final SensorEvent event) {
//        int id = event.sensor.getType();
//
//        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
//
//            Completable.timer(50, TimeUnit.MICROSECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new CompletableObserver() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//                            disposal = d;
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                            //get x, y, z values
//                            float value[] = event.values;
//                            float x = value[0];
//                            float y = value[1];
//                            float z = value[2];
//
//                            Log.d("x is", x + "");
//                            Log.d("y  is ", y + "");
//                            Log.d("z  is ", z + "");
//
//                            if (x < 1 && x > -0.1 && y < 1 && y > -0.1)
//                                return;
//
//                            if (x > 2)
//                                x = 2;
//                            if (y > 2)
//                                y = 2;
//                            if (x < -2)
//                                x = -2;
//                            if (y < -2)
//                                y = -2;
//
//
////            if it is left motion
//
//                            if(x > 1 && y == 2)
//                            {
//                                x = 2;
//                                y = 2;
//                                boolean drawX = true, drawY = true;
//
//                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                    if (i != GameView.verticalStartXList.size()) {
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float myY = GameView.verticalStartYList.get(i);
//                                        float nextY = GameView.verticalEndYList.get(i);
//
//                                        if (((myX - (int) (originalX - x) - 35) > -70
//                                                && (myX - (int) (originalX - x) - 35) < 0)
//                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                            drawX = false;
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                        if (i != GameView.horzEndXList.size()) {
//                                            float myY = GameView.horzStartYList.get(i);
//                                            float myX = GameView.horzStartXList.get(i);
//                                            float nextX = GameView.horzEndXList.get(i);
//
//                                            Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
//                                                    + " " + myY);
//
//                                            if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
//                                                    && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
//                                                    && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                    && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                                drawY = false;
//                                                break;
//                                            }
//                                        }
//                                }
//
//
//
//                                ObjectAnimator translateX = null, translateY = null;
//
//                                if(drawX)
//                                if((originalX - x*1 - 35) > 0)
//                                {
////                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) (originalX - x*1));
////                                    layoutParams.topMargin  = ((int) (originalY + y*1));
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
//
//                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() - 35;
//                                    translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//
//                                }
//
//                                if(drawY)
//                                    if((originalY + y*1 + linearRotatingView.getHeight() + 300) < maxY)
//                                {
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() + 35;
//                                    translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//                                }
//
//                                if(drawX || drawY) {
//                                    AnimatorSet anim = new AnimatorSet();
//                                    anim.setInterpolator(new LinearInterpolator());
//                                    if (drawX && drawY)
//                                        anim.playTogether(translateX, translateY);
//                                    else if (drawX)
//                                        anim.play(translateX);
//                                    else if (drawY)
//                                        anim.play(translateY);
//                                    anim.start();
//
//                                    anim.addListener(new Animator.AnimatorListener() {
//                                        @Override
//                                        public void onAnimationStart(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            originalX = linearRotatingView.getX();
//                                            originalY = linearRotatingView.getY();
//                                        }
//
//                                        @Override
//                                        public void onAnimationCancel(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationRepeat(Animator animation) {
//
//                                        }
//                                    });
//                                }
//
//
//                            }
//                            else if ( x < - 1 && y < -.55)
//                            {
//                                x = -2;
//                                y = -2;
//                                boolean drawX = true, drawY = true;
//
//
//                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                    if (i != GameView.horzEndXList.size()) {
//                                        float myY = GameView.horzStartYList.get(i);
//                                        float myX = GameView.horzStartXList.get(i);
//                                        float nextX = GameView.horzEndXList.get(i);
//
//
//                                        if (((-myY + (int) (originalY + y - 35)) < 8
//                                                && (-myY + (int) (originalY + y - 35)) > -30)
//                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                            drawX = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                        if (i != GameView.verticalStartXList.size()) {
//                                            float myX = GameView.verticalStartXList.get(i);
//                                            float myY = GameView.verticalStartYList.get(i);
//                                            float nextY = GameView.verticalEndYList.get(i);
//                                            Log.d("hee", linearRotatingView.getWidth() + "");
//                                            if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
//                                                    && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
//                                                    && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                    && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                                drawY = false;
//                                                break;
//                                            }
//                                        }
//                                }
//
//
//
//                                if(drawX)
//                                if((originalX - x*1 + linearRotatingView.getWidth() + 35) < maxX )
//                                {
////                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) (originalX - x*1));
////                                    layoutParams.topMargin = ((int) (originalY + y*1));
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
//
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() - 35;
//
//
//                                }
//                                if(drawY)
//                                    if((originalY + y*1 - 35) > 0)
//                                    {
//
//                                    }
//
//
//
//                                ObjectAnimator translateX = null, translateY = null;
//
//                                if(drawX)
//                                    if((originalX - x*1 - 35) > 0)
//                                    {
////                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) (originalX - x*1));
////                                    layoutParams.topMargin  = ((int) (originalY + y*1));
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
//
//                                        int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() + 35;
//                                        translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//
//                                    }
//
//                                if(drawY)
//                                    if((originalY + y*1 + linearRotatingView.getHeight() + 300) < maxY)
//                                    {
//                                        int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() + 35;
//                                        translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//                                    }
//
//                                if(drawX || drawY) {
//                                    AnimatorSet anim = new AnimatorSet();
//                                    anim.setInterpolator(new LinearInterpolator());
//                                    if (drawX && drawY)
//                                        anim.playTogether(translateX, translateY);
//                                    else if (drawX)
//                                        anim.play(translateX);
//                                    else if (drawY)
//                                        anim.play(translateY);
//                                    anim.start();
//
//                                    anim.addListener(new Animator.AnimatorListener() {
//                                        @Override
//                                        public void onAnimationStart(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            originalX = linearRotatingView.getX();
//                                            originalY = linearRotatingView.getY();
//                                        }
//
//                                        @Override
//                                        public void onAnimationCancel(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationRepeat(Animator animation) {
//
//                                        }
//                                    });
//                                }
//
//                            }
//                            else if (x < -.1 && y == 2)
//                            {
//                                    x = -2;
//                                boolean draw = true;
//
//                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                    if (i != GameView.verticalStartXList.size()) {
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float myY = GameView.verticalStartYList.get(i);
//                                        float nextY = GameView.verticalEndYList.get(i);
//                                        Log.d("hee", linearRotatingView.getWidth() + "");
//                                        if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
//                                                && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
//                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if(draw)
//                                {
//                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                        if (i != GameView.horzEndXList.size()) {
//                                            float myY = GameView.horzStartYList.get(i);
//                                            float myX = GameView.horzStartXList.get(i);
//                                            float nextX = GameView.horzEndXList.get(i);
//
//                                            Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
//                                                    + " " + myY);
//
//                                            if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
//                                                    && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
//                                                    && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                    && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                                draw = false;
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//
//                                if(draw)
//                                if((originalX - x*1 + linearRotatingView.getWidth() + 35) < maxX && (originalY + y*1 + linearRotatingView.getHeight() + 300) < maxY)
//                                {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) ((originalX - x*1) * 1));
////                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
//                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() + 35;
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() + 35;
//
//                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//                                    AnimatorSet anim = new AnimatorSet();
//                                    anim.setInterpolator(new LinearInterpolator());
//                                    anim.playTogether(translateX,translateY);
//                                    anim.start();
//
//                                    anim.addListener(new Animator.AnimatorListener() {
//                                        @Override
//                                        public void onAnimationStart(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            originalX = linearRotatingView.getX();
//                                            originalY = linearRotatingView.getY();
//                                        }
//
//                                        @Override
//                                        public void onAnimationCancel(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationRepeat(Animator animation) {
//
//                                        }
//                                    });
//                                }
//                            }
//                            else if(x > .5 && y < -.15)
//                            {
//                                x = 2;
//                                y = -2;
//                                boolean draw = true;
//
//
//                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                    if (i != GameView.horzEndXList.size()) {
//                                        float myY = GameView.horzStartYList.get(i);
//                                        float myX = GameView.horzStartXList.get(i);
//                                        float nextX = GameView.horzEndXList.get(i);
//
//
//                                        if (((-myY + (int) (originalY + y - 35)) < 8
//                                                && (-myY + (int) (originalY + y - 35)) > -30)
//                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if (draw) {
//                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                        if (i != GameView.verticalStartXList.size()) {
//                                            float myX = GameView.verticalStartXList.get(i);
//                                            float myY = GameView.verticalStartYList.get(i);
//                                            float nextY = GameView.verticalEndYList.get(i);
//
//                                            if (((myX - (int) (originalX - x) - 35) > -70
//                                                    && (myX - (int) (originalX - x) - 35) < 0)
//                                                    && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                    && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                                draw = false;
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//
//                                if(draw)
//                                if((originalX - x*1 - 35) > 0 && originalY + y*1 - 35  > 0)
//                                {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) (originalX - x*1));
////                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() - 35;
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() - 35;
//
//                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//                                    AnimatorSet anim = new AnimatorSet();
//                                    anim.setInterpolator(new LinearInterpolator());
//                                    anim.playTogether(translateX,translateY);
//                                    anim.start();
//
//                                    anim.addListener(new Animator.AnimatorListener() {
//                                        @Override
//                                        public void onAnimationStart(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            originalX = linearRotatingView.getX();
//                                            originalY = linearRotatingView.getY();
//                                        }
//
//                                        @Override
//                                        public void onAnimationCancel(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationRepeat(Animator animation) {
//
//                                        }
//                                    });
//                                }
//                            }
//                            else if (x < -1.6) {
//                                x = -2;
//                                boolean draw = true;
//
//                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                    if (i != GameView.verticalStartXList.size()) {
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float myY = GameView.verticalStartYList.get(i);
//                                        float nextY = GameView.verticalEndYList.get(i);
//                                        Log.d("hee", linearRotatingView.getWidth() + "");
//                                        if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
//                                                && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
//                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                if(draw)
//                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//
//                                    float myY = GameView.horzStartYList.get(i);
//                                    float myX = GameView.horzStartXList.get(i);
//                                    float nextX = GameView.horzEndXList.get(i);
//
//                                    if((myX - (int) (originalX - x + linearRotatingView.getWidth()) + 35) < 70 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
//                                    {
//                                        draw = false;
//                                        break;
//                                    }
////                                        if y1 < pty < y2
//
////                                                 --
////                                                    ----
////                                                 --
//                                }
//
//
//                                if (draw)
//                                    if ((originalX - x * 1 + linearRotatingView.getWidth() + 35) < maxX) {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) ((originalX - x*1) * 1));
////                                    layoutParams.topMargin = (int)originalY;
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//                                        int deltaX = ((int) (originalX - x * 1)) - linearRotatingView.getLeft() + 35;
////                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop();
//
//                                        ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
////                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//
////                                    translateX.setDuration(100);
////                                    translateY.setDuration(100);
//
//                                        translateX.start();
////                                    translateY.start();
//
//                                        translateX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
//                                                originalX = linearRotatingView.getX();
//                                                originalY = linearRotatingView.getY();
//                                            }
//                                        });
//                                    }
//                            }
////            right motion
//                            else if (x > 1.7) {
//                                x = 2;
//                                boolean draw = true;
//
//                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                    if (i != GameView.verticalStartXList.size()) {
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float myY = GameView.verticalStartYList.get(i);
//                                        float nextY = GameView.verticalEndYList.get(i);
//
//                                        if (((myX - (int) (originalX - x) - 35) > -70
//                                                && (myX - (int) (originalX - x) - 35) < 0)
//                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
//                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                if(draw)
//                                {
//                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//
//                                        float myY = GameView.horzStartYList.get(i);
//                                        float myX = GameView.horzStartXList.get(i);
//                                        float nextX = GameView.horzEndXList.get(i);
//
//                                        if(((int) (originalX - x) - 35) - nextX > -70 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
//                                        {
//                                            draw = false;
//                                            break;
//                                        }
////                                        if y1 < pty < y2
//
////                                                 --
////                                            ----
////                                                 --
//                                    }
//                                }
//
//                                if (draw) {
//                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {
////                                        if(i != GameView.horzEndXList.size()) {
////                                            float myY = GameView.horzStartYList.get(i);
////                                            float myX = GameView.horzStartXList.get(i);
////                                            float nextX = GameView.horzEndXList.get(i);
////
////                                            if ((( myY - (int) (originalY + y +linearRotatingView.getHeight() + 7)) < 8
////                                                    && ( myY - (int) (originalY + y +linearRotatingView.getHeight() + 7)) > 0)
////                                                    && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX )
////                                                    && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX ))  {
////                                                draw = false;
////                                                break;
////                                            }
////                                            else
////                                                draw = true;
////                                        }
//                                    }
//                                }
//
//
//                                if (draw)
//                                    if ((originalX - x * 1 - 35) > 0) {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.leftMargin = ((int) (originalX - x*1));
////                                    layoutParams.topMargin = (int)originalY;
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
//                                        int deltaX = ((int) (originalX - x * 1)) - linearRotatingView.getLeft() - 35;
////                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop();
//
//                                        ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
////                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//
////                                    translateX.setDuration(100);
////                                    translateY.setDuration(100);
//
//                                        translateX.start();
////                                    translateY.start();
//
//                                        translateX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
//                                                originalX = linearRotatingView.getX();
//                                                originalY = linearRotatingView.getY();
//                                            }
//                                        });
//
//                                    } else {
//
//                                    }
//                            } else if (y >= 2) {
//                                boolean draw = true;
//                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                    if (i != GameView.horzEndXList.size()) {
//                                        float myY = GameView.horzStartYList.get(i);
//                                        float myX = GameView.horzStartXList.get(i);
//                                        float nextX = GameView.horzEndXList.get(i);
//
//                                        Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
//                                                + " " + myY);
//
//                                        if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
//                                                && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
//                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if (draw) {
//                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float myY = GameView.verticalStartYList.get(i);
//
//                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8) {
//                                            draw = false;
//                                            break;
//                                        }
//
////                                        if x1 < ptX < x2 and (pty - (y1 + 35) < 8)
//
////                                        if maxY and minY of line is between
////                                        |   |
////                                         |
////
////
////
//                                    }
//                                }
//
//                                if (draw)
//                                    if ((originalY + y * 1 + linearRotatingView.getHeight() + 300) < maxY) {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
////                                    layoutParams.leftMargin = (int)originalX;
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
////                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft();
//                                        int deltaY = ((int) (originalY + y * 1)) - linearRotatingView.getTop() + 35;
//
////                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                        ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//
////                                    translateX.setDuration(100);
////                                    translateY.setDuration(100);
//
////                                    translateX.start();
//                                        translateY.start();
//
//                                        translateY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
//                                                originalX = linearRotatingView.getX();
//                                                originalY = linearRotatingView.getY();
//                                            }
//                                        });
//                                    }
//                            } else if (y < -.2) {
//                                y = -2;
//                                boolean draw = true;
//                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
//                                    if (i != GameView.horzEndXList.size()) {
//                                        float myY = GameView.horzStartYList.get(i);
//                                        float myX = GameView.horzStartXList.get(i);
//                                        float nextX = GameView.horzEndXList.get(i);
//
//
//                                        if (((-myY + (int) (originalY + y - 35)) < 8
//                                                && (-myY + (int) (originalY + y - 35)) > -30)
//                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
//                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
//                                            draw = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if (draw) {
//                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {
//
//                                        float myX = GameView.verticalStartXList.get(i);
//                                        float nextY = GameView.verticalEndYList.get(i);
//
//                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && ((int) (originalY + y - 35) - nextY) < 8) {
//                                            draw = false;
//                                            break;
//                                        }
//
//                                    }
//
//                                    if (draw)
//                                        if (originalY + y * 1 - 35 > 0) {
////                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
////                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
////                                    layoutParams.leftMargin = (int)originalX;
////                                    linearRotatingView.setLayoutParams(layoutParams);
////                                    linearRotatingView.requestLayout();
////                                    linearRotatingView.invalidate();
//
//
////                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft();
//                                            int deltaY = ((int) (originalY + y * 1)) - linearRotatingView.getTop() - 35;
//
////                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                            ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
//
////                                    translateX.setDuration(100);
////                                    translateY.setDuration(100);
//
////                                    translateX.start();
//                                            translateY.start();
//
//                                            translateY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                                @Override
//                                                public void onAnimationUpdate(ValueAnimator animation) {
//                                                    originalX = linearRotatingView.getX();
//                                                    originalY = linearRotatingView.getY();
//                                                }
//                                            });
//                                        }
//                                }
//
//
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//
//                        }
//                    });
//
//
//        }
//    }


    @Override
    public void onSensorChanged(final SensorEvent event) {
        int id = event.sensor.getType();

        if(event.sensor.getType()== Sensor.TYPE_ACCELEROMETER) {

            Completable.timer(50, TimeUnit.MICROSECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            disposal = d;
                        }

                        @Override
                        public void onComplete() {

                            //get x, y, z values
                            float value[] = event.values;
                            float x = value[0];
                            float y = value[1];
                            float z = value[2];

                            Log.d("x is", x + "");
                            Log.d("y  is ", y + "");
                            Log.d("z  is ", z + "");

                            if (x < 1 && x > -0.1 && y < 1 && y > -0.1)
                                return;

                            if (x > 2)
                                x = 2;
                            if (y > 2)
                                y = 2;
                            if (x < -2)
                                x = -2;
                            if (y < -2)
                                y = -2;


//            if it is left motion

                            if(x > 1 && y == 2)
                            {
                                boolean drawX = true, drawY = true;
                                boolean midDrawX = true, midDrawY = true;

                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if (((myX - (int) (originalX - x) - 35) > -70
                                                && (myX - (int) (originalX - x) - 35) < 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            drawX = false;
                                            break;
                                        }
                                    }
                                }

                                if(drawX)
                               {
                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                    float myY = GameView.horzStartYList.get(i);
                                    float myX = GameView.horzStartXList.get(i);
                                    float nextX = GameView.horzEndXList.get(i);

                                    if(((int) (originalX - x) - 35) - nextX > -70 && ((int) (originalX - x) - 35) - myX > 0 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
                                    {
                                        drawX = false;
                                        midDrawX = false;
                                        break;
                                    }
//                                        if y1 < pty < y2

//                                                 --
//                                            ----
//                                                 --
                                }
                            }


                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
                                                + " " + myY);

                                        if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
                                                && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            drawY = false;
                                            break;
                                        }
                                    }
                                }
                                if (drawY) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);

                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8) {
                                            drawY = false;
                                            midDrawY = false;
                                            break;
                                        }

//                                        if x1 < ptX < x2 and (pty - (y1 + 35) < 8)

//                                        if maxY and minY of line is between
//                                        |   |
//                                         |
//
//
//
                                    }
                                }



                                ObjectAnimator translateX = null, translateY = null;

                                if(drawX)
                                    if((originalX - x*1 - 35) > 0)
                                    {
//                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) (originalX - x*1));
//                                    layoutParams.topMargin  = ((int) (originalY + y*1));
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();



                                        int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() - 35;
                                        translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);

                                    }
                                    else
                                        drawX = false;

                                if(drawY)
                                    if((originalY + y*1 + linearRotatingView.getHeight() + 300) < maxY)
                                    {
                                        int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() + 35;
                                        translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
                                    }
                                    else
                                        drawY = false;

                                if(drawX || drawY) {
                                    AnimatorSet anim = new AnimatorSet();
                                    anim.setInterpolator(new LinearInterpolator());
                                    if (drawX && drawY)
                                        anim.playTogether(translateX, translateY);
                                    else if (drawX)
                                        anim.play(translateX);
                                    else if (drawY)
                                        anim.play(translateY);
//                                    if(midDrawX && midDrawY)
                                    anim.start();

                                    anim.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            originalX = linearRotatingView.getX();
                                            originalY = linearRotatingView.getY();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                }


                            }
                            else if ( x < - 1 && y < -.55)
                            {
                                boolean drawX = true, drawY = true;
                                boolean midDrawX = true, midDrawY = true;


                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);


                                        if (((-myY + (int) (originalY + y - 35)) < 8
                                                && (-myY + (int) (originalY + y - 35)) > -30)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            drawY = false;
                                            break;
                                        }

                                    }
                                }

                                if (drawY) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && ((int) (originalY + y - 35) - nextY) < 35 && ((int) (originalY + y - 35) - nextY) > 0) {
                                            drawY = false;
                                            midDrawY = false;
                                            break;
                                        }

                                    }
                                }

                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);
                                        Log.d("hee", linearRotatingView.getWidth() + "");
                                        if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
                                                && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            drawX = false;
                                            break;
                                        }
                                    }
                                }
                                if(drawX)
                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        if((myX - (int) (originalX - x + linearRotatingView.getWidth()) + 35) < 70 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
                                        {
                                            drawX = false;
                                            midDrawX = false;
                                            break;
                                        }
//                                        if y1 < pty < y2

//                                                 --
//                                                    ----
//                                                 --
                                    }

                                ObjectAnimator translateX = null, translateY = null;

                                if(drawX)
                                    if((originalX - x*1 + linearRotatingView.getWidth() + 35) < maxX)
                                    {
//                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) (originalX - x*1));
//                                    layoutParams.topMargin  = ((int) (originalY + y*1));
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();



                                        int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() + 35;
                                        translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);

                                    }
                                    else
                                        drawX = false;

                                if(drawY)
                                    if((originalY + y*1 - 35) > 0)
                                    {
                                        int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() - 35;
                                        translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
                                    }
                                    else
                                        drawY = false;

                                if(drawX || drawY) {
                                    AnimatorSet anim = new AnimatorSet();
                                    anim.setInterpolator(new LinearInterpolator());
                                    if (drawX && drawY)
                                        anim.playTogether(translateX, translateY);
                                    else if (drawX)
                                        anim.play(translateX);
                                    else if (drawY)
                                        anim.play(translateY);
//                                    if(midDrawY && midDrawX)
                                    anim.start();

                                    anim.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            originalX = linearRotatingView.getX();
                                            originalY = linearRotatingView.getY();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                }

                            }
                            else if (x < -.1 && y == 2)
                            {
                                boolean drawX = true, drawY = true;
                                boolean midDrawY = true, midDrawX = true;;

                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);
                                        Log.d("hee", linearRotatingView.getWidth() + "");
                                        if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
                                                && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            drawX = false;
                                            break;
                                        }
                                    }
                                }
                                if(drawX)
                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        if((myX - (int) (originalX - x + linearRotatingView.getWidth()) + 35) < 70 && (myY >= originalY) && (myY <= originalY + linearRotatingView.getHeight()))
                                        {
                                            drawX = false;
                                            midDrawX = false;
                                            break;
                                        }
//                                        if y1 < pty < y2

//                                                 --
//                                                    ----
//                                                 --
                                    }
                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
                                                + " " + myY);

                                        if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
                                                && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            drawY = false;
                                            break;
                                        }
                                    }
                                }
                                if (drawY) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);

                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8) {
                                            drawY = false;
                                            midDrawY = false;
                                            break;
                                        }

//                                        if x1 < ptX < x2 and (pty - (y1 + 35) < 8)

//                                        if maxY and minY of line is between
//                                        |   |
//                                         |
//
//
//
                                    }
                                }

                                ObjectAnimator translateX = null, translateY = null;

                                if(drawX)
                                    if((originalX - x*1 + linearRotatingView.getWidth() + 35) < maxX)
                                    {
//                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) (originalX - x*1));
//                                    layoutParams.topMargin  = ((int) (originalY + y*1));
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();



                                        int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() + 35;
                                        translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);

                                    }
                                    else
                                        drawX = false;

                                if(drawY)
                                    if((originalY + y*1 + linearRotatingView.getHeight() + 300) < maxY)
                                    {
                                        int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() + 35;
                                        translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
                                    }
                                    else
                                        drawY = false;

                                if(drawX || drawY) {
                                    AnimatorSet anim = new AnimatorSet();
                                    anim.setInterpolator(new LinearInterpolator());
                                    if (drawX && drawY)
                                        anim.playTogether(translateX, translateY);
                                    else if (drawX)
                                        anim.play(translateX);
                                    else if (drawY)
                                        anim.play(translateY);

//                                    if(midDrawY && midDrawX)
                                    anim.start();

                                    anim.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            originalX = linearRotatingView.getX();
                                            originalY = linearRotatingView.getY();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                }

                            }
                            else if(x > .5 && y < -.15)
                            {
                                boolean drawY = true, drawX = true;
                                boolean midDrawY = true, midDrawX = true;;


                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);


                                        if (((-myY + (int) (originalY + y - 35)) <= 8
                                                && (-myY + (int) (originalY + y - 35)) > -35)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            drawY = false;
                                            break;
                                        }
                                    }
                                }

                                if (drawY) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && ((int) (originalY + y - 35) - nextY) < 35 && ((int) (originalY + y - 35) - nextY) > 0) {
                                            drawY = false;
                                            midDrawY = false;
                                            break;
                                        }

                                    }
                                }
                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if (((myX - (int) (originalX - x) - 35) > -70
                                                && (myX - (int) (originalX - x) - 35) < 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            drawX = false;
                                            break;
                                        }
                                    }
                                }

                                if(drawX)
                                {
                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        if(((int) (originalX - x) - 35) - nextX > -70 && ((int) (originalX - x) - 35) - myX > 0 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
                                        {
                                            drawX = false;
                                            midDrawX = false;
                                            break;
                                        }
//                                        if y1 < pty < y2

//                                                 --
//                                            ----
//                                                 --
                                    }
                                }

                                ObjectAnimator translateX = null, translateY = null;

                                if(drawX)
                                    if((originalX - x*1 - 35) > 0)
                                    {
//                                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) (originalX - x*1));
//                                    layoutParams.topMargin  = ((int) (originalY + y*1));
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();



                                        int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft() - 35;
                                        translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);

                                    }
                                    else
                                        drawX = false;

                                if(drawY)
                                    if(originalY + y*1 - 35  > 0)
                                    {
                                        int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop() - 35;
                                        translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);
                                    }
                                    else
                                        drawY = false;

                                if(drawX || drawY) {
                                    AnimatorSet anim = new AnimatorSet();
                                    anim.setInterpolator(new LinearInterpolator());
                                    if (drawX && drawY)
                                        anim.playTogether(translateX, translateY);
                                    else if (drawX)
                                        anim.play(translateX);
                                    else if (drawY)
                                        anim.play(translateY);
//                                    if(midDrawY && midDrawX)
                                    anim.start();

                                    anim.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            originalX = linearRotatingView.getX();
                                            originalY = linearRotatingView.getY();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                }



                            }
                            else if (x < -1.6) {
                                x = -2;
                                boolean draw = true;

                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);
                                        Log.d("hee", linearRotatingView.getWidth() + "");
                                        if (((myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) < 70
                                                && (myX - (int) (originalX - x * 1 + linearRotatingView.getWidth()) + 35) > 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            draw = false;
                                            break;
                                        }
                                    }
                                }

                                if(draw)
                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        if((myX - (int) (originalX - x + linearRotatingView.getWidth()) + 35) < 70 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
                                        {
                                            draw = false;
                                            break;
                                        }
//                                        if y1 < pty < y2

//                                                 --
//                                                    ----
//                                                 --
                                    }


                                if (draw)
                                    if ((originalX - x * 1 + linearRotatingView.getWidth() + 35) < maxX) {
//                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) ((originalX - x*1) * 1));
//                                    layoutParams.topMargin = (int)originalY;
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();

                                        int deltaX = ((int) (originalX - x * 1)) - linearRotatingView.getLeft() + 35;
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop();

                                        ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);

//                                    translateX.setDuration(100);
//                                    translateY.setDuration(100);

                                        translateX.start();
//                                    translateY.start();

                                        translateX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                originalX = linearRotatingView.getX();
                                                originalY = linearRotatingView.getY();
                                            }
                                        });
                                    }
                            }
//            right motion
                            else if (x > 1.7) {
                                x = 2;
                                boolean draw = true;

                                for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                    if (i != GameView.verticalStartXList.size()) {
                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if (((myX - (int) (originalX - x) - 35) > -70
                                                && (myX - (int) (originalX - x) - 35) <= 0)
                                                && ((originalY) >= myY || (originalY + linearRotatingView.getHeight()) >= myY)
                                                && ((originalY) <= nextY || (originalY + linearRotatingView.getHeight()) <= nextY)) {
                                            draw = false;
                                            break;
                                        }
                                    }
                                }

                                if(draw)
                                {
                                    for (int i = 0; i < GameView.horzEndXList.size(); i++) {

                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        if(((int) (originalX - x) - 35) - nextX > -70 && ((int) (originalX - x) - 35) - myX > 0 && (myY > originalY) && (myY < originalY + linearRotatingView.getHeight()))
                                        {
                                            draw = false;
                                            break;
                                        }
//                                        if y1 < pty < y2

//                                                 --
//                                            ----
//                                                 --
                                    }
                                }

                                if (draw)
                                    if ((originalX - x * 1 - 35) > 0) {
//                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.leftMargin = ((int) (originalX - x*1));
//                                    layoutParams.topMargin = (int)originalY;
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();


                                        int deltaX = ((int) (originalX - x * 1)) - linearRotatingView.getLeft() - 35;
//                                    int deltaY = ((int) (originalY + y*1)) - linearRotatingView.getTop();

                                        ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
//                                    ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);

//                                    translateX.setDuration(100);
//                                    translateY.setDuration(100);

                                        translateX.start();
//                                    translateY.start();

                                        translateX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                originalX = linearRotatingView.getX();
                                                originalY = linearRotatingView.getY();
                                            }
                                        });

                                    } else {

                                    }
                            } else if (y >= 2) {
                                boolean draw = true;
                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);

                                        Log.d("sv :", (int) (originalY + y + linearRotatingView.getHeight() + 35)
                                                + " " + myY);

                                        if (((myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 8
                                                && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) > -35)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            draw = false;
                                            break;
                                        }
                                    }
                                }
                                if (draw) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float myY = GameView.verticalStartYList.get(i);

                                        if ((originalX <= myX) && (originalX + linearRotatingView.getWidth() >= myX) && (myY - (int) (originalY + y + linearRotatingView.getHeight() + 35)) < 35) {
                                            draw = false;
                                            break;
                                        }

//                                        if x1 < ptX < x2 and (pty - (y1 + 35) < 8)

//                                        if maxY and minY of line is between
//                                        |   |
//                                         |
//
//
//
                                    }
                                }

                                if (draw)
                                    if ((originalY + y * 1 + linearRotatingView.getHeight() + 300) < maxY) {
//                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
//                                    layoutParams.leftMargin = (int)originalX;
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();

//                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft();
                                        int deltaY = ((int) (originalY + y * 1)) - linearRotatingView.getTop() + 35;

//                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
                                        ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);

//                                    translateX.setDuration(100);
//                                    translateY.setDuration(100);

//                                    translateX.start();
                                        translateY.start();

                                        translateY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                originalX = linearRotatingView.getX();
                                                originalY = linearRotatingView.getY();
                                            }
                                        });
                                    }
                            } else if (y < -.2) {
                                y = -2;
                                boolean draw = true;
                                for (int i = 0; i < GameView.horzEndXList.size(); i++) {
                                    if (i != GameView.horzEndXList.size()) {
                                        float myY = GameView.horzStartYList.get(i);
                                        float myX = GameView.horzStartXList.get(i);
                                        float nextX = GameView.horzEndXList.get(i);


                                        if (((-myY + (int) (originalY + y - 35)) <= 0
                                                && (-myY + (int) (originalY + y - 35)) >= -35)
                                                && ((originalX) >= myX || (originalX + linearRotatingView.getWidth()) >= myX)
                                                && ((originalX) <= nextX || (originalX + linearRotatingView.getWidth()) <= nextX)) {
                                            draw = false;
                                            break;
                                        }
                                    }
                                }
                                if (draw) {
                                    for (int i = 0; i < GameView.verticalStartXList.size(); i++) {

                                        float myX = GameView.verticalStartXList.get(i);
                                        float nextY = GameView.verticalEndYList.get(i);

                                        if ((originalX < myX) && (originalX + linearRotatingView.getWidth() > myX) && ((int) (originalY + y - 35) - nextY) < 35 && ((int) (originalY + y - 35) - nextY) > 0) {
                                            draw = false;
                                            break;
                                        }

                                    }
                                }

                                    if (draw)
                                        if (originalY + y * 1 - 35 > 0) {
//                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearRotatingView.getWidth(), linearRotatingView.getHeight());
//                                    layoutParams.topMargin = ((int) ((originalY + y*1) * 1));
//                                    layoutParams.leftMargin = (int)originalX;
//                                    linearRotatingView.setLayoutParams(layoutParams);
//                                    linearRotatingView.requestLayout();
//                                    linearRotatingView.invalidate();


//                                    int deltaX = ((int) (originalX - x*1)) - linearRotatingView.getLeft();
                                            int deltaY = ((int) (originalY + y * 1)) - linearRotatingView.getTop() - 35;

//                                    ObjectAnimator translateX = ObjectAnimator.ofFloat(linearRotatingView, "translationX", deltaX);
                                            ObjectAnimator translateY = ObjectAnimator.ofFloat(linearRotatingView, "translationY", deltaY);

//                                    translateX.setDuration(100);
//                                    translateY.setDuration(100);

//                                    translateX.start();
                                            translateY.start();

                                            translateY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                @Override
                                                public void onAnimationUpdate(ValueAnimator animation) {
                                                    originalX = linearRotatingView.getX();
                                                    originalY = linearRotatingView.getY();
                                                }
                                            });
                                        }

                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        disposal.dispose();
        sm.unregisterListener(this);
    }
}

//
//|| ((int) (originalX - x * 1 + linearRotatingView.getWidth()) + 9) == myX
//        || ((int) (originalX - x * 1 + linearRotatingView.getWidth()) - 8) == myX
//        || ((int) (originalX - x * 1 + linearRotatingView.getWidth()) - 9) == myX)