# AndroidMoveGestureDetector
An Android move gesture detector with the sample of panning imageview object

Since there is no MoveGestureDetector in current Android Kit, I decided to write one myself.
This MoveGestureDetector is created based on the official ScaleGestureDetector https://developer.android.com/reference/android/view/ScaleGestureDetector.html.

This project is a sample to use the MoveGestureDetector to pan multiple imageviews separately.

I also created a RotateGestureDetector here:https://github.com/xiaotinghong/AndroidRotateGestureDetector
# Methouds
```
public boolean isInProgress()

public float getCurrX()

public float getCurrY() 

public float getFocusX() 

public float getFocusY() 

public float getDeltaX() 

public  float getDeltaY() 
```
# How to use it
To use the MoveGestureDetector, simply add the file "MoveGestureDetector.java" into your own project.

After that, it's the coding time :)

First, declare the MoveGestureDetector object in your activity or view.
```
private MoveGestureDetector moveGestureDetector;
```
Then, extend the MoveGestureDetector.SimpleOnMoveGestureListener to let you do your own sepcial moves. Most of your own codes about what to react on the move gesture will go here.
```
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
```
Assign this MoveListener to your MoveGestureDetector object.
```
@Override
protected void onCreate(Bundle savedInstanceState) {
      ...

      moveGestureDetector = new MoveGestureDetector(this, new MoveListener());
}
```
Don't forget the last step, let the MoveGestureDetector object detect the touch event! There will be nothing happen on the move gesture if you miss this step.
```
 @Override
public boolean onTouchEvent(MotionEvent event) {
      // Let moveGestureDetector detects all events
      if (moveGestureDetector != null) {
          moveGestureDetector.onTouchEvent(event);
      }

      ...

      return true;
}
```
That's all. Enjoy!
