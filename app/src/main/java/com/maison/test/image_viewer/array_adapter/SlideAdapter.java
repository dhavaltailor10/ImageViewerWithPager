package com.maison.test.image_viewer.array_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maison.test.image_viewer.fragment.SlideFragment;
import com.maison.test.image_viewer.model.Hero;

/**
 * Created by Christophe on 03/06/2016.
 */
public class SlideAdapter extends FragmentStatePagerAdapter {
    private Hero hero;

    public SlideAdapter(FragmentManager fm, Hero hero) {
        super(fm);
        this.hero = hero;
    }

    @Override
    public Fragment getItem(int position) {
        SlideFragment slideFragment = new SlideFragment();
        slideFragment.setTitle(hero.getTitre());
        slideFragment.setIntro(hero.getIntro());
        slideFragment.setText(hero.getText());
        slideFragment.setColorString(hero.getColor());
        slideFragment.setImagePath(hero.getImageList().get(position));

        return slideFragment;
    }

    @Override
    public int getCount() {
        return hero.getImageList().size();
    }
}