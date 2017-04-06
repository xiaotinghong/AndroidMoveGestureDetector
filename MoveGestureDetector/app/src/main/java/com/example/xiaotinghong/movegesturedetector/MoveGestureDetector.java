package com.example.xiaotinghong.movegesturedetector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;


/**
 * Created by xiaotinghong on 2017-04-06.
 * All the rights and credits reserved to Xiaoting Hong
 */

public class MoveGestureDetector {
    private static final String TAG = "MoveGestureDetector";
    private final int INVALID_POINTER_ID = -1;

    public interface OnMoveGestureListener {

        boolean onMove(MoveGestureDetector detector);

        boolean onMoveBegin(MoveGestureDetector detector);

        void onMoveEnd(MoveGestureDetector detector);
    }

    public static class SimpleOnMoveGestureListener implements OnMoveGestureListener {

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {
            // Intentionally empty
        }
    }

    private final Context mContext;
    private final OnMoveGestureListener mListener;

    private int mActivePointerId;
    private float mCurrTouchX;
    private float mCurrTouchY;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mDeltaX;
    private float mDeltaY;
    private boolean mInProgress;

    public MoveGestureDetector(Context context, @NonNull OnMoveGestureListener listener) {
        mContext = context;
        mListener = listener;
        mInProgress = false;
        mActivePointerId = INVALID_POINTER_ID;
    }

    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mListener.onMoveBegin(this);
                mInProgress = true;
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointerIndex);
                final float y = MotionEventCompat.getY(event, pointerIndex);

                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                mListener.onMove(this);
                mInProgress = true;
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(event, mActivePointerId);

                final float x = MotionEventCompat.getX(event, pointerIndex);
                final float y = MotionEventCompat.getY(event, pointerIndex);

                // Calculate the distance moved
                mDeltaX = x - mLastTouchX;
                mDeltaY = y - mLastTouchY;

                mCurrTouchX += mDeltaX;
                mCurrTouchY += mDeltaY;

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }


            case MotionEvent.ACTION_UP: {
                mListener.onMoveEnd(this);
                mInProgress = false;
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mListener.onMoveEnd(this);
                mInProgress = false;
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                mListener.onMove(this);
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(event, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(event, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    public boolean isInProgress() {
        return mInProgress;
    }

    public float getCurrX() { return mCurrTouchX; }

    public float getCurrY() { return  mCurrTouchY; }

    public float getFocusX() {
        return mCurrTouchX;
    }

    public float getFocusY() {
        return mCurrTouchY;
    }

    public float getDeltaX() {
        return mDeltaX;
    }

    public  float getDeltaY() {
        return mDeltaY;
    }
}
