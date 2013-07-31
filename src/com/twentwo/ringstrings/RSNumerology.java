package com.twentwo.ringstrings;

public class RSNumerology
{

  private static final int[] ChaldeanArr;
  private static final int[] PythArr;
  private static final char[] charVOWELS;
  private static int _CRNTSYS = 0;
  public static final int _CHALDSYS = 8;
  public static final int _PYTHSYS = 9;
  public static final String _BALANCE = "Balance Number";
  public static final String _BIRTHDAY = "Birth Day Number";
  public static final String _EXPRESSION = "Expression Number";
  public static final String _MEXPRESSION = "Minor Expression Number";
  public static final String _MPERSONALITY = "Minor Personality Number";
  public static final String _MSOULURGE = "Minor Soul Urge Number";
  public static final String _HPASSIONS = "Hidden Passions Number";
  public static final String _KARMICLESSONS = "Karmic Lesson";
  public static final String _LIFEPATH = "Life Path Number";
  public static final String _MATURITY = "Maturity Number";
  public static final String _RTHOUGHT = "Rational Thought Number";
  public static final String _SOULURGE = "Soul Urge Number";
  public static final String _SUBSELF = "Subconscious Self Number";
  public static final String _PERSONALDAY = "Personal Day";
  public static final String _PERSONALITY = "Personality Number";
  public static final String _PERSONALMONTH = "Personal Month";
  public static final String _PERSONALYEAR = "Personal Year";
  public static final String _CHALLENGES = "Challenges";
  public static final String _CHALLENGE1 = "Challenge One";
  public static final String _CHALLENGE2 = "Challenge Two";
  public static final String _CHALLENGE3 = "Challenge Three";
  public static final String _CHALLENGE4 = "Challenge Four";
  public static final String _PERIODS = "Period Cycles";
  public static final String _PERIOD1 = "Cycle One";
  public static final String _PERIOD2 = "Cycle Two";
  public static final String _PERIOD3 = "Cycle Three";
  public static final String _PINNACLES = "Pinnacles";
  public static final String _PINNACLE1 = "1st Pinnacle";
  public static final String _PINNACLE2 = "2nd Pinnacle";
  public static final String _PINNACLE3 = "3rd Pinnacle";
  public static final String _PINNACLE4 = "4th Pinnacle";
  /*private static final int _MASTER11 = 11;
  private static final int _MASTER22 = 22;
  private static final int _MASTER33 = 33;
  private static final int _BASECHAR = 97;
  private static final int _EXCESSCHAR = 122;*/
  
  static
  {
    int[] arrayOfInt1 = new int[26];
    arrayOfInt1[0] = 1;
    arrayOfInt1[1] = 2;
    arrayOfInt1[2] = 3;
    arrayOfInt1[3] = 4;
    arrayOfInt1[4] = 5;
    arrayOfInt1[5] = 8;
    arrayOfInt1[6] = 3;
    arrayOfInt1[7] = 5;
    arrayOfInt1[8] = 1;
    arrayOfInt1[9] = 1;
    arrayOfInt1[10] = 2;
    arrayOfInt1[11] = 3;
    arrayOfInt1[12] = 4;
    arrayOfInt1[13] = 5;
    arrayOfInt1[14] = 7;
    arrayOfInt1[15] = 8;
    arrayOfInt1[16] = 1;
    arrayOfInt1[17] = 2;
    arrayOfInt1[18] = 3;
    arrayOfInt1[19] = 4;
    arrayOfInt1[20] = 6;
    arrayOfInt1[21] = 6;
    arrayOfInt1[22] = 7;
    arrayOfInt1[23] = 5;
    arrayOfInt1[24] = 1;
    arrayOfInt1[25] = 7;
    ChaldeanArr = arrayOfInt1;
    int[] arrayOfInt2 = new int[26];
    arrayOfInt2[0] = 1;
    arrayOfInt2[1] = 2;
    arrayOfInt2[2] = 3;
    arrayOfInt2[3] = 4;
    arrayOfInt2[4] = 5;
    arrayOfInt2[5] = 6;
    arrayOfInt2[6] = 7;
    arrayOfInt2[7] = 8;
    arrayOfInt2[8] = 9;
    arrayOfInt2[9] = 1;
    arrayOfInt2[10] = 2;
    arrayOfInt2[11] = 3;
    arrayOfInt2[12] = 4;
    arrayOfInt2[13] = 5;
    arrayOfInt2[14] = 6;
    arrayOfInt2[15] = 7;
    arrayOfInt2[16] = 8;
    arrayOfInt2[17] = 9;
    arrayOfInt2[18] = 1;
    arrayOfInt2[19] = 2;
    arrayOfInt2[20] = 3;
    arrayOfInt2[21] = 4;
    arrayOfInt2[22] = 5;
    arrayOfInt2[23] = 6;
    arrayOfInt2[24] = 7;
    arrayOfInt2[25] = 8;
    PythArr = arrayOfInt2;
    char[] arrayOfChar = new char[5];
    arrayOfChar[0] = 97;
    arrayOfChar[1] = 101;
    arrayOfChar[2] = 105;
    arrayOfChar[3] = 111;
    arrayOfChar[4] = 117;
    charVOWELS = arrayOfChar;
  }

  public static int Chaldeanize(String paramString)
  {
    char[] arrayOfChar = paramString.toLowerCase().toCharArray();
    int i = 0;
    int[] sysArr = (_CRNTSYS == _CHALDSYS) ? ChaldeanArr : PythArr;
    for (int j = 0; j < arrayOfChar.length; j++)
    {
      if (isChar(arrayOfChar[j])) {
    	  i += sysArr[(arrayOfChar[j] - 'a')];
      }
    }
    return numSingularize(i);
  }

  public static int getBase10(int paramInt1, int paramInt2)
  {
	  String str = String.valueOf(paramInt2);
	  int val = str.length() - 1;
	  char ch = str.charAt(val - paramInt1);
	  val = Integer.parseInt(String.valueOf(ch));

	  return val;
  }

  public static String getConsts(String paramString)
  {
    char[] arrayOfChar = paramString.toLowerCase().toCharArray();
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (int i = 0; i < arrayOfChar.length; i++) {
    	if (isChar(arrayOfChar[i]))
        {
          if (arrayOfChar[i] != 'y') {
            if (!isVowel(arrayOfChar[i]))
            	localStringBuilder.append(arrayOfChar[i]);
          } else {
        	  	if (i == 0)
        	  		localStringBuilder.append(arrayOfChar[i]);
        	  	else if (isVowel(arrayOfChar[(i - 1)]))
            		localStringBuilder.append(arrayOfChar[i]);
          }
        }
    }

    return localStringBuilder.toString();
  }

  public static String getVowels(String paramString)
  {
	  char[] arrayOfChar = paramString.toLowerCase().toCharArray();
	    StringBuilder localStringBuilder = new StringBuilder();
	    
	    for (int i = 0; i < arrayOfChar.length; i++) {
	    	if (isChar(arrayOfChar[i]))
	        {
	          if (arrayOfChar[i] != 'y') {
	            if (isVowel(arrayOfChar[i]))
	            	localStringBuilder.append(arrayOfChar[i]);
	          } else {
	        	  	if (i != 0) {
	        	  		if (!isVowel(arrayOfChar[(i - 1)]))
	        	  			localStringBuilder.append(arrayOfChar[i]);
	        	  	}
	          }
	        }
	    }

	    return localStringBuilder.toString();
  }

  public static boolean isChar(char paramChar)
  {
    return ((paramChar >= 'a') && (paramChar <= 'z'));
  }

  public static boolean isVowel(char paramChar)
  {
    for (int i = 0; i < charVOWELS.length; i++)
    {
    	if (paramChar == charVOWELS[i])
    		return true;
    }
    return false;
  }

  public static int numSingularize(int paramInt)
  {
    return numSingularize(paramInt, false);
  }
  
  public static int numSingularize(int paramInt, boolean paramBoolean)
  {
    if ((!paramBoolean) && (paramInt % 11 == 0) && (paramInt < 34))
    {
      return paramInt;
    }
    String str = String.valueOf(paramInt);
    int i = 0;
    for (int j = 0; j < str.length(); j++) {
    	i += Integer.parseInt(String.valueOf(str.charAt(j)));
    }
    if ((i > 9) && ((i % 11 != 0) || (i > 33) || paramBoolean))
        i = numSingularize(i, paramBoolean);
    return i;
  }

  public static void setNumberSystem(int paramInt)
  {
    if ((paramInt == 8) || (paramInt == 9))
      _CRNTSYS = paramInt;
  } 
  
  private static boolean numCheckKarmicDebt(int val) {
	  if (val != 10 && val != 13 && val != 14 && val != 16 && val != 19)
		  return false;
	  else
		  return true;

  }

  public static Numerology numGetBalance(String paramString1, String paramString2, String paramString3)
  {
	int i = (paramString1.length() > 0) ? Chaldeanize(paramString1.substring(0, 1)) : 0;
	int j = (paramString2.length() > 0) ? Chaldeanize(paramString2.substring(0, 1)) : 0;
	int k = ((paramString3.length() > 0) ? Chaldeanize(paramString3.substring(0, 1)) : 0) + (i + j);
    Numerology resval = new Numerology(RSNumerology._BALANCE, Numerology.NUM_QUALITY);
    Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
    resval.addChild(num);
    num.setKarmicDebt(numCheckKarmicDebt(k));
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetBirthDay(int paramInt)
  {
	  Numerology resval = new Numerology(RSNumerology._BIRTHDAY, Numerology.NUM_QUALITY);
	  Numerology num = new Numerology(String.valueOf(numSingularize(paramInt)), Numerology.NUM_NUMBER);
	  resval.addChild(num);
	  num.setKarmicDebt(numCheckKarmicDebt(paramInt));
	    if ((paramInt > 9) && (paramInt % 11 != 0))
	    {
	    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, paramInt)), Numerology.NUM_NUMBER);
	    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, paramInt)), Numerology.NUM_NUMBER);
	    	num.setBaseNumbers(num1, num2);
	    }
	    return resval;
  }

  public static Numerology numGetExpression(String paramString1, String paramString2, String paramString3)
  {
    int i = Chaldeanize(paramString1);
    int j = Chaldeanize(paramString2);
    int k = Chaldeanize(paramString3) + (i + j);
    Numerology resval = new Numerology(RSNumerology._EXPRESSION, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    
	if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetLifePath(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = numSingularize(paramInt1);
    int j = numSingularize(paramInt2);
    int k = numSingularize(paramInt3) + (i + j);
    Numerology resval = new Numerology(RSNumerology._LIFEPATH, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetMaturity(int paramInt1, int paramInt2)
  {
    int k = paramInt1 + paramInt2;
    Numerology resval = new Numerology(RSNumerology._MATURITY, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
	
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetPersonalDay(int paramInt1, int paramInt2, int paramInt3)
  {
	  int val = numSingularize(numGetPersonalMonth(paramInt1, paramInt2, paramInt3).getValue() + numSingularize(paramInt1));
	  Numerology resval = new Numerology(RSNumerology._PERSONALDAY, Numerology.NUM_QUALITY);
	  Numerology num = new Numerology(String.valueOf(val), Numerology.NUM_NUMBER);
	  resval.addChild(num);
	  
	  return resval;
  }

  public static Numerology numGetPersonalMonth(int paramInt1, int paramInt2, int paramInt3)
  {
	  int val = numSingularize(numGetPersonalYear(paramInt1, paramInt2, paramInt3).getValue() + numSingularize(paramInt2));
      Numerology resval = new Numerology(RSNumerology._PERSONALMONTH, Numerology.NUM_QUALITY);
	  Numerology num = new Numerology(String.valueOf(val), Numerology.NUM_NUMBER);
	  resval.addChild(num);
	  
	  return resval;
  }

  public static Numerology numGetPersonalYear(int paramInt1, int paramInt2, int paramInt3)
  {
	  	int i = numSingularize(paramInt1);
	    int j = numSingularize(paramInt2);
	    int k = numSingularize(paramInt3) + (i + j);
	    Numerology resval = new Numerology(RSNumerology._PERSONALYEAR, Numerology.NUM_QUALITY);
		Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
		resval.addChild(num);
		num.setKarmicDebt(numCheckKarmicDebt(k));
	    if ((k > 9) && (k % 11 != 0))
	    {
	    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
	    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
	    	num.setBaseNumbers(num1, num2);
	    }
	    return resval;
  }

  public static Numerology numGetPersonality(String paramString1, String paramString2, String paramString3)
  {
    int i = Chaldeanize(getConsts(paramString1));
    int j = Chaldeanize(getConsts(paramString2));
    int k = Chaldeanize(getConsts(paramString3)) + (i + j);
    Numerology resval = new Numerology(RSNumerology._PERSONALITY, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetRationalThought(String paramString, int paramInt)
  {
    int k = Chaldeanize(paramString) + numSingularize(paramInt);
    Numerology resval = new Numerology(RSNumerology._RTHOUGHT, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetSoulUrge(String paramString1, String paramString2, String paramString3)
  {
    int i = Chaldeanize(getVowels(paramString1));
    int j = Chaldeanize(getVowels(paramString2));
    int k = Chaldeanize(getVowels(paramString3)) + (i + j);
    Numerology resval = new Numerology(RSNumerology._SOULURGE, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetSubconsciouSelf(int paramInt)
  {
	Numerology resval = new Numerology(RSNumerology._SUBSELF, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(9 - paramInt), Numerology.NUM_NUMBER);
	resval.addChild(num);
    return resval;
  }

  public static Numerology numGetmExpression(String paramString1, String paramString2)
  {
    int k = Chaldeanize(paramString1) + Chaldeanize(paramString2);
    Numerology resval = new Numerology(RSNumerology._MEXPRESSION, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetmPersonality(String paramString1, String paramString2)
  {
    int k = Chaldeanize(getConsts(paramString1)) + Chaldeanize(getConsts(paramString2));
    Numerology resval = new Numerology(RSNumerology._MPERSONALITY, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }

  public static Numerology numGetmSoulUrge(String paramString1, String paramString2)
  {
    int k = Chaldeanize(getVowels(paramString1)) + Chaldeanize(getVowels(paramString2));
    Numerology resval = new Numerology(RSNumerology._MSOULURGE, Numerology.NUM_QUALITY);
	Numerology num = new Numerology(String.valueOf(numSingularize(k)), Numerology.NUM_NUMBER);
	num.setKarmicDebt(numCheckKarmicDebt(k));
	resval.addChild(num);
    if ((k > 9) && (k % 11 != 0))
    {
    	Numerology num1 = new Numerology(String.valueOf(getBase10(1, k)), Numerology.NUM_NUMBER);
    	Numerology num2 = new Numerology(String.valueOf(getBase10(0, k)), Numerology.NUM_NUMBER);
    	num.setBaseNumbers(num1, num2);
    }
    return resval;
  }
  
  public static Numerology numGetPinnacles(int paramInt1, int paramInt2, int paramInt3, Entity tent)
  {
    int i = numSingularize(paramInt1);
    int j = numSingularize(paramInt2);
    int k = numSingularize(paramInt3);
    int saved1;
    int saved2;
    Numerology resval = new Numerology(RSNumerology._PINNACLES, Numerology.NUM_QUALITY);
    String baseDesc = resval.getDesc();
    String fullDesc;
    
    Numerology crntQuality = new Numerology(RSNumerology._PINNACLE1, Numerology.NUM_QUALITY);
    saved1 = numSingularize(i + j, true);
    Numerology num	= new Numerology(String.valueOf(saved1), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._PINNACLE2, Numerology.NUM_QUALITY);
    saved2 = numSingularize(k + j, true);
    num	= new Numerology(String.valueOf(saved2), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._PINNACLE3, Numerology.NUM_QUALITY);
    num	= new Numerology(String.valueOf(numSingularize(saved1 + saved2, true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._PINNACLE4, Numerology.NUM_QUALITY);
    num	= new Numerology(String.valueOf(numSingularize(i + k, true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    return resval;
  }

  public static Numerology numGetCycles(int paramInt1, int paramInt2, int paramInt3, Entity tent)
  {
    int i = numSingularize(paramInt1);
    int j = numSingularize(paramInt2);
    int k = numSingularize(paramInt3);
    Numerology resval = new Numerology(RSNumerology._PERIODS, Numerology.NUM_QUALITY);
    String baseDesc = resval.getDesc();
    String fullDesc;
    Numerology crntQuality;
    Numerology num;
    
    crntQuality = new Numerology(RSNumerology._PERIOD1, Numerology.NUM_QUALITY);
    num	= new Numerology(String.valueOf(i), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._PERIOD2, Numerology.NUM_QUALITY);
    num	= new Numerology(String.valueOf(j), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._PERIOD3, Numerology.NUM_QUALITY);
    num	= new Numerology(String.valueOf(k), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    return resval;
  }
  
  public static Numerology numGetHiddenPassions(String paramString1, String paramString2, String paramString3)
  {
    char[] arrayOfChar = (paramString1 + paramString2 + paramString3).toLowerCase().toCharArray();
    int[] crntsysarr = (_CRNTSYS == _CHALDSYS) ? ChaldeanArr : PythArr;
    int[] arrayOfInt = new int[_CRNTSYS];

    
    for (int i = 0; i < arrayOfChar.length; i++) {
    	if (isChar(arrayOfChar[i]))
    		arrayOfInt[(crntsysarr[(arrayOfChar[i] - 'a')])-1]++;
    }
    
    int val = 0;
    for (int i = 0; i < arrayOfInt.length; i++) {
    	if (arrayOfInt[i] > arrayOfInt[val])
    		val = i;
    }
    val++;
    
    Numerology resval = new Numerology(RSNumerology._HPASSIONS, Numerology.NUM_QUALITY);
    Numerology num	= new Numerology(String.valueOf(val), Numerology.NUM_NUMBER);
    resval.addChild(num);
    
    return resval;
  }

  public static Numerology numGetKarmicLessonsList(String paramString1, String paramString2, String paramString3)
  {
	  	char[] arrayOfChar = (paramString1 + paramString2 + paramString3).toLowerCase().toCharArray();
	    int[] crntsysarr = (_CRNTSYS == _CHALDSYS) ? ChaldeanArr : PythArr;
	    int[] arrayOfInt = new int[_CRNTSYS];
	    Numerology resval = new Numerology(RSNumerology._KARMICLESSONS, Numerology.NUM_QUALITY);
	    Numerology num;
	    
	    for (int i = 0; i < arrayOfChar.length; i++) {
	    	if (isChar(arrayOfChar[i]))
	    		arrayOfInt[(crntsysarr[(arrayOfChar[i] - 'a')])-1]++;
	    }
	    
	    for (int i = 0; i < arrayOfInt.length; i++) {
	    	if (arrayOfInt[i] == 0) {
	    		num = new Numerology(String.valueOf(i+1), Numerology.NUM_NUMBER);
	    		resval.addChild(num);
	    	}
	    }
 
	    return resval;
  }

  public static Numerology numGetChallenges(int paramInt1, int paramInt2, int paramInt3, Entity tent)
  {
    int i = numSingularize(paramInt1); // Month
    int j = numSingularize(paramInt2); // Day
    int k = numSingularize(paramInt3); // Year
    int m;
    int n;
    int o;
    Numerology resval = new Numerology(RSNumerology._CHALLENGES, Numerology.NUM_QUALITY);
    String baseDesc = resval.getDesc();
    String fullDesc;
    Numerology crntQuality;
    Numerology num;
    
    crntQuality = new Numerology(RSNumerology._CHALLENGE1, Numerology.NUM_QUALITY);
    m = Math.abs(j - i);
    num = new Numerology(String.valueOf(numSingularize(m,true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._CHALLENGE2, Numerology.NUM_QUALITY);
    n = Math.abs(k - j);
    num = new Numerology(String.valueOf(numSingularize(n,true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._CHALLENGE3, Numerology.NUM_QUALITY);
    o = Math.abs(n - m);
    num = new Numerology(String.valueOf(numSingularize(o,true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    crntQuality = new Numerology(RSNumerology._CHALLENGE4, Numerology.NUM_QUALITY);
    m = Math.abs(k - i);
    num = new Numerology(String.valueOf(numSingularize(m,true)), Numerology.NUM_NUMBER);
    crntQuality.addChild(num); resval.addChild(crntQuality); tent.addChild(crntQuality);
    fullDesc = baseDesc + " " + crntQuality.getDesc(); crntQuality.setDesc(fullDesc);
    
    return resval;
  }

}