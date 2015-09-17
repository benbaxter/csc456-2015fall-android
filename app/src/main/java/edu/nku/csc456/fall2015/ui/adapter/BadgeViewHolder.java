package edu.nku.csc456.fall2015.ui.adapter;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nku.csc456.fall2015.Csc456Application;
import edu.nku.csc456.fall2015.R;
import edu.nku.csc456.fall2015.model.Badge;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class BadgeViewHolder extends RecyclerView.ViewHolder
{
    public interface OnBadgeClicked {

        public void onBadgeClicked(int position);
    }

    private final OnBadgeClicked listener;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.xp)
    TextView xp;

    @Bind(R.id.badge)
    TextView badge;

    @Bind(R.id.description)
    TextView description;

    public BadgeViewHolder(OnBadgeClicked listener, View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
    }

    @OnClick(R.id.card_view)
    public void clicked(View v) {
        listener.onBadgeClicked(getAdapterPosition());
    }
}
