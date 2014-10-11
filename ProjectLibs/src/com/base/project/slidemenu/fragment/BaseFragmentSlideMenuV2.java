package com.base.project.slidemenu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.project.listener.IOnBackPressListener;

public abstract class BaseFragmentSlideMenuV2 extends BaseFragmentSlideMenu implements IOnBackPressListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//register handle back press from activity to fragment
		getBaseActivity().registerOnBackPressListener(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * if method  return true cannot call back of system,else otherwise
	 */
	@Override
	public boolean onBackPress() {
		return false;
	}
	
	// Methods switch fragment 
	protected int switchFragment(Class<? extends BaseFragmentSlideMenu> fragment){
		return getBaseActivity().switchFragment(fragment);
	}
	
	protected int  switchFragment(Class<? extends BaseFragmentSlideMenu> fragment,Bundle pBundle){
		return getBaseActivity().switchFragment(fragment, pBundle);
	}
	
	protected  int  switchFragment(Class<? extends BaseFragmentSlideMenu> fragment,Bundle pBundle,boolean isReplace){
			return getBaseActivity().switchFragment(fragment, pBundle, isReplace);
	}
	
	protected int switchFragment(Class<? extends BaseFragmentSlideMenu> fragment, String tag, Bundle pBundle,boolean isReplace){
		return getBaseActivity().switchFragment(fragment, tag, pBundle, isReplace);
	}
	
	
	@Override
	public void onDestroyView() {
		//unregister handle back press from activity to fragment
		getBaseActivity().unregisterOnBackPressListener();
		super.onDestroyView();
	}
	
}
