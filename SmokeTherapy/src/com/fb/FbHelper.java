package com.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

 
public class FbHelper extends Activity
{
	String message;
	static String shareType;
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

	private boolean pendingPublishReauthorization = false;

	
	//============== For Testing post images ========================================================//
	private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback_uiHelper = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) 
        {
            onSessionStateChange(session, state, exception);
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_login_screen);
		
		Intent intent = getIntent();
		message = intent.getStringExtra("Message");
		shareType = intent.getStringExtra("ShareType");
		
		// 2 configure the UiLifecycleHelper in onCreate with:
		uiHelper = new UiLifecycleHelper(FbHelper.this, callback_uiHelper);
        uiHelper.onCreate(savedInstanceState);
        
        
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) 
		{
			onSessionStateChange(session, session.getState(), null);
		}
		else
		{
			onClickLogin();
		}
		
	}
	
	
	public  void onClickLogin()
	{
		openActiveSession(FbHelper.this, true, Arrays.asList("email", "user_birthday", "user_hometown", "user_location"), new Session.StatusCallback() 
		{
			@Override
			public void call(Session session, SessionState state, Exception exception) 
			{
				if (exception != null) 
				{
					Log.d("Facebook", exception.getMessage());
				}
				
				if(state.isOpened())
				{
					Log.d("Facebook", "Session State: " + session.getState());
					System.out.println("=========== Share Type in call back========================="+shareType);
					if(shareType != null)
					{
						publishStory();	
					}
					else
					{	
						publishFeedDialog();
					}
				}
				if(state.isClosed())
				{
					Log.d("Facebook", "Session State: " + session.getState());
				}
				
			}
		});
	}

	private  Session openActiveSession(Activity activity,boolean allowLoginUI, List permissions, StatusCallback callback) 
	{
		OpenRequest openRequest = new OpenRequest(activity).setPermissions(permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) 
		{
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
        if (state.isOpened()) 
        {
        	if(shareType != null)
			{
				publishStory();	
			}
			else
			{	
				publishFeedDialog();
			}
        }
        else if (state.isClosed()) 
        {
        	onClickLogin(); 
        }
    }

	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	Session.getActiveSession().onActivityResult(FbHelper.this, requestCode, resultCode, data);
    	uiHelper.onActivityResult(requestCode, resultCode, data);
    }
    
    
	private void publishFeedDialog() 
	{
			
		Bundle params = new Bundle();
		params.putString("name", "Smoke Therapy");
		params.putString("caption",
				"I am using smoke therapy");
		params.putString(
				"description",message);
		params.putString("link", "https://developers.facebook.com/android");
		

		// Invoke the dialog
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(FbHelper.this, Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,FacebookException error) 
					{
						if (error == null) 
						{
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								
								Toast.makeText(getApplicationContext(), "Post Successfully",Toast.LENGTH_SHORT).show();
								/*Toast.makeText(getApplicationContext(),
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();*/
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
						
						FbHelper.this.finish();
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
		    Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), StaticData.graphBitmap, callback);
		        
		    Bundle params = request.getParameters();
		    params.putString("name","Smoke Therapy");
		    params.putString("caption","Quite Smoke Using SmokeTherapy");
		    params.putString("message","I have Reduced "+Application.prefrences.Get_Nicotine_After_Stage4()+"% "+"Nicotine in my body using smoke therapy app");
		    params.putString("link", "https://developers.facebook.com/android");
		    
		    RequestAsyncTask task = new RequestAsyncTask(request);
		    task.execute();
		}
	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) 
	{
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	System.out.println("=================On Pause Called ============================");
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	System.out.println("================ On Resume Called ========================");
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	System.out.println("============== On Stop Called==============================");
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	System.out.println("==============On Destroy Called ============================");
    }
    
    
}
