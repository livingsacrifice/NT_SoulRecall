/*
* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ntmemtest;

import android.database.sqlite.*;
import android.database.Cursor;
import android.content.Context;
import java.io.*;
import java.sql.SQLException;
import android.content.res.*;
import android.os.Environment;
import android.util.Log;
import java.util.*;

/**
 *
 * @author srichard
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/org.me.ntmemtest/databases/";

    private static String DB_NAME = "mem_verses";

    private SQLiteDatabase myDataBase;
 
    private final Context myContext;

    public String[][] KJVverses;
    public String[][] NASBverses;
    public String[][] NKJVverses;
    public String[][] NIVverses;
    public String[][] HCSBverses;
    public String[][] RVRverses;
    public String[][] AMPverses;
    public String[][] ESVverses;
    public int KJVcount;
    public int NASBcount;
    public int NKJVcount;
    public int NIVcount;
    public int HCSBcount;
    public int RVRcount;
    public int AMPcount;
    public int ESVcount;
    public int KJVvcount;
    public int NASBvcount;
    public int NKJVvcount;
    public int NIVvcount;
    public int HCSBvcount;
    public int RVRvcount;
    public int AMPvcount;
    public int ESVvcount;
    public String queryBook;
    public int queryChapter;
    public int queryVerse;
    public boolean saveHint;
    public String saveAuto;
    public String saveVersion;
    public String saveBook;
    public int saveChapter;
    public int saveVerse;
    public String saveSearch;
    public String saveVersion2;
    public String saveParams;
    public boolean saveSearchShow;


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

  /**
     * Creates and/or opens database and adds verses table.
     * */
    public void createDataBase() throws IOException{

    	boolean dbExist = checkDataBase();

    	if(dbExist){
    		//open database
                String myPath = DB_PATH + DB_NAME;
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	}else{

    		//create empty database
                //add verses table
        	this.getReadableDatabase();
                String myPath = DB_PATH + DB_NAME;
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                myDataBase.execSQL("CREATE TABLE verses (_id INTEGER PRIMARY KEY,"
                   + "book TEXT, chapter INTEGER, verse INTEGER, version TEXT, words TEXT, plus INTEGER, minus INTEGER);");

    	}

    }

    /**
     * Check if the database already exist 
     */
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
                myDataBase.close();
        super.close();
    }

    public void resetDbConnection() {
      Log.i("Database", "resetting database connection (close and re-open).");
      cleanup();
      myDataBase =
               SQLiteDatabase.openDatabase("/data/data/org.me.ntmemtest/databases/mem_verses", null,
                        SQLiteDatabase.OPEN_READWRITE);
   }

   public void cleanup() {
      if ((myDataBase != null) && myDataBase.isOpen()) {
         myDataBase.close();
      }
   }

   // super delete - clears all tables
   public void deleteAllDataYesIAmSure() {
      Log.i("Database", "deleting all data from database - deleteAllYesIAmSure invoked");
      String myPath = DB_PATH + DB_NAME;
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         myDataBase.execSQL("delete from verses");
         myDataBase.execSQL("delete from bible");
   }
   
// partial delete - clears all bibles
   public void deleteAllBiblesYesIAmSure() {
      Log.i("Database", "deleting all data from database - deleteAllYesIAmSure invoked");
      String myPath = DB_PATH + DB_NAME;
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         myDataBase.execSQL("delete from bible");
   }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    
    public void checkSave(){
        String ssql = "select count(*) as cnt from sqlite_master where name = 'curr_state'";
        Cursor c = myDataBase.rawQuery(ssql,null);
        int cntColumn = c.getColumnIndex("cnt");
        int i = 0;
        if (c != null){
            if (c.moveToFirst()){
                i = c.getInt(cntColumn);                                 
            }
            c.close();
        }
        if (i == 0){
            //create table to save current verse, it does not exist
            myDataBase.execSQL("CREATE TABLE curr_state (_id INTEGER PRIMARY KEY,"
                   + "book TEXT, chapter INTEGER, verse INTEGER, version TEXT, auto TEXT, hint INTEGER);");
            myDataBase.execSQL("insert into curr_state (book, chapter, verse, version, auto, hint) "
                + "values ('James', 1, 1, 'KJV', 'Increment', 0)");
        }
    }

    public void checkSearchSave(){
        //need table to store search parameters
        String ssql = "select count(*) as cnt from sqlite_master where name = 'curr_search'";
        Cursor c = myDataBase.rawQuery(ssql,null);
        int cntColumn = c.getColumnIndex("cnt");
        int i = 0;
        if (c != null){
            if (c.moveToFirst()){
                i = c.getInt(cntColumn);
            }
            c.close();
        }
        if (i == 0){
            //create table to save current search options, it does not exist
            myDataBase.execSQL("CREATE TABLE curr_search (_id INTEGER PRIMARY KEY,"
                    + "searchtext TEXT, version TEXT, searchparam TEXT, showtext INTEGER);");
            myDataBase.execSQL("insert into curr_search (searchtext, version, searchparam, showtext) "
                    + "values ('', 'any', 'all', 1)");
        }
        //need table to store search results
        //create table if does not exist
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS search_results (_id INTEGER PRIMARY KEY,"
                + "book TEXT, chapter INTEGER, verse INTEGER, version TEXT, words TEXT);");
    }


    public void checkBible(){
    	//create table if does not exist
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS bible (_id INTEGER PRIMARY KEY,"
               + "book TEXT, chapter INTEGER, verse INTEGER, version TEXT, words TEXT);");
        //create index if does not exist
        myDataBase.execSQL("CREATE INDEX IF NOT EXISTS idx_bible ON bible (book, chapter, verse, version)");
    }

    public boolean checkStrong(){
        //create table if does not exist
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS strong (_id INTEGER PRIMARY KEY,"
                + "str_number TEXT, str_definition TEXT);");
        int recCount = 0;
        String ssql = "select count(*) as numRecords from strong";
        Cursor c = myDataBase.rawQuery(ssql, null);
        if (c != null){
            int WordColumn = c.getColumnIndex("numRecords");
            if (c.moveToFirst()){
                recCount = c.getInt(WordColumn);
            }
            c.close();
        }
        if (recCount > 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public int checkBibleVersion(String versionx){
    	int verseCountX = 0;
    	String ssql = "select count(*) as numVerses from bible where version = '" + versionx + "'";
    	Cursor c = myDataBase.rawQuery(ssql, null);
    	if (c != null){
    		int VerseColumn = c.getColumnIndex("numVerses");
    		if (c.moveToFirst()){
    			verseCountX = c.getInt(VerseColumn);
    		}
    		c.close();
    	}
    	return verseCountX;
    }
    
    public void resetBibleVersion(String versionx){
    	myDataBase.execSQL("delete from bible where version = '" + versionx + "'");
    }
    
    public void getSave(){
        String ssql = "select * from curr_state";
        Cursor c = myDataBase.rawQuery(ssql,null);
        int bookColumn = c.getColumnIndex("book");
        int chapterColumn = c.getColumnIndex("chapter");
        int verseColumn = c.getColumnIndex("verse");
        int autoColumn = c.getColumnIndex("auto");
        int hintColumn = c.getColumnIndex("hint");
        int versionColumn = c.getColumnIndex("version");
        if (c != null){
            if (c.moveToFirst()){
                 int saveHint2 = c.getInt(hintColumn);
                 if (saveHint2 == 0){
                     saveHint = false;
                 }else{
                     saveHint = true;
                 }
                 saveAuto = c.getString(autoColumn);
                 saveVersion = c.getString(versionColumn);
                 saveBook = c.getString(bookColumn);
                 saveChapter = c.getInt(chapterColumn);
                 saveVerse = c.getInt(verseColumn);
            }
            c.close();
        }
    }
    
    public void setSave(String book, int chapter, int verse, String version, 
            String auto, boolean hint){
        int hint2 = 0;
        if (hint){
            hint2 = 1;
        }
        myDataBase.execSQL("delete from curr_state where book is not null");
        myDataBase.execSQL("insert into curr_state (book, chapter, verse, version, auto, hint) "
                + "values ('" + book + "', " + chapter + ", " + verse + ", '" + version + "', '" + auto + "', " + hint2 + ")");

    }

    public void getSearchSave(){
        String ssql = "select * from curr_search where version is not null";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int searchColumn = c.getColumnIndex("searchtext");
        int versionColumn = c.getColumnIndex("version");
        int paramColumn = c.getColumnIndex("searchparam");
        int showColumn = c.getColumnIndex("showtext");
        if (c != null){
            if (c.moveToFirst()){
                int saveHint2 = c.getInt(showColumn);
                if (saveHint2 == 0){
                    saveSearchShow = false;
                }else{
                    saveSearchShow = true;
                }
                saveSearch = c.getString(searchColumn);
                saveVersion2 = c.getString(versionColumn);
                saveParams = c.getString(paramColumn);
            }
            c.close();
        }
    }

    public void setSearchSave(String arg1, String arg2, String arg3,  boolean hint){
        int hint2 = 0;
        if (hint){
            hint2 = 1;
        }
        myDataBase.execSQL("delete from curr_search where version is not null");
        myDataBase.execSQL("insert into curr_search (searchtext, version, searchparam, showtext) "
                + "values ('" + arg1 + "', '" + arg2 + "', '" + arg3 + "', " + hint2 + ")");

    }

    public boolean checkVerse(String book, int chapter, int verse, String version, boolean storage){
        boolean foundVerse = false;
        String xtable = "verses";
        if (storage)
        	xtable = "bible";
        String ssql = "select * from " + xtable + " where book = '" + book + "' and chapter = "
                + chapter + " and verse = " + verse + " and version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        if (c != null){
            if (c.moveToFirst()){
                foundVerse = true;
                c.close();
            }
        }
        return foundVerse;
    }

    public String[][] SearchVerses(String queryText, String queryParam, String version) {
        String[][] foundVerses = new String[2][];
        String ssql = "";
        if (queryParam.equals("exact")) {
            ssql = "select book, chapter, verse, version, words from bible where lower(words) like '%" + queryText.toLowerCase() + "%'";
        }
        else
        {
            queryText = queryText.replaceAll("[^A-Za-z0-9\\s]", "");
            String[] wordList = new String[10];
            int currWordNum = 0;
            int refWordNunm = 0;
            for (int i = 0; i < (queryText.length()-1); i++)
            {
                if ((queryText.substring(i+1,i+2).trim().length()==0) && (queryText.substring(i,i+1).trim().length()==1))
                {
                    //found space for new word
                    wordList[currWordNum] = queryText.substring(refWordNunm,i+1).trim();
                    currWordNum++;
                    refWordNunm = i+2;
                }
                if (currWordNum == 9)
                {
                    break;
                }

            }
            wordList[currWordNum] = queryText.substring(refWordNunm,queryText.length());
            currWordNum++;
            if (currWordNum < 2)
            {
                ssql = "select book, chapter, verse, version, words from bible where lower(words) like '%" + queryText.toLowerCase() + "%'";
            }
            else {
                if (queryParam.equals("any")) {
                    ssql = "select book, chapter, verse, version, words from bible where (";
                    for (int i = 0; i < currWordNum; i++) {
                        ssql = ssql + "lower(words) like '%" + wordList[i] + "%' or ";
                    }
                    ssql = ssql.substring(0,ssql.length()-4) + ")";
                } else //all
                {
                    ssql = "select book, chapter, verse, version, words from bible where (";
                    for (int i = 0; i < currWordNum; i++) {
                        ssql = ssql + "lower(words) like '%" + wordList[i] + "%' and ";
                    }
                    ssql = ssql.substring(0,ssql.length()-5) + ")";
                }
            }
        }
        if (!version.equals("any"))
        {
            ssql = ssql + " and version = '" + version + "'";
        }
        Cursor c = myDataBase.rawQuery(ssql,null);
        int bookColumn = c.getColumnIndex("book");
        int chapterColumn = c.getColumnIndex("chapter");
        int verseColumn = c.getColumnIndex("verse");
        int versionColumn = c.getColumnIndex("version");
        int wordsColumn = c.getColumnIndex("words");
        if (c!= null){
            if (c.moveToFirst()){
                if (c.getCount()>50){
                    foundVerses = new String[2][50];
                }
                else{
                    foundVerses = new String[2][c.getCount()];
                }
                if(c.getCount() == 0){
                    foundVerses = new String[2][1];
                    foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
                    foundVerses[1][0] = "";
                }
                else {
                    int i = 0;
                    do {
                        foundVerses[0][i] = c.getString(bookColumn) + " " + Integer.toString(c.getInt(chapterColumn)) + ":"
                                + Integer.toString(c.getInt(verseColumn)) + " - " + c.getString(versionColumn);
                        foundVerses[1][i] = c.getString(wordsColumn);
                        i++;
                    } while (c.moveToNext() && i < 50);
                }
            }
            else{
                foundVerses = new String[2][1];
                foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
                foundVerses[1][0] = "";
            }
        }
        else
        {
            foundVerses = new String[2][1];
            foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
            foundVerses[1][0] = "";
        }
        return foundVerses;
    }

    public void setSearchResults(String queryText, String queryParam, String version) {
        myDataBase.execSQL("delete from search_results where book is not null");
        String ssql = "";
        if (queryParam.equals("exact")) {
            ssql = "select book, chapter, verse, version, words from bible where lower(words) like '%" + queryText.toLowerCase() + "%'";
        }
        else
        {
            queryText = queryText.replaceAll("[^A-Za-z0-9{}\\s]", "");
            String[] wordList = new String[10];
            int currWordNum = 0;
            int refWordNunm = 0;
            for (int i = 0; i < (queryText.length()-1); i++)
            {
                if ((queryText.substring(i+1,i+2).trim().length()==0) && (queryText.substring(i,i+1).trim().length()==1))
                {
                    //found space for new word
                    wordList[currWordNum] = queryText.substring(refWordNunm,i+1).trim();
                    currWordNum++;
                    refWordNunm = i+2;
                }
                if (currWordNum == 9)
                {
                    break;
                }

            }
            wordList[currWordNum] = queryText.substring(refWordNunm,queryText.length());
            currWordNum++;
            if (currWordNum < 2)
            {
                if (version.equals("Strong")){
                    ssql = "select str_number book, 0 chapter, 0 verse, '' version, str_definition words from strong where lower(str_definition) like '%" + queryText.toLowerCase() + "%'";
                }
                else {
                    ssql = "select book, chapter, verse, version, words from bible where lower(words) like '%" + queryText.toLowerCase() + "%'";
                }
            }
            else {
                if (queryParam.equals("any")) {
                    if (version.equals("Strong")){
                        ssql = "select str_number book, 0 chapter, 0 verse, '' version, str_definition words from strong where (";
                        for (int i = 0; i < currWordNum; i++) {
                            ssql = ssql + "lower(str_definition) like '%" + wordList[i] + "%' or ";
                        }
                        ssql = ssql.substring(0, ssql.length() - 4) + ")";
                    } else {
                        ssql = "select book, chapter, verse, version, words from bible where (";
                        for (int i = 0; i < currWordNum; i++) {
                            ssql = ssql + "lower(words) like '%" + wordList[i] + "%' or ";
                        }
                        ssql = ssql.substring(0, ssql.length() - 4) + ")";
                    }
                } else //all
                {
                    if (version.equals("Strong")){
                        ssql = "select str_number book, 0 chapter, 0 verse, '' version, str_definition words from strong where (";
                        for (int i = 0; i < currWordNum; i++) {
                            ssql = ssql + "lower(str_definition) like '%" + wordList[i] + "%' and ";
                        }
                        ssql = ssql.substring(0, ssql.length() - 5) + ")";
                    } else {
                        ssql = "select book, chapter, verse, version, words from bible where (";
                        for (int i = 0; i < currWordNum; i++) {
                            ssql = ssql + "lower(words) like '%" + wordList[i] + "%' and ";
                        }
                        ssql = ssql.substring(0, ssql.length() - 5) + ")";
                    }
                }
            }
        }
        if (!version.equals("any") && !version.equals("Strong"))
        {
            ssql = ssql + " and version = '" + version + "'";
        }
        ssql = "insert into search_results (book, chapter, verse, version, words) " + ssql;
        myDataBase.execSQL(ssql);
    }

    public String[][] getSearchResults() {
        String[][] foundVerses = new String[2][];
        String ssql = "select book, chapter, verse, version, words from search_results";
        Cursor c = myDataBase.rawQuery(ssql,null);
        int bookColumn = c.getColumnIndex("book");
        int chapterColumn = c.getColumnIndex("chapter");
        int verseColumn = c.getColumnIndex("verse");
        int versionColumn = c.getColumnIndex("version");
        int wordsColumn = c.getColumnIndex("words");
        if (c!= null){
            if (c.moveToFirst()){
                if (c.getCount()>100){
                    foundVerses = new String[2][100];
                }
                else{
                    foundVerses = new String[2][c.getCount()];
                }
                if(c.getCount() == 0){
                    foundVerses = new String[2][1];
                    foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
                    foundVerses[1][0] = "";
                }
                else {
                    int i = 0;
                    do {
                        foundVerses[0][i] = c.getString(bookColumn) + " " + Integer.toString(c.getInt(chapterColumn)) + ":"
                                + Integer.toString(c.getInt(verseColumn)) + " - " + c.getString(versionColumn);
                        foundVerses[1][i] = c.getString(wordsColumn);
                        i++;
                    } while (c.moveToNext() && i < 100);
                }
            }
            else{
                foundVerses = new String[2][1];
                foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
                foundVerses[1][0] = "";
            }
        }
        else
        {
            foundVerses = new String[2][1];
            foundVerses[0][0] = "No verses found matching that criteria. Note: can only search versions cached into database.";
            foundVerses[1][0] = "";
        }
        return foundVerses;
    }
    
    public String[][] ListVerses(String version){
    	String[][] allVerses = new String[3][];
    	String ssql = "select book, chapter, verse from verses where version = '" + version + "'";
    	Cursor c = myDataBase.rawQuery(ssql, null);
    	int bookColumn = c.getColumnIndex("book");
    	int chapterColumn = c.getColumnIndex("chapter");
    	int verseColumn = c.getColumnIndex("verse");
    	if (c != null){
    		if (c.moveToFirst()){
    			allVerses = new String[3][c.getCount()];
    			int i = 0;
    			do{
    			allVerses[0][i] = c.getString(bookColumn);
    			allVerses[1][i] = Integer.toString(c.getInt(chapterColumn));
    			allVerses[2][i] = Integer.toString(c.getInt(verseColumn));
    			i++;
    			} while (c.moveToNext());
    		}else{
    			allVerses = new String[2][];
    		}
    	}
    	return allVerses;
    }

    public String QueryStrong(String strongx){
        String definition = "Not available";
        String ssql = "select str_definition from strong where str_number = '" + strongx + "'";
        Cursor c = myDataBase.rawQuery(ssql,null);
        int defColumn = c.getColumnIndex("str_definition");
        if (c != null){
            if (c.moveToFirst()){
                definition = c.getString(defColumn);
            }
        }
        return definition;
    }
    public String QueryVerse(String book, int chapter, int verse, String version, boolean storage){
        String verseWords = "Verse not found";
        String xtable = "verses";
        if (storage)
        	xtable = "bible";
        String ssql = "select words from " + xtable + " where book = '" + book + "' and chapter = "
	                + chapter + " and verse = " + verse + " and version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int wordsColumn = c.getColumnIndex("words");
        if (c != null){
            if (c.moveToFirst()){
                verseWords = c.getString(wordsColumn);
            }
            c.close();
        }
        return verseWords;
    }
    
    public void beginTrans(){
    	myDataBase.beginTransaction();
    }
    
    public void finishTrans(){
    	myDataBase.setTransactionSuccessful();
    }
    
    public void commitTrans(){
    	myDataBase.endTransaction();
    }
    
    public void batchInsert(Vector<String> insertList){
    	myDataBase.beginTransaction();
    	for (int i = 0; i < insertList.size(); i++){
    		myDataBase.execSQL(insertList.get(i));
    	}
    	myDataBase.setTransactionSuccessful();
    	myDataBase.endTransaction();
    }

    public void addVerse(String book, int chapter, int verse, String version, String verseText, boolean storage){
        //check if exists before adding
    	if (verseText == "Bible version not cached. Please cache from menu -> database -> cache selected version."){
    		return;
    	}
        String singQ = Character.toString('\'');
        String doubQ = Character.toString('\"');
        for (int i = verseText.length() -1; i >= 0; i--){
            if (verseText.substring(i, i+1).equals(singQ)){
                if (i == verseText.length()-1){
                    verseText = verseText + '\'';
                } else{
                    verseText = verseText.substring(0, i+1) + '\'' + verseText.substring(i+1);
                }
            }
        }
        if (storage){
        	myDataBase.execSQL("insert into bible (book, chapter, verse, version, words) "
	                + "values ('" + book + "', " + chapter + ", " + verse + ", '" + version + "', '" + verseText + "')");
        }
        else{
	        myDataBase.execSQL("insert into verses (book, chapter, verse, version, words, plus, minus) "
	                + "values ('" + book + "', " + chapter + ", " + verse + ", '" + version + "', '" + verseText + "', 0, 0)");
	        //check if verse is in weighted verses array; if not, add it to end; if so, change weight to 25
	        int sumWeight = 0;
	        String tempVerses[][] = new String[4][1];
	        int i = 0;
	        if (version.equals("KJV")){
	            tempVerses = KJVverses;
	            sumWeight = KJVcount;
	            i = KJVvcount;
	        }else if (version.equals("NIV")){
	            tempVerses = NIVverses;
	            sumWeight = NIVcount;
	            i = NIVvcount;
	        }else if (version.equals("NKJV")){
	            tempVerses = NKJVverses;
	            sumWeight = NKJVcount;
	            i = NKJVvcount;
	        }else if (version.equals("NASB")){
	            tempVerses = NASBverses;
	            sumWeight = NASBcount;
	            i = NASBvcount;
	        }else if (version.equals("HCSB")){
	            tempVerses = HCSBverses;
	            sumWeight = HCSBcount;
	            i = HCSBvcount;
	        }else if (version.equals("RVR")){
                tempVerses = RVRverses;
                sumWeight = RVRcount;
                i = RVRvcount;
            }else if (version.equals("AMP")){
                tempVerses = AMPverses;
                sumWeight = AMPcount;
                i = AMPvcount;
            }else if (version.equals("ESV")){
                tempVerses = ESVverses;
                sumWeight = ESVcount;
                i = ESVvcount;
            }
	        i++;
	        tempVerses[0][i] = book;
	        tempVerses[1][i] = Integer.toString(chapter);
	        tempVerses[2][i] = Integer.toString(verse);
	        tempVerses[3][i] = Integer.toString(5);
	        sumWeight += 125;
	        if (version.equals("KJV")){
	            KJVverses = tempVerses;
	            KJVcount = sumWeight;
	            KJVvcount = i;
	        }else if (version.equals("NIV")){
	            NIVverses = tempVerses;
	            NIVcount = sumWeight;
	            NIVvcount = i;
	        }else if (version.equals("NKJV")){
	            NKJVverses = tempVerses;
	            NKJVcount = sumWeight;
	            NKJVvcount = i;
	        }else if (version.equals("NASB")){
	            NASBverses = tempVerses;
	            NASBcount = sumWeight;
	            NASBvcount = i;
	        }else if (version.equals("HCSB")){
	            HCSBverses = tempVerses;
	            HCSBcount = sumWeight;
	            HCSBvcount = i;
	        }else if (version.equals("RVR")){
                RVRverses = tempVerses;
                RVRcount = sumWeight;
                RVRvcount = i;
            }else if (version.equals("ESV")){
                ESVverses = tempVerses;
                ESVcount = sumWeight;
                ESVvcount = i;
            }else if (version.equals("AMP")){
                AMPverses = tempVerses;
                AMPcount = sumWeight;
                AMPvcount = i;
            }
        }
    }

    public void removeVerse(String book, int chapter, int verse, String version){
        //get version specific results
        int sumWeight = 0;
        String tempVerses[][] = new String[4][1];
        int i = 0;
        if (version.equals("KJV")){
            tempVerses = KJVverses;
            sumWeight = KJVcount;
            i = KJVvcount;
        }else if (version.equals("NIV")){
            tempVerses = NIVverses;
            sumWeight = NIVcount;
            i = NIVvcount;
        }else if (version.equals("NKJV")){
            tempVerses = NKJVverses;
            sumWeight = NKJVcount;
            i = NKJVvcount;
        }else if (version.equals("NASB")){
            tempVerses = NASBverses;
            sumWeight = NASBcount;
            i = NASBvcount;
        }else if (version.equals("HCSB")){
            tempVerses = HCSBverses;
            sumWeight = HCSBcount;
            i = HCSBvcount;
        }else if (version.equals("RVR")){
            tempVerses = RVRverses;
            sumWeight = RVRcount;
            i = RVRvcount;
        }else if (version.equals("ESV")){
            tempVerses = ESVverses;
            sumWeight = ESVcount;
            i = ESVvcount;
        }else if (version.equals("AMP")){
            tempVerses = AMPverses;
            sumWeight = AMPcount;
            i = AMPvcount;
        }
        //find record in [version]verses that matches verse
        //set record to "","","","0"
        for (int j = 1; j <= i; j++){
            if (tempVerses[0][j].equals(book) &&
                    tempVerses[1][j].equals(Integer.toString(chapter)) &&
                    tempVerses[2][j].equals(Integer.toString(verse))){
                tempVerses[0][j] = "";
                tempVerses[1][j] = "0";
                tempVerses[2][j] = "0";
                tempVerses[3][j] = "0";
                break;
            }
        }
        //calculate current weight of +/- for verse
        //subtract weight from sumWeight (verses and vcount doesn't change, count does)
        String ssql = "select plus, minus from verses where book = '" + book + "' and chapter = "
                + chapter + " and verse = " + verse + " and version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int plusColumn = c.getColumnIndex("plus");
        int minusColumn = c.getColumnIndex("minus");
        if (c != null){
            if (c.moveToFirst()){
                int currPlus = c.getInt(plusColumn);
                int currMinus = c.getInt(minusColumn);
                int weight1 = calcWeightValue(currPlus, currMinus);
                sumWeight -= Math.pow(weight1,3);
            }
            c.close();
        }
        //remove verse
        myDataBase.execSQL("delete from verses where book = '" + book + "' and chapter = "
                + chapter + " and verse = " + verse + " and version = '" + version + "'");

        //copy tempverses to right variable
        if (version.equals("KJV")){
            KJVverses = tempVerses;
            KJVcount = sumWeight;
            KJVvcount = i;
        }else if (version.equals("NIV")){
            NIVverses = tempVerses;
            NIVcount = sumWeight;
            NIVvcount = i;
        }else if (version.equals("NKJV")){
            NKJVverses = tempVerses;
            NKJVcount = sumWeight;
            NKJVvcount = i;
        }else if (version.equals("NASB")){
            NASBverses = tempVerses;
            NASBcount = sumWeight;
            NASBvcount = i;
        }else if (version.equals("HCSB")){
            HCSBverses = tempVerses;
            HCSBcount = sumWeight;
            HCSBvcount = i;
        }else if (version.equals("RVR")){
            RVRverses = tempVerses;
            RVRcount = sumWeight;
            RVRvcount = i;
        }else if (version.equals("ESV")){
            ESVverses = tempVerses;
            ESVcount = sumWeight;
            ESVvcount = i;
        }else if (version.equals("AMP")){
            AMPverses = tempVerses;
            AMPcount = sumWeight;
            AMPvcount = i;
        }
    }

    public void VerseRight(String book, int chapter, int verse, String version, boolean correct){
        //get version specific results
        int sumWeight = 0;
        String tempVerses[][] = new String[4][1];
        int i = 0;
        if (version.equals("KJV")){
            tempVerses = KJVverses;
            sumWeight = KJVcount;
            i = KJVvcount;
        }else if (version.equals("NIV")){
            tempVerses = NIVverses;
            sumWeight = NIVcount;
            i = NIVvcount;
        }else if (version.equals("NKJV")){
            tempVerses = NKJVverses;
            sumWeight = NKJVcount;
            i = NKJVvcount;
        }else if (version.equals("NASB")){
            tempVerses = NASBverses;
            sumWeight = NASBcount;
            i = NASBvcount;
        }else if (version.equals("HCSB")){
            tempVerses = HCSBverses;
            sumWeight = HCSBcount;
            i = HCSBvcount;
        }else if (version.equals("RVR")){
            tempVerses = RVRverses;
            sumWeight = RVRcount;
            i = RVRvcount;
        }else if (version.equals("ESV")){
            tempVerses = ESVverses;
            sumWeight = ESVcount;
            i = ESVvcount;
        }else if (version.equals("AMP")){
            tempVerses = AMPverses;
            sumWeight = AMPcount;
            i = AMPvcount;
        }
        //get current value of plus
        int currPlus = 0;
        int currMinus = 0;
        String ssql = "select plus, minus from verses where book = '" + book + "' and chapter = "
                + chapter + " and verse = " + verse + " and version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int plusColumn = c.getColumnIndex("plus");
        int minusColumn = c.getColumnIndex("minus");
        if (c != null){
            if (c.moveToFirst()){
                currPlus = c.getInt(plusColumn);
                currMinus = c.getInt(minusColumn);
            }
            c.close();
        }
        int weight0 = calcWeightValue(currPlus, currMinus);
        if (correct){
            //update it + 1
            currPlus += 1;
            ssql = "update verses set plus = " + currPlus + " where book = '" + book + "' and chapter = "
                    + chapter + " and verse = " + verse + " and version = '" + version + "'";
            myDataBase.execSQL(ssql);
        }else{
            //update it + 1
            currMinus += 1;
            ssql = "update verses set minus = " + currMinus + " where book = '" + book + "' and chapter = "
                    + chapter + " and verse = " + verse + " and version = '" + version + "'";
            myDataBase.execSQL(ssql);
        }
        //update tempVerses with new weight
        int weight1 = calcWeightValue(currPlus, currMinus);
        for (int j = 1; j <= i; j++){
            if (tempVerses[0][j].equals(book) &&
                    tempVerses[1][j].equals(Integer.toString(chapter)) &&
                    tempVerses[2][j].equals(Integer.toString(verse))){
                tempVerses[3][j] = Integer.toString(weight1);
                break;
            }
        }
        //update sumWeight
        sumWeight -= Math.pow(weight0, 3);
        sumWeight += Math.pow(weight1, 3);
        //copy tempverses to right variable
        if (version.equals("KJV")){
            KJVverses = tempVerses;
            KJVcount = sumWeight;
            KJVvcount = i;
        }else if (version.equals("NIV")){
            NIVverses = tempVerses;
            NIVcount = sumWeight;
            NIVvcount = i;
        }else if (version.equals("NKJV")){
            NKJVverses = tempVerses;
            NKJVcount = sumWeight;
            NKJVvcount = i;
        }else if (version.equals("NASB")){
            NASBverses = tempVerses;
            NASBcount = sumWeight;
            NASBvcount = i;
        }else if (version.equals("HCSB")){
            HCSBverses = tempVerses;
            HCSBcount = sumWeight;
            HCSBvcount = i;
        }else if (version.equals("RVR")){
            RVRverses = tempVerses;
            RVRcount = sumWeight;
            RVRvcount = i;
        }else if (version.equals("ESV")){
            ESVverses = tempVerses;
            ESVcount = sumWeight;
            ESVvcount = i;
        }else if (version.equals("AMP")){
            AMPverses = tempVerses;
            AMPcount = sumWeight;
            AMPvcount = i;
        }
    }
    
    public void clearScores(){
    	//reset scores
        myDataBase.execSQL("update verses set plus = 0, minus = 0 where plus > 0 or minus > 0");
    }

    public void weightVerses(String version){
        int sumWeight = 0;
        String tempVerses[][] = new String[4][8000];
        String ssql = "select * from verses where version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int bookColumn = c.getColumnIndex("book");
        int chapterColumn = c.getColumnIndex("chapter");
        int verseColumn = c.getColumnIndex("verse");
        int plusColumn = c.getColumnIndex("plus");
        int minusColumn = c.getColumnIndex("minus");
        int i = 0;
        if (c != null){
            if (c.moveToFirst()){
                int verseWeight = 5;

                do {
                    i++;
                    String book1 = c.getString(bookColumn);
                    int chapter1 = c.getInt(chapterColumn);
                    int verse1 = c.getInt(verseColumn);
                    int plus1 = c.getInt(plusColumn);
                    int minus1 = c.getInt(minusColumn);
                    verseWeight = calcWeightValue(plus1, minus1);
                    //add to tempVerses based on weight
                    tempVerses[0][i] = book1;
                    tempVerses[1][i] = Integer.toString(chapter1);
                    tempVerses[2][i] = Integer.toString(verse1);
                    tempVerses[3][i] = Integer.toString(verseWeight);
                    sumWeight += (verseWeight*verseWeight*verseWeight);
                    //add this stuff to String[][] type
                } while (c.moveToNext());

                c.close();
            }
        }
        if (version.equals("KJV")){
            KJVverses = tempVerses;
            KJVcount = sumWeight;
            KJVvcount = i;
        }else if (version.equals("NIV")){
            NIVverses = tempVerses;
            NIVcount = sumWeight;
            NIVvcount = i;
        }else if (version.equals("NKJV")){
            NKJVverses = tempVerses;
            NKJVcount = sumWeight;
            NKJVvcount = i;
        }else if (version.equals("NASB")){
            NASBverses = tempVerses;
            NASBcount = sumWeight;
            NASBvcount = i;
        }else if (version.equals("HCSB")){
            HCSBverses = tempVerses;
            HCSBcount = sumWeight;
            HCSBvcount = i;
        }else if (version.equals("RVR")){
            RVRverses = tempVerses;
            RVRcount = sumWeight;
            RVRvcount = i;
        }else if (version.equals("ESV")){
            ESVverses = tempVerses;
            ESVcount = sumWeight;
            ESVvcount = i;
        }else if (version.equals("AMP")){
            AMPverses = tempVerses;
            AMPcount = sumWeight;
            AMPvcount = i;
        }
    }

    private int calcWeightValue(int plus1, int minus1){
        int verseWeight = 5;
        if (plus1 <= minus1){
            verseWeight = 5;
        }else if(minus1 == 0){
            verseWeight = Math.max(5 - plus1, 1);
        }else{
            verseWeight = plus1/minus1;
            verseWeight = Math.max(5 - verseWeight, 1);
        }
        return verseWeight;
    }

    public int verseCount(String version){
        int numOfVerses = 0;
        String ssql = "select count(*) as count1 from verses where version = '" + version + "'";
        Cursor c = myDataBase.rawQuery(ssql, null);
        int wordsColumn = c.getColumnIndex("count1");
        if (c != null){
            if (c.moveToFirst()){
                numOfVerses = c.getInt(wordsColumn);
            }
            c.close();
        }
        return numOfVerses;
    }

    public int verseScoreCount(String version, int valScore){
        int numOfVerses = 0;
        String ssql = "";
        int plusOne = valScore - 1;
        if (valScore > 1 && valScore < 5) {
            ssql = "select sum(case when minus = 0 and plus = " + plusOne + " then 1 when minus = 0 then 0 " +
                    "when plus/minus >= " + plusOne + " and plus/minus < " + valScore + " then 1 else 0 end) as count1 " +
                    "from verses where version = '" + version + "'";
        }else if (valScore == 0){
            ssql = "select sum(case when plus = 0 then 1 else 0 end) as count1 " +
                    "from verses where version = '" + version + "'";
        }else if (valScore == 1){
            ssql = "select sum(case when minus = 0 then 0 " +
                    "when plus > 0 and plus/minus < " + valScore + " then 1 else 0 end) as count1 " +
                    "from verses where version = '" + version + "'";
        }else{
            ssql = "select sum(case when minus = 0 and plus >= " + plusOne + " then 1 when minus = 0 then 0 " +
                    "when plus/minus >= " + plusOne + " then 1 else 0 end) as count1 " +
                    "from verses where version = '" + version + "'";
        }
        Cursor c = myDataBase.rawQuery(ssql, null);
        int wordsColumn = c.getColumnIndex("count1");
        if (c != null){
            if (c.moveToFirst()){
                numOfVerses = c.getInt(wordsColumn);
            }
            c.close();
        }
        return numOfVerses;
    }
}