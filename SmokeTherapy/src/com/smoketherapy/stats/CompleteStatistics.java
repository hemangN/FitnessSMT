package com.smoketherapy.stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;
import com.tweet.HelperMethods;
import com.tweet.HelperMethods.TwitterCallback;
import com.tweet.LoginActivity;

public class CompleteStatistics extends Activity implements OnClickListener {

	//============ For Fb Share Image=========================//
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

	private boolean pendingPublishReauthorization = false;
	private static List<String> permissions;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	Session session = Session.getActiveSession();
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback()
	{
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) 
		{
			 //onSessionStateChange(session, state, exception);
		}
	};
	
	 private Session.StatusCallback scallback = new Session.StatusCallback() 
	    {
	        @Override
	        public void call(final Session session, final SessionState state, final Exception exception) 
	        {
	            onSessionStateChange(session, state, exception);
	        }
	    };
	//======= End ==========================//
	
	LinearLayout lyt_appback,lyt_graph;
	TextView txt_complete_stat, txt_result;
	Button btn_share_result, btn_back;
	ProgressBar progressbar;
	String result = "";
	
	
	static boolean onShareCallFlag;
	static String graphFilePath;
	static Uri graphFileUri;
	static Bitmap bm;
	
	int[] margine = {50,50,50,50};
	
	String message;
	Context context;
	//===========Vars To COunt Statistics =====================//
			float percentage;
			int day_taken;
	
	// ================ Share Popup Window// ============================================//
	Button btn_fb_share, btn_google_share, btn_twitter_share, btn_insta_share,btn_cancel_share;
	PopupWindow mPopupWindow;
	AlertDialog mAlertBuilder;
	
    //=============== Make Smoke Therapy Directory =====================================================//
			String root = Environment.getExternalStorageDirectory().toString();
			String dirName = "SmokeTherapy";
			String fileName = "graph.jpg";

	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		uiHelper.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_statistics_screen);
		
		//=========== For Fb Share Image ==========================//
		
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		permissions = new ArrayList<String>();
		permissions.add("email");
		
	
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(getApplicationContext(), null,
						callback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(getApplicationContext());
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(callback));
			}
		}
		
		//===========Ends ========================================//
		
		
		context = CompleteStatistics.this;
		//=========== On resumeFalg =========================//
		onShareCallFlag = false;
		
		lyt_graph = (LinearLayout)findViewById(R.id.lyt_graph);

		txt_complete_stat = (TextView) findViewById(R.id.txt_complete_stat);
		txt_result = (TextView) findViewById(R.id.txt_result);

		progressbar = (ProgressBar)findViewById(R.id.progressbar);
		progressbar.setVisibility(View.GONE);
		
		btn_share_result = (Button) findViewById(R.id.btn_share_result);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_share_result.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		
		
		setFontTypeFace();
		
		percentage = Application.prefrences.Get_Nicotine_After_Stage4();
		day_taken    = Application.prefrences.Get_Days_Taken_Stage4();
		
		result = "RESULT:"
				+ "\n\n"
				+String.format("%.2f", percentage)+"% less nicotine in your body from the beginning of the therapy\n\n"
				+ "Smoke TherApp has taken you a total of "+day_taken+" days to complete";
		
		txt_result.setText(result);
		
		final GraphicalView gv =createIntent();
		lyt_graph.addView(gv,new LayoutParams(LayoutParams.MATCH_PARENT,700));
		
		message = "I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage4()+"% Nicotine in my body using smoke therapy app";
	}

	//============= For Fb Share ======================================================//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Session.getActiveSession().addCallback(callback);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Session.getActiveSession().removeCallback(callback);
	}
	
	public void updateView() {
		// TODO Auto-generated method stub
		Session session = Session.getActiveSession();
		if (session.isOpened()) 
		{
			System.out.println("getActiveSession() =====>"+ session.getAccessToken());
			publishStory();
		} 
		else 
		{
			onClickLogin();
		}
	}

	public void onClickLogin() {

		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					Arrays.asList("email")).setCallback(scallback));
		} else {
			Session.openActiveSession(CompleteStatistics.this, true,scallback);
			updateView();
		}
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
		if (session.isOpened()) 
		{
			System.out.println("getActiveSession() =====>"+ session.getAccessToken());
			publishStory();
		} 
    }
	//===================== Ends ======================================================//
	
	
	public GraphicalView createIntent() 
	{
        //String[] titles = new String[] { "Reduced Nicotine Level"};
        List<Float> values = new ArrayList<Float>();
        values.add(Float.valueOf(Application.prefrences.Get_Nicotine_After_Stage2()));
        values.add(Float.valueOf(Application.prefrences.Get_Nicotine_After_Stage3()));
        values.add(Float.valueOf(Application.prefrences.Get_Nicotine_After_Stage4()));
        
 
        int[] colors = new int[] { Color.parseColor("#77c4d3")};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);
        setChartSettings(renderer, "Statistics", "Stages", " Reduced Nicotine Level in % ", 0 ,
            4, 0, 100 , Color.BLACK, Color.BLACK);
        renderer.setXLabels(0);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, "Stage2");
        renderer.addXTextLabel(2, "Stage3");
        renderer.addXTextLabel(3, "Stage4");
      
        
        
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
          SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
          seriesRenderer.setDisplayChartValues(true);
        }
 
 
        final GraphicalView grfv = ChartFactory.getBarChartView(CompleteStatistics.this, buildBarDataset(values), renderer,Type.DEFAULT);
   
         
        return grfv;
      }
	  
	  
	  
	  //	
	protected XYMultipleSeriesDataset buildBarDataset(List<Float> values) 
    {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      
          CategorySeries series = new CategorySeries("Reduced Nicotine Level");
         // Double v = values.get(0);
          int seriesLength = values.size();
          for (int k = 0; k < seriesLength; k++) {
            series.add(values.get(k));
          }
          dataset.addSeries(series.toXYSeries());
       
        return dataset;
    }
	
	//
	
	    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) 
	  {
          XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
          
          renderer.setPanEnabled(false, false);
          renderer.setZoomEnabled(false, false);
          renderer.setMargins(margine);
          
          renderer.setAxisTitleTextSize(16);
          renderer.setChartTitleTextSize(20);
          renderer.setLabelsTextSize(15);
          renderer.setLegendTextSize(15);
          renderer.setBarSpacing(1);
           
          renderer.setMarginsColor(Color.parseColor("#577A7B"));
          renderer.setXLabelsColor(Color.BLACK);
          renderer.setYLabelsColor(0,Color.BLACK);
           
          renderer.setApplyBackgroundColor(true);
          renderer.setBackgroundColor(Color.parseColor("#577A7B"));
          renderer.setInScroll(true);
           
          int length = colors.length;
          Log.d("Color Len",""+length);
          for (int i = 0; i < length; i++) 
          {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[i]);
           // r.setChartvalueAngle(-90);
            
            r.setChartValuesSpacing(15);
            renderer.addSeriesRenderer(r);
          }
          return renderer;
    }
	
	//
	
	    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) 
		{
          renderer.setChartTitle(title);
          renderer.setYLabelsAlign(Align.RIGHT);
          renderer.setXTitle(xTitle);
          renderer.setYTitle(yTitle);
          renderer.setXAxisMin(xMin);
          renderer.setXAxisMax(xMax);
          renderer.setYAxisMin(yMin);
          renderer.setYAxisMax(yMax);
          renderer.setMargins(new int[] { 70, 65 , 80, 15}); //T L B R
          renderer.setAxesColor(axesColor);
          renderer.setLabelsColor(labelsColor);
        }
	
	public void setFontTypeFace()
	{		
		txt_complete_stat.setTypeface(Application.tf);
		txt_result.setTypeface(Application.tf);
		btn_share_result.setTypeface(Application.tf);
		btn_back.setTypeface(Application.tf);	
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_share_result:
			
			if(!onShareCallFlag)
			{
				onShareCallFlag = true;
				View v1 = lyt_graph;
				v1.setDrawingCacheEnabled(true);
				bm = Bitmap.createBitmap( 600, 800 , Bitmap.Config.ARGB_8888 );
			    bm = v1.getDrawingCache();
			    
			    File smokeDir = new File(root + File.separator +dirName);
			    smokeDir.mkdir();
			   
			    File file = new File(smokeDir,fileName);
			    
			    if(file.exists())
			    	file.delete();
			    
			    try 
			    {
					FileOutputStream fOut = new FileOutputStream(file);
					bm.compress(Bitmap.CompressFormat.JPEG, 100 , fOut);
					fOut.flush();
					fOut.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    graphFilePath = file.getAbsolutePath();
			    graphFileUri  = Uri.fromFile(file);
			    StaticData.graphBitmap = bm;
			    StaticData.graphFile   = file;
			    System.out.println("========= Graph File Path =============="+graphFilePath);
			    System.out.println("========= Graph File Uri  =============="+graphFileUri);
			}
			
			
			OpenShareWindow(v);
			break;
		case R.id.btn_back:
			CompleteStatistics.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
			
		case R.id.btn_fb_share:
			
			mPopupWindow.dismiss();
			updateView();
					
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

					HelperMethods.postToTwitterWithImage(context, ((Activity)context),message, new TwitterCallback() {
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
						.setType("image/jpeg")
						.setStream(graphFileUri)
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
			 /*intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
		     
		     Intent shareIntent = new Intent();
		              shareIntent.setAction(Intent.ACTION_SEND);
		              shareIntent.setPackage("com.instagram.android");
		              shareIntent.putExtra(Intent.EXTRA_STREAM, message);
		              shareIntent.setType("image/jpeg");

		              startActivity(shareIntent);*/
				
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("image/*");
				shareIntent.putExtra(Intent.EXTRA_STREAM,graphFileUri);
				shareIntent.putExtra(Intent.EXTRA_TITLE, message);
				shareIntent.setPackage("com.instagram.android");
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
		LayoutInflater layoutInflater = (LayoutInflater) CompleteStatistics.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        
        
        btn_fb_share.setOnClickListener(this);
        btn_google_share.setOnClickListener(this);
        btn_twitter_share.setOnClickListener(this);
        btn_insta_share.setOnClickListener(this);
        btn_cancel_share.setOnClickListener(this);
        
	}
	
	
	//================ Today =======================================================================
	
	private void publishStory() 
	{
		progressbar.setVisibility(View.VISIBLE);
	    Session session = Session.getActiveSession();
	    if (session != null) {

		    // Check for publish permissions    
		    List<String> permissions = session.getPermissions();
		        if (!isSubsetOf(PERMISSIONS, permissions)) 
		        {
		        	pendingPublishReauthorization = true;
		            Session.NewPermissionsRequest newPermissionsRequest = new Session
		                    .NewPermissionsRequest(this, PERMISSIONS);
		            session.requestNewPublishPermissions(newPermissionsRequest);
		            return;
		       }

		   /* Bundle postParams = new Bundle();
		    postParams.putString("name", "Facebook SDK for Android");
		    postParams.putString("caption", "Build great social apps and get more installs.");
		    postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		    postParams.putString("link", "https://developers.facebook.com/android");
		    postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
*/
		    Request.Callback callback= new Request.Callback() {
		        public void onCompleted(Response response) {
		            JSONObject graphResponse = response
		                                       .getGraphObject()
		                                       .getInnerJSONObject();
		            String postId = null;
		            try {
		                postId = graphResponse.getString("id");
		            } catch (JSONException e) {
		               
		            }
		            FacebookRequestError error = response.getError();
		            if (error != null) 
		            {
		            	progressbar.setVisibility(View.GONE);
		                Toast.makeText(getApplicationContext()
		                     .getApplicationContext(),
		                     error.getErrorMessage(),
		                     Toast.LENGTH_SHORT).show();
		            }
		            else
		            {
		            	progressbar.setVisibility(View.GONE);
		            	Toast.makeText(getApplicationContext().getApplicationContext(),"Post Successfully",Toast.LENGTH_LONG).show();
		                //Toast.makeText(getApplicationContext().getApplicationContext(),postId,Toast.LENGTH_LONG).show();
		            }
		        }
		    };

		   /* Request request = new Request(session, "me/feed", postParams, 
		                          HttpMethod.POST, callback);*/
		    Request request = Request.newUploadPhotoRequest(Session.getActiveSession(),bm, callback);
		        
		    Bundle params = request.getParameters();
		    params.putString("name","Smoke Therapy");
		    params.putString("caption","Quite Smoke Using SmokeTherapy");
		    params.putString("message","I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage4()+"% "+"Nicotine in my body using smoke therapy app");
		    params.putString("link", "https://developers.facebook.com/android");
		    
		    RequestAsyncTask task = new RequestAsyncTask(request);
		    task.execute();
		}
	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	//=============== End ==============================================================================
}
