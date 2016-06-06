package com.maison.test.image_viewer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maison.test.image_viewer.R;
import com.maison.test.image_viewer.array_adapter.MainActivityListAdapter;
import com.maison.test.image_viewer.model.Hero;
import com.maison.test.image_viewer.utils.ImageUtils;
import com.maison.test.image_viewer.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Christophe on 26/05/2016.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Hero> heroesList;
    private ListView listView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            JSONObject container = new JSONObject(new JsonUtils().getJsonHeroes());
            JSONArray jsonArray = container.getJSONArray("elements");

            heroesList = new ArrayList<Hero>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String title, year, intro, text, color;
                Hero hero = new Hero();
                ArrayList<String> imageList = new ArrayList<String>();

                boolean validHero = true;

                JSONObject insideObj = jsonArray.getJSONObject(i);

                // Contraintes : un titre, une annee, une intro et au moins une image
                if (!insideObj.getString("title").isEmpty()) {
                    title = insideObj.getString("title");
                    hero.setTitre(title);
                } else {
                    title = insideObj.getString("title");
                    validHero = false;
                }

                if (!insideObj.getString("year").isEmpty()) {
                    year = insideObj.getString("year");
                    hero.setYear(year);
                } else {
                    year = insideObj.getString("year");
                    validHero = false;
                }

                if (!insideObj.getString("intro").isEmpty()) {
                    intro = insideObj.getString("intro");
                    hero.setIntro(intro);
                } else {
                    intro = insideObj.getString("intro");
                    validHero = false;
                }

                text = insideObj.getString("text");
                hero.setText(text);

                color = insideObj.getString("color");
                hero.setColor(color);

                // Extraction des images distantes
                JSONArray images = insideObj.getJSONArray("images");

                if (images.length() > 0) {
                    for (int j = 0; j < images.length(); j++) {
                        // Mise en "cache" des images
                        String imagePath = ImageUtils.getInstance().saveToInternalStorage(getActivity(), ImageUtils.getInstance().getImageFromWS(images.getString(j)), images.getString(j));
                        imageList.add(imagePath);
                    }
                    hero.setImageList(imageList);
                } else {
                    validHero = false;
                }

                // Si le hero a bien rempli toutes les contraintes alors on l'ajoute à la liste
                if (validHero) {
                    heroesList.add(hero);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainActivityListAdapter mainActivityListAdapter = new MainActivityListAdapter(getActivity(), heroesList);
        listView = (ListView) view.findViewById(R.id.image_list);
        // Styling
        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));

        listView.setAdapter(mainActivityListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.fragment_activity_main, container, false);
        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
