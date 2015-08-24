package edu.nku.csc456.fall2015.repository;

import java.util.List;

import edu.nku.csc456.fall2015.model.Chapter;

/**
 * Created by Benjamin on 8/23/2015.
 */
public interface MutableRepository<T> {

    void save(List<T> data);

    List<T> findAll();
}
