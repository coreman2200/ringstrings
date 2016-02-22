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
package com.coreman2200.ringstrings.rsprovider;

import android.provider.BaseColumns;

/**
 * A helper interface which defines constants for work with the DB.
 *
 */
/* package private */ interface RingStringsDbSchema {

	String DB_NAME = "ringstrings.db";

	String TBL_SYMBOLS = "symbol";
	String TBL_QUALITIES = "quality";
	String TBL_SYMBOLDEFS = "symboldef";

	String COL_ID = BaseColumns._ID;
	String COL_PROFILE = "profile_id";
	String COL_CHART = "chart_id";
	String COL_STRATA = "strata_id";
	String COL_TYPE = "type_id";
	String COL_SYMBOL = "_symbol_id";
    String COL_QUALITY = "quality_id";
    String COL_SYMBOLDEF = "_desc_id";
	String COL_PROTOMESSAGE = "_message";
	String COL_NAME = "_name";
	String COL_DESC = "_description";
    String COL_DEGREE = "_degree";
	String COL_VALUE = "_value";
	String COL_SIZE = "_size";

	
	// BE AWARE: Normally you would store the LOOKUP_KEY
	// of a contact from the device. But this would
	// have needless complicated the sample. Thus I
	// omitted it.

	String DDL_CREATE_TBL_SYMBOLS =
			"CREATE TABLE symbol (" +
					"_id    	   	INTEGER  AUTOINCREMENT, \n" +
					"_symbol_id    	INTEGER  PRIMARY KEY UNIQUE, \n" +
					"_profile_id   	INTEGER  NOT NULL  UNIQUE, \n" +
					"_chart_id    	INTEGER  NOT NULL  UNIQUE, \n" +
					"_strata_id   	INTEGER  NOT NULL  UNIQUE, \n" +
					"_type_id    	INTEGER  NOT NULL  UNIQUE, \n" +
					"_desc_id    	INTEGER  UNIQUE, \n" +
            		"_size        	INTEGER,\n" +
					"_value        	INTEGER,\n"+
					"_degree     	REAL \n" +
			")";

	// BE AWARE: old sqlite versions didn't support referential
	// integrity; for this reasons I do _not_ use foreign keys!
	// I use triggers instead (see the sample trigger below).
	// 
	// If you only target newer Android versions you could
	// of course use proper foreign keys instead.

	String DDL_CREATE_TBL_QUALITIES =
			"CREATE TABLE quality (" +
			"_id                INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
            "_quality_id        INTEGER  NOT NULL  UNIQUE, \n" +
			"_symbol_id      	INTEGER  NOT NULL  UNIQUE, \n" +
			"_count      		INTEGER  NOT NULL  UNIQUE \n" +
			")";

    String DDL_CREATE_TBL_SYMBOLDEFS =
            "CREATE TABLE symboldef (" +
					"_id                  INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
                    "_name                TEXT,\n" +
                    "_description         TEXT,\n" +
					"_message             TEXT,\n"+
                    "_symbol_id      	  INTEGER  NOT NULL  UNIQUE \n" +
                    ")";


		
	// The following trigger is here to show you how to
	// achieve referential integrity without foreign keys.
	String DDL_CREATE_TRIGGER_DEL_SYMBOLS =
			  "CREATE TRIGGER delete_symbol DELETE ON symbol \n"
			+ "begin\n"
			+ "  delete from quality where _id = old._id;\n"
            + "  delete from symboldef where _id = old._id;\n"
			+ "end\n";
	
	String DDL_DROP_TBL_SYMBOLS =
			"DROP TABLE IF EXISTS symbol";

	String DDL_DROP_TBL_QUALITY =
			"DROP TABLE IF EXISTS quality";

    String DDL_DROP_TBL_SYMBOLDEF =
            "DROP TABLE IF EXISTS symboldef";

	
	String DDL_DROP_TRIGGER_DEL_SYMBOLS =
			"DROP TRIGGER IF EXISTS delete_symbol";

	String DML_WHERE_ID_CLAUSE = "_id = ?";
	
	String DEFAULT_TBL_ITEMS_SORT_ORDER = "_symbol_id ASC";
	
	String LEFT_OUTER_JOIN_STATEMENT = 	TBL_SYMBOLS
				+ " LEFT OUTER JOIN " + TBL_SYMBOLDEFS + 	" ON(symbol._symbol_id = symboldef._symbol_id)"
				+ " LEFT OUTER JOIN " + TBL_QUALITIES + 	" ON(symbol._symbol_id = quality._symbol_id)";
}
