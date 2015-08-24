package edu.nku.csc456.fall2015.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.R;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class BadgeViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.xp)
    TextView xp;

    @Bind(R.id.badge)
    ImageView badge;

    @Bind(R.id.description)
    TextView description;

    public BadgeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
