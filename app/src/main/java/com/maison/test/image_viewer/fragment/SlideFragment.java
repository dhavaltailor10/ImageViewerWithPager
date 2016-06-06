package com.maison.test.image_viewer.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maison.test.image_viewer.R;
import com.maison.test.image_viewer.utils.ImageUtils;

/**
 * Created by Christophe on 02/06/2016.
 */
public class SlideFragment extends android.support.v4.app.Fragment {
    private String imagePath;
    private String title;
    private String intro;
    private String text;
    private String colorString;
    private TextView introTextView;
    private TextView textTextView;
    private ImageView imageView;
    private android.support.v7.app.ActionBar bar;

    public SlideFragment() {}

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slide, container, false);

        introTextView = (TextView) rootView.findViewById(R.id.intro_slide);
        introTextView.setText(this.intro);

        textTextView = (TextView) rootView.findViewById(R.id.text_slide);
        textTextView.setText(this.text);

        imageView = (ImageView)rootView.findViewById(R.id.image_slide);
        imageView.setImageBitmap(ImageUtils.getInstance().decodeSampledBitmapFromFile(this.imagePath, 300, 300));

        bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(this.colorString)));
        bar.setTitle(this.title);

        return rootView;
    }
}
