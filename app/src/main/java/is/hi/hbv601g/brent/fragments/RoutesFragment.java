package is.hi.hbv601g.brent.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import is.hi.hbv601g.brent.R;
import is.hi.hbv601g.brent.models.Route;

public class RoutesFragment extends Fragment {


    private RecyclerView mRecycleView = null;
    private SelectionListener mListener;
    private ArrayList<Route> mRoutes;
    private ArrayList<Route> mRoutesUnfiltered;
    private int MarginLeftAndRight = 0;
    private int MarginTopAndBot = 0;
    private RoutesFragment.RouteListAdapter mAdapter;
    private boolean mLandscapeMode = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        mRecycleView = view.findViewById(R.id.route_recycle_view);
        if (landscapeMode()) {
            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }
        mListener = (SelectionListener) getActivity();
        Bundle bundle = getArguments();
        ArrayList<Route> routes = bundle.getParcelableArrayList("routes");
        mRoutes = routes;
        mRoutesUnfiltered = (ArrayList<Route>) routes.clone();
        mAdapter = new RouteListAdapter();
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new SpacesItemDecoration());
        return view;
    }

    private boolean landscapeMode() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) return true;
        return false;
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.right = MarginLeftAndRight;
            outRect.left = MarginLeftAndRight;
            outRect.bottom = MarginTopAndBot;
            outRect.top = MarginTopAndBot;
        }
    }

    private class RouteListAdapter extends RecyclerView.Adapter<RoutesFragment.RouteHolder> {
        public RouteListAdapter() {
            super();
        }
        @NonNull
        @Override
        public RoutesFragment.RouteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            FrameLayout layout = (FrameLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.viewholder_bike, viewGroup, false);
            RouteHolder routeHolder = new RouteHolder(layout, viewGroup.getMeasuredHeight());
            return routeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RoutesFragment.RouteHolder routeHolder, int i) {
            Route route = mRoutes.get(i);
            routeHolder.mRoute = route;
            routeHolder.mCardTitle.setText(route.getLocation());
            routeHolder.mCardLength.setText(route.getLength() + " km");
            Picasso.get().load(route.getImage())
                    .placeholder(R.drawable.bike_hybrid)
                    .centerInside()
                    .resize(200, 200)
                    .into(routeHolder.mRouteImage);
        }

        @Override
        public int getItemCount() {
            return mRoutes.size();
        }
    }

    private class RouteHolder extends RecyclerView.ViewHolder {
        TextView mCardTitle;
        TextView mCardLength;
        TextView mCardDescription;
        TextView mCardLikes;
        ImageView mRouteImage;
        FrameLayout mLayout;
        Route mRoute;
        public RouteHolder(@NonNull View itemView, int parentHeight) {
            super(itemView);
            mLayout = (FrameLayout) itemView;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mLayout.getLayoutParams();
            mLayout.setLayoutParams(params);
            mCardTitle = mLayout.findViewById(R.id.card_title_id);
            mRouteImage = mLayout.findViewById(R.id.card_image_id);
            mCardLength = mLayout.findViewById(R.id.card_info3_id);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRouteSelected(mRoute);
                }
            });
        }
    }

    public interface SelectionListener {
        void onRouteSelected(Route Route);
    }

}
