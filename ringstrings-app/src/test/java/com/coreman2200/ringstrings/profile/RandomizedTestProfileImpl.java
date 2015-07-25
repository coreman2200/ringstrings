package com.coreman2200.ringstrings.profile;


import com.coreman2200.ringstrings.numbersystem.NumberSystemType;

import org.robolectric.shadows.ShadowLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * RandomizedTestProfileImpl
 * Random profile generator
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class RandomizedTestProfileImpl  implements IProfile, IProfileTestLoc {
    private final String[] NAMES = {"Nicola", "Karen", "Fiona", "Susan", "Claire", "Sharon", "Angela", "Gillian",
            "Julie", "Michelle", "Jacqueline", "Amanda", "Tracy", "Louise", "Jennifer", "Alison", "Sarah", "Donna",
            "Caroline", "Elaine", "Lynn", "Margaret", "Elizabeth", "Lesley", "Deborah", "Pauline", "Lorraine", "Laura",
            "Lisa", "Tracey", "Carol", "Linda", "Lorna", "Catherine", "Wendy", "Lynne", "Yvonne", "Pamela", "Kirsty",
            "Jane", "Emma", "Joanne", "Heather", "Suzanne", "Anne", "Diane", "Helen", "Victoria", "Dawn", "Mary",
            "Samantha", "Marie", "Kerry", "Ann", "Hazel", "Christine", "Gail", "Andrea", "Clare", "Sandra", "Shona",
            "Kathleen", "Paula", "Shirley", "Denise", "Melanie", "Patricia", "Audrey", "Ruth", "Jill", "Lee", "Leigh",
            "Catriona", "Rachel", "Morag", "Kirsten", "Kirsteen", "Katrina", "Joanna", "Lynsey", "Cheryl", "Debbie",
            "Maureen", "Janet", "Aileen", "Arlene", "Zoe", "Lindsay", "Stephanie", "Judith", "Mandy", "Jillian", "Mhairi",
            "Barbara", "Carolyn", "Gayle", "Maria", "Valerie", "Christina", "Marion", "Nicola", "Karen", "Susan", "Claire",
            "Fiona", "Angela", "Sharon", "Gillian", "Julie", "Jennifer", "Michelle", "Louise", "Lisa", "Amanda", "Donna",
            "Tracy", "Alison", "Elaine", "Jacqueline", "Sarah", "Caroline", "Elizabeth", "Laura", "Lynn", "Deborah",
            "Lesley", "Margaret", "Joanne", "Pauline", "Lorraine", "Carol", "Kirsty", "Yvonne", "Lorna", "Emma", "Lynne",
            "Tracey", "Heather", "Catherine", "Pamela", "Helen", "Linda", "Jane", "Anne", "Kerry", "Suzanne", "Wendy",
            "Victoria", "Diane", "Mary", "Dawn", "Clare", "Gail", "Paula", "Ann", "Shona", "Hazel", "Christine", "Andrea",
            "Samantha", "Marie", "Lynsey", "Sandra", "Denise", "Lee", "Kelly", "Gayle", "Debbie", "Jill", "Kathleen",
            "Patricia", "Joanna", "Catriona", "Shirley", "Ruth", "Zoe", "Leigh", "Rachel", "Melanie", "Kirsteen",
            "Aileen", "Christina", "Janet", "Katrina", "Stephanie", "Audrey", "Kirsten", "Arlene", "Maureen", "Morag",
            "Marion", "Mhairi", "Allison", "Cheryl", "Maria", "Kim", "Anna", "Lindsay", "Rebecca", "Katherine",
            "Mandy", "Nicola", "Karen", "Claire", "Angela", "Fiona", "Susan", "Jennifer", "Julie", "Gillian",
            "Michelle", "Sharon", "Sarah", "Louise", "Donna", "Laura", "Amanda", "Alison", "Lisa", "Caroline",
            "Kirsty", "Jacqueline", "Elaine", "Lesley", "Lynn", "Deborah", "Elizabeth", "Joanne", "Emma", "Tracy",
            "Lorraine", "Lynne", "Margaret", "Heather", "Carol", "Lorna", "Pauline", "Kelly", "Helen", "Catherine",
            "Linda", "Victoria", "Suzanne", "Kerry", "Pamela", "Lee", "Wendy", "Jane", "Yvonne", "Tracey", "Anne",
            "Clare", "Mary", "Diane", "Christine", "Lynsey", "Samantha", "Shona", "Andrea", "Marie", "Gail",
            "Melanie", "Dawn", "Ann", "Paula", "Jill", "Ruth", "Leigh", "Hazel", "Debbie", "Joanna", "Denise",
            "Lindsay", "Gayle", "Patricia", "Catriona", "Kathleen", "Sandra", "Leanne", "Stephanie", "Rachel",
            "Katrina", "Shirley", "Kirsteen", "Janet", "Arlene", "Zoe", "Jillian", "Anna", "Judith", "Mhairi",
            "Natalie", "Audrey", "Carolyn", "Morag", "Aileen", "Cheryl", "Rebecca", "Allison", "Barbara", "Mandy",
            "Claire", "Nicola", "Karen", "Angela", "Gillian", "Fiona", "Jennifer", "Laura", "Susan", "Julie", "Michelle",
            "Lisa", "Sharon", "Louise", "Sarah", "Tracy", "Donna", "Kelly", "Kirsty", "Amanda", "Alison", "Joanne",
            "Caroline", "Emma", "Jacqueline", "Elaine", "Elizabeth", "Lynne", "Lesley", "Deborah", "Kerry", "Victoria",
            "Carol", "Catherine", "Lynn", "Pauline", "Margaret", "Lorna", "Lynsey", "Lorraine", "Linda", "Suzanne",
            "Tracey", "Heather", "Yvonne", "Jane", "Dawn", "Mary", "Helen", "Anne", "Wendy", "Lee", "Pamela", "Jill",
            "Lindsay", "Clare", "Christine", "Diane", "Leigh", "Samantha", "Shona", "Joanna", "Ruth", "Debbie", "Gail",
            "Marie", "Andrea", "Paula", "Kathleen", "Catriona", "Katrina", "Denise", "Melanie", "Ann", "Sandra", "Gayle",
            "Hazel", "Jillian", "Stephanie", "Rachel", "Kim", "Natalie", "Katherine", "Patricia", "Leanne", "Cheryl",
            "Mhairi", "Morag", "Arlene", "Zoe", "Kathryn", "Aileen", "Ashley", "Judith", "Anna", "Frances", "Janet",
            "Lucy", "Vicky", "Christina", "Kirsten", "Rebecca", "Nicola", "Claire", "Laura", "Karen", "Michelle",
            "Louise", "Jennifer", "Fiona", "Lisa", "Gillian", "Angela", "Julie", "Susan", "Sarah", "Kelly", "Donna",
            "Sharon", "Emma", "Caroline", "Alison", "Joanne", "Tracy", "Kirsty", "Lynne", "Amanda", "Elaine",
            "Jacqueline", "Lesley", "Kerry", "Elizabeth", "Lynn", "Margaret", "Deborah", "Catherine", "Heather,",
            "David", "John", "Paul", "Mark", "James", "Andrew", "Scott", "Steven", "Robert", "Stephen", "William",
            "Craig", "Michael", "Stuart", "Christopher", "Alan", "Colin", "Brian", "Kevin", "Gary", "Richard", "Derek",
            "Martin", "Thomas", "Neil", "Barry", "Ian", "Jason", "Iain", "Gordon", "Alexander", "Graeme", "Peter",
            "Darren", "Graham", "George", "Kenneth", "Allan", "Simon", "Douglas", "Keith", "Lee", "Anthony", "Grant",
            "Ross", "Jonathan", "Gavin", "Nicholas", "Joseph", "Stewart", "Daniel", "Edward", "Matthew", "Donald",
            "Fraser", "Garry", "Malcolm", "Charles", "Duncan", "Alistair", "Raymond", "Philip", "Ronald", "Ewan",
            "Ryan", "Francis", "Bruce", "Patrick", "Alastair", "Bryan", "Marc", "Jamie", "Hugh", "Euan", "Gerard",
            "Sean", "Wayne", "Adam", "Calum", "Alasdair", "Robin", "Greig", "Angus", "Russell", "Cameron", "Roderick",
            "Norman", "Murray", "Gareth", "Dean", "Eric", "Adrian", "Gregor", "Samuel", "Gerald", "Henry", "Justin",
            "Benjamin", "Shaun", "Callum", "Campbell", "Frank", "Roy", "Timothy", "David", "John", "Paul", "James",
            "Mark", "Scott", "Andrew", "Steven", "Robert", "Stephen", "Craig", "Christopher", "Alan", "Michael",
            "Stuart", "William", "Kevin", "Colin", "Brian", "Derek", "Neil", "Richard", "Gary", "Barry", "Martin",
            "Thomas", "Ian", "Gordon", "Kenneth", "Alexander", "Graeme", "Peter", "Iain", "Graham", "Jason",
            "George", "Allan", "Keith", "Darren", "Simon", "Douglas", "Ross", "Stewart", "Lee", "Grant", "Nicholas",
            "Joseph", "Gavin", "Anthony", "Jonathan", "Daniel", "Fraser", "Matthew", "Donald", "Malcolm", "Alistair",
            "Edward", "Raymond", "Charles", "Philip", "Bruce", "Garry", "Jamie", "Ryan", "Bryan", "Francis",
            "Alastair", "Duncan", "Patrick", "Ronald", "Alasdair", "Ewan", "Marc", "Wayne", "Hugh", "Robin", "Sean",
            "Calum", "Euan", "Adam", "Russell", "Cameron", "Gerard", "Murray", "Norman", "Angus", "Greig",
            "Justin", "Gregor", "Gerald", "Roderick", "Roy", "Benjamin", "Timothy", "Dean", "Samuel", "Greg",
            "Shaun", "Adrian", "Campbell", "David", "John", "Paul", "James", "Andrew", "Steven", "Scott", "Mark",
            "Robert", "Stephen", "Craig", "Christopher", "Stuart", "Alan", "William", "Michael", "Kevin", "Colin",
            "Brian", "Derek", "Neil", "Richard", "Martin", "Gary", "Ross", "Thomas", "Ian", "Iain", "Barry", "Gordon",
            "Graeme", "Graham", "Alexander", "Peter", "Kenneth", "Simon", "Allan", "Darren", "George", "Douglas",
            "Jason", "Lee", "Gavin", "Anthony", "Jonathan", "Stewart", "Jamie", "Keith", "Matthew", "Joseph", "Daniel",
            "Edward", "Nicholas", "Grant", "Ryan", "Philip", "Alistair", "Donald", "Charles", "Duncan", "Garry",
            "Malcolm", "Raymond", "Bryan", "Ewan", "Fraser", "Alastair", "Euan", "Patrick", "Bruce", "Ronald",
            "Greig", "Hugh", "Francis", "Gerard", "Russell", "Alasdair", "Adam", "Marc", "Sean", "Benjamin",
            "Gregor", "Calum", "Wayne", "Robin", "Roderick", "Murray", "Greg", "Angus", "Cameron", "Gerald",
            "Shaun", "Samuel", "Timothy", "Liam", "Campbell", "Gareth", "Niall", "Dean", "Justin", "David",
            "John", "Paul", "James", "Andrew", "Steven", "Scott", "Mark", "Robert", "Christopher", "Craig",
            "Stuart", "Kevin", "Alan", "Michael", "Stephen", "William", "Colin", "Brian", "Neil", "Richard",
            "Ross", "Thomas", "Gary", "Derek", "Iain", "Gordon", "Graeme", "Martin", "Barry", "Gavin", "Ian",
            "Kenneth", "Alexander", "Peter", "Graham", "Allan", "Darren", "Jamie", "Simon", "Lee", "George",
            "Keith", "Stewart", "Douglas", "Jonathan", "Matthew", "Daniel", "Grant", "Joseph", "Jason", "Anthony"};

    private final static String[] LAST_APPENDS = {"s", "enstein", "lion", "son", "short", "dumdiddly", "inions", "idious-Law"};
    private final NumberSystemType testNumberSystemType = NumberSystemType.PYTHAGOREAN;
    private final double mMaxOrb = 2.0;

    private static String[] testName = new String[3];
    private static GregorianCalendar mTestDate = new GregorianCalendar(Locale.US);

    private static ShadowLocation mBirthplace = new ShadowLocation();
    private static ShadowLocation mCurrentLoc = new ShadowLocation();


    public RandomizedTestProfileImpl() {
        setBirthplace();
        setCurrentLoc();
        setBirthplaceTimeOffset();
    }

    private void setBirthplace() {
        mBirthplace.setLatitude(-60.0 + (Math.random()*120));
        mBirthplace.setLongitude(-180.0 + (Math.random()*360));
        mBirthplace.setAltitude(Math.random()*100);
    }

    private void setCurrentLoc() {
        mCurrentLoc.setLatitude(-60.0 + (Math.random()*120));
        mCurrentLoc.setLongitude(-180.0 + (Math.random()*360));
        mCurrentLoc.setAltitude(Math.random()*100);
    }

    private void setBirthplaceTimeOffset() {
        mTestDate.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public String getFirstName() { return testName[0]; }
    public String getMiddleName(){ return testName[1]; }
    public String getLastName()  { return testName[2]; }
    public int getBirthDay() { return mTestDate.get(Calendar.DAY_OF_MONTH); }
    public int getBirthMonth() { return mTestDate.get(Calendar.MONTH)+1; }
    public int getBirthYear() { return mTestDate.get(Calendar.YEAR); }

    public double getMaxOrb() {
        return mMaxOrb;
    }

    @Override
    public GregorianCalendar getBirthDate() {
        return mTestDate;
    }

    public void genProfile() {
        testName[0] = NAMES[(int)(Math.random()*NAMES.length)];
        testName[1] = NAMES[(int)(Math.random()*NAMES.length)];
        testName[2] = NAMES[(int)(Math.random()*NAMES.length)] + LAST_APPENDS[(int)(Math.random()*LAST_APPENDS.length)];

        mTestDate.set(1800 + (int) (Math.random() * 600), (int) (Math.random() * 12),
                (int) (Math.random() * 31), (int) (Math.random() * 24), (int) (Math.random() * 60));


        setBirthplace();
        setCurrentLoc();

        //System.out.println("Name: " + getFirstName() + " " + getMiddleName() + " " + getLastName());
        //System.out.println("Birth: " + mTestDate.getTime());
        //System.out.println("=================================RandomlyGeneratedUser");

    }
    public NumberSystemType getNumberSystem() { return testNumberSystemType; }


    public ShadowLocation getBirthLocation() {
        return mBirthplace;
    }

    public ShadowLocation getCurrentLocation() {
        return mCurrentLoc;
    }
}
