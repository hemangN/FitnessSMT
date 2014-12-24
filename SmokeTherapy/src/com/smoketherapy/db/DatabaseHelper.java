package com.smoketherapy.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smoketherapy.staticdata.StaticData;

public class DatabaseHelper extends SQLiteOpenHelper {

	private Context mContext;
	private SQLiteDatabase myDatabase;
	private static final String DATABASE_NAME = "Smoketherapy.sqlite";
	public static final String DATABASE_PATH = "/data/data/com.smoketherapy/databases/";
	// public static final String DATABASE_PATH =
	// String.format("//data//data//com.smoketherapy//databases//");
	private static final int DATABASE_VERSION = 1;

	// ================ Base Calc Table Columns
	// =======================================================//
	public static final String BASE_CALC_TABLE = "BASE_CALC";
	public static final String AVG = "AVG";
	public static final String S2 = "S2";
	public static final String S3 = "S3";
	public static final String S4 = "S4";
	public static final String THERAPYTIME = "THERAPYTIME";

	// ================ Stage1 Table Columns
	// ==========================================================//
	public static final String STAGE1_TABLE = "STAGE1";
	public static final String ID = "ID";
	public static final String SMOKE_DATE = "DATE";
	public static final String SMOKE_TIME = "TIME";
	public static final String NO_OF_CIGGE = "NO_OF_CIGGE";

	// ================= CIGGE IN STAGE1 Table Detatils
	// ===============================================//
	public static final String CIGGE_IN_STAGE1_TABLE = "CIGGE_IN_STAGE1";
	public static final String TOTAL_CIGGE = "TOTAL_CIGGE";

	// ================== Personal Info Table
	// Columns===================================================//

	public static final String USER_INFO_TABLE = "USER_INFO";
	public static final String USER_ID = "USER_ID";
	public static final String GENDER = "GENDER";
	public static final String WAKEUP_TIME = "WAKEUPTIME";
	public static final String SLEEP_HOURS = "SLEEPHOURS";

	// ================= SmokeTracker Table Info
	// ==================================================//

	public static final String SMOKE_TRACKER_TABLE = "SMOKE_TRACKER";
	public static final String SM_ID    = "ID";
	public static final String SM_STAGE = "STAGE";
	public static final String SM_DATE = "DATE";
	public static final String SM_DAY_REQ = "DAY_REQ";
	public static final String SM_ALLOW_CIGGE = "ALLOW_CIGGE";
	public static final String SM_EXTRA_CIGGE = "EXTRA_CIGGE";
	public static final String SM_DAYS_IN_STAGE = "DAY_IN_STAGE";
	public static final String SM_NOTYFIED_CIGGE = "NOTYFIED_CIGGE";

	// ================== ends
	// ======================================================================//

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
		openDataBase();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {

			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		/*
		 * SQLiteDatabase checkDB = null;
		 * 
		 * try { String myPath = DATABASE_PATH + DATABASE_NAME; checkDB =
		 * SQLiteDatabase.openDatabase(myPath, null,
		 * SQLiteDatabase.OPEN_READONLY);
		 * 
		 * } catch (SQLiteException e) {
		 * 
		 * // database does't exist yet.
		 * 
		 * }
		 * 
		 * if (checkDB != null) {
		 * 
		 * checkDB.close();
		 * 
		 * }
		 * 
		 * return checkDB != null ? true : false;
		 */

		File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
		return dbFile.exists();
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream externalDbStream = mContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream localDbStream = new FileOutputStream(outFileName);

		// Copying the database
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = externalDbStream.read(buffer)) > 0) {
			localDbStream.write(buffer, 0, bytesRead);
		}

		// Close the streams
		// myOutput.flush();
		externalDbStream.close();
		localDbStream.close();

	}

	// delete database
	public void db_delete() {
		File file = new File(DATABASE_PATH + DATABASE_NAME);
		if (file.exists()) {
			file.delete();
			System.out.println("delete database file.");
		}
	}

	// Open database
	public SQLiteDatabase openDataBase() throws SQLException {
		String path = DATABASE_PATH + DATABASE_NAME;
		if (myDatabase == null) {
			try {
				createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myDatabase = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
		}

		return myDatabase;
	}

	public synchronized void closeDataBase() throws SQLException {
		if (myDatabase != null)
			myDatabase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor getBaseCalc() {
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = "Select * from BASE_CALC";
		Cursor cursor = db.rawQuery(Query, null);
		return cursor;
	}

	public void insertPersonalInfo(PersonalInfo personalInfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID, StaticData.USER_ID);
		values.put(GENDER, personalInfo.gender);
		values.put(SLEEP_HOURS, personalInfo.sleep_hours);
		values.put(WAKEUP_TIME, personalInfo.wake_up_time);

		db.insert(USER_INFO_TABLE, null, values);
		db.close();

	}

	public PersonalInfo getPersonalInfo() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectPersonalInfo = "select * from " + USER_INFO_TABLE;

		Cursor cursor = db.rawQuery(selectPersonalInfo, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		PersonalInfo personalInfo = new PersonalInfo(cursor.getString(1),
				cursor.getString(2), cursor.getString(3));

		return personalInfo;

	}

	public long insertStage1Info(StageOneInfo stageOneInfo) {
		long rawId;
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SMOKE_DATE, stageOneInfo.date);
		values.put(SMOKE_TIME, stageOneInfo.time);
		values.put(NO_OF_CIGGE, stageOneInfo.no_of_cigge);

		rawId = db.insert(STAGE1_TABLE, null, values);
		db.close();

		return rawId;
	}

	public int getTherapyTime(int avg) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(BASE_CALC_TABLE, new String[] { THERAPYTIME },
				AVG + "=?", new String[] { String.valueOf(avg) }, null, null,
				null, null);

		if (cursor != null)
			cursor.moveToFirst();

		return Integer.parseInt(cursor.getString(0));
	}

	public BaseCalc getBaseCalcInfo(int avg)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(BASE_CALC_TABLE, new String[] { S2,S3,S4,THERAPYTIME },
				AVG + "=?", new String[] { String.valueOf(avg) }, null, null,
				null, null);
		if(cursor != null)
			cursor.moveToFirst();
		
		BaseCalc mBaseCalc = new BaseCalc(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
		return mBaseCalc;
	}
	public long insertSmokeTrackerInfo(SmokeTracker smTracker) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SM_STAGE, smTracker.stage);
		values.put(SM_DATE, smTracker.date);
		values.put(SM_DAY_REQ, smTracker.day_req);
		values.put(SM_ALLOW_CIGGE, smTracker.allow_cigge);
		values.put(SM_EXTRA_CIGGE, smTracker.extra_cigge);
		values.put(SM_DAYS_IN_STAGE, smTracker.day_in_stage);
		values.put(SM_NOTYFIED_CIGGE,smTracker.notyfied_cigge);
		
		return db.insert(SMOKE_TRACKER_TABLE, null, values);
	}

	public SmokeTracker getSmokeTrackerInfo() {
		SQLiteDatabase db = this.getReadableDatabase();

		//String selectQuery = "SELECT  * FROM " + SMOKE_TRACKER_TABLE;

		//Cursor cursor = db.rawQuery(selectQuery, null);

		Cursor cursor = db.query(SMOKE_TRACKER_TABLE, 
				  new String[] {SM_ID,SM_STAGE,SM_DATE, SM_DAY_REQ,SM_ALLOW_CIGGE,SM_EXTRA_CIGGE,SM_DAYS_IN_STAGE,SM_NOTYFIED_CIGGE},
				  null, null, null, null, null);
				 
		if (cursor != null) 
		{

			if(cursor.moveToLast())
			{
				System.out.println(cursor.getString(0));
				System.out.println(cursor.getString(1));
				System.out.println(cursor.getString(2));
				System.out.println(cursor.getString(3));
				System.out.println(cursor.getString(4));
				System.out.println(cursor.getString(5));
				System.out.println(cursor.getString(6));
				
				SmokeTracker smTracker = new SmokeTracker(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
						cursor.getString(2), Integer.parseInt(cursor.getString(3)),
						Integer.parseInt(cursor.getString(4)),
						Integer.parseInt(cursor.getString(5)),
						Integer.parseInt(cursor.getString(6)),
						Integer.parseInt(cursor.getString(7)));
				return smTracker;
			}
				
			return null;

		}
		else 
		{
			return null;
		}
	}

	public int updateSmokeTrackerInfo(SmokeTracker smTracker)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 	ContentValues values = new ContentValues();
			values.put(SM_STAGE, smTracker.stage);
			values.put(SM_DATE, smTracker.date);
			values.put(SM_DAY_REQ, smTracker.day_req);
			values.put(SM_ALLOW_CIGGE, smTracker.allow_cigge);
			values.put(SM_EXTRA_CIGGE, 1);
			values.put(SM_DAYS_IN_STAGE, smTracker.day_in_stage);
			values.put(SM_NOTYFIED_CIGGE, smTracker.notyfied_cigge);

	 
	        // updating row
	        return db.update(SMOKE_TRACKER_TABLE, values, SM_ID + " = ?",
	                new String[] { String.valueOf(smTracker.getRawID()) });
	}
	
	public int updateNotifiedCiggeInSmokeTracker(SmokeTracker smTracker)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	 	ContentValues values = new ContentValues();
		values.put(SM_STAGE, smTracker.stage);
		values.put(SM_DATE, smTracker.date);
		values.put(SM_DAY_REQ, smTracker.day_req);
		values.put(SM_ALLOW_CIGGE, smTracker.allow_cigge);
		values.put(SM_EXTRA_CIGGE, smTracker.extra_cigge);
		values.put(SM_DAYS_IN_STAGE, smTracker.day_in_stage);
		
		int notyfied_cigge = smTracker.notyfied_cigge + 1;
		
		values.put(SM_NOTYFIED_CIGGE, notyfied_cigge);

 
        // updating row
        return db.update(SMOKE_TRACKER_TABLE, values, SM_ID + " = ?",
                new String[] { String.valueOf(smTracker.getRawID()) });
	}
	
	public int getDayCountForStage(int stage)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(SMOKE_TRACKER_TABLE, 
				  new String[] {SM_ID,SM_STAGE,SM_DATE, SM_DAY_REQ,SM_ALLOW_CIGGE,SM_EXTRA_CIGGE,SM_DAYS_IN_STAGE},
				  SM_STAGE + "= ?", new String[] { String.valueOf(stage) }, null, null, null);
		
		if(cursor.moveToFirst())
			return cursor.getCount();
		
		return 0;
	}
	
	public int getDayCountToCompTherapy()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(SMOKE_TRACKER_TABLE, 
				  new String[] {SM_ID,SM_STAGE,SM_DATE, SM_DAY_REQ,SM_ALLOW_CIGGE,SM_EXTRA_CIGGE,SM_DAYS_IN_STAGE},
				  null, null, null, null, null);
		
		if(cursor.moveToFirst())
			return cursor.getCount();
		
		return 0;
	}
	
	public void updateTotalCiggeInStage1() {
		SQLiteDatabase db = this.getWritableDatabase();

		/*
		 * Cursor cursor = db.query(CIGGE_IN_STAGE1_TABLE, new String[] {
		 * TOTAL_CIGGE }, USER_ID + "=?", new String[] {
		 * String.valueOf(StaticData.USER_ID) }, null, null, null, null);
		 */
		String selectQuery = "SELECT  * FROM " + CIGGE_IN_STAGE1_TABLE;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null)
			cursor.moveToFirst();

		System.out.println("========== Total Count in tab=========="
				+ cursor.getCount());
		int pre_no_of_cigge = Integer.parseInt(cursor.getString(1));
		int current_no_of_cigge = pre_no_of_cigge + 1;

		System.out.println("==========Previous of cigge ==============="
				+ pre_no_of_cigge);
		System.out.println("==========Current No Of cigge =============="
				+ current_no_of_cigge);

		ContentValues values = new ContentValues();
		values.put(TOTAL_CIGGE, current_no_of_cigge);

		int rawAffacted = db.update(CIGGE_IN_STAGE1_TABLE, values, USER_ID
				+ " = ?", new String[] { String.valueOf(StaticData.USER_ID) });

		System.out.println("=============No Of Raw Affectd ================="
				+ rawAffacted);
		db.close();

	}

}
