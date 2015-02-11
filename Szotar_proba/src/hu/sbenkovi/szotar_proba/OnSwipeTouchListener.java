package hu.sbenkovi.szotar_proba;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private final GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	public OnSwipeTouchListener(Context context) {
		gestureDetector = new GestureDetector(context, new MyGestureDetector());
		//		gestureDetector.setIsLongpressEnabled(true);
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
	}

	public void onSwipeRight() {}

	public void onSwipeLeft() {}

	public void onLongClick() {}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private final class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if(Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
					return false;
				}
				// right to left swipe
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					onSwipeLeft();
				} else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					onSwipeRight();
				}
			} catch(Exception e) {
				// nothing
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			onLongClick();
		}
	}
}