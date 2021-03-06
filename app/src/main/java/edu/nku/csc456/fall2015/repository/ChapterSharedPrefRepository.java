package edu.nku.csc456.fall2015.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import edu.nku.csc456.fall2015.model.Chapter;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class ChapterSharedPrefRepository extends BaseSharedPrefRepository<Chapter> {

    private static final String KEY_CHAPTERS = "chapters";

    public ChapterSharedPrefRepository(Context context) {
        super(context);
    }

    @Override
    protected String getKey() {
        return KEY_CHAPTERS;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<List<Chapter>>(){}.getType();
    }
}
