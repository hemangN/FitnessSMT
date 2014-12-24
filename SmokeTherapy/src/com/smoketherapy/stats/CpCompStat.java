package com.smoketherapy.stats;

import java.io.ByteArrayOutputStream;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.Facebook;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.smoketherapy.Application;
import com.smoketherapy.R;

public class CpCompStat extends Activity implements OnClickListener {

	LinearLayout lyt_appback, lyt_graph, lyt_share_view;
	TextView txt_complete_stat, txt_result;
	Button btn_share_result, btn_back;
	String result = "";
	int[] margine = { 50, 50, 50, 50 };
	Bitmap bm;
	byte[] byteArray;
	// ===========Vars To COunt Statistics =====================//
	float percentage;
	int day_taken;

	// ============ Facebook Share ==============================//

	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	Session session = Session.getActiveSession();

	// 1 ============== Share Facebook using graph api
	// ===========================//
	static final boolean UPLOAD_IMAGE = false;
	private ProgressDialog progressDialog;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

	private boolean pendingPublishReauthorization = false;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) 
		{
			// onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		uiHelper.onResume();
		lyt_appback = (LinearLayout) findViewById(R.id.lyt_appback);
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_statistics_screen);

		// 2 ============== Facebook Share Dialog Using graph api
		// ==========================//
		// 2 configure the UiLifecycleHelper in onCreate with:
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		// ===========e n d
		// =====================================================//

		txt_complete_stat = (TextView) findViewById(R.id.txt_complete_stat);
		txt_result = (TextView) findViewById(R.id.txt_result);

		btn_share_result = (Button) findViewById(R.id.btn_share_result);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_share_result.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		lyt_graph = (LinearLayout) findViewById(R.id.lyt_graph);
		//lyt_share_view = (LinearLayout) findViewById(R.id.lyt_share_view);

		setFontTypeFace();

		percentage = Application.prefrences.Get_Nicotine_After_Stage4();
		day_taken = Application.prefrences.Get_Days_Taken_Stage4();

		result = "RESULT:"
				+ "\n\n"
				+ String.format("%.2f", percentage)
				+ "% less nicotine in your body from the beginning of the therapy\n\n"
				+ "Smoke TherApp has taken you a total of " + day_taken
				+ " days to complete";

		txt_result.setText(result);

		final GraphicalView gv = createIntent();
		lyt_graph.addView(gv, new LayoutParams(LayoutParams.MATCH_PARENT, 700));

		//============= Crate Bitmap From Graphical View gv ==================================//
		
		/*bm = Bitmap.createBitmap( 1000, 1000, Bitmap.Config.ARGB_8888 );
		Canvas canvas = new Canvas(bm);
		gv.layout(0,0, 600 , 800);
		gv.draw( canvas );*/
		
		/*View v1 = lyt_graph;
		v1.setDrawingCacheEnabled(true);
		v1.buildDrawingCache();
	    bm = v1.getDrawingCache();*/

		//============= E           n          d ==============================================//
		// ===================== Facebook Share
		// ============================================================================================//
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(getApplicationContext(), null,
						statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(getApplicationContext());
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}
		
	}

	// 3 ============ Facebook Share Dialog Using Graph Api calls
	// =============================//
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	// ===========================================================================

	// =========================== Facebook Sharing Function
	// ===========================================================================//

	// *****************************Facebook SHaring Functions
	// *********************
	public class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

		}
	}

	/*
	 * @Override public void onActivityResult(int requestCode, int resultCode,
	 * Intent data) { super.onActivityResult(requestCode, resultCode, data);
	 * Session.getActiveSession().onActivityResult(CompleteStatistics.this,
	 * requestCode, resultCode, data); }
	 */

	@Override
	public void onSaveInstanceState(Bundle outState) {
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
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	public void updateView() {
		// TODO Auto-generated method stub
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			System.out.println("getActiveSession() =====>"
					+ session.getAccessToken());
			
			  publishStory();
			 //publishFeedDialog();
		} else {
			onClickLogin();
		}
	}

	public void onClickLogin() {

		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					Arrays.asList("email")).setCallback(statusCallback));
		} else {
			Session.openActiveSession(CpCompStat.this, true,
					statusCallback);
			updateView();
		}
	}

	public static void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) 
		{
			session.closeAndClearTokenInformation();
		}
	}

	private void publishFeedDialog() 
	{
			
		Bundle params = new Bundle();
		params.putString("name", "Smoke Therapy");
		params.putString("caption",
				"I am using smoke therapy");
		params.putString(
				"description","I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage4()+"% "+"Nicotine in my body using smoke therapy app");
		params.putString("link", "https://developers.facebook.com/android");
		params.putByteArray("picture", byteArray);
		// params.putString("picture",byteArray);

		// Invoke the dialog
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
				CpCompStat.this, Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(getApplicationContext(),
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(
										getApplicationContext()
												.getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(
									getApplicationContext()
											.getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(
									getApplicationContext()
											.getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();
	}

	
	private void publishStory() 
	{
		
		
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
		                Toast.makeText(getApplicationContext()
		                     .getApplicationContext(),
		                     error.getErrorMessage(),
		                     Toast.LENGTH_SHORT).show();
		            }
		            else
		            {
		                    Toast.makeText(getApplicationContext()
		                         .getApplicationContext(), 
		                         postId,
		                         Toast.LENGTH_LONG).show();
		            }
		        }
		    };

		   /* Request request = new Request(session, "me/feed", postParams, 
		                          HttpMethod.POST, callback);*/
		    Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), bm, callback);
		        
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
	
	
	
/*	private void publishStory() 
	{
		// Un-comment the line below to turn on debugging of requests
		//Settings.addLoggingBehavior(LoggingBehavior.REQUESTS);
		
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
		    
		    // Show a progress dialog because the batch request could take a while.
	        progressDialog = ProgressDialog.show(CompleteStatistics.this, "","Please wait", true);
	        
		    try {
				// Create a batch request, firstly to post a new object and
				// secondly to publish the action with the new object's id.
				RequestBatch requestBatch = new RequestBatch();
				
				// Request: Staging image upload request
				// --------------------------------------------
				
				// If uploading an image, set up the first batch request
				// to do this.
				if (UPLOAD_IMAGE) {
					// Set up image upload request parameters
					Bundle imageParams = new Bundle();
					
					imageParams.putParcelable("file", bm);
					
					// Set up the image upload request callback
				    Request.Callback imageCallback = new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							// Log any response error
							FacebookRequestError error = response.getError();
							if (error != null) {
								dismissProgressDialog();
							
							}
						}
				    };
				    
				    // Create the request for the image upload
					Request imageRequest = new Request(Session.getActiveSession(), 
							"me/staging_resources", imageParams, 
			                HttpMethod.POST, imageCallback);
					
					// Set the batch name so you can refer to the result
					// in the follow-on object creation request
					imageRequest.setBatchEntryName("imageUpload");
					
					// Add the request to the batch
					requestBatch.add(imageRequest);
				}
								
				// Request: Object request
				// --------------------------------------------
				
		    	// Set up the JSON representing the book
				JSONObject book = new JSONObject();
				
				// Set up the book image
				if (UPLOAD_IMAGE) {
					// Set the book's image from the "uri" result from 
					// the previous batch request
					book.put("image", "{result=imageUpload:$.uri}");
				} else {
					// Set the book's image from a URL
					book.put("image", 
							"https://furious-mist-4378.herokuapp.com/books/a_game_of_thrones.png");
				}				
				book.put("title", "A Game of Thrones");			
				book.put("url",
						"https://furious-mist-4378.herokuapp.com/books/a_game_of_thrones/");
				book.put("description", 
						"In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.");
				JSONObject data = new JSONObject();
				data.put("isbn", "0-553-57340-3");
				book.put("data", data);
				
				// Set up object request parameters
				Bundle objectParams = new Bundle();
				objectParams.putString("object", book.toString());
				// Set up the object request callback
			    Request.Callback objectCallback = new Request.Callback() {

					@Override
					public void onCompleted(Response response) {
						// Log any response error
						FacebookRequestError error = response.getError();
						if (error != null) {
							dismissProgressDialog();
							
						}
					}
			    };
			    
			    // Create the request for object creation
				Request objectRequest = new Request(Session.getActiveSession(), 
						"me/objects/books.book", objectParams, 
		                HttpMethod.POST, objectCallback);
				
				// Set the batch name so you can refer to the result
				// in the follow-on publish action request
				objectRequest.setBatchEntryName("objectCreate");
				
				// Add the request to the batch
				requestBatch.add(objectRequest);
				
				// Request: Publish action request
				// --------------------------------------------
				Bundle actionParams = new Bundle();
				// Refer to the "id" in the result from the previous batch request
				actionParams.putString("book", "{result=objectCreate:$.id}");
				// Turn on the explicit share flag
				actionParams.putString("fb:explicitly_shared", "true");
				
				// Set up the action request callback
				Request.Callback actionCallback = new Request.Callback() {

					@Override
					public void onCompleted(Response response) {
						dismissProgressDialog();
						FacebookRequestError error = response.getError();
						if (error != null) {
							Toast.makeText(getApplicationContext()
								.getApplicationContext(),
								error.getErrorMessage(),
								Toast.LENGTH_LONG).show();
						} else {
							String actionId = null;
							try {
								JSONObject graphResponse = response
				                .getGraphObject()
				                .getInnerJSONObject();
								actionId = graphResponse.getString("id");
							} catch (JSONException e) {
								
							}
							Toast.makeText(getApplicationContext()
								.getApplicationContext(), 
								actionId+"Post Id",
								Toast.LENGTH_LONG).show();
						}
					}
				};
				
				// Create the publish action request
				Request actionRequest = new Request(Session.getActiveSession(),
						"me/books.reads", actionParams, HttpMethod.POST,
						actionCallback);
				
				// Add the request to the batch
				requestBatch.add(actionRequest);
				
				// Execute the batch request
				requestBatch.executeAsync();
			} catch (JSONException e) {
				
				dismissProgressDialog();
			}
		}
	}*/
	
	/*
	 * Helper method to dismiss the progress dialog.
	 */
	private void dismissProgressDialog() 
	{
		// Dismiss the progress dialog
		if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
	}
	

	// ================ e n d =============================================//

	public GraphicalView createIntent() {
		// String[] titles = new String[] { "Reduced Nicotine Level"};
		List<Float> values = new ArrayList<Float>();
		values.add(Float.valueOf(Application.prefrences
				.Get_Nicotine_After_Stage2()));
		values.add(Float.valueOf(Application.prefrences
				.Get_Nicotine_After_Stage3()));
		values.add(Float.valueOf(Application.prefrences
				.Get_Nicotine_After_Stage4()));

		int[] colors = new int[] { Color.parseColor("#77c4d3") };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		renderer.setOrientation(Orientation.HORIZONTAL);
		setChartSettings(renderer, "Statistics", "Stages",
				" Reduced Nicotine Level in % ", 0, 4, 0, 100, Color.BLACK,
				Color.BLACK);
		renderer.setXLabels(0);
		renderer.setYLabels(10);
		renderer.addXTextLabel(1, "Stage2");
		renderer.addXTextLabel(2, "Stage3");
		renderer.addXTextLabel(3, "Stage4");

		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer seriesRenderer = renderer
					.getSeriesRendererAt(i);
			seriesRenderer.setDisplayChartValues(true);
		}

		final GraphicalView grfv = ChartFactory.getBarChartView(
				CpCompStat.this, buildBarDataset(values), renderer,
				Type.DEFAULT);

		return grfv;
	}

	/*
	 * protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
	 * List<double[]> values) { XYMultipleSeriesDataset dataset = new
	 * XYMultipleSeriesDataset(); int length = titles.length; for (int i = 0; i
	 * < length; i++) { CategorySeries series = new CategorySeries(titles[i]);
	 * double[] v = values.get(i); int seriesLength = v.length; for (int k = 0;
	 * k < seriesLength; k++) { series.add(v[k]); }
	 * dataset.addSeries(series.toXYSeries()); } return dataset; }
	 */

	protected XYMultipleSeriesDataset buildBarDataset(List<Float> values) {
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

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
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
		renderer.setYLabelsColor(0, Color.BLACK);

		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#577A7B"));
		renderer.setInScroll(true);

		int length = colors.length;
		Log.d("Color Len", "" + length);
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			// r.setChartvalueAngle(-90);

			r.setChartValuesSpacing(15);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setMargins(new int[] { 70, 65, 80, 15 }); // T L B R
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	public void setFontTypeFace() {
		txt_complete_stat.setTypeface(Application.tf);
		txt_result.setTypeface(Application.tf);
		btn_share_result.setTypeface(Application.tf);
		btn_back.setTypeface(Application.tf);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_share_result:

			// ============= Convert View In Bitmap
			// ===========================//
			//View v1 = lyt_share_view.getRootView();
			View v1 = lyt_graph;
			v1.setDrawingCacheEnabled(true);
			bm = Bitmap.createBitmap( 600, 800 , Bitmap.Config.ARGB_8888 );
		    bm = v1.getDrawingCache();
		    
		    
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		    byteArray = stream.toByteArray();
			// ============= e n d ===========================

			updateView();
			break;
		case R.id.btn_back:
			CpCompStat.this.finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		default:
			break;
		}
	}
}
