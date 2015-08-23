package edu.nku.csc456.fall2015.di;

import dagger.Component;
import edu.nku.csc456.fall2015.repository.ChapterRepository;
import edu.nku.csc456.fall2015.service.Csc456ApiService;
import edu.nku.csc456.fall2015.ui.presenter.ChaptersListPresenter;

/**
 * Created by Benjamin on 8/23/2015.
 */
@Component(modules = {DIModule.class})
public interface DIComponent {

    public ChapterRepository provideChapterRepository();
    public Csc456ApiService provideCsc456ApiService();

    void inject(ChaptersListPresenter chaptersListPresenter);
}
