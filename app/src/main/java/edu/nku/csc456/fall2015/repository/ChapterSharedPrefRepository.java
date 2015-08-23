package edu.nku.csc456.fall2015.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

import edu.nku.csc456.fall2015.model.Chapter;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class ChapterSharedPrefRepository implements ChapterRepository {

    private static final String PREFS = "csc456";
    private static final String KEY_CHAPTERS = "chapters";
    private SharedPreferences prefs;
    private Gson gson;

    public ChapterSharedPrefRepository(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    @Override
    public void save(List<Chapter> chapters) {
        String json = gson.toJson(chapters);

        prefs.edit()
                .putString(KEY_CHAPTERS, json)
                .commit();
    }

    @Override
    public List<Chapter> findAll() {
        String json = prefs.getString(KEY_CHAPTERS, null);
        if( json == null ) {
            return Collections.emptyList();
        }
        return gson.fromJson(json, new TypeToken<List<Chapter>>(){}.getType());
    }

}
