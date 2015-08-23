package edu.nku.csc456.fall2015.ui.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import edu.nku.csc456.fall2015.Csc456Application;
import edu.nku.csc456.fall2015.R;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.util.TextViewCompat;

import static edu.nku.csc456.fall2015.service.Csc456ApiService.SLIDE_ENDPOINT_URL;
import static edu.nku.csc456.fall2015.util.Collections.hasItems;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ChaptersAdapter extends RecyclerView.Adapter<ChapterViewHolder> {

    private static final String LOG_TAG = ChaptersAdapter.class.getSimpleName();
    private final Context context;
    private final List<Chapter> chapters;
    private Tracker tracker;

    public ChaptersAdapter(Context context, List<Chapter> chapters) {
        this.context = context;
        this.chapters = chapters;
        tracker = Csc456Application.getInstance().getDefaultTracker();
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_row, null);
        ChapterViewHolder holder = new ChapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder vh, int position) {
        Chapter chapter = chapters.get(position);

        bindTitle(vh, chapter);
        bindTopics(vh, chapter.topics);
        bindReadings(vh, chapter.readings);
        bindSlides(vh, chapter.slides);
    }

    private void bindTitle(ChapterViewHolder vh, Chapter chapter) {
        vh.dateView.setText(chapter.date);
        int background = R.color.primary;
        if (chapter.isBossFight) {
            background = R.color.accent;
        } else if (chapter.projectDemo) {
            background = R.color.primary_dark;
        } else if (chapter.noClass) {
            background = R.color.primary_light;
        }
        vh.dateView.setBackgroundColor(ContextCompat.getColor(context, background));
    }

    private void bindTopics(ChapterViewHolder vh, List<String> topics) {
        if (topics != null) {
            String topicsText = TextUtils.join("\n", topics);
            vh.topicList.setText(topicsText);
            //have only one topic, make it the title
            boolean highlander = topics.size() == 1;
            if (highlander) {
                TextViewCompat.setTextAppearance(vh.topicList, context, android.R.style.TextAppearance_Large);
                vh.topicTitle.setVisibility(View.GONE);
            } else {
                TextViewCompat.setTextAppearance(vh.topicList, context, android.R.style.TextAppearance_DeviceDefault);
                vh.topicTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    private void bindReadings(ChapterViewHolder vh, List<String> readingList) {
        if (hasItems(readingList)) {
            String readings = TextUtils.join("\n", readingList);
            vh.readingList.setText(readings);
            vh.readingList.setVisibility(View.VISIBLE);
            vh.readingTitle.setVisibility(View.VISIBLE);
        } else {
            vh.readingList.setVisibility(View.GONE);
            vh.readingTitle.setVisibility(View.GONE);
        }
    }

    private void bindSlides(ChapterViewHolder vh, List<String> slides) {
        vh.downloadContainer.removeAllViews();
        if (hasItems(slides)) {
            for (String slide : slides) {
                View slideContainer = LayoutInflater.from(context).inflate(R.layout.fab_slide_download, null);
                FloatingActionButton button = (FloatingActionButton) slideContainer.findViewById(R.id.slide_fab);
                button.setOnClickListener((v) -> {

                    Uri ppt = Uri.parse(SLIDE_ENDPOINT_URL + slide);

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(ppt)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    downloadManager.enqueue(request);
                    
                    Log.i(LOG_TAG, "Sending downloading event for slide: " + slide);
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Slides Downloading")
                            .setAction("downloading: " + slide)
                            .build());
                });
                vh.downloadContainer.addView(slideContainer);
            }
            vh.slidesContainer.setVisibility(View.VISIBLE);
        } else {
            vh.slidesContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }
}
