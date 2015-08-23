package edu.nku.csc456.fall2015.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.R;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ChapterViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.date_text)
    TextView dateView;

    @Bind(R.id.topics_title)
    TextView topicTitle;

    @Bind(R.id.topics_list)
    TextView topicList;

    @Bind(R.id.readings_title)
    TextView readingTitle;

    @Bind(R.id.reading_list)
    TextView readingList;

    @Bind(R.id.slides_download_container)
    ViewGroup slidesContainer;

    @Bind(R.id.slides_download)
    ViewGroup downloadContainer;

    public ChapterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
