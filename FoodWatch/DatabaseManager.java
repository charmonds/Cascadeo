package com.cs192.foodwatch;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_FIRSTNAME = "firstname";
	public static final String KEY_MIDDLENAME = "middlename";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_FIRSTWEIGHT = "Iweight";
	public static final String KEY_THEFIRSTHEIGHT = "Fheight";
	
	public static final String KEY_FNAME_DIETITIAN = "fname";
	public static final String KEY_MNAME_DIETITIAN = "mname";
	public static final String KEY_LNAME_DIETITIAN = "lname";
	public static final String KEY_EMAIL_DIETITIAN = "email";
	public static final String KEY_SCHEDULE_DIETITIAN = "schedule";
	
	public static final String KEY_AGE = "age";
	public static final String KEY_OCCUPATION = "occupation";
	public static final String KEY_SMOKER = "is_smoker";
	public static final String KEY_ALCOHOLIC = "is_alcoholic";
	public static final String KEY_CHEWING = "chewing_difficulty";
	
	public static final String KEY_NAME_AILMENT = "name";
	public static final String KEY_DATE_ACQUIRED = "date_acquired";
	
	public static final String KEY_WEIGHT = "weight";
	public static final String KEY_DATE = "date";
	
	public static final String KEY_NAMEOFFOOD ="Foodname";
	public static final String KEY_CHOLESTEROL = "cholesterol";
	public static final String KEY_CARBOHYDRATES = "carbohydrates";
	public static final String KEY_TOTALFATS = "fats";
	public static final String KEY_SUGAR = "sugar";
	public static final String KEY_CALORIES = "calories";
	public static final String KEY_AMOUNTOFFOOD = "amount";
	public static final String KEY_DATERECORDED = "daterecorded";
	public static final String KEY_TIMERECORDED = "timerecorded";
	public static final String KEY_MEALTIME = "mealtime";
	public static final String KEY_MEAL = "DineInOrLunchOut";
	
	public static final String KEY_TARIDENTITY = "identifier";
	public static final String KEY_TARGETWEIGHT = "targetWeight";
	public static final String KEY_TARGETDATE = "targetDate";
	
	private static final String DATABASE_NAME = "Foodwatch Database";
	private static final String DATABASE_TABLE_ONE = "patient";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String KEY_TARCALORIEIDENTITY = "caltargetidentifier";
	private static final String KEY_TARGETCALORIE = "targetCalories";
	private static final String KEY_TARGETCALORIEDATE = "targetDateOfCalories";
	
	
	private DatabaseHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL("CREATE TABLE "+ DATABASE_TABLE_ONE + " (" + 
						KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_FIRSTNAME + " TEXT NOT NULL, " + 
						KEY_MIDDLENAME + " TEXT NOT NULL, " +
						KEY_LASTNAME + " TEXT NOT NULL, " +
						KEY_EMAIL + " TEXT NOT NULL, " +
						KEY_PASSWORD + " TEXT NOT NULL, " +
						KEY_FIRSTWEIGHT + " TEXT NOT NULL, " +
						KEY_THEFIRSTHEIGHT + " TEXT NOT NULL);"
					);
			
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ONE);
			onCreate(db);
		}
		
	}
	
	public DatabaseManager(Context c) {
		ourContext = c;
	}
	
	public DatabaseManager open() throws SQLException {
		ourHelper = new DatabaseHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}

	public long createEntry(String first, String middle, String last,
			String mail, String p, String w, String h) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_FIRSTNAME, first);
		cv.put(KEY_MIDDLENAME, middle);
		cv.put(KEY_LASTNAME, last);
		cv.put(KEY_EMAIL, mail);
		cv.put(KEY_PASSWORD, p);
		cv.put(KEY_FIRSTWEIGHT, w);
		cv.put(KEY_THEFIRSTHEIGHT, h);
		return ourDatabase.insert(DATABASE_TABLE_ONE, null, cv);
	
	}
	
	
	public long updateEntry(String id, String first, String middle, String last,String mail){
		ContentValues cv = new ContentValues();
		cv.put(KEY_FIRSTNAME, first);
		cv.put(KEY_MIDDLENAME, middle);
		cv.put(KEY_LASTNAME, last);
		cv.put(KEY_EMAIL, mail);
		return ourDatabase.update(DATABASE_TABLE_ONE, cv, "_id = " + id, null);
	}
	
	public long updateDietitian(String id, String first, String middle, String last, String mail, String sched){
		ContentValues cv = new ContentValues();
		cv.put(KEY_FNAME_DIETITIAN, first);
		cv.put(KEY_MNAME_DIETITIAN, middle);
		cv.put(KEY_LNAME_DIETITIAN, last);
		cv.put(KEY_EMAIL_DIETITIAN, mail);
		cv.put(KEY_SCHEDULE_DIETITIAN, mail);
		return ourDatabase.update("dietitianTable"+id, cv, null, null);
	}
	
	public long insertAilment(String id, String name, String date) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME_AILMENT, name);
		cv.put(KEY_DATE_ACQUIRED, date);
		return ourDatabase.insert("HealthTable"+id, null, cv);
	
	}
	
	
	public long addToWeightDatabaseOfPatient(String weight, String date, String id) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_WEIGHT, weight);
		cv.put(KEY_DATE, date);
		String tablename = "weightTable"+id;
		return ourDatabase.insert(tablename, null, cv);
	}

	public void createWeightDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String patient_table_name = "weightTable"+id;
		db.execSQL("DROP TABLE IF EXISTS " + patient_table_name);
		db.execSQL("CREATE TABLE " + patient_table_name + " (" +
				KEY_WEIGHT + " TEXT NOT NULL, " +
				KEY_DATE + " TEXT NOT NULL);"
			);
	}

	public void createDietitianDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String dietitian_table_name = "dietitianTable"+id;
		db.execSQL("DROP TABLE IF EXISTS " + dietitian_table_name);
		db.execSQL("CREATE TABLE " + dietitian_table_name + " (" +
				KEY_FNAME_DIETITIAN + " VARCHAR(50) NOT NULL, " +
				KEY_MNAME_DIETITIAN + " VARCHAR(50) NOT NULL, " +
				KEY_LNAME_DIETITIAN + " VARCHAR(50) NOT NULL, " +
				KEY_EMAIL_DIETITIAN + " VARCHAR(50) NOT NULL, " +
				KEY_SCHEDULE_DIETITIAN + " TEXT NOT NULL);"
			);
	}
	
	public void createPersonalDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String dietitian_table_name = "PersonalTable"+id;
		db.execSQL("DROP TABLE IF EXISTS " + dietitian_table_name);
		db.execSQL("CREATE TABLE " + dietitian_table_name + " (" +
				KEY_AGE + " INT NOT NULL, " +
				KEY_OCCUPATION + " VARCHAR(50) NOT NULL, " +
				KEY_SMOKER + "BINARY NOT NULL, " +
				KEY_ALCOHOLIC + "BINARY NOT NULL," +
				KEY_CHEWING + "BINARY NOT NULL);"
			);
	}
	
	public void createHealthDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String dietitian_table_name = "HealthTable"+id;
		db.execSQL("DROP TABLE IF EXISTS " + dietitian_table_name);
		db.execSQL("CREATE TABLE " + dietitian_table_name + " (" +
				KEY_NAME_AILMENT + " VARCHAR(50) NOT NULL, " +
				KEY_DATE_ACQUIRED + " DATETIME NOT NULL);"
			);
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns, null, null, null, null, null);
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iFname = c.getColumnIndex(KEY_FIRSTNAME);
		int iMname = c.getColumnIndex(KEY_MIDDLENAME);
		int iLname = c.getColumnIndex(KEY_LASTNAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPass = c.getColumnIndex(KEY_PASSWORD);
		int iWeight = c.getColumnIndex(KEY_FIRSTWEIGHT);
		int iHeight = c.getColumnIndex(KEY_THEFIRSTHEIGHT);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iFname) + " " + c.getString(iMname) + " " + c.getString(iLname) + " " + c.getString(iEmail) + " " + c.getString(iPass) + " " + c.getString(iWeight) + c.getString(iHeight) + "\n";
		}
		c.close();
		return result;
	}
	
	public String getHealth(String id) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_NAME_AILMENT, KEY_DATE_ACQUIRED};
		Cursor c = ourDatabase.query("HealthTable" + id, columns, null, null, null, null, null);
		String result = "";
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(c.getColumnIndex("name")) + "\n\t\t\t" + c.getString(c.getColumnIndex("date_acquired")) + "\n";
		}
		c.close();
		return result;
	}
	
	public String getPersonal(String id, String column) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_AGE, KEY_OCCUPATION, KEY_SMOKER, KEY_ALCOHOLIC, KEY_CHEWING};
		Cursor c = ourDatabase.query("PersonalTable"+id, columns, null, null, null, null, null);
		String result = "";
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(c.getColumnIndex(column));
		}
		c.close();
		return result;
	}
	
	public String getInfo(String id, String column) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns, "_id = " + id, null, null, null, null);
		String result = "";
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(c.getColumnIndex(column));
		}
		c.close();
		return result;
	}
	
	public String getDietitian(String id, String column) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_FNAME_DIETITIAN, KEY_MNAME_DIETITIAN, KEY_LNAME_DIETITIAN, KEY_EMAIL_DIETITIAN, KEY_SCHEDULE_DIETITIAN};
		Cursor c = ourDatabase.query("dietitianTable"+id, columns, null, null, null, null, null);
		String result = "";
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(c.getColumnIndex(column));
		}
		c.close();
		return result;
	}


	public String getID(String loginName) {
		// TODO Auto-generated method stub
		
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns,"email LIKE " + "'%"+loginName+"%'", null, null, null, null);
		if (c!=null) {
			c.moveToFirst();
			String result = "";
			result = c.getString(0);
			c.close();
			return result;
			
		} else {
			return null;
		}
		
		
	}
	
	public String validAccount(String loginName, String password) {
		// TODO Auto-generated method stub
		
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns,"email LIKE " + "'%"+loginName+"%' AND password = '" + password + "'", null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String result = "";
			result = c.getString(0);
			c.close();
			return result;
		} else {
			c.close();
			return null;
		}
		
		
	}

	
	public String getWeightData(String id, long l) {
		// TODO Auto-generated method stub
		String tablename = "weightTable"+id;
		String[] columns = new String[] {KEY_WEIGHT, KEY_DATE};
		Cursor c = ourDatabase.query(tablename, columns, null, null, null, null, null);
		String result = "";
		
		int iWeight = c.getColumnIndex(KEY_WEIGHT);
		int iDate = c.getColumnIndex(KEY_DATE);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(iWeight) + " " + c.getString(iDate) + "\n";
		}
		
		c.close();
		return result;
	}

	
	public String getDataInitialWeight(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns,KEY_ROWID+"="+l, null, null, null, null);
		if (c!=null) {
			c.moveToFirst();
			String last = c.getString(6);
			c.close();
			return last;
		} else {
			return null;
		}
		
	}

	public Cursor getDataAndReturnedAsQuery(String id) {
		// TODO Auto-generated method stub
		String tablename = "weightTable"+id;
		String[] columns = new String[] {KEY_WEIGHT, KEY_DATE};
		Log.d("TAG", tablename);
		Cursor c = ourDatabase.query(tablename, columns, null, null, null, null, null, null);
		if(c!=null) {
		return c;
		} else {
			return null;
		}
	}

	public void createTargetDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String targetTableName = "targetTable"+id;
		db.execSQL("CREATE TABLE " + targetTableName + " (" +
				KEY_TARIDENTITY + " INTEGER PRIMARY KEY, " +
				KEY_TARGETWEIGHT + " TEXT NOT NULL, " +
				KEY_TARGETDATE + " TEXT NOT NULL);"
			);
		
	}

	public void createTarget(String initialdateoftarget, String initialtarget, String id) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		String targetTableName = "targetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		cv.put(KEY_TARIDENTITY, l);
		cv.put(KEY_TARGETWEIGHT, initialtarget);
		cv.put(KEY_TARGETDATE, initialdateoftarget);
		ourDatabase.insert(targetTableName, null, cv);
	}

	public String getTarget(String id) {
		// TODO Auto-generated method stub
		String tablename = "targetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		String[] columns = new String[] {KEY_TARIDENTITY, KEY_TARGETWEIGHT, KEY_TARGETDATE};
		Cursor c = ourDatabase.query(tablename, columns,KEY_TARIDENTITY+"="+l, null, null, null, null);
		
		String result = "";
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(0)+ " " + c.getString(1) + " " + c.getString(2);
		}
		c.close();
		return result;
	}

	public void updateTarget(String tarWeight, String tarDate, String one, String id) {
		// TODO Auto-generated method stub
		String targetTableName = "targetTable"+id;
		String won = "1";
		long l = Long.parseLong(won);
		ContentValues cv = new ContentValues();
		cv.put(KEY_TARIDENTITY, one);
		cv.put(KEY_TARGETWEIGHT, tarWeight);
		cv.put(KEY_TARGETDATE, tarDate);
		ourDatabase.update(targetTableName, cv, KEY_TARIDENTITY+"="+l, null);
	}

	public String getTargetWeight(String id) {
		// TODO Auto-generated method stub
		String tablename = "targetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		String[] columns = new String[] {KEY_TARIDENTITY, KEY_TARGETWEIGHT, KEY_TARGETDATE};
		Cursor c = ourDatabase.query(tablename, columns,KEY_TARIDENTITY+"="+l, null, null, null, null);
		
		String result = "";
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = c.getString(1);
		}
		c.close();
		return result;
	}

	public String getTargetDate(String id) {
		// TODO Auto-generated method stub
		String tablename = "targetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		String[] columns = new String[] {KEY_TARIDENTITY, KEY_TARGETWEIGHT, KEY_TARGETDATE};
		Cursor c = ourDatabase.query(tablename, columns,KEY_TARIDENTITY+"="+l, null, null, null, null);
		
		String result = "";
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = c.getString(2);
		}
		c.close();
		return result;
	}

	public long addToNutritionalFactsDatabase(String foodName, String cho,
			String ca, String fa, String su, String cal, String quantity,
			String date, String time, String timeOfMeal, String meal, String id) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAMEOFFOOD, foodName);
		cv.put(KEY_CHOLESTEROL, cho);
		cv.put(KEY_CARBOHYDRATES, ca);
		cv.put(KEY_TOTALFATS, fa);
		cv.put(KEY_SUGAR, su);
		cv.put(KEY_CALORIES, cal);
		cv.put(KEY_AMOUNTOFFOOD, quantity);
		cv.put(KEY_DATERECORDED, date);
		cv.put(KEY_TIMERECORDED, time);
		cv.put(KEY_MEALTIME, timeOfMeal);
		cv.put(KEY_MEAL, meal);
		String targetTableName = "NutritionalFactTable"+id;
		return ourDatabase.insert(targetTableName, null, cv);
	}

	public void createNutritionalFactDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String targetTableName = "NutritionalFactTable"+id;
		db.execSQL("CREATE TABLE " + targetTableName + " (" +
				KEY_NAMEOFFOOD + " TEXT NOT NULL, " +
				KEY_CHOLESTEROL + " TEXT NOT NULL, " +
				KEY_CARBOHYDRATES + " TEXT NOT NULL, " +
				KEY_TOTALFATS + " TEXT NOT NULL, " +
				KEY_SUGAR + " TEXT NOT NULL, " +
				KEY_CALORIES + " TEXT NOT NULL, " +
				KEY_AMOUNTOFFOOD + " TEXT NOT NULL, " +
				KEY_DATERECORDED + " TEXT NOT NULL, " +
				KEY_TIMERECORDED + " TEXT NOT NULL, " +
				KEY_MEALTIME + " TEXT NOT NULL, " +
				KEY_MEAL + " TEXT NOT NULL);"
			);
		
	}

	public Cursor getDataOfNutFactandReturnAsQuerry(String id) {
		// TODO Auto-generated method stub
		String tablename = "NutritionalFactTable"+id;
		String[] columns = new String[] {KEY_NAMEOFFOOD,KEY_CHOLESTEROL,KEY_CARBOHYDRATES,KEY_TOTALFATS,KEY_SUGAR,KEY_CALORIES,KEY_AMOUNTOFFOOD,KEY_DATERECORDED,KEY_TIMERECORDED};
		Cursor c = ourDatabase.query(tablename, columns, null, null, null, null, null, null);
		return c;
	}

	public String getNutFactData(String nametest) {
		// TODO Auto-generated method stub
		String tablename = "NutritionalFactTable"+nametest;
		String[] columns = new String[] {KEY_NAMEOFFOOD,KEY_CHOLESTEROL,KEY_CARBOHYDRATES,KEY_TOTALFATS,KEY_SUGAR,KEY_CALORIES,KEY_AMOUNTOFFOOD,KEY_DATERECORDED,KEY_TIMERECORDED};
		Cursor c = ourDatabase.query(tablename, columns, null, null, null, null, null);
		String result = "";
		
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4) + " " + c.getString(5) + " " + c.getString(6) + " " + c.getString(7) + " " + c.getString(8) + "\n";
		}
		
		c.close();
		return result;
	}

	public Cursor getDataOfCorrespondingMonth(String monthID, String id) {
		String tablename = "NutritionalFactTable"+id;
		String[] columns = new String[] {KEY_NAMEOFFOOD,KEY_CHOLESTEROL,KEY_CARBOHYDRATES,KEY_TOTALFATS,KEY_SUGAR,KEY_CALORIES,KEY_AMOUNTOFFOOD,KEY_DATERECORDED,KEY_TIMERECORDED};
		Cursor c = ourDatabase.query(tablename, columns, "daterecorded LIKE " + "'"+monthID+"%'", null, null, null, null, null);
		return c;
	}

	public void createCalorieTargetDatabase(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db;
		db = ourDatabase;
		String targetTableName = "calorieTargetTable"+id;
		db.execSQL("CREATE TABLE " + targetTableName + " (" +
				KEY_TARCALORIEIDENTITY + " INTEGER PRIMARY KEY, " +
				KEY_TARGETCALORIE + " TEXT NOT NULL, " +
				KEY_TARGETCALORIEDATE + " TEXT NOT NULL);"
			);
		
	}

	public void createCalorieTarget(String initialdateoftarget,
			String initialtarget, String id) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		String targetTableName = "calorieTargetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		cv.put(KEY_TARCALORIEIDENTITY, l);
		cv.put(KEY_TARGETCALORIE, initialtarget);
		cv.put(KEY_TARGETCALORIEDATE, initialdateoftarget);
		ourDatabase.insert(targetTableName, null, cv);
		
	}

	public void updateTargetCalories(String tarCalories, String tarDate,
			String one, String id) {
		// TODO Auto-generated method stub
		String targetTableName = "calorieTargetTable"+id;
		String won = "1";
		long l = Long.parseLong(won);
		ContentValues cv = new ContentValues();
		cv.put(KEY_TARCALORIEIDENTITY, one);
		cv.put(KEY_TARGETCALORIE, tarCalories);
		cv.put(KEY_TARGETCALORIEDATE, tarDate);
		ourDatabase.update(targetTableName, cv, KEY_TARCALORIEIDENTITY+"="+l, null);
	}

	public String getTargetCalories(String id) {
		// TODO Auto-generated method stub
		String tablename = "calorieTargetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		String[] columns = new String[] {KEY_TARCALORIEIDENTITY, KEY_TARGETCALORIE, KEY_TARGETCALORIEDATE};
		Cursor c = ourDatabase.query(tablename, columns,KEY_TARCALORIEIDENTITY+"="+l, null, null, null, null);
		
		String result = "";
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = c.getString(1);
		}
		c.close();
		return result;
	}

	public String getTargetDateOfCalories(String id) {
		// TODO Auto-generated method stub
		String tablename = "calorieTargetTable"+id;
		String one = "1";
		long l = Long.parseLong(one);
		String[] columns = new String[] {KEY_TARCALORIEIDENTITY, KEY_TARGETCALORIE, KEY_TARGETCALORIEDATE};
		Cursor c = ourDatabase.query(tablename, columns,KEY_TARCALORIEIDENTITY+"="+l, null, null, null, null);
		
		String result = "";
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result = c.getString(2);
		}
		c.close();
		return result;
	}

	public Cursor getDataOfPatientAndReturnAsQuery() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE, columns,null, null, null, null, null);
		return c;
		
	}

	public Cursor getDataOfSpecificPatientAndReturnAsQuery(String idPassed) {
		// TODO Auto-generated method stub
		long l = Long.parseLong(idPassed);
		String[] columns = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_MIDDLENAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD, KEY_FIRSTWEIGHT, KEY_THEFIRSTHEIGHT};
		Cursor c = ourDatabase.query(DATABASE_TABLE_ONE,columns, KEY_ROWID + "=" + l,null, null, null, null);
		return c;
	}
}
