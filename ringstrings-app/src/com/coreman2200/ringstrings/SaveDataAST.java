package com.coreman2200.ringstrings;

public class SaveDataAST extends SaveData {
	private static final long serialVersionUID = Entity._ASTID;
	public boolean isRetro;
	public int asttype;
	public String House;
	public String Sign;
	public int Cel1;
	public int Cel2;
	public float degsign;
	public float deghouse;
	public float degtransit;
	public int housenum;
	public int signnum;
	
	public SaveDataAST(String name, int type) {
		super(name, (int)serialVersionUID);
		asttype = type;
	}

	/*
	public static final int AST_CELESTBODY = 0x01;
	public static final int AST_SIGN = 0x02;
	public static final int AST_HOUSE = 0x04;
	public static final int AST_TRANSIT = 0x08; 
	*/
	
	public Entity toEnt(Entity parent) {
		if (houses == null || signs == null) {
			houses = new Entity("House Group", 12);
			signs = new Entity("Sign Group", 12);
			for (int i = 0; i < 12; i++) {
				houses.addChild(new Astrology(RSAstrology.STR_Houses[i], Astrology.AST_HOUSE));
				signs.addChild(new Astrology(RSAstrology.STR_Zodiac[i], Astrology.AST_SIGN));
			}
		}
		
		Astrology me = new Astrology(this.EntName, asttype);
		me.setColor(this.color);
		me.setDesc(this.EntDesc);
		Tags[] ttags = new Tags[this.tags.length];
		for (int i = 0; i < this.tags.length; i++) 
			ttags[i] = GlobalLL.TagsLL[tags[i]];
		
		me.setTags(ttags, tags.length);
		if (asttype == Astrology.AST_CELESTBODY) {
			me.setCelestBody((Astrology)houses.getChild(housenum-1), (Astrology)signs.getChild(signnum));
			me.setDegreeInHouse(deghouse);
	        me.setDegreeInSign(degsign);
	        me.setRetro(isRetro);
		}
		if (asttype == Astrology.AST_TRANSIT) {
			Astrology cb1 = (Astrology)parent.getChild(Cel1, Entity._ASTID);
			Astrology cb2 = (Astrology)parent.getChild(Cel2, Entity._ASTID);
			me.setTransit(cb1, cb2, degtransit);
		}
		
		return me;
	}
}
