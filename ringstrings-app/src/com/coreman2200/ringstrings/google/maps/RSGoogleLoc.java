package com.coreman2200.ringstrings.google.maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.*;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.coreman2200.ringstrings.deprbizlogic.RSMath;


public class RSGoogleLoc implements LocationListener
{
	//private static Context
	private static LocationManager locMan;
	private static Location crntLoc;
	private static Criteria dfltCrit;
	private static String provider;
	
	private static void initCriteria() {
		dfltCrit = new Criteria();
		dfltCrit.setAccuracy(Criteria.ACCURACY_COARSE);
	}
	
	public static void setCrntLocation(Location cLoc) {
		crntLoc = cLoc;
		getAltitude();
		Log.d("setCrntLocation()", "Lon: " + crntLoc.getLongitude() + " x Lat: " + crntLoc.getLatitude() + " x Alt: " + crntLoc.getAltitude());
	}
	
	public static Location getCrntLocation() {
		return crntLoc;
	}
	
	public static Location getCrntLocation(Context c) {
		initCriteria();
		locMan = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		provider = locMan.getBestProvider(dfltCrit, false);
		crntLoc = locMan.getLastKnownLocation(provider);
		
		
		if (crntLoc == null) 
			Log.e("getCrntLocation", "Error getting current location.");
		else
			getAltitude();

		return crntLoc;
	}
	
	public static TimeZone getBirthTimeZone(Location loc) {
		int result = 0;
		TimeZone tmz = TimeZone.getDefault();
		
		Log.d("RSGoogleLoc", loc.getClass().getName() + "'s TimeZone Search...");
		
		HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    String url = "http://www.earthtools.org/"
	            + "timezone/" + String.valueOf(loc.getLatitude())
	            + "/" + String.valueOf(loc.getLongitude());
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse response = httpClient.execute(httpGet, localContext);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            int r = -1;
	            StringBuffer respStr = new StringBuffer();
	            while ((r = instream.read()) != -1)
	                respStr.append((char) r);
	            String tagOpen = "<offset>";
	            String tagClose = "</offset>";
	            if (respStr.indexOf(tagOpen) != -1) {
	                int start = respStr.indexOf(tagOpen) + tagOpen.length();
	                int end = respStr.indexOf(tagClose);
	                String value = respStr.substring(start, end);
	                result = Integer.parseInt(value);
	            }
	            instream.close();
	        }
	    } catch (ClientProtocolException e) { Log.e("getBirthTimeZone()", e.getMessage()); } 
	    catch (IOException e) { Log.e("getBirthTimeZone()", e.getMessage()); }
	    Log.d("RSGoogleLoc", "Set TimeZone is: " + result);
	    tmz.setRawOffset(result * RSMath._MHOUR);
	   
	    return tmz;
	}
	
	public static void getAltitude(Location cLoc) {
		crntLoc = cLoc;
		getAltitude();
		crntLoc = null;
	}
	
	private static void getAltitude() {
	    double result = Double.NaN;
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    String url = "http://gisdata.usgs.gov/"
	            + "xmlwebservices2/elevation_service.asmx/"
	            + "getElevation?X_Value=" + String.valueOf(crntLoc.getLongitude())
	            + "&Y_Value=" + String.valueOf(crntLoc.getLatitude())
	            + "&Elevation_Units=METERS&Source_Layer=-1&Elevation_Only=true";
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse response = httpClient.execute(httpGet, localContext);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            int r = -1;
	            StringBuffer respStr = new StringBuffer();
	            while ((r = instream.read()) != -1)
	                respStr.append((char) r);
	            String tagOpen = "<double>";
	            String tagClose = "</double>";
	            if (respStr.indexOf(tagOpen) != -1) {
	                int start = respStr.indexOf(tagOpen) + tagOpen.length();
	                int end = respStr.indexOf(tagClose);
	                String value = respStr.substring(start, end);
	                result = Double.parseDouble(value);
	            }
	            instream.close();
	        }
	    } catch (ClientProtocolException e) { Log.e("getAltitude()", e.getMessage());} 
	    catch (IOException e) { Log.e("getAltitude()", e.getMessage());}
	    crntLoc.setAltitude(result);
	}

	public void onLocationChanged(Location arg0) {
		crntLoc = arg0;
		getAltitude();
		
	}

	public void onProviderEnabled(String provider) {
		Log.d("RSGoogleLoc", "Enabled new provider " + provider);

	}

	public void onProviderDisabled(String provider) {
		Log.d("RSGoogleLoc", "Disabled new provider " + provider);
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
	
}