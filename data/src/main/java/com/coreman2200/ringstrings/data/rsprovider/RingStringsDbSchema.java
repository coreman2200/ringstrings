/*
 * Copyright (C) 2013 Wolfram Rittmeyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coreman2200.ringstrings.data.rsprovider;

import android.provider.BaseColumns;

/**
 * A helper interface which defines constants for work with the DB.
 *
 */
/* package private */ interface RingStringsDbSchema {

	String DB_NAME = "ringstrings.db";

	String TBL_SYMBOLS = RingStringsContract.Tables.SYMBOLS.tableName();
	String TBL_QUALITIES = RingStringsContract.Tables.QUALITIES.tableName();
	String TBL_SYMBOLDEFS = RingStringsContract.Tables.SYMBOLDEFS.tableName();
    String TBL_DEFQUAL_JUNCT = RingStringsContract.Tables.DEF_QUALITIES.tableName();
    String TBL_SYMBOLQUAL_JUNCT = RingStringsContract.Tables.SYMBOL_QUALITIES.tableName();


	String COL_ID = BaseColumns._ID;
	String COL_PROFILE = RingStringsContract.CommonSymbolColumns._PROFILEID;
	String COL_CHART = RingStringsContract.CommonSymbolColumns._CHARTID;
	String COL_STRATA = RingStringsContract.CommonSymbolColumns._STRATAID;
	String COL_TYPE = RingStringsContract.CommonSymbolColumns._TYPEID;
	String COL_SYMBOL = RingStringsContract.CommonSymbolColumns._SYMBOLID;
    String COL_SYMBOLDEF = RingStringsContract.SymbolDescription._DESC_ID;
    String COL_SYMBOLQUALITIES = RingStringsContract.Symbols.SYMBOL_QUALITIES;
    String COL_SYMBOLCHILDREN = RingStringsContract.Symbols.SYMBOL_CHILDREN;

    String COL_DEGREE = RingStringsContract.Symbols._DEGREE;
    String COL_VALUE = RingStringsContract.Symbols._VALUE;
    String COL_SIZE = RingStringsContract.Symbols._SIZE;
    String COL_QUALITY = RingStringsContract.Quality._QUALITY_ID;
    String COL_NAME = RingStringsContract.Quality._NAME;
    String COL_COUNT = RingStringsContract.Quality._COUNT;
	String COL_MESSAGE = RingStringsContract.SymbolDescription._MESSAGE;
    String COL_DESC = RingStringsContract.SymbolDescription._DESCRIPTION;



	
	// BE AWARE: Normally you would store the LOOKUP_KEY
	// of a contact from the device. But this would
	// have needless complicated the sample. Thus I
	// omitted it.

	String DDL_CREATE_TBL_SYMBOLS =
			"CREATE TABLE symbol (" +
					"_id    	   	INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "_profile_id   	INTEGER  NOT NULL, \n" +
					"_symbol_id    	INTEGER  NOT NULL, \n" +
					"_chart_id    	INTEGER  NOT NULL, \n" +
					"_strata_id   	INTEGER  NOT NULL, \n" +
					"_type_id    	INTEGER  NOT NULL, \n" +
                    "_name      	TEXT     NOT NULL, \n" +
					"_desc_id    	INTEGER, \n" +
            		"_size        	INTEGER, \n" +
					"_value        	INTEGER, \n" +
					"_degree     	REAL \n" +
			")";

    String DDL_CREATE_TBL_QUALITIES =
            "CREATE TABLE quality (" +
                    "_id                INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "_profile_id   	    INTEGER  NOT NULL, \n" +
                    "_quality_id        INTEGER  NOT NULL, \n" +
                    "_name              TEXT, \n" +
                    "_count             INTEGER \n" +
                    ")";

    String DDL_CREATE_TBL_SYMBOLDEFS =
            "CREATE TABLE symboldef (" +
                    "_id                  INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "_profile_id   	      INTEGER  NOT NULL, \n" +
                    "_name                TEXT,\n" +
                    "_description         TEXT,\n" +
                    "_message             TEXT,\n"+
                    "_desc_id         	  INTEGER  NOT NULL \n" +
                    ")";

    String DDL_CREATE_TBL_DEFQUAL_JUNCT =
			"CREATE TABLE symboldef_quality (" +
			        "_id                  INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "_profile_id   	      INTEGER  NOT NULL, \n" +
                    "_quality_id          INTEGER  NOT NULL, \n" +
                    "_desc_id         	  INTEGER  NOT NULL\n" +
			        ")";

    String DDL_CREATE_TBL_SYMBOLQUALITY_JUNCT =
            "CREATE TABLE symbol_quality (" +
                    "_id                  INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "_profile_id   	      INTEGER  NOT NULL, \n" +
                    "_quality_id       	  INTEGER  NOT NULL, \n" +
                    "_symbol_id      	  INTEGER  NOT NULL, \n" +
					"_count      		  INTEGER  NOT NULL\n" +
                    ")";
		
	// The following trigger is here to show you how to
	// achieve referential integrity without foreign keys.
	String DDL_CREATE_TRIGGER_DEL_SYMBOLS =
			  "CREATE TRIGGER delete_symbol DELETE ON symbol \n"
			+ "begin\n"
			+ "  delete from symboldef_quality where _symbol_id = old._symbol_id;\n"
            + "  delete from symbol_quality where _symbol_id = old._symbol_id;\n"
			+ "end\n";

    String DDL_DROP_TBL_SYMBOLS =
            "DROP TABLE IF EXISTS symbol";

    String DDL_DROP_TBL_QUALITY =
            "DROP TABLE IF EXISTS quality";

    String DDL_DROP_TBL_SYMBOLDEF =
            "DROP TABLE IF EXISTS symboldef";

	String DDL_DROP_TBL_SYMBOLQUALITY_JUNCT =
			"DROP TABLE IF EXISTS symbol_quality";

    String DDL_DROP_TBL_DEFQUAL_JUNCT =
            "DROP TABLE IF EXISTS symboldef_quality";

	
	String DDL_DROP_TRIGGER_DEL_SYMBOLS =
			"DROP TRIGGER IF EXISTS delete_symbol";

	String DML_WHERE_SYMBOLID_CLAUSE = "_symbol_id = ?";

    String DML_WHERE_ID_CLAUSE = "_id = ?";
	
	String DEFAULT_TBL_SYMBOL_SORT_ORDER = "_symbol_id ASC";


    String JOIN_SYMBOLQUALITIES_STATEMENT = "" +
            "                        LEFT JOIN (\n" +
            "                           SELECT  J._symbol_id, group_concat(J._quality_id, ', ') AS SymbolQualities\n" +
            "                           FROM    symbol_quality J\n" +
            "                           JOIN    quality P ON J._quality_id = P._quality_id\n" +
            "                           GROUP   BY J._symbol_id \n" +
            "                           ORDER   BY count(J._quality_id) DESC \n" +
            "                        ) AS SQ ON SQ._symbol_id = symbol._symbol_id";

	String LEFT_OUTER_JOIN_STATEMENT = 	TBL_SYMBOLS + " \n"
                + JOIN_SYMBOLQUALITIES_STATEMENT;
}
