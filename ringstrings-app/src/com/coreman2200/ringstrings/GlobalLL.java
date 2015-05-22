package com.coreman2200.ringstrings;

import android.util.Log;
//import java.util.LinkedList;
//import java.util.ListIterator;

public class GlobalLL
{
  //public static LinkedList<Entity> EntityLL;
  public static Tags[] TagsLL = new Tags[Tags._ALLTAGS];
  public static Tags[] GblTagsUsed;

  
  public static void resetTags() {
	  for (int i = 0; i < TagsLL.length; i++) {
		  TagsLL[i].resetTag();
	  }
  }

  public static int getTagsUsed() {
	  int TagsUsed = 0;
	  for (int i = 0; i < Tags._ALLTAGS; i++) {
		  if (TagsLL[i].getCount() > 0)
			  TagsUsed++;
	  }
	  return TagsUsed;
  }

  public static Tags getTagByName(String paramString)
  {
	  for (int i = 0; i < Tags._ALLTAGS; i++) {
		  if (TagsLL[i].getName().contentEquals(paramString)) 
			  return TagsLL[i];
	  }
	  Log.e("GlobalLL", "Tag " + paramString + " not found.");
	  return null;
  }
  
  public static Tags getTagByID(int num) {
	  int index = num - Entity._TAGID;
	  
	  if (index < Tags._ALLTAGS) {
		  return TagsLL[index];
	  } else {
		  Log.e("getTagByID", "None found for ID " + index);
		  return null;
	  }
  }
  
}
