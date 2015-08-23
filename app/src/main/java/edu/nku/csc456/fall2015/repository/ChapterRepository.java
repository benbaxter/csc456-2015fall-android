package edu.nku.csc456.fall2015.repository;

import java.util.List;

import edu.nku.csc456.fall2015.model.Chapter;

/**
 * Created by Benjamin on 8/23/2015.
 */
public interface ChapterRepository {

    void save(List<Chapter> chapters);

    List<Chapter> findAll();
}
