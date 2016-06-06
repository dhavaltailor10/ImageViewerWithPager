package com.maison.test.image_viewer.array_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maison.test.image_viewer.R;
import com.maison.test.image_viewer.SlideActivity;
import com.maison.test.image_viewer.model.Hero;
import com.maison.test.image_viewer.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by Christophe on 27/05/2016.
 */
public class MainActivityListAdapter extends ArrayAdapter<Hero> {

    private ImageView heroesListView;
    private TextView title;
    private TextView year;
    private TextView intro;
    private ArrayList<Hero> heroesList;
    private View separator;

    public MainActivityListAdapter(Context context, ArrayList<Hero> heroesList) {
        super(context, 0, heroesList);
        this.heroesList = heroesList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_row_adapter, parent, false);
        }

        int color = Color.parseColor(heroesList.get(position).getColor());
        separator = (View) convertView.findViewById(R.id.separator);
        separator.setBackgroundColor(color);

        title = (TextView) convertView.findViewById(R.id.title);
        title.setText(heroesList.get(position).getTitre());
        title.setTextColor(color);

        year = (TextView) convertView.findViewById(R.id.year);
        year.setText(heroesList.get(position).getYear());

//        intro = (TextView) convertView.findViewById(R.id.intro);
//        intro.setText(heroesList.get(position).getIntro());
//        intro.setTag(new Integer(position));

        final TextView intro_expand = (TextView) convertView.findViewById(R.id.intro);
        intro_expand.setText(heroesList.get(position).getIntro());

        intro_expand.setOnClickListener(new View.OnClickListener() {
            private boolean isTextViewClicked = false;

            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    intro_expand.setMaxLines(2);
                    isTextViewClicked = false;
                } else {
                    intro_expand.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SlideActivity.class);
                Hero hero = new Hero();

                hero.setTitre(heroesList.get(position).getTitre());
                hero.setIntro(heroesList.get(position).getIntro());
                //hero.setYear(heroesList.get(position).getYear());
                hero.setText(heroesList.get(position).getText());
                hero.setColor(heroesList.get(position).getColor());
                hero.setImageList(heroesList.get(position).getImageList());
                intent.putExtra("image_object", hero);

                getContext().startActivity(intent);
            }
        });

        heroesListView = (ImageView) convertView.findViewById(R.id.image_list_view);
        heroesListView.setImageBitmap(ImageUtils.getInstance().decodeSampledBitmapFromFile(heroesList.get(position).getImageList().get(0), 50, 50));

        return convertView;
    }


}