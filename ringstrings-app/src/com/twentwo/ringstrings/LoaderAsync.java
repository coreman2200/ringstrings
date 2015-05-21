/* package com.twentwo.ringstrings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.util.Log;
import android.widget.Toast;
import android.os.AsyncTask;

public class LoaderAsync extends AsyncTask<String, String, Boolean> {
	  protected boolean foundFiles = false;
	  protected int filecount = 0;
	  
	  @Override
		protected Boolean doInBackground(String... params) {
			
			Person crntPerson;
			File nFile;
			ObjectInputStream is;
			FileInputStream fis;
			String folder = RSIO.getProfileDir() + File.separator;
			
			
			for (String fname : params) {
				foundFiles = true;
				//publishProgress("Loading "+fname);
				if (fname.contains(".rez")) {
					try {
						nFile = new File(folder + fname); 
						fis = new FileInputStream(nFile);
						//fis = c.openFileInput(files[i]);
						
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
				publishProgress("done processing "+fname);
			}
			
			return foundFiles;
		}

		
		
		@Override
		protected void onProgressUpdate(String... values) {
			Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onPostExecute(Boolean files) {
			if (files)
				Toast.makeText(getApplicationContext(), filecount + " files successfully loaded.", Toast.LENGTH_LONG).show();
			else {
				Toast.makeText(getApplicationContext(), "No files loaded/found", Toast.LENGTH_SHORT).show();
				RSTests.loadAllProfiles();
			}
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(getApplicationContext(), "Checking for files..", Toast.LENGTH_SHORT).show();
		}
		
} */