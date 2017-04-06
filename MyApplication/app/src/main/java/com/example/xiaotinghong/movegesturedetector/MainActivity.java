package com.example.xiaotinghong.movegesturedetector;

import android.graphics.PointF;
import android.graphics.Rect;
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

        // Add all the image view to the candidate list
        allImageViews = new LinkedList<>();
        allImageViews.add( (ImageView) findViewById(R.id.image_view_1) );
        allImageViews.add( (ImageView) findViewById(R.id.image_view_2) );
        allImageViews.add( (ImageView) findViewById(R.id.image_view_3) );

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
                selectedImageView = selectImageViewOnEvent(event);
                break;
            }
            case MotionEvent.ACTION_UP: {
                selectedImageView = null;
                break;
            }
        }
    }

    private ImageView selectImageViewOnEvent (MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        for(ImageView imageView: allImageViews) {
            // Calculate the hit rect of the current image view
            int[] screenLocation = new int[2];
            imageView.getLocationOnScreen(screenLocation);
            Rect hitRect = new Rect(screenLocation[0], screenLocation[1],
                    screenLocation[0] + imageView.getWidth(), screenLocation[1] + imageView.getHeight());

            // check if the touch point land within the hit rect
            if( hitRect.contains((int) touchX, (int) touchY) ) {
                return imageView;
            }
        }

        return null;
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            // The operations based on move touch event go here

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
