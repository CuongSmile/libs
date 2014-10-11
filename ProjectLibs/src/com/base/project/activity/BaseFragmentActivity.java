package com.base.project.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.project.fragment.BaseFragment;
import com.base.project.listener.IOnBackPressListener;
import com.base.project.util.Constant;
import com.base.project.widget.DialogFragmentLoading;
import com.example.projectlibs.R;

/**
 * @author CuongPG Dec 13, 2013
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

	protected IOnBackPressListener onbackPressListener = null;
	private Object tempObject;
	protected boolean isForeground = true;
	protected DialogFragmentLoading loadingDialog = null;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// config screen
		this.setScreenConfig();
		this.initContent(savedInstanceState);
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	public void registerOnBackPressListener(
			IOnBackPressListener _onbackPressListener) {
		this.unregisterOnBackPressListener();
		this.onbackPressListener = _onbackPressListener;
	}

	public void unregisterOnBackPressListener() {
		this.onbackPressListener = null;
	}

	protected void setScreenConfig() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	public int switchFragment(Class<? extends Fragment> fragment, String tag,
			Bundle pBundle, boolean isAddBackTack) {
		if (isForeground) {
			FragmentManager fragmentManager = this.getSupportFragmentManager();
			/**
			 * old code check fragment have exit in stack by tag if fragment
			 * exit in back stack pop out it from backs tack nad return else
			 * create new instance of fragment and add to back stack
			 * 
			 */
			/*
			 * if (tag != null) { int count =
			 * fragmentManager.getBackStackEntryCount(); for (int i = count - 1;
			 * i >= 0; i--) { BackStackEntry entry = fragmentManager
			 * .getBackStackEntryAt(i); if (tag.equals(entry.getName())) { if
			 * (fragmentManager.popBackStackImmediate(i, 0)) { return
			 * entry.getId(); } } } }
			 */
			/**
			 * new code from 19/02/2014. check fragment have exit in stack by
			 * tag if fragment exit in back stack pop out it from backs tack nad
			 * return else create new instance of fragment and add to back stack
			 */
			if(this.isFragmentCurrentExit(tag)){
			    return Constant.EXIT_FRAGMENT_IN_BACKSTACK;
			}
			boolean isExitInBackStack = fragmentManager.popBackStackImmediate(
					tag, 0);
			if (!isExitInBackStack) {
				Fragment fragmentInstance = null;
				try {
					fragmentInstance = fragment.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (fragmentInstance == null) {
					return Constant.UNKNOW_INSTANCE_FRAGMENT;
				}

				fragmentInstance.setArguments(pBundle);
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.setCustomAnimations(R.anim.slide_right_in,
						R.anim.slide_left_out, R.anim.slide_left_in,
						R.anim.slide_right_out);
				transaction.replace(R.id.container_fragment, fragmentInstance);
				if (isAddBackTack) {
					transaction.addToBackStack(tag);
				}
				return transaction.commit();
			}
			return Constant.EXIT_FRAGMENT_IN_BACKSTACK;
		}
		return Constant.UNKNOW_INSTANCE_FRAGMENT;
	}

	public int switchFragment(Class<? extends BaseFragment> fragment,
			Bundle pBundle, boolean isAddBackTack) {
		if (fragment == null) {
			return Constant.UNKNOW_INSTANCE_FRAGMENT;
		}
		return this.switchFragment(fragment, fragment.getName(), pBundle,
				isAddBackTack);
	}

	public int switchFragment(Class<? extends BaseFragment> fragment,
			Bundle pBundle) {
		return this.switchFragment(fragment, fragment.getName(), pBundle, true);
	}

	public int switchFragment(Class<? extends BaseFragment> fragment) {
		return this.switchFragment(fragment, fragment.getName(), null, true);
	}

	public int switchFragmentWithInstance(Fragment fragmentInstance,
			String tag, Bundle pBundle, boolean isAddBackTack) {
		if (isForeground) {
			FragmentManager fragmentManager = this.getSupportFragmentManager();
			// if (tag != null) {
			// int count = fragmentManager.getBackStackEntryCount();
			// for (int i = count - 1; i >= 0; i--) {
			// BackStackEntry entry = fragmentManager
			// .getBackStackEntryAt(i);
			// if (tag.equals(entry.getName())) {
			// if (fragmentManager.popBackStackImmediate(i, 0)) {
			// return entry.getId();
			// }
			// }
			// }
			// }
			/**
			 * new code from 19/02/2014. check fragment have exit in stack by
			 * tag if fragment exit in back stack pop out it from backs tack nad
			 * return else create new instance of fragment and add to back stack
			 */
			
			if(this.isFragmentCurrentExit(tag)){
			    return Constant.EXIT_FRAGMENT_IN_BACKSTACK;
			}
			
			boolean isExitInBackStack = fragmentManager.popBackStackImmediate(
					tag, 0);
			if (!isExitInBackStack) {
				if (fragmentInstance == null) {
					return Constant.UNKNOW_INSTANCE_FRAGMENT;
				}

				fragmentInstance.setArguments(pBundle);
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.setCustomAnimations(R.anim.slide_right_in,
						R.anim.slide_left_out, R.anim.slide_left_in,
						R.anim.slide_right_out);
				transaction.replace(R.id.container_fragment, fragmentInstance);
				if (isAddBackTack) {
					transaction.addToBackStack(tag);
				}
				return transaction.commit();
			}
			return Constant.EXIT_FRAGMENT_IN_BACKSTACK;
		}
		return Constant.UNKNOW_INSTANCE_FRAGMENT;
	}

	public int switchFragmentWithInstance(Fragment fragmentInstance,
			String tag, Bundle pBundle) {
		return switchFragmentWithInstance(fragmentInstance, tag, pBundle, true);
	}

	public int switchFragmentWithInstance(Fragment fragmentInstance, String tag) {
		return switchFragmentWithInstance(fragmentInstance, tag, null, true);
	}

	public int switchFragmentWithInstance(Fragment fragmentInstance) {
		return switchFragmentWithInstance(fragmentInstance, fragmentInstance
				.getClass().getSimpleName(), null, true);
	}

	@SuppressLint("NewApi")
	public void jumpToFragment(Class<? extends BaseFragment> fragment) {
		if (fragment == null) {
			return;
		}
		getFragmentManager().popBackStack(fragment.getName(),
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	public void overrideEnterAnimation() {
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}

	public void overrideExitAnimation() {
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	@Override
	public void finish() {
		super.finish();
		overrideExitAnimation();
	}

	@Override
	public void onBackPressed() {
		if (this.onbackPressListener != null) {
			if (this.onbackPressListener.onBackPress()) {
				return;
			}
		}
		if(getSupportFragmentManager().getBackStackEntryCount() <= 1){
			finish();
		} else {
		super.onBackPressed();
		}
	}

	public void setTempObject(Object objTemp) {
		this.tempObject = objTemp;
	}

	public <T> T getTempObjectAndClear(Class<T> classTemp) {
		if (this.tempObject != null && classTemp.isInstance(this.tempObject)) {
			return (T) this.getTempObjectAndClear();
		}
		return null;
	}

	public Object getTempObjectAndClear() {
		Object objTemp = null;
		objTemp = this.tempObject;
		this.tempObject = null;
		return objTemp;
	}

	protected abstract Class<? extends BaseFragment> getFragment();

	@Override
	protected void onStart() {
		super.onStart();
		isForeground = true;
	}
	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T findViewByID(int viewID) {
		return (T) findViewById(viewID);
	}
	protected <T extends View> T inflateLayout(int layoutID,ViewGroup group){
        return (T) getLayoutInflater().inflate(layoutID, group);
    }
    
    protected <T extends View> T inflateLayout(int layoutID,ViewGroup group,boolean isAttack){
        return (T) getLayoutInflater().inflate(layoutID, group,isAttack);
    }
    
    public void showDialog(){
        if(loadingDialog == null){
            this.loadingDialog = new DialogFragmentLoading();
        }
        this.loadingDialog.controlShowDialog(this);
    }
    public void dismissDialog(){
        if(this.loadingDialog != null){
            this.loadingDialog.dismissDialog();
        }
    }
    protected void  initContent(Bundle savedInstanceState) {
        //
        // //set content layout
        //
        setContentView(R.layout.layout_screen);
        //
        switchFragment(getFragment(), savedInstanceState, false);
    }
    /**
     *  get current fragment
     * @return
     */
    protected Fragment getCurrentFragment(){
    	Fragment currentFragment = null;
    	 currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
    	return currentFragment;
    }
    /**
     * check screen user want to switch have exits at now
     * @param tag
     * @return true if screen show is screen user want to switch at now
     */
    protected boolean isFragmentCurrentExit(String tag ){
        boolean ret = false;
        int countFragmentInBackStack = this.getSupportFragmentManager().getBackStackEntryCount();
        
        if(countFragmentInBackStack > 0){
            BackStackEntry currentEntry = this.getSupportFragmentManager().getBackStackEntryAt(countFragmentInBackStack - 1); 
            if(currentEntry != null && currentEntry.getName().equals(tag)){
                ret = true;
            }
        }
        return ret;
    }
}
