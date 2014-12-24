package com.smoketherapy.stats;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fb.FbHelper;
import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.tweet.HelperMethods;
import com.tweet.LoginActivity;
import com.tweet.HelperMethods.TwitterCallback;

public class stage_three_statistics extends Activity implements OnClickListener{

	LinearLayout lyt_appback;
	TextView txt_stage_three_stat;
	TextView txt_progress,txt_prog_percent,txt_less_nicotine,txt_stage_has_taken;
	TextView txt_num_days_to_comp,txt_days_to_comp;
	Button btn_share_stat,btn_back;
	
	String message;
	Context context;
	
	//================ Share Popup Window ============================================//
			Button btn_fb_share,btn_google_share,btn_twitter_share,btn_insta_share,btn_cancel_share;
			PopupWindow mPopupWindow;
			AlertDialog mAlertBuilder;
	
	//===========Vars To COunt Statistics =====================//
		float percentage;
		int day_taken;
		
		
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_three_statistics_screen);
		context = stage_three_statistics.this;
		
		txt_stage_three_stat = (TextView)findViewById(R.id.txt_stage_three_stat);
		
		txt_progress = (TextView)findViewById(R.id.txt_progress);
		txt_prog_percent = (TextView)findViewById(R.id.txt_prog_percent);
		txt_less_nicotine = (TextView)findViewById(R.id.txt_less_nicotine);
		txt_stage_has_taken = (TextView)findViewById(R.id.txt_stage_has_taken);
		txt_num_days_to_comp = (TextView)findViewById(R.id.txt_num_days_to_comp);
		txt_days_to_comp = (TextView)findViewById(R.id.txt_days_to_comp);
		
		btn_share_stat = (Button)findViewById(R.id.btn_share_stat);
		btn_back = (Button)findViewById(R.id.btn_back);
		
		btn_share_stat.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();	
		
		percentage = Application.prefrences.Get_Nicotine_After_Stage3();
		day_taken    = Application.prefrences.Get_Days_Taken_Stage3();
		txt_prog_percent.setText(String.format("%.2f", percentage)+"%");
		txt_num_days_to_comp.setText(""+day_taken);
		
		message = "I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage3()+"% Nicotine in my body using smoke therapy app";
	}

	public void setFontTypeFace()
	{		
		
		txt_stage_three_stat.setTypeface(Application.tf);	
		txt_progress.setTypeface(Application.tf);
		txt_prog_percent.setTypeface(Application.tf);
		txt_less_nicotine.setTypeface(Application.tf);
		txt_stage_has_taken.setTypeface(Application.tf);
		txt_num_days_to_comp.setTypeface(Application.tf);
		txt_days_to_comp.setTypeface(Application.tf);
			
		btn_share_stat.setTypeface(Application.tf);
		btn_back.setTypeface(Application.tf);	
	}
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) 
		{
		case R.id.btn_share_stat:
			OpenShareWindow(v);
			break;
		case R.id.btn_back:
			stage_three_statistics.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
			
		case R.id.btn_fb_share:
			
			mPopupWindow.dismiss();
			intent = new Intent(getApplicationContext(), FbHelper.class);
			intent.putExtra("Message", message);
			startActivity(intent);
			
			break;
		
		case R.id.btn_twitter_share:
			if (LoginActivity.isActive(context)) 
			{
				try {

					mAlertBuilder = new AlertDialog.Builder(context).create();
					mAlertBuilder.setCancelable(false);
					mAlertBuilder.setTitle(R.string.please_wait_title);
					View view = getLayoutInflater().inflate(R.layout.view_loading, null);
					((TextView) view.findViewById(R.id.messageTextViewFromLoading)).setText(getString(R.string.posting_tweet_message));
					mAlertBuilder.setView(view);
					mAlertBuilder.show();

					HelperMethods.postToTwitter(context, ((Activity)context),message, new TwitterCallback() {
						@Override
						public void onFinsihed(Boolean response) {
							Log.d("Twitter Post", "----------------response----------------" + response);
							mAlertBuilder.dismiss();
							mPopupWindow.dismiss();
							if(response)
							{
								Toast.makeText(context, getString(R.string.tweet_posted_on_twitter), Toast.LENGTH_SHORT).show();
							}
							else
							{
								Toast.makeText(context, getString(R.string.tweet_posted_error), Toast.LENGTH_SHORT).show();
							}

						}
					});

				} catch (Exception ex) {
					ex.printStackTrace();
					Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				startActivity(new Intent(context, LoginActivity.class));
			}	
			break;
			
		case R.id.btn_google_share:
			
			if(isGooglePlusInstalled())
			{
				
		
				intent = ShareCompat.IntentBuilder.from(this)
						.setType("text/plain")
						.setText(message)
						.getIntent()
						.setPackage("com.google.android.apps.plus");
				startActivity(intent);
			}
			else
			{
				/*new AlertDialog.Builder(stage_one_statistics.this)
				.setTitle("Get Google+")
				.setMessage("Please install Google+ from play store to share")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.show();*/
				intent = new Intent(Intent.ACTION_VIEW);
	              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	              intent.setData(Uri.parse("market://details?id="+"com.google.android.apps.plus"));
	              startActivity(intent);
			}
			
			break;
			
		case R.id.btn_insta_share:
			
			if(isInstagramInstalled() == true)
			{
			 intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
		     
		     Intent shareIntent = new Intent();
		              shareIntent.setAction(Intent.ACTION_SEND);
		              shareIntent.setPackage("com.instagram.android");
		              shareIntent.putExtra(Intent.EXTRA_STREAM, message);
		              shareIntent.setType("image/jpeg");

		              startActivity(shareIntent);
		              
		    }
			else
			{
		     
		     // bring user to the market to download the app.
		              // or let them choose an app?
		              intent = new Intent(Intent.ACTION_VIEW);
		              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		              intent.setData(Uri.parse("market://details?id="+"com.instagram.android"));
		              startActivity(intent);
		    }
			
			break;
			
		case R.id.btn_cancel_share:
			mPopupWindow.dismiss();
			break;
		default:
			break;
		}
	}
	
	
	public boolean isGooglePlusInstalled()
	{
	    try
	    {
	        getPackageManager().getApplicationInfo("com.google.android.apps.plus", 0 );
	        return true;
	    } 
	    catch(PackageManager.NameNotFoundException e)
	    {
	        return false;
	    }
	}
	
	public boolean isInstagramInstalled()
	{
	    try
	    {
	        getPackageManager().getApplicationInfo("com.instagram.android", 0 );
	        return true;
	    } 
	    catch(PackageManager.NameNotFoundException e)
	    {
	        return false;
	    }
	}
	private void OpenShareWindow(View v)
	{
		LayoutInflater layoutInflater = (LayoutInflater) stage_three_statistics.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.share_popup, null);
		mPopupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT,true);
		mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_box));
		mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        btn_fb_share = (Button)popupView.findViewById(R.id.btn_fb_share);
        btn_google_share = (Button)popupView.findViewById(R.id.btn_google_share);
        btn_twitter_share = (Button)popupView.findViewById(R.id.btn_twitter_share);
        btn_insta_share   = (Button)popupView.findViewById(R.id.btn_insta_share);
        btn_cancel_share = (Button)popupView.findViewById(R.id.btn_cancel_share);
        
        btn_insta_share.setVisibility(View.GONE);
        btn_fb_share.setOnClickListener(this);
        btn_google_share.setOnClickListener(this);
        btn_twitter_share.setOnClickListener(this);
        btn_insta_share.setOnClickListener(this);
        btn_cancel_share.setOnClickListener(this);
        
	}
}



