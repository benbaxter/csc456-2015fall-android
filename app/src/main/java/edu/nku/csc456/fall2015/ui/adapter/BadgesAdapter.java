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
import edu.nku.csc456.fall2015.model.Badge;
import edu.nku.csc456.fall2015.model.Chapter;
import edu.nku.csc456.fall2015.util.TextViewCompat;

import static edu.nku.csc456.fall2015.service.Csc456ApiService.SLIDE_ENDPOINT_URL;
import static edu.nku.csc456.fall2015.util.Collections.hasItems;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class BadgesAdapter extends RecyclerView.Adapter<BadgeViewHolder> {

    private static final String LOG_TAG = BadgesAdapter.class.getSimpleName();
    private final Context context;
    private final List<Badge> badges;

    public BadgesAdapter(Context context, List<Badge> badges) {
        this.context = context;
        this.badges = badges;
    }

    @Override
    public BadgeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.badge_row, null);
        BadgeViewHolder holder = new BadgeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BadgeViewHolder vh, int position) {
        Badge badge = badges.get(position);

        vh.title.setText(badge.title);
        vh.xp.setText(badge.xp);
        vh.description.setText(badge.description);
        //TODO: set badge icon
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }
}
