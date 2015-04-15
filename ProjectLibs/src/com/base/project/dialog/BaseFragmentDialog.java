package com.base.project.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.base.project.activity.BaseFragmentActivity;
import com.base.project.util.ScreenDeviceUIUtil;

public abstract class BaseFragmentDialog extends DialogFragment {
	private View mViewDialog;
	public static final String TAG = "BaseFragmentDialog";

	public void show(BaseFragmentActivity fragmentActivity) {
		super.show(fragmentActivity.getSupportFragmentManager(), TAG);
	}

	private void setConfigDialog() {
		super.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.getDialog().getWindow()
				.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		super.getDialog().setCanceledOnTouchOutside(true);
	}

	/**
	 * overide dialog.cannot dismiss dialog by back key when dialog visible.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// the content
		final RelativeLayout root = new RelativeLayout(getActivity());
		root.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		// creating the fullscreen dialog
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(root);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.setConfigDialog();
		this.mViewDialog = inflater.inflate(this.getViewContentID(), container,
				false);
		// this.setSizeForDialog();
		this.onCreateDialog(super.getDialog(), this.mViewDialog);
		getDialog().setCancelable(false);
		return this.mViewDialog;

	}

	protected void setSizeForDialog() {
		Point point = ScreenDeviceUIUtil.getDisplaySize(getActivity());
		LayoutParams layoutParam = new android.widget.LinearLayout.LayoutParams(
				point.x / 2, point.y / 3);
		if (mViewDialog != null) {
			this.mViewDialog.setLayoutParams(layoutParam);
		}
	}

	protected abstract int getViewContentID();

	protected abstract void onCreateDialog(Dialog pDialog, View pViewDialog);

}
