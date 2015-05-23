package com.coreman2200.ringstrings.depruielems;

import java.util.concurrent.ArrayBlockingQueue;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class InputObject {
	private static final int SWIPE_TIME_MAX = 800;
	public static final byte EVENT_TYPE_KEY = 1;
	public static final byte EVENT_TYPE_TOUCH = 2;
	public static final int ACTION_KEY_DOWN = 1;
	public static final int ACTION_KEY_UP = 2;
	public static final int ACTION_TOUCH_DOWN = 3;
	public static final int ACTION_TOUCH_MOVE = 4;
	public static final int ACTION_TOUCH_UP = 5;
	
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	public ArrayBlockingQueue<InputObject> pool;
	public byte eventType;
	public static long time;
	public long timediff;
	public int action;
	public int keyCode;
	private static float oldx;
	private static float oldy;
	public float distx;
	public float disty;
	public int flingx;
	public int flingy;
	public float x;
	public float y;
	public float nearclip;
	public float farclip;
	public int[] viewport;
	public boolean isSwipe;

	public InputObject(ArrayBlockingQueue<InputObject> pool) {
		this.pool = pool;
	}
	
	public void useEvent(MotionEvent event) {
		eventType = EVENT_TYPE_TOUCH;
		int a = event.getAction();
		x = event.getX();
		y = event.getY();
		
		switch (a) {
			case MotionEvent.ACTION_DOWN:
				action = ACTION_TOUCH_DOWN;
				oldx = x;
				oldy = y;
				time = event.getEventTime();
				//Log.d("InputObject", "ACTION_DOWN ~ x:"+x+ " y:"+y);
				break;
			case MotionEvent.ACTION_MOVE:
				action = ACTION_TOUCH_MOVE;
				distx = x - oldx;
				disty = y - oldy;
				//Log.d("InputObject", "ACTION_MOVE("+timediff+") ~ x:" + x + " y:" + y + "  ~~ distx:" + distx + " disty:" + disty);
				break;
			case MotionEvent.ACTION_UP:
				action = ACTION_TOUCH_UP;
				timediff = event.getEventTime() - time;
				Log.d("InputObject", "Time: " + time + " vs Timediff: " + timediff);
				/*isSwipe = (timediff > 0 && timediff < SWIPE_TIME_MAX);
				if (isSwipe && (Math.abs(distx) > SWIPE_MIN_DISTANCE || Math.abs(disty) > SWIPE_MIN_DISTANCE)) {
					flingx = (distx > disty) ? RSMath.sign((int)distx) : 0;
					flingy = (distx < disty) ? RSMath.sign((int)disty) : 0;
					Log.d("InputObject", "Swiped! " + timediff + " ~ x:"+flingx + " y:"+flingy);
				}
				//Log.d("InputObject", "ACTION_UP ~ x:"+x+ " y:"+y); */
				break;
			default:
				action = 0;
		}
		
		
	}

	public void useEvent(KeyEvent event) {
		eventType = EVENT_TYPE_KEY;
		int a = event.getAction();
		switch (a) {
			case KeyEvent.ACTION_DOWN:
				action = ACTION_KEY_DOWN;
				break;
			case KeyEvent.ACTION_UP:
				action = ACTION_KEY_UP;
				break;
			default:
				action = 0;
		}
		time = event.getEventTime();
		keyCode = event.getKeyCode();
	}

	public void useEventHistory(MotionEvent event, int historyItem) {
		eventType = EVENT_TYPE_TOUCH;
		action = ACTION_TOUCH_MOVE;
		time = event.getHistoricalEventTime(historyItem);
		x = event.getHistoricalX(historyItem);
		y = event.getHistoricalY(historyItem);
	}

	public void returnToPool() {
		pool.add(this);
	}
}
