package com.coreman2200.ringstrings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.concurrent.ArrayBlockingQueue;

import rsgfx.RSGFXBase;
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

import com.coreman2200.ringstrings.depruielems.InputObject;
import com.coreman2200.ringstrings.entities.Person;
import com.coreman2200.ringstrings.file.io.RSIO;
import com.coreman2200.ringstrings.google.maps.RSGoogleLoc;

public class RingStringsActivity extends Activity
{
	private static final int INPUT_QUEUE_SIZE = 20;
	//private RSGFXBase mGLView;
	private ArrayBlockingQueue<InputObject> inputObjectPool;

  public void onCreate(Bundle paramBundle)
  {
	    super.onCreate(paramBundle);
	    
        setContentView(R.layout.main);
	    RSIO.initRSIO(this);
	    //RSIO.loadGlobals();
	    RSIO.initEpheData();
	    //Person.setToday();
	    Location myLoc = RSGoogleLoc.getCrntLocation(this);
	    
  }
  
  private void createInputObjectPool() {
	  inputObjectPool = new ArrayBlockingQueue<InputObject>(INPUT_QUEUE_SIZE);
	  for (int i = 0; i < INPUT_QUEUE_SIZE; i++) {
	  inputObjectPool.add(new InputObject(inputObjectPool));
	  }
  }

	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	  // we only care about down actions in this game.
	  try {
		  // history first
		  int hist = event.getHistorySize();
		  if (hist > 0) {
			  // add from oldest to newest
			  for (int i = 0; i < hist; i++) {
			  InputObject input = inputObjectPool.take();
			  input.useEventHistory(event, i);
			  //mGLView.getRenderer().feedInput(input);
			  }
		  }
		  // current last
		  InputObject input = inputObjectPool.take();
		  input.useEvent(event);
		  //mGLView.getRenderer().feedInput(input);
	  } catch (InterruptedException e) { }
	  // don't allow more than 60 motion events per second
	  try {
		  Thread.sleep(16);
	  } catch (InterruptedException e) { }
	  return true;
  }
  
  private class LoaderAsync extends AsyncTask<String, String, Boolean> {
	  protected Person crntPerson = null;
	  protected boolean foundFiles = false;
	  protected int filecount = 0;
	  
	  @Override
		protected Boolean doInBackground(String... params) {
				
			File nFile;
			ObjectInputStream is;
			FileInputStream fis;
			String folder = RSIO.getProfileDir() + File.separator;
			
			
			for (String fname : params) {
				foundFiles = true;

				if (fname.contains(".rez")) {
					try {
						//Log.v("LoaderAsync", fname + " started loading..");
						nFile = new File(folder + fname); 
						fis = new FileInputStream(nFile);
						
						is = new ObjectInputStream(fis);
						crntPerson = (Person)is.readObject();
						crntPerson.ReviveLoad();
						//
						is.close();
					} catch (FileNotFoundException e) {
						Log.e("loadProfiles", e.getMessage());
						return false;
					} catch (StreamCorruptedException e) {
						Log.e("loadProfiles", e.getMessage());
						return false;
					} catch (IOException e) {
						Log.e("loadProfiles", e.getMessage());
						return false;
					} catch (ClassNotFoundException e) {
						Log.e("loadProfiles", e.getMessage());
						return false;
					}
				  
				}	  
				filecount++;
				//if (crntPerson != null)
				publishProgress(fname + " has been successfully loaded..");
			}
			//Person.ALLPEOPLE.setOrientation();
			
			return foundFiles;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			//Log.d("LoaderAsync", values[0]);
		}
		
		@Override
		protected void onPostExecute(Boolean files) {
			if (files)
				Toast.makeText(getApplicationContext(), filecount + " files successfully loaded.", Toast.LENGTH_LONG).show();
			else {
				Toast.makeText(getApplicationContext(), "No files loaded/found", Toast.LENGTH_LONG).show();
				//RSTests.loadAllProfiles();
			}
			
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(getApplicationContext(), "Checking for files..", Toast.LENGTH_SHORT).show();
		}
		
  }

  protected void onPause()
  {
    super.onPause();
    //this.mGLView.onPause();
  }

  protected void onResume()
  {
    super.onResume();
    //this.mGLView.onResume();
  }
}