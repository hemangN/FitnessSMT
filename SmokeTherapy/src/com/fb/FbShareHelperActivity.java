

//====================  Original =================================================================//

package com.fb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class FbShareHelperActivity extends Activity{

	 private static List<String> permissions;
	 private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	 Session.StatusCallback statusCallback = new SessionStatusCallback();
	 Session session = Session.getActiveSession();
	 private UiLifecycleHelper uiHelper;
	 
	 private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

	 private boolean pendingPublishReauthorization = false;
	 
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.fb_login_screen);
			
			uiHelper = new UiLifecycleHelper(this, statusCallback);
			uiHelper.onCreate(savedInstanceState);
			
			permissions = new ArrayList<String>();
			permissions.add("email");
			
			
			
			
		if (session == null) 
		{
			if (savedInstanceState != null) 
			{
				session = Session.restoreSession(getApplicationContext(), null,
						statusCallback, savedInstanceState);
			}
			if (session == null) 
			{
				session = new Session(getApplicationContext());
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) 
			{
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}
			
			
			session = Session.getActiveSession();
			if(!session.isOpened()) 
			{
				System.out.println("========= IN oncreate Open FOr Read ===================");
				session.openForRead(new Session.OpenRequest(FbShareHelperActivity.this).setCallback(statusCallback).setPermissions(permissions));
			}
			else
			{
					
				System.out.println("========= IN oncreate Open FOr Active ===================");
				Session.openActiveSession(FbShareHelperActivity.this, true, statusCallback);
			
			}
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			super.onActivityResult(requestCode, resultCode, data);
			uiHelper.onActivityResult(requestCode, resultCode, data);
			Log.d("FbLogin", "Result Code is - " + resultCode +"");
			//Session.getActiveSession().onActivityResult(FbShareHelperActivity.this, requestCode, resultCode, data);
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
		protected void onStart() 
		{
		// TODO Add status callback
			super.onStart();
			System.out.println("======= On Start Active Session called =============");
			Session.getActiveSession().addCallback(statusCallback);
		}
		@Override
		protected void onStop() 
		{
			// TODO Remove callback
			super.onStop();
			System.out.println("======= On Stop Active Session called =============");
			Session.getActiveSession().removeCallback(statusCallback);
		}
		private class SessionStatusCallback implements Session.StatusCallback
		{
			 
			@Override
			public void call(Session session, SessionState state, Exception exception) 
			{
			//Check if Session is Opened or not
			//processSessionStatus(session, state, exception);
				System.out.println("===== Status Call Back On Session State Changes Called =========");
				FbShareHelperActivity.this.finish();
				//onSessionStateChange(session, state, exception);
			}
		}
		
		private void onSessionStateChange(Session session, SessionState state, Exception exception) 
		{
			
			
	        if (session.isOpened()) 
	        {        	
		       publishStory();	
		        		        	
	        }
	        
			
	        
	    }
		
		private void publishStory() 
		{
		    Session session = Session.getActiveSession();
		    if (session != null) 
		    {

			    // Check for publish permissions    
			    List<String> permissions = session.getPermissions();
			        if (!isSubsetOf(PERMISSIONS, permissions)) 
			        {
			        	
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
}
