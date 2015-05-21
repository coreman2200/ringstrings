package com.twentwo.ringstrings;

public class SaveDataNUM extends SaveData {
	private static final long serialVersionUID = Entity._ASTID;
	public int numtype;
	public int numval;
	public int basenum1;
	public int basenum2;
	public boolean iskdebt;

	public SaveDataNUM(String name, int type) {
		super(name, (int)serialVersionUID);
		Elems = new SaveData[16];
	}
	
	public Entity toEnt(Entity parent) {
		Numerology me = new Numerology(EntName, numtype, true);
		me.setColor(this.color);
		me.setDesc(this.EntDesc);
		Tags[] ttags = new Tags[this.tags.length];
		for (int i = 0; i < this.tags.length; i++) 
			ttags[i] = GlobalLL.TagsLL[tags[i]];
		
		me.setTags(ttags, tags.length);
		if (basenum1 != -1 && basenum2 != -1)
			me.setBaseNumbers(new Numerology(String.valueOf(basenum1), Numerology.NUM_NUMBER), new Numerology(String.valueOf(basenum2), Numerology.NUM_NUMBER));
		me.setKarmicDebt(iskdebt);
		if (numtype == Numerology.NUM_QUALITY) {
			if (Elems != null) {
				for (int i = 0; i < Elems.length; i++) {
					if (Elems[i] != null) {
						me.addChild(Elems[i].toEnt(me));
					} else {
						break;
					}
				}
			}
		}
		return me;
	}
}
