package is.hi.hbv601g.brent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import org.json.JSONObject;


public class BikeActivity extends CurrentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.connected) {
            setUp();
        }
    }

    @Override
    public void setUp() {
        setContentView(R.layout.activity_bike);
        // Get toolbar in layout (defined in xml file)
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Set it as actionbar
        setSupportActionBar(toolbar);
        Intent bikesActivity_intent = getIntent();
        final Bike bike = bikesActivity_intent.getParcelableExtra("bike");
        Button bookButton= findViewById(R.id.bookButton);
        final Intent cartActivity_intent = new Intent(this, CartActivity.class);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartActivity_intent.putExtra("bike", bike);
                startActivity(cartActivity_intent);
            }
        });
    }
}
