package edu.nku.csc456.fall2015.di;

import javax.inject.Named;

import dagger.Component;
import edu.nku.csc456.fall2015.model.Badge;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.repository.MutableRepository;
import edu.nku.csc456.fall2015.service.Csc456ApiService;
import edu.nku.csc456.fall2015.ui.presenter.BadgeListPresenter;
import edu.nku.csc456.fall2015.ui.presenter.ChaptersListPresenter;
import edu.nku.csc456.fall2015.ui.presenter.ListPresenter;

/**
 * Created by Benjamin on 8/23/2015.
 */
@Component(modules = {DIModule.class})
public interface DIComponent {

    MutableRepository<Chapter> provideChapterRepository();
    MutableRepository<Badge> provideBadgeRepository();
    Csc456ApiService provideCsc456ApiService();

    void inject(ChaptersListPresenter listPresenter);
    void inject(BadgeListPresenter listPresenter);

}
