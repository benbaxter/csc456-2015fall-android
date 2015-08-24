package edu.nku.csc456.fall2015.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import edu.nku.csc456.fall2015.di.DIComponent;
import edu.nku.csc456.fall2015.di.DIModule;
import edu.nku.csc456.fall2015.di.DaggerDIComponent;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.repository.MutableRepository;
import edu.nku.csc456.fall2015.service.Csc456ApiService;
import edu.nku.csc456.fall2015.ui.ListContentFragment;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ChaptersListPresenter implements ListPresenter<Chapter> {

    private static final String LOG_TAG = ChaptersListPresenter.class.getSimpleName();

    private final DIComponent component;
    @Inject
    protected Csc456ApiService service;
    @Inject
    protected MutableRepository<Chapter> chapterRepository;
    private ListContentFragment view;
    private Subscription chaptersSubscription;

    public ChaptersListPresenter(ListContentFragment view) {
        this.view = view;
        chaptersSubscription = new CompositeSubscription();

        component = DaggerDIComponent.builder().dIModule(new DIModule(view.getActivity())).build();
        component.inject(this);
    }

    @Override
    public void retrieveData() {
        if( chaptersSubscription != null ) {
            chaptersSubscription.unsubscribe();
        }

        chaptersSubscription = Observable.defer(() -> {
            return Observable.just(chapterRepository.findAll());
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((chapters) -> {
                if (chapters.isEmpty()) {
                    refreshData();
                } else {
                    handleChaptersAndUpdateView(chapters);
                }
            });
    }

    @Override
    public void refreshData() {
        if( chaptersSubscription != null ) {
            chaptersSubscription.unsubscribe();
        }
        Log.d(LOG_TAG, "refreshing chapters");
        chaptersSubscription = service.getChapters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((chapters) -> {
                    chapterRepository.save(chapters);
                    handleChaptersAndUpdateView(chapters);
                });
    }

    private void handleChaptersAndUpdateView(List<Chapter> chapters) {
        //push the dates in the past to the end of the list.
        int pivot = getPivot(new Date(), chapters);
        Collections.rotate(chapters, -pivot);
        view.updateChapters(chapters);
    }

    private int getPivot(Date today, List<Chapter> chapters) {
        for( int i = 0; i < chapters.size(); ++i ) {
            Chapter c = chapters.get(i);
            Calendar cCal = convertDate(c.date);

            Date cDate = cCal.getTime();
            if( today.getTime() - cDate.getTime() < 0 ) {
                return i;
            }
        }
        return 0;
    }

    private Calendar convertDate(@NonNull String date) {
        String[] dates = date.split("/");
        Calendar cCal = Calendar.getInstance();
        cCal.set(Calendar.MONTH, Integer.parseInt(dates[0]) - 1);
        cCal.set(Calendar.DATE, Integer.parseInt(dates[1]));
        cCal.set(Calendar.YEAR, 2015);
        return cCal;
    }
}
