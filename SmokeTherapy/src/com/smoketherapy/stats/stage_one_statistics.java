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
import com.tweet.HelperMethods.TwitterCallback;
import com.tweet.LoginActivity;

public class stage_one_statistics extends Activity implements OnClickListener{

	//================ Share Popup Window ============================================//
		Button btn_fb_share,btn_google_share,btn_twitter_share,btn_insta_share,btn_cancel_share;
		PopupWindow mPopupWindow;
		AlertDialog mAlertBuilder;
	
	LinearLayout lyt_appback;
	TextView txt_stage_one_stat;
	TextView txt_at_the_begin,txt_avg_cig_num,txt_cig_per_day;
	TextView txt_if_u_follow_stat,txt_therap_num_days,txt_days;
	Button btn_share_stat,btn_back;
	
	//================ Stattistic Data ====================//
	int avg_cigge;
	int therapy_time;
	
	Context context;
	
	String message;
	
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
		
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_one_statistic_screen);
		
		context = stage_one_statistics.this;
			
		txt_stage_one_stat = (TextView)findViewById(R.id.txt_stage_one_stat);
		txt_at_the_begin = (TextView)findViewById(R.id.txt_at_the_begin);
		txt_avg_cig_num = (TextView)findViewById(R.id.txt_avg_cig_num);
		txt_cig_per_day = (TextView)findViewById(R.id.txt_cig_per_day);
		
		txt_if_u_follow_stat = (TextView)findViewById(R.id.txt_if_u_follow_stat);
		txt_therap_num_days = (TextView)findViewById(R.id.txt_therap_num_days);
		txt_days = (TextView)findViewById(R.id.txt_days);
		
		btn_share_stat = (Button)findViewById(R.id.btn_share_stat);
		btn_back = (Button)findViewById(R.id.btn_back);
		
		btn_share_stat.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();
		
		avg_cigge = Application.prefrences.Get_Avg_No_Of_Cigge();
		therapy_time = Application.dbHelper.getTherapyTime(avg_cigge);
		
		txt_avg_cig_num.setText(""+avg_cigge);
		txt_therap_num_days.setText(""+therapy_time);
		
		message = "My avarage no of cigarette is "+avg_cigge+" and in about "+therapy_time+" days of therapy I will enjoy smoke free life.";
	}
	
	
	public void setFontTypeFace()
	{		
		
		txt_stage_one_stat.setTypeface(Application.tf);		
		txt_at_the_begin.setTypeface(Application.tf);
		txt_avg_cig_num.setTypeface(Application.tf);
		txt_cig_per_day.setTypeface(Application.tf);	
		txt_if_u_follow_stat.setTypeface(Application.tf);
		txt_therap_num_days.setTypeface(Application.tf);
		txt_days.setTypeface(Application.tf);
		
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
			//String message = "I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage2()+"% Nicotine in my body using smoke therapy app";		
			break;
		case R.id.btn_back:
			stage_one_statistics.this.finish();
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
		LayoutInflater layoutInflater = (LayoutInflater) stage_one_statistics.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


