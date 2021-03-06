package is.hi.hbv601g.brent.activities.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import is.hi.hbv601g.brent.R;
import is.hi.hbv601g.brent.activities.CurrentActivity;
import is.hi.hbv601g.brent.models.Route;

public class RouteActivity extends CurrentActivity {

    TextView mTitle;
    TextView mLength;
    TextView mLikes;
    TextView mDescription;
    TextView mSeeOnMapButton;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.connected) {
            setUp();
        }
    }

    @Override
    public void setUp() {
        setContentView(R.layout.activity_route);
        // Get toolbar in layout (defined in xml file)
        super.setUp();
        loadData();
        mSeeOnMapButton = findViewById(R.id.see_on_map_button);
        mSeeOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Coming soon");
            }
        });
    }

    private void loadData() {
        Intent routesActivity_intent = getIntent();
        final Route route = routesActivity_intent.getParcelableExtra("route");
        mTitle = findViewById(R.id.place_location);
        mLength = findViewById(R.id.place_info_1);
        mLikes = findViewById(R.id.place_info_2);
        mDescription = findViewById(R.id.place_description);
        mImage = findViewById(R.id.route_image);
        mTitle.setText(route.getLocation());
        mLength.setText(route.getLength() + " km");
        mDescription.setText(route.getDescription());
        mLikes.setText(route.getLikes() + " likes");
        Picasso.get().load(route.getImage()).into(mImage);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
