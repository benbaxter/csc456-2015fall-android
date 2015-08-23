package edu.nku.csc456.fall2015.ui.presenter;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import edu.nku.csc456.fall2015.ChapterRepository;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.service.Csc456ApiService;
import edu.nku.csc456.fall2015.ui.ChaptersActivity;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ChaptersListPresenter {

    private final Csc456ApiService service;
    private final ChapterRepository chapterRepository;
    private ChaptersFragment view;
    private Subscription chaptersSubscription;

    public ChaptersListPresenter(ChaptersFragment view, Csc456ApiService service) {
        this.view = view;
        this.service = service;
        chapterRepository = new ChapterRepository(view.getActivity());
        chaptersSubscription = new CompositeSubscription();
    }

    public void retrieveChapters() {
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
                    refreshChapters();
                } else {
                    handleChaptersAndUpdateView(chapters);
                }
            });
    }

    public void refreshChapters() {
        if( chaptersSubscription != null ) {
            chaptersSubscription.unsubscribe();
        }
        Log.d("TAG", "refreshing");
        chaptersSubscription = service.getChapters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((chapters) -> {
                    handleChaptersAndUpdateView(chapters);
                });
    }

    private void handleChaptersAndUpdateView(List<Chapter> chapters) {
        //push the dates in the past to the end of the list.
        Date today = new Date();
        int pivot = getPivot(today, chapters);
        Collections.rotate(chapters, -pivot);
        chapterRepository.save(chapters);
        view.updateChapters(chapters);
    }

    private int getPivot(Date today, List<Chapter> chapters) {
        int pivot = 0;
        for( int i = 0; i < chapters.size(); ++i ) {
            Chapter c = chapters.get(i);
            String[] dates = c.date.split("/");
            Calendar cCal = Calendar.getInstance();
            cCal.set(Calendar.MONTH, Integer.parseInt(dates[0]) - 1);
            cCal.set(Calendar.DATE, Integer.parseInt(dates[1]));

            Date cDate = cCal.getTime();
            if( today.getTime() - cDate.getTime() < 0 ) {
                pivot = i;
                break;
            }
        }
        return pivot;
    }
}
