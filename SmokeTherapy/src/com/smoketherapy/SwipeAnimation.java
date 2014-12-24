package com.smoketherapy;

import static com.nineoldandroids.view.ViewHelper.setTranslationX;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeAnimation implements View.OnTouchListener
{

    // Transient properties
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;
    private boolean mSwipingLeft;
    private boolean mSwipingRight;
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private VelocityTracker mVelocityTracker;
    private View mDownView;
    private long mAnimationTime;
    private int minSwipeDistance;
	
	

	 public SwipeAnimation(View v, int minDistance) {
	        ViewConfiguration vc = ViewConfiguration.get(v.getContext());
	        mSlop = vc.getScaledTouchSlop();
	        minSwipeDistance = minDistance;
	        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
	        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
	        mAnimationTime = v.getContext().getResources().getInteger(
	                android.R.integer.config_shortAnimTime);
	    }
	 
	 @Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			// TODO Auto-generated method stub
		 
		 switch (motionEvent.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			mDownView = view;
			
			if (mDownView != null) 
            {
				mSwiping = false;
				mSwipingRight = false;
				mSwipingLeft  = false;
				
                mDownX = motionEvent.getRawX();
                mDownY = motionEvent.getRawY();
                
                Log.d("..........................","Down X................................");
                Log.d("mDownX","............................"+mDownX);
                Log.d("mDownY","........................"+mDownY);
                
                mVelocityTracker = VelocityTracker.obtain();
                mVelocityTracker.addMovement(motionEvent);
               
            }
           
			break;
			
		case MotionEvent.ACTION_CANCEL:
			
			  animate(mDownView)
              .translationX(0)
              .alpha(1)
              .setDuration(mAnimationTime)
              .setListener(null);
			
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			mVelocityTracker.addMovement(motionEvent);
            float deltaX = motionEvent.getRawX() - mDownX;
            float deltaY = motionEvent.getRawY() - mDownY;
            Log.d("Action Move Stars","..................MOve Action............");
            Log.d("delta X",    "  "+deltaX);
            Log.d("delta y",   "  "+Math.abs(deltaY));
            Log.d("Slop  ",""+mSlop);
            Log.d("Action Move End","..................MOve Action............");
            
            if(deltaX > minSwipeDistance){
            	 mSwipingRight=true;
            }
            if(deltaX<0){
            	mSwipingLeft=true;
            }
               
            if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                mSwiping = true;
            }
            
            /*
              * if mSwiping is true then perform animation to list item to be half transparent    
              */
            if (mSwiping) 
            {
               setTranslationX(mDownView, deltaX);
               /*setAlpha(mDownView, Math.max(0f, Math.min(1f,
                        1f - 2f * Math.abs(deltaX))));*/
                return true;
            }
			
			break;
			
		case MotionEvent.ACTION_UP:
			  animate(mDownView)
              .translationX(0)
              .alpha(1)
              .setDuration(mAnimationTime)
              .setListener(null);
			  
			  if(mSwipingRight)
			  {
				  mSwiping = false;
				  mSwipingRight = false;
				  mSwipingLeft  = false;
				  onSwipeRight();
				 // mDownView.setVisibility(View.GONE);
			  }
			  
			break;

		default:
			break;
		}
			return false;
		}
	
	
		public void onSwipeRight() {
		}
}
