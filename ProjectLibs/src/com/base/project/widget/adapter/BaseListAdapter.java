package com.base.project.widget.adapter;

import java.util.List;

public abstract class BaseListAdapter<T> {

	protected List<T> lstData = null;
	public BaseListAdapter(List<T> _lstData) {
		this.lstData =  _lstData;
	}
}
