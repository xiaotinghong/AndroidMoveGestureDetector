package com.example.xiaotinghong.movegesturedetector;

import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private static String TAG = "Move Gesture Detector";

    List<ImageView> allImageViews;
    private ImageView selectedImageView;

    private MoveGestureDetector moveGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allImageViews = new LinkedList<>();
        allImageViews.add( (ImageView) findViewById(R.id.image_view_1) );
        allImageViews.add( (ImageView) findViewById(R.id.image_view_2) );

        selectedImageView = null;

        moveGestureDetector = new MoveGestureDetector(this, new MoveListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let moveGestureDetector detects all events
        if (moveGestureDetector != null) {
            moveGestureDetector.onTouchEvent(event);
        }

        // Decide which image view should be moved based on events
        updateSelectedImageView(event);

        return true;
    }

    private void updateSelectedImageView(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {

                break;
            } case MotionEvent.ACTION_UP: {

                break;
            }
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            if(selectedImageView == null) {
                return false;
            }

            float deltaX = detector.getDeltaX();
            float deltaY = detector.getDeltaY();

            selectedImageView.setX(selectedImageView.getX() + deltaX);
            selectedImageView.setY(selectedImageView.getY() + deltaY);

            return true;
        }
    }
}
