package com.coreman2200.ringstrings;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

public class RSInput extends GestureDetector.SimpleOnGestureListener
  implements View.OnKeyListener, View.OnTouchListener
{
  private static final int FLING_DIST_TO_END = 2700;
  private static final int FLING_DIST_TO_LINE = 300;
  private static final int SWIPE_MAX_OFF_PATH = 250;
  private static final int SWIPE_MIN_DISTANCE = 120;
  private static final int SWIPE_THRESHOLD_VELOCITY = 200;
  private static final int VIB_RING_HIT = 100;
  private float accumForceX;
  private float accumForceY;
  private Context context;
  private float[] crntXY = new float[2];
  private float[] dXY = new float[2];
  private float[] oldXY = new float[2];
  private float[] velXY = new float[2];

  public RSInput(Context paramContext)
  {
    this.context = paramContext;
    this.accumForceY = 0.0F;
    this.accumForceX = 0.0F;
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    ((Vibrator)this.context.getSystemService("vibrator")).vibrate(100L);
    Log.e("RSInput", "onDown Influenced.");
    return true;
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    Log.e("RSInput", "onFling Influenced.");
    return true;
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0);
    for (boolean bool = onKeyDown(paramInt, paramKeyEvent); ; bool = false)
      return bool;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    StringBuilder localStringBuilder1 = new StringBuilder("dx: ");
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Float.valueOf(paramFloat1);
    StringBuilder localStringBuilder2 = localStringBuilder1.append(String.format("%.2f", arrayOfObject1)).append(" ~~~ dy:");
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Float.valueOf(paramFloat2);
    Log.v("RSInput", String.format("%.2f", arrayOfObject2));
    return true;
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    Log.e("RSInput", "onSingleTap Influenced.");
    return true;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    boolean i = false;
    this.crntXY[0] = paramMotionEvent.getX();
    this.crntXY[1] = paramMotionEvent.getY();
    if (paramMotionEvent.getAction() == 2)
    {
      this.dXY[0] = (this.crntXY[0] - this.oldXY[0]);
      this.dXY[1] = (this.crntXY[1] - this.oldXY[1]);
      i = true;
    }
    this.oldXY[0] = this.crntXY[0];
    this.oldXY[1] = this.crntXY[1];
    return i;
  }
}

/* Location:           C:\Users\higgie\Documents\htc\DEV\RingStrings\classes_dex2jar.jar
 * Qualified Name:     com.coreman2200.ringstrings.RSInput
 * JD-Core Version:    0.6.0
 */