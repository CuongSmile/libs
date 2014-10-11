package com.base.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.base.project.activity.BaseFragmentActivity;
import com.base.project.util.Constant;
import com.example.projectlibs.R;

/**
 * @author CuongPG Dec 13, 2013
 */
public abstract class BaseFragment extends Fragment {

    // /**
    // * old code
    // *
    protected View viewHeader = null;
    protected ViewGroup viewContent = null;
    protected View viewFooter = null;
    // */sa
    protected ViewGroup viewContainer = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getBaseActivity().getLayoutInflater();
        if (inflater != null) {
            this.viewContainer = (ViewGroup) inflater.inflate(R.layout.layout_container_fragment, null);

            /**
             * old code find to view and add view child to header,content,footer
             * 
             * 
             * // this.viewHeader = (ViewGroup)this.viewContainer.findViewById(R.id.header); //
             * this.viewContent = (ViewGroup)this.viewContainer.findViewById(R.id.content); //
             * this.viewFooter = (ViewGroup)this.viewContainer.findViewById(R.id.footer);
             * 
             * // add header to fragment
             * 
             * View header = this.getViewHeader(inflater,this.viewHeader); if(header != null){
             * this.viewHeader.addView(header); }
             * 
             * // add content to fragment
             * 
             * View content = this.getViewContent(inflater,this.viewContent); if(content != null){
             * this.viewContent.addView(content); }
             * 
             * // add footer to fragment
             * 
             * View footer = this.getViewFooter(inflater,this.viewFooter); if(footer != null){
             * this.viewFooter.addView(footer); }
             */

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewParent viewParent = this.viewContainer.getParent();
        if (viewParent != null) {
            ((ViewGroup) this.viewContainer.getParent()).removeView(this.viewContainer);
        }
        return this.viewContainer;
    }

    /**
     * old code templte get View child of header,content,footer
     * 
     * protected abstract View getViewHeader(LayoutInflater inflater, ViewGroup container);
     * protected abstract View getViewContent(LayoutInflater inflater, ViewGroup container);
     * protected abstract View getViewFooter(LayoutInflater inflater, ViewGroup container);
     */

    /**
     * add Header view to screen
     * 
     * @param pHeaderView
     * @return
     */
    protected boolean addViewHeader(int pHeaderviewID, int width, int height) {
        boolean ret = false;
        if (pHeaderviewID != Constant.UNKNOW_RESOURCE && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            this.viewHeader = this.inflateLayout(pHeaderviewID, null);
            viewContainer.addView(this.viewHeader, params);
            ret = true;
        }
        return ret;
    }

    /**
     * add Content View to screen
     * 
     * @param pContentView
     * @return
     */
    protected boolean addContentView(int pContentviewID, int width, int height) {
        boolean ret = false;
        if (pContentviewID != Constant.UNKNOW_RESOURCE && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            // params.addRule(RelativeLayout.CENTER_IN_PARENT);
            this.viewContent = this.inflateLayout(pContentviewID, null);
            if (this.viewHeader != null) {
                params.addRule(RelativeLayout.BELOW, this.viewHeader.getId());
            }
            if (this.viewFooter != null) {
                params.addRule(RelativeLayout.ABOVE, this.viewFooter.getId());
            }
            // viewContainer.addView(this.inflateLayout(pContentviewID, null),params);
            this.viewContainer.addView(this.viewContent, params);
            ret = true;
        }
        return ret;
    }

    /**
     * add Footer view to screen
     * 
     * @param pFooterView
     * @return
     */
    protected boolean addFooterView(int pFooterviewID, int width, int height) {
        boolean ret = false;
        if (pFooterviewID != Constant.UNKNOW_RESOURCE && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.viewFooter = this.inflateLayout(pFooterviewID, null);
            this.viewContainer.addView(viewFooter, params);
            // viewContainer.addView(this.inflateLayout(pFooterviewID, null),params);
            ret = true;
        }
        return ret;
    }

    protected void setContentView(int resourceID) {
        if (resourceID != Constant.UNKNOW_RESOURCE) {
            this.addContentView(resourceID, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
    }

    /**
     * add Header view to screen
     * 
     * @param pHeaderView
     * @return
     */
    protected boolean addViewHeader(View pHeaderView, int width, int height) {
        boolean ret = false;
        if (pHeaderView != null && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            this.viewHeader = pHeaderView;
            viewContainer.addView(pHeaderView, params);
            ret = true;
        }
        return ret;
    }

    /**
     * add Content View to screen
     * 
     * @param pContentView
     * @return
     */
    protected boolean addContentView(View pHeaderView, int width, int height) {
        boolean ret = false;
        if (pHeaderView != null && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewContainer.addView(pHeaderView, params);
            ret = true;
        }
        return ret;
    }

    /**
     * add Footer view to screen
     * 
     * @param pFooterView
     * @return
     */
    protected boolean addFooterView(View pFooterView, int width, int height) {
        boolean ret = false;
        if (pFooterView != null && viewContainer != null) {
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.viewFooter = pFooterView;
            viewContainer.addView(pFooterView, params);
            ret = true;
        }
        return ret;
    }

    protected void setContentView(View pContentView) {
        if (pContentView != null) {
            this.addContentView(pContentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
    }

    protected final BaseFragmentActivity getBaseActivity() {
        return (BaseFragmentActivity) getActivity();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewByID(int viewID) {
        return (T) viewContainer.findViewById(viewID);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T inflateLayout(int layoutID, ViewGroup group) {
        return (T) getBaseActivity().getLayoutInflater().inflate(layoutID, group);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T inflateLayout(int layoutID, ViewGroup group, boolean isAttack) {
        return (T) getBaseActivity().getLayoutInflater().inflate(layoutID, group, isAttack);
    }
    /**
     * method find view id of a view if view is null or you want re-find view id when view is not null.
     * @param viewWantInit : view,that you want find view id
     * @param viewID : id of resource that you want find view id for view.
     * @param isCheckExit :  true if you want re-find view id of a view isn't null,false if otherwise 
     */
    protected <T extends View> T findViewIDWithCheckExits(T viewWantInit, int viewID, boolean isCheckExits) {
        if (!isCheckExits || viewWantInit == null) {
            viewWantInit = this.findViewByID(viewID);
        }
        return viewWantInit;
    }

    @Override
    public void onDestroy() {
        this.viewContent = null;
        this.viewHeader = null;
        this.viewFooter = null;
        this.viewContainer = null;
        /**
         * old code release view
         * 
         * this.viewContent = null; this.viewHeader = null; this.viewFooter = null;
         */
        super.onDestroy();
    }


}
