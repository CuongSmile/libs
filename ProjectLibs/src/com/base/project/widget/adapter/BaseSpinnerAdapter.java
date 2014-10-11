/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.widget.adapter;

import java.util.List;

import android.widget.BaseAdapter;

/**
 * @author CuongPG
 * May 12, 2014
 */
public abstract class BaseSpinnerAdapter<T> extends BaseAdapter {
    
    private List<T> listData = null;
    public BaseSpinnerAdapter(List<T> _listData) {
        this.listData = _listData;
    }

    @Override
    public int getCount() {
        return (this.listData == null? 0 : this.listData.size());
    }
    @Override
    public T getItem(int position) {
        return (this.listData == null ? null : ((position < 0 || position >= this.listData.size()) ? null : this.listData
                .get(position)));
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
        this.notifyDataSetChanged();
    }

}
