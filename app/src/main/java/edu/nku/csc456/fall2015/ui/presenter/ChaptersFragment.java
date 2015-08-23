package edu.nku.csc456.fall2015.ui.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.R;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.service.ApiServiceFactory;
import edu.nku.csc456.fall2015.ui.adapter.ChapterViewHolder;
import edu.nku.csc456.fall2015.ui.adapter.ChaptersAdapter;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ChaptersFragment extends Fragment {

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.chapters_list)
    RecyclerView recyclerView;

    private RecyclerView.Adapter<ChapterViewHolder> adapter;
    List<Chapter> chapters;

    private StaggeredGridLayoutManager gridLayoutManager;

    public static ChaptersFragment newInstance() {
        return new ChaptersFragment();
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

        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        chapters = new ArrayList<>();
        adapter = new ChaptersAdapter(getActivity(), chapters);
        recyclerView.setAdapter(adapter);

        ChaptersListPresenter presenter = new ChaptersListPresenter(this);
        presenter.retrieveChapters();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refreshChapters();
        });
    }

    public void updateChapters(List<Chapter> chapters) {
        this.chapters.clear();
        this.chapters.addAll(chapters);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
