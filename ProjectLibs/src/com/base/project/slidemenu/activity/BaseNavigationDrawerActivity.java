package com.base.project.slidemenu.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.base.project.fragment.BaseFragment;
import com.base.project.listener.IOnBackPressListener;
import com.base.project.slidemenu.fragment.BaseFragmentSlideMenu;
import com.base.project.util.Constant;
import com.example.projectlibs.R;

public abstract class BaseNavigationDrawerActivity extends ActionBarActivity implements OnItemClickListener{
	protected IOnBackPressListener onbackPressListener = null;
	protected DrawerLayout navigationDrawer;
	protected ActionBarDrawerToggle actionBarTooger;
	protected ListView lstItem;
	protected boolean isForeground = true;
	private Object tempObject;
	
	@Override
	protected final void  onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.onCreateFragment(savedInstanceState);
		this.setScreenConfig();
		setContentView(R.layout.layout_main_navigation_drawer);
		
		this.navigationDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		this.lstItem = (ListView)findViewById(R.id.list_item);
		this.lstItem.setOnItemClickListener(this);
//		this.actionBarTooger = getSupportActionBar();
		switchFragment(getFragment(), savedInstanceState, false);
		this.lstItem.setAdapter(this.getAdapterForListItem());
		// create actionbar of apps
		this.onCreateActionBar();
		
	}
	
	protected void onCreateFragment(Bundle savedInstanceState){}
	protected void onCreateActionBar(){}
	
	public void registerOnBackPressListener(
			IOnBackPressListener _onbackPressListener) {
		this.unregisterOnBackPressListener();
		this.onbackPressListener = _onbackPressListener;
	}

	public void unregisterOnBackPressListener() {
		this.onbackPressListener = null;
	}
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		if(actionBarTooger != null){
		actionBarTooger.syncState();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		if(actionBarTooger != null){
		actionBarTooger.onConfigurationChanged(newConfig);
		}
	}
	
	protected void setScreenConfig() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	
	//-------------------------------------------------------------//
	//
	//All method support switch fragment
	//
	
	public int switchFragment(Class<? extends Fragment> fragment, String tag,
			Bundle pBundle, boolean isAddBackTack) {
		if (isForeground) {
				
			FragmentManager fragmentManager = this.getSupportFragmentManager();
			if (tag != null) {
				int count = fragmentManager.getBackStackEntryCount();
				for (int i = count - 1; i >= 0; i--) {
					BackStackEntry entry = fragmentManager
							.getBackStackEntryAt(i);
					if (tag.equals(entry.getName())) {
						if (fragmentManager.popBackStackImmediate(i, 0)) {
							return entry.getId();
						}
					}
				}
			}
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
			transaction.replace(R.id.frame_container, fragmentInstance);
			if (isAddBackTack) {
				transaction.addToBackStack(tag);
			}
			return transaction.commit();
		}
		return Constant.UNKNOW_INSTANCE_FRAGMENT;
	}

	public int switchFragment(Class<? extends BaseFragmentSlideMenu> fragment,
			Bundle pBundle, boolean isAddBackTack) {
		if (fragment == null) {
			return Constant.UNKNOW_INSTANCE_FRAGMENT;
		}
		return this.switchFragment(fragment, fragment.getName(), pBundle,
				isAddBackTack);
	}

	public int switchFragment(Class<? extends BaseFragmentSlideMenu> fragment,
			Bundle pBundle) {
		return this.switchFragment(fragment, fragment.getName(), pBundle, true);
	}

	public int switchFragment(Class<? extends BaseFragmentSlideMenu> fragment) {
		return this.switchFragment(fragment, fragment.getName(), null, true);
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

	
	///-------------------------------------------------------------//
	@Override
	public void finish() {
		super.finish();
		overrideExitAnimation();
	}
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
	
	@Override
	public void onBackPressed() {
		if (this.onbackPressListener != null) {
			if (this.onbackPressListener.onBackPress()) {
				return;
			}
		}
		super.onBackPressed();
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
	
	protected abstract Class<? extends BaseFragmentSlideMenu> getFragment();
	protected abstract BaseAdapter getAdapterForListItem();
	
	
	@SuppressWarnings("unchecked")
    protected <T extends View> T findViewByID(int viewID){
        return (T)this.findViewById(viewID);
    }
	
}
