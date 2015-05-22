package com.coreman2200.ringstrings;

import java.io.*;

public class SaveData implements Serializable {
	private static final long serialVersionUID = Entity._ENTID;
	transient protected static Entity houses;
	transient protected static Entity signs;
	public String EntName;
	public String EntDesc;
	public int color;
	public int[] tags;
	public SaveData[] Elems;
	public boolean isNew;
	public int sdType;
	
	public SaveData(String name, int type) {
		EntName = name;
		sdType = type;
	}
	
	public Entity toEnt(Entity parent) { return null; }
}
