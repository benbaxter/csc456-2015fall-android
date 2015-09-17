package edu.nku.csc456.fall2015.repository;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import edu.nku.csc456.fall2015.model.Badge;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class BadgeSharedPrefRepository extends BaseSharedPrefRepository {

    private static final String KEY_BADGES = "badges";

    public BadgeSharedPrefRepository(Context context) {
        super(context);
    }

    @Override
    protected String getKey() {
        return KEY_BADGES;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<List<Badge>>(){}.getType();
    }
}
