package com.smoketherapy;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.database.SQLException;
import android.graphics.Typeface;

import com.smoketherapy.db.DatabaseHelper;
import com.smoketherapy.db.PersonalInfo;
import com.smoketherapy.staticdata.Prefrences;

@ReportsCrashes (formKey = "dGtVQURyYVJOX3ZQSElnNHZjcDUwZFE6MQ" )
public class Application extends android.app.Application{

	public static DatabaseHelper dbHelper;
	public static Prefrences prefrences;
	public static float dip;
	public static int appDrawableId;
	public static String AppDrawableName;
	public static Typeface tf;
	public static String fontPath = "fonts/gillsans.ttf";
	
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		ACRA.init(this);
		
		dip = (float) (0.48+(+Float.valueOf((getResources().getDisplayMetrics().widthPixels)/Float.valueOf(1000))));

		System.out.println("========== This First Executed ==================");

		dbHelper = new DatabaseHelper(getApplicationContext());
		prefrences = new Prefrences(getApplicationContext());
		
		try {
	          dbHelper.openDataBase();
	    }catch(SQLException sqle){
	          throw sqle;
	    }
		
		
		//Setting App Background
		AppDrawableName = Application.prefrences.Get_App_Background();
		appDrawableId = getResources().getIdentifier(AppDrawableName, "drawable", getPackageName());
		//appDrawable = getResources().getDrawable(id);
		
		
		//Setting App Fonts
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		
	}
	
	
}
