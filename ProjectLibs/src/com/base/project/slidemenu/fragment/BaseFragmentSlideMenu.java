package com.base.project.slidemenu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;

import com.base.project.slidemenu.activity.BaseNavigationDrawerActivity;
import com.example.projectlibs.R;

/**
 * @author CuongPG
 * Dec 13, 2013
 */
public abstract class BaseFragmentSlideMenu extends Fragment{
    
    /**
     *  old code
     * 
    protected ViewGroup viewHeader = null;
    protected ViewGroup viewContent = null;
    protected ViewGroup viewFooter = null;
    
    */
    protected ViewGroup viewContainer = null;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater inflater = getBaseActivity().getLayoutInflater();
        if(inflater != null){
            this.viewContainer = (ViewGroup)inflater.inflate(R.layout.layout_container_fragment, null);
            
            /**
             * old code find to view  and add view child to header,content,footer
             
            
//          this.viewHeader = (ViewGroup)this.viewContainer.findViewById(R.id.header);
//          this.viewContent = (ViewGroup)this.viewContainer.findViewById(R.id.content);
//          this.viewFooter = (ViewGroup)this.viewContainer.findViewById(R.id.footer);
            
            // add header to fragment
            
            View header = this.getViewHeader(inflater,this.viewHeader);
            if(header != null){
                this.viewHeader.addView(header);
            }
            
            // add content to fragment
            
            View content = this.getViewContent(inflater,this.viewContent);
            if(content != null){
                this.viewContent.addView(content);
            }
                    
            // add footer to fragment
            
            View footer = this.getViewFooter(inflater,this.viewFooter);
            if(footer != null){
                this.viewFooter.addView(footer);
            }   
            */
        }
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewParent viewParent = this.viewContainer.getParent();
        if(viewParent != null){
            ((ViewGroup)this.viewContainer.getParent()).removeView(this.viewContainer);
        }
        return this.viewContainer;
    }
    /**
     *  old code templte get View child of header,content,footer
     *
    protected abstract View getViewHeader(LayoutInflater inflater, ViewGroup container);
    protected abstract View getViewContent(LayoutInflater inflater, ViewGroup container);
    protected abstract View getViewFooter(LayoutInflater inflater, ViewGroup container);
    */
    
    /**
     * add Header view to screen
     * @param pHeaderView
     * @return
     */
    protected boolean addViewHeader(View pHeaderView,int width,int height){
        boolean ret = false;
        if(pHeaderView != null && viewContainer != null){
            LayoutParams params = new LayoutParams(width, height);
            viewContainer.addView(pHeaderView,0,params);
            ret = true;
        }
        return ret;
    }
    
    /**
     * add Content View to screen
     * @param pContentView
     * @return
     */
    protected boolean addContentView(View pContentView,int width,int height){
        boolean ret = false;
        if(pContentView != null && viewContainer != null){
            LayoutParams params = new LayoutParams(width, height);
            viewContainer.addView(pContentView,1,params);
            ret = true;
        }
        return ret;
        }
    
    /**
     * add Footer view to screen
     * @param pFooterView
     * @return
     */
    protected boolean addFooterView(View pFooterView,int width,int height){
        boolean ret = false;
        if(pFooterView != null && viewContainer != null){
            LayoutParams params = new LayoutParams(width, height);
            viewContainer.addView(pFooterView,2,params);
            ret = true;
        }
        return ret;
    }
    
    protected final BaseNavigationDrawerActivity getBaseActivity(){
        return (BaseNavigationDrawerActivity)getActivity();
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewByID(int viewID){
        return (T)viewContainer.findViewById(viewID);
    }
    @Override
    public void onDestroy() {
        this.viewContainer =null;
        /**
         * old code release view
         * 
        this.viewContent = null;
        this.viewHeader = null;
        this.viewFooter = null;
        */
        super.onDestroy();
}
}
