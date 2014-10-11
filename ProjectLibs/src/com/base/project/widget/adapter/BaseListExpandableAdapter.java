package com.base.project.widget.adapter;

import java.util.List;

import android.widget.BaseExpandableListAdapter;

public abstract class BaseListExpandableAdapter<T> extends BaseExpandableListAdapter{
	protected List<T> lstData = null;
	public BaseListExpandableAdapter(List<T> _lstData) {
		this.lstData = _lstData;
	}

}
