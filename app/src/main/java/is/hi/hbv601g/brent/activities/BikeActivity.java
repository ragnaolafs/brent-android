package is.hi.hbv601g.brent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Date;

import is.hi.hbv601g.brent.models.Bike;
import is.hi.hbv601g.brent.models.Booking;
import is.hi.hbv601g.brent.Cart;
import is.hi.hbv601g.brent.R;

public class BikeActivity extends CurrentActivity {

    ImageButton toolbarProfile;
    ImageButton toolbarHome;
    ImageButton toolbarCart;

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
        toolbarProfile = findViewById(R.id.toolbar_profile);
        toolbarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(userIntent);
            }
        });
        toolbarHome = findViewById(R.id.toolbar_home);
        toolbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });
        toolbarCart = findViewById(R.id.toolbar_cart);
        toolbarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });


        Intent bikesActivity_intent = getIntent();
        final Bike bike = bikesActivity_intent.getParcelableExtra("bike");
        final Date startDate = (Date) bikesActivity_intent.getSerializableExtra("startDate");
        final Date endDate = (Date) bikesActivity_intent.getSerializableExtra("endDate");

        Button bookButton= findViewById(R.id.bookButton);
        final Intent cartActivity_intent = new Intent(this, CartActivity.class);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = Cart.getCart();
                if (!cart.contains(startDate, endDate)) {
                    Booking booking = new Booking(startDate, endDate);
                    booking.addBike(bike);
                    cart.putBooking(booking);
                }
                startActivity(cartActivity_intent);
            }
        });
    }
}