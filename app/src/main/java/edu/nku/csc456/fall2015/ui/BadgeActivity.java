package edu.nku.csc456.fall2015.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.nku.csc456.fall2015.R;
import edu.nku.csc456.fall2015.model.Badge;

/**
 * Created by Benjamin on 9/17/2015.
 */
public class BadgeActivity extends AppCompatActivity {

    public final static String EXTRA_BADGE = "extra_badge";

    Badge badge;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.xp)
    TextView xp;

    @Bind(R.id.badge)
    TextView badgeIcon;

    @Bind(R.id.description)
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if( extras != null ) {
            badge = (Badge) extras.getSerializable(EXTRA_BADGE);
        }

        bindView();
    }

    private void bindView() {
        if( badge != null ) {
            title.setText(badge.title);
            xp.setText(badge.xp);
            description.setText(Html.fromHtml(badge.description));
            badgeIcon.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/material_design_icon_font.ttf"));
            badgeIcon.setText(badge.unicode);
        }
    }
}
