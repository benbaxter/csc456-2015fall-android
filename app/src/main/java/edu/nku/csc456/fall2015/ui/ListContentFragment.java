package edu.nku.csc456.fall2015.ui;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.R;
import edu.nku.csc456.fall2015.model.Badge;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.ui.adapter.BadgesAdapter;
import edu.nku.csc456.fall2015.ui.adapter.ChaptersAdapter;
import edu.nku.csc456.fall2015.ui.presenter.BadgeListPresenter;
import edu.nku.csc456.fall2015.ui.presenter.ChaptersListPresenter;
import edu.nku.csc456.fall2015.ui.presenter.ListPresenter;
import edu.nku.csc456.fall2015.util.DeviceUtil;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ListContentFragment extends Fragment {

    private ListPresenter<?> presenter;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHAPTERS, ADVENTURES, BADGES})
    public @interface ListType {}

    public static final int CHAPTERS = 0;
    public static final int ADVENTURES = 1;
    public static final int BADGES = 2;
    private static final String EXTRA_LIST_TYPE = "ListFragement.EXTRA_LIST_TYPE";
    private int listType;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.chapters_list)
    RecyclerView recyclerView;

    private RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter;
    List<Chapter> chapters;
    List<Chapter> adventures;
    List<Badge> badges;


    private StaggeredGridLayoutManager gridLayoutManager;

    public static ListContentFragment newInstance(@ListType int listType) {
        ListContentFragment frag = new ListContentFragment();
        Bundle b = new Bundle();
        b.putInt(EXTRA_LIST_TYPE, listType);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listType = getArguments().getInt(EXTRA_LIST_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(DeviceUtil.isLarge(getActivity())) {
            gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
            gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        recyclerView.setLayoutManager(gridLayoutManager);

        if( listType == CHAPTERS ) {
            chapters = new ArrayList<>();
            adapter = new ChaptersAdapter(getActivity(), chapters);
            presenter = new ChaptersListPresenter(this);
        } else if ( listType == ADVENTURES ) {
            chapters = new ArrayList<>();
            adventures = new ArrayList<>();
            adapter = new ChaptersAdapter(getActivity(), adventures);
            presenter = new ChaptersListPresenter(this);
        } else if ( listType == BADGES ) {
            badges = new ArrayList<>();
            adapter = new BadgesAdapter(getActivity(), badges);
            presenter = new BadgeListPresenter(this);
        }

        recyclerView.setAdapter(adapter);
        
        presenter.retrieveData();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refreshData();
        });
    }

    public void updateChapters(List<Chapter> chapters) {
        updateData(this.chapters, chapters);
    }

    public void updateAdventures(List<Chapter> adventures) {
        updateData(this.adventures, adventures);
    }

    public void updateBadges(List<Badge> badges) {
        updateData(this.badges, badges);
    }

    private <T> void updateData(List<T> data, List<T> newData) {
        data.clear();
        data.addAll(newData);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
