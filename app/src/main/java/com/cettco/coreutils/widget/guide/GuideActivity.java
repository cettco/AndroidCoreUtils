package com.cettco.coreutils.widget.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cettco.coreutils.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {
    private ViewPager pager;
    private CustomerIndicator indicator;
    private List<View> list;
    private GuidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.guide_pager);
        indicator = (CustomerIndicator) findViewById(R.id.guide_indicator);
        list = new ArrayList<View>();
        // TODO(cettco)
        addAdapterView(R.drawable.guide1, false, null);
        addAdapterView(R.drawable.guide2, false, null);
        addAdapterView(R.drawable.guide3, true, null);
        pagerAdapter = new GuidePagerAdapter(list);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(pageChangeListener);

        // Set indicator properties.
        indicator.setCount(list.size());
        indicator.show();
        indicator.setPosition(0);
    }

    private void addAdapterView(int resId, boolean isShowBtn,
            View.OnClickListener listener) {
        View view = View.inflate(this, R.layout.layout_guide_single, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.guide_image);
        imageView.setBackgroundResource(resId);
        Button btn = (Button) view.findViewById(R.id.guide_button);
        btn.setVisibility(isShowBtn == true ? View.VISIBLE : View.GONE);
        btn.setOnClickListener(listener);
        list.add(view);
    }

    private class GuidePagerAdapter extends PagerAdapter {
        private List<View> datas;

        public GuidePagerAdapter(List<View> datas) {
            this.datas = datas;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = datas.get(position);
            container.addView(v);
            v.setTag(position);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return this.datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            indicator.setPosition(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
