package com.base.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.project.listener.IOnBackPressListener;

public abstract class BaseFragment2 extends BaseFragment implements IOnBackPressListener{
	
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
	protected int switchFragment(Class<? extends BaseFragment> fragment){
		return getBaseActivity().switchFragment(fragment);
	}
	
	protected int  switchFragment(Class<? extends BaseFragment> fragment,Bundle pBundle){
		return getBaseActivity().switchFragment(fragment, pBundle);
	}
	
	protected  int  switchFragment(Class<? extends BaseFragment> fragment,Bundle pBundle,boolean isReplace){
			return getBaseActivity().switchFragment(fragment, pBundle, isReplace);
	}
	
	protected int switchFragment(Class<? extends BaseFragment> fragment, String tag, Bundle pBundle,boolean isReplace){
		return getBaseActivity().switchFragment(fragment, tag, pBundle, isReplace);
	}
	
	
	@Override
	public void onDestroyView() {
		//unregister handle back press from activity to fragment
		getBaseActivity().unregisterOnBackPressListener();
		super.onDestroyView();
	}
}
