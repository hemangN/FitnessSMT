package com.smoketherapy;

import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LongPress_Detector implements View.OnTouchListener{

	//private Timer longpressTimer; //won't depend on a motion event to fire
    private final int longpressTimeDownBegin = 1000; //0.5 s
    private Point previousPoint;
    
    Handler mHandler;
    
    public LongPress_Detector() {
		// TODO Auto-generated constructor stub
    	
    	mHandler = new Handler();
	}
    
	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		switch (motionEvent.getAction()) 
		{
		case MotionEvent.ACTION_DOWN:
			Log.d("====== Down =========","y  y  y ");
			
			//====to Set back ground ===================//
			view.setBackgroundResource(R.drawable.stage_back_click);
			
			previousPoint = new Point((int)motionEvent.getX(), (int)motionEvent.getY());
			
			Log.d("previous Point In Action Down",""+previousPoint);
		/*	longpressTimer = new Timer();
	        longpressTimer.schedule(new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					onLongPressConfirm();
				}
	            //whatever happens on a longpress
	        }, longpressTimeDownBegin);*/
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					onLongPressConfirm();
				}
			},longpressTimeDownBegin );
	        
	        return true; //the parent was also handling long clicks
	
	
		case MotionEvent.ACTION_MOVE:
			view.setBackgroundResource(R.drawable.stage_back_click);
		    Point currentPoint = new Point((int)motionEvent.getX(), (int)motionEvent.getY());
            
		    Log.d("previous In Action Down",""+previousPoint);
		    Log.d("currentPoint In Action Down",""+currentPoint);
	        if(previousPoint == null)
	        {
	            previousPoint = currentPoint;
	        }
	        
	        int dx = Math.abs(currentPoint.x - previousPoint.x);
	        int dy = Math.abs(currentPoint.y - previousPoint.y);
	        int s = (int) Math.sqrt(dx*dx + dy*dy);
	        boolean isActuallyMoving = s >= 30; //we're moving

	        
	        Log.d("value of s in Move",""+s);
	        Log.d("is Actual Moving",""+isActuallyMoving);
	        
	        if(isActuallyMoving){ //only restart timer over if we're actually moving (threshold needed because everyone's finger shakes a little)
	            mHandler.removeCallbacksAndMessages(null);
	            return false; //didn't trigger long press (will be treated as scroll)
	        }
	        else{ //finger shaking a little, so continue to wait for possible long press
	            return true; //still waiting for potential long press
	        }
			
			
		default:
            mHandler.removeCallbacksAndMessages(null);
            view.setBackgroundResource(R.drawable.stage_back);
			break;
		}
		return false;
	}
	
	public void onLongPressConfirm(){};

}
