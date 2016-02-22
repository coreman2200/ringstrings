package com.coreman2200.ringstrings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.concurrent.ArrayBlockingQueue;

//import java.util.GregorianCalendar;

import android.app.Activity;
//import android.opengl.GLSurfaceView.GLWrapper;
import android.location.Location;
import android.os.Bundle;
//import javax.microedition.khronos.opengles.GL;
//import android.util.Log;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import android.os.AsyncTask;


public class RingStringsActivity extends Activity
{
	private static final int INPUT_QUEUE_SIZE = 20;
	//private RSGFXBase mGLView;

 	 public void onCreate(Bundle paramBundle) {
		  super.onCreate(paramBundle);

		  setContentView(R.layout.main);
  	}
}