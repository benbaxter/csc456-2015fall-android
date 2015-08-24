package edu.nku.csc456.fall2015.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import edu.nku.csc456.fall2015.model.Badge;
import edu.nku.csc456.fall2015.model.Chapter;

/**
 * Created by Benjamin on 8/23/2015.
 */
public abstract class BaseSharedPrefRepository<T> implements MutableRepository<T> {

    private static final String PREFS = "csc456";
    private SharedPreferences prefs;
    private Gson gson;

    public BaseSharedPrefRepository(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    protected abstract String getKey();

    @Override
    public void save(List<T> data) {
        String json = gson.toJson(data);

        prefs.edit()
                .putString(getKey(), json)
                .commit();
    }

    @Override
    public List<T> findAll() {
        String json = prefs.getString(getKey(), null);
        if( json == null ) {
            return Collections.emptyList();
        }

        return gson.fromJson(json, getTypeToken());
    }

    protected abstract Type getTypeToken();
}
