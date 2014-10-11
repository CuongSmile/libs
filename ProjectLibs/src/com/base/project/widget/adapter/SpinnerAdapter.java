/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.widget.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectlibs.R;

/**
 * @author CuongPG
 * May 14, 2014
 * 
 * /**
 * setup with this.adapterSpiner = new SpinnerAdapter(spinerData, R.layout.layout_spinner_item,
                R.layout.layout_item_spinner_dropdown);
 * @author CuongPG
 * Jun 6, 2014
 */
public class SpinnerAdapter extends BaseSpinnerAdapter<String> {
	private int resItem;
	private int resItemDropdown;
    public SpinnerAdapter(List<String> _listData, int resItem, int resItemDropdown) {
        super(_listData);
        this.resItem = resItem;
        this.resItemDropdown = resItemDropdown;
    }


    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null ){
            //convertView = View.inflate(parent.getContext(), R.layout.layout_item_spinner_dropdown, null);
            convertView = View.inflate(parent.getContext(), resItemDropdown, null);
            holder = new Holder();
            holder.txtContent = (TextView)convertView.findViewById(R.id.txtContent);
            convertView.setTag(holder);
        }
        else {
            Object objTemp = convertView.getTag();
            if(objTemp != null && objTemp instanceof Holder){
                holder =  (Holder)objTemp;
            }
        }
        holder.txtContent.setText(this.getItem(position));
        holder.txtContent.setMaxWidth(0);
        return convertView;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            
            //convertView= View.inflate(parent.getContext(), R.layout.layout_spinner_item , null);
            convertView= View.inflate(parent.getContext(),resItem , null);
            holder = new Holder();
            
            holder.txtContent= (TextView) convertView.findViewById(R.id.txtContent);
            
            convertView.setTag(holder);
        } else {
            Object objTemp = convertView.getTag();
            if(objTemp != null && objTemp instanceof Holder){
                holder =  (Holder)objTemp;
            }
        }
        if(holder != null && holder.txtContent != null){
            holder.txtContent.setText(this.getItem(position));
            holder.txtContent.setMaxWidth(0);
        }
       
        return convertView;
    }
    
    
    static class Holder {
        TextView txtContent;
    }
}
