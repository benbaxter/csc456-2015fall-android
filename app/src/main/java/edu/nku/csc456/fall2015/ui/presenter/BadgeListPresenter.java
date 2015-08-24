package edu.nku.csc456.fall2015.ui.presenter;

import android.util.Log;

import java.util.Collections;

import javax.inject.Inject;

import edu.nku.csc456.fall2015.di.DIComponent;
import edu.nku.csc456.fall2015.di.DIModule;
import edu.nku.csc456.fall2015.di.DaggerDIComponent;
import edu.nku.csc456.fall2015.model.Badge;
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
public class BadgeListPresenter implements ListPresenter<Badge> {

    private static final String LOG_TAG = BadgeListPresenter.class.getSimpleName();

    private final DIComponent component;
    @Inject
    protected Csc456ApiService service;
    @Inject
    protected MutableRepository<Badge> badgeRepository;
    private ListContentFragment view;
    private Subscription badgesSubscription;

    public BadgeListPresenter(ListContentFragment view) {
        this.view = view;
        badgesSubscription = new CompositeSubscription();

        component = DaggerDIComponent.builder().dIModule(new DIModule(view.getActivity())).build();
        component.inject(this);
    }

    @Override
    public void retrieveData() {
        if( badgesSubscription != null ) {
            badgesSubscription.unsubscribe();
        }

        badgesSubscription = Observable.defer(() -> {
            return Observable.just(badgeRepository.findAll());
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((badges) -> {
                if (badges.isEmpty()) {
                    refreshData();
                } else {
                    view.updateBadges(badges);
                }
            });
    }

    @Override
    public void refreshData() {
        if( badgesSubscription != null ) {
            badgesSubscription.unsubscribe();
        }
        Log.d(LOG_TAG, "refreshing badges");
        badgesSubscription = service.getBadges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((badges) -> {
                    //move that cloud one to the end of the list
                    Collections.rotate(badges, -1);
                    badgeRepository.save(badges);
                    view.updateBadges(badges);
                });
    }
}
