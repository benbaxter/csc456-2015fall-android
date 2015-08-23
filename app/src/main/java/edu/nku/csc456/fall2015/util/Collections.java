package edu.nku.csc456.fall2015.util;

import java.util.Collection;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class Collections {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean hasItems(Collection<?> collection) {
        return ! isEmpty(collection);
    }
}
