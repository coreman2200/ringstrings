package com.coreman2200.ringstrings;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
//import android.util.Log;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLPullGlobals
{
	private static Entity crntEnt;
  	private static XmlResourceParser crntRes;
  	private static Resources resources;
  
  	public static void initSetRes(Resources rr) {
	  	resources = rr;
	  	//Log.d("XMLPullGlobals", "initSetRes complete.");
  	}
  
  	public static boolean initEnt(Entity ent) 
		throws XmlPullParserException, IOException
	{
  		crntEnt = null;
  		crntRes = resources.getXml(R.xml.gent);
  		int event = crntRes.getEventType();
  		Tags[] mtags = new Tags[16];
  		int tagcount = 0;
  		
  		while (event != XmlResourceParser.END_DOCUMENT) {
  			if (event == XmlResourceParser.START_TAG) {
  				if (crntRes.getName().contentEquals("GEnt")) {
  					if (crntRes.getIdAttribute().contentEquals(ent.getName()))
  						crntEnt = ent;
  				}
  				if (crntEnt != null) {
	  				if (crntRes.getName().contentEquals("Description"))
						crntEnt.setDesc(crntRes.nextText());
	  				
	  				if (crntRes.getName().contentEquals("Color"))
						crntEnt.setColor(crntRes.getAttributeIntValue(null, "value", 0));
	  				
	  				if (crntRes.getName().contentEquals("tag"))
	  				{
	  					Tags localTags = GlobalLL.getTagByName(crntRes.nextText());
	  					if (localTags != null) {
	  						mtags[tagcount++] = localTags;
	  					}
	  				}
  				}
  			}
  				
  			if (event == XmlResourceParser.END_TAG) {
  				if (crntRes.getName().contentEquals("GEnt")) {
  					if (crntEnt != null) {
  						crntEnt.setTags(mtags);
  						crntEnt = null;
  				  		crntRes.close();
  						return true;
  					}
  				}
  			}
  			crntRes.next();
			event = crntRes.getEventType();
  		}
  		crntEnt = null;
  		crntRes.close();
  		return false;
	}

  public static void getTags() throws XmlPullParserException, IOException
  {
    crntRes = resources.getXml(R.xml.gtags);
    int event = crntRes.getEventType();
    
    while (event != XmlPullParser.END_DOCUMENT) {
    	if (event == XmlPullParser.START_TAG) {
    		if (crntRes.getName().contentEquals("GTags")) {
    			new Tags(crntRes.getIdAttribute());
    		}
    	}
    	crntRes.next();
    	event = crntRes.getEventType();
    }
    crntRes.close();
  }
}