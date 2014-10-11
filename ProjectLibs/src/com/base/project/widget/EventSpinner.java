/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.base.project.widget.adapter.SpinnerAdapter;
import com.example.projectlibs.R;

/**
 * @author CuongPG
 * May 14, 2014
 */
public class EventSpinner extends RelativeLayout implements   OnItemSelectedListener, OnClickListener{
    /**
     * object show list data
     */
    protected Spinner spinner;
    /**
     * adapter for spinner item
     */
    protected SpinnerAdapter adapter;
    
    protected OnItemSelectedListener listener ;
    
    protected TextView txtContentSpinner;
    
    private int currentSelection = -1;
    
    
    private OnClickListener onClickListener = null;
    
    
    
    public OnClickListener getOnClickListener() {
        return onClickListener;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public OnItemSelectedListener getListener() {
        return listener;
    }
    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }
    public EventSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(attrs);
    }
    public EventSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }
    public EventSpinner(Context context) {
        super(context);
        this.init(null);
    }
    
    /**
     * init view
     * @param attrs`
     */
    protected void init(AttributeSet attrs){
        View.inflate(getContext(), R.layout.layout_event_spinner, this);
        spinner = (Spinner) this.findViewById(R.id.spinerEvent);
        this.txtContentSpinner  = (TextView) this.findViewById(R.id.txtContentSpinner);
        RelativeLayout rl = (RelativeLayout) this.findViewById(R.id.container_spinner);
        rl.setOnClickListener(this);
        //this.txtContentSpinner.setOnClickListener(this);
    }
    
    /**
     *  set adapter for spinner
     */
    public void setAdapterForSpinner(SpinnerAdapter _adapter){
        this.adapter = _adapter;
        this.refreshDataInSpinner();
    }
    
    /**
     * refresh data in spinner
     */
    
    private void refreshDataInSpinner(){
        this.currentSelection = -1;
        if(this.spinner != null && this.adapter != null){
            this.spinner.setAdapter(adapter);
            this.spinner.setOnItemSelectedListener(this);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.currentSelection  = position;
        if(this.listener != null){
            this.updateText(this.listener.onItemSelected(parent, view, position, id));
            
        }
        this.updateText(this.adapter.getItem(position));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if(this.listener != null){
            this.listener.onNothingSelected(parent);
        }
    }
    /**
     * update text for textview show content user select from spinner;
     * @param content
     */
    private void updateText(String content){
        if(this.txtContentSpinner != null){
            this.txtContentSpinner.setText(content);
        }
    }
    @Override
    public void onClick(View v) {
        if(this.spinner != null){
            spinner.performClick();
        }
        if(this.onClickListener != null){
            this.onClickListener.onClick(v);
        }
    }
    
    /**
     * get adapter of spinner
     * @return
     */
    public SpinnerAdapter getAdapter() {
        return adapter;
    }

    public int getCurrentSelection() {
        return currentSelection;
    }
    public void setCurrentSelection(int currentSelection) {
        this.currentSelection = currentSelection;
    }

    public Spinner getSpinner() {
        return spinner;
    }
    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    
    public void setSelection(String content){
        if(content != null && this.adapter.getListData() != null){
            for(String item : this.adapter.getListData()){
                if(item.equals(content)){
                    getSpinner().setSelection(this.adapter.getListData().indexOf(item));
                    break;
                }
            }
        }
    }
    
    public interface OnItemSelectedListener{
        public String onItemSelected(AdapterView<?> parent, View view, int position, long id);
        public void onNothingSelected(AdapterView<?> parent);
    }
}
