/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tweet;

import java.util.Calendar;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

public class HelperMethods {
	private static final String TAG = "HelperMethods";
	
	public HelperMethods() {}

	public static void postToTwitter(Context context, final Activity callingActivity, final String message, final TwitterCallback postResponse){
		if(!LoginActivity.isActive(context)){
			postResponse.onFinsihed(false);
			return;
		}

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(context.getResources().getString(R.string.twitter_consumer_key));
		configurationBuilder.setOAuthConsumerSecret(context.getResources().getString(R.string.twitter_consumer_secret));
		configurationBuilder.setOAuthAccessToken(LoginActivity.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(LoginActivity.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();

		new Thread(new Runnable() {


			@Override
			public void run() {
				boolean success = true;
				try {
					Calendar cal = Calendar.getInstance();
					
					twitter.updateStatus(message+ "\n@"+cal.getTime().toString());
				} catch (TwitterException e) {
					e.printStackTrace();
					success = false;
				}

				final boolean finalSuccess = success;

				callingActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						postResponse.onFinsihed(finalSuccess);
					}
				});

			}
		}).start();
	}


	public static void postToTwitterWithImage(Context context, final Activity callingActivity,final String message, final TwitterCallback postResponse){
		if(!LoginActivity.isActive(context)){
			postResponse.onFinsihed(false);
			return;
		}

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(context.getResources().getString(R.string.twitter_consumer_key));
		configurationBuilder.setOAuthConsumerSecret(context.getResources().getString(R.string.twitter_consumer_secret));
		configurationBuilder.setOAuthAccessToken(LoginActivity.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(LoginActivity.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();

		//final File file = new File(imageUrl);

		new Thread(new Runnable() {


			@Override
			public void run() {
				boolean success = true;
				try {
					
					if (StaticData.graphFile.exists()) 
					{
						Calendar cal = Calendar.getInstance();
						
						StatusUpdate status = new StatusUpdate(message+ "\n@"+cal.getTime().toString());
						status.setMedia(StaticData.graphFile);
						twitter.updateStatus(status);
					}else{
						Log.d(TAG, "----- Invalid File ----------");
						success = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					success = false;
				}



				final boolean finalSuccess = success;

				callingActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						postResponse.onFinsihed(finalSuccess);
					}
				});

			}
		}).start();
	}

	public static abstract class TwitterCallback{
		public abstract void onFinsihed(Boolean success);
	}
}
