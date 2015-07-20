package com.cettco.coreutils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cettco.coreutils.R;

public class DockView extends LinearLayout implements View.OnClickListener {


    private View homeView;
    private View liveView;
    private int currentIndex=0;
    private DockViewListener mDockViewListener;
    public DockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //TODO(cettco)
        //LayoutInflater.from(context).inflate(R.layout.xxx,this);
        initViews();
    }
    public enum DockViewType{
        Home(1),
        Live(2),;
        private int dockViewType;

        DockViewType(int dockViewType) {
            this.dockViewType = dockViewType;
        }
    }
    public interface DockViewListener{
        public void dockViewSelected(int index);
    }

    /**
     * TODO(cettco)
     */
    private void initViews(){
        homeView = findViewById(R.id.xxx);
        homeView.setOnClickListener(this);
        liveView = findViewById(R.id.xxx);
        liveView.setOnClickListener(this);
    }
    public void setmDockViewListener(DockViewListener dockViewListener){
        this.mDockViewListener = dockViewListener;
    }
    public int getCurrentIndex(){
        return currentIndex;
    }
    @Override
    public void onClick(View view) {
        int index = Integer.parseInt(String.valueOf(view.getTag()));
        if(mDockViewListener!=null){
            mDockViewListener.dockViewSelected(index);
        }
        setCurrentTabIndex(index);
    }

    /**
     * TODO(cettco)
     * Mainly for setting the tab background
     * @param index
     */
    public void setCurrentTabIndex(int index){
        currentIndex = index;
        if(index==DockViewType.Home.dockViewType){

        }else{

        }
    }
}