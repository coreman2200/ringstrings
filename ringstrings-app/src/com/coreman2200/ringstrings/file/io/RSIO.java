package com.coreman2200.ringstrings.file.io;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.coreman2200.ringstrings.entities.Person;
import com.coreman2200.ringstrings.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.xmlpull.v1.XmlPullParserException;

public class RSIO
{
  private static final String _EPHEDIR = "ephe";
  //private static final String _EPHEZIP = "sweph_18.zip";
  private static final String _PRODATDIR = "MyRez2200/SaveDat";
  private static final String _PROCHARTDIR = "MyRez2200/Charts";
  private static final String _EPHEASTDATA = "seas_18.se1";
  private static final String _EPHEMOONDATA = "semo_18.se1";
  private static final String _EPHEPLANDATA = "sepl_18.se1";
  public  static final String _LB			= "\r\n";
  private static Context c;
  private static Resources res;

  
  public static void writeChart(String name, StringBuffer tofile) {
	  String filename = getChartDir() + File.separator + name + ".txt";
	  try {
		  BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		  String outText = tofile.toString();
		  out.write(outText);
		  out.close();
		  tofile = null;
		  
	  }
	  catch (IOException e)
	  {
	      Log.e("RSIO", "Error writing to file: " + e.getMessage());
	  }
	  Log.d("RSIO", name + "'s chart saved to " + filename + "!");
  }
  
  public static String getEpheDir()
  {
	  String ephedir = c.getFilesDir().getAbsolutePath() + File.separator + _EPHEDIR;
	  setDir(ephedir);
	  return ephedir;
  }

  public static String getProfileDir()
  {
	  String profdir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _PRODATDIR;
	  setDir(profdir);
	  return profdir;
  }
  
  public static String getChartDir()
  {
	  String chartdir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _PROCHARTDIR;
	  setDir(chartdir);
	  return chartdir;
  }

  public static void initEpheData()
  {
    //Log.v("Ephemeris", "Begin Initializing Ephemeris Data..");
    if (!isEpheData())
    {
    	moveEpheToExt();
    	Log.v("Ephemeris", "Success! Ephemeris data Loaded to Phone: " + getEpheDir());
    }
  }

  public static void initRSIO(Context paramContext)
  {
    c = paramContext;
    res = c.getResources();
    //Log.e("RSIO~Init..", "RSIO Initiated...");
  }
  
  public static void loadGlobals()
  {
    //Log.v("Globals", "Beginning load variables via XMLPullGlobals..");
    XMLPullGlobals.initSetRes(res);
    try {
		XMLPullGlobals.getTags();
		//Log.v("loadGlobals", "Tags set.");
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		Log.e("loadGlobals", e.getMessage());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		Log.e("loadGlobals", e.getMessage());
	}
  }
  
  private static void moveEpheToExt()
  {
	  //Log.v("RSIO", "Moving Ephemeris data files to phone..");
	  File localFile1 = null;
    try
    {
      localFile1 = new File(getEpheDir() + File.separator + _EPHEASTDATA);
      inputstreamToFile(res.openRawResource(R.raw.seas_18), localFile1);
      
      localFile1 = new File(getEpheDir() + File.separator + _EPHEMOONDATA);
      inputstreamToFile(res.openRawResource(R.raw.semo_18), localFile1);
      
      localFile1 = new File(getEpheDir() + File.separator + _EPHEPLANDATA);
      inputstreamToFile(res.openRawResource(R.raw.sepl_18), localFile1);
    }
    catch (Exception localException)
    {
        Log.d("control", "Move error: " + localException);
        localFile1 = null;
    }
    
    //return localFile1;
    
  }

  private static void inputstreamToFile(InputStream paramInputStream, File paramFile)
  {
	    try
	    {
	      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
	      byte[] arrayOfByte = new byte[1024];
	  
	      while (true)
	      {
	        int i = paramInputStream.read(arrayOfByte);
	        if (i <= 0)
	        {
	          localFileOutputStream.close();
	          break;
	        }
	        localFileOutputStream.write(arrayOfByte, 0, i);
	      }
	    }
	    catch (Exception localException)
	    {
	      Log.e("inputstreamToFile", localException.getMessage());
	      return;
	    }
  }

  public static boolean isEpheData()
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = _EPHEASTDATA;
    arrayOfString[1] = _EPHEMOONDATA;
    arrayOfString[2] = _EPHEPLANDATA;
    String str = getEpheDir() + File.separator;
    File ff;
    for (int i = 0; i < arrayOfString.length; i++)
    {
    	ff = new File(str + arrayOfString[i]);
    	if (!ff.exists())
    		return false;
   
    	//Log.v("isEpheData", str + arrayOfString[i] + " was found..");
    }
    return true;
  }

  private static void setDir(String paramString)
  {
    File localFile = new File(paramString);
    if (!localFile.isDirectory())
      localFile.mkdirs();
  }

  public static boolean isProfile()
  {
    return (!new File(getProfileDir() + File.separator + "MyProfile.rez").exists());
  }

  public static void eraseProfile()
  {
  }
  
  public static String[] getProfiles() {
	  File folder = new File(getProfileDir());
	  ObjectInputStream is;
	  FileInputStream fis;
	  String[] files = folder.list();
	  if (files == null)
		  return files;
	  
	  for (int i = 0; i < files.length; i++) {
		  if (files[i].contains("SELF-") && i != 0) {
			  String str = files[0];
			  files[0] = files[i];
			  files[i] = str;	  
		  }
	  }
	  
	  return files;
	  
  }
  
  public static boolean loadProfiles()
  {
	  boolean foundself = false;
	  File folder = new File(getProfileDir());
	  ObjectInputStream is;
	  FileInputStream fis;
	  String[] files = folder.list();
	  if (files == null)
		  return false;
	  
	  for (int i = 0; i < files.length; i++) {
		  if (files[i].contains("SELF-") && i != 0) {
			  foundself = true;
			  String str = files[0];
			  files[0] = files[i];
			  files[i] = str;
			  
		  }
	  }
	  File nFile;
	  if (files.length == 0)
		  return false;
	  Person crntPerson;
	  Log.d("loadProfiles", "Loading " + files.length + " profiles...");
	  for (int i = 0; i < files.length; i++) {
		  if (files[i].contains(".rez")) {
				try {
					nFile = new File(getProfileDir() + File.separator + files[i]); 
					fis = new FileInputStream(nFile);
					//fis = c.openFileInput(files[i]);
					
					is = new ObjectInputStream(fis);
					crntPerson = (Person)is.readObject();
					if (i == 0)
						crntPerson.setMySelf(foundself);
					crntPerson.ReviveLoad();
					Log.d("loadProfiles", crntPerson.getName() + " is now successfully loaded.");
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
		 
	  }
	  
	  return true;
  }
  
  public static boolean loadProfile(String fname)
  {
	  File thefile = new File(getProfileDir() + File.separator + fname + ".rez");
	  if (!thefile.exists())
		  return false;
	  ObjectInputStream is;
	  FileInputStream fis;
	
	  Person crntPerson;
	  Log.d("loadProfiles", "Loading " + fname + "...");

		try {
			fis = new FileInputStream(thefile);
			is = new ObjectInputStream(fis);
			crntPerson = (Person)is.readObject();
			crntPerson.ReviveLoad();
			Log.d("loadProfiles", crntPerson.getName() + " is now successfully loaded.");
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
	  
	  return true;
  }
  

  public static void saveProfile(Person savedata)
  {
	  
	  String Filename = getProfileDir() + File.separator + ((savedata.getIsMyself()) ? "SELF-" : "") + savedata.getFirstName() + savedata.getLastName().substring(0, 2) + ".rez";
	  
	  try {
		  File nFile = new File(Filename);
		  nFile.createNewFile();
		  FileOutputStream fos = null;
		  fos = new FileOutputStream(nFile); // = c.openFileOutput(Filename, Context.MODE_PRIVATE);
		  ObjectOutputStream os = new ObjectOutputStream(fos);
		  os.writeObject(savedata);
		  os.close();
	  } catch (FileNotFoundException e) {
		  Log.e("saveProfile", e.getMessage());
		  return;
	  } catch (IOException e) {
		  Log.e("saveProfile", e.getMessage());
	  }  
	  Log.d("saveProfile", Filename + " saved.");
  }
  
}