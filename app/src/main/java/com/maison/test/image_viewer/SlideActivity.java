package com.maison.test.image_viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.maison.test.image_viewer.array_adapter.SlideAdapter;
import com.maison.test.image_viewer.model.Hero;

/**
 * Created by Christophe on 03/06/2016.
 */
public class SlideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(
                    viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        Hero hero = (Hero) getIntent().getExtras().getSerializable("image_object");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new SlideAdapter(getSupportFragmentManager(), hero);
        viewPager.setAdapter(pagerAdapter);
    }
}
