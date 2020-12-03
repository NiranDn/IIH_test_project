package com.example.iihtestproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iihtestproject.R;
import com.example.iihtestproject.object.Car;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private GoogleMap map;
    private MapFragmentListener mListener;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            if (mListener != null) {
                mListener.onMapReady();
            }
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }

    }

    public void setCarLocation(Car car) {
        if (map != null) {
            map.clear();
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(createMarker(car.getCoordinates().get(1), car.getCoordinates().get(0), car.getName()).getPosition());

        LatLng location = new LatLng(car.getCoordinates().get(1), car.getCoordinates().get(0));
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

//        map.addMarker(new MarkerOptions().position(location).title(car.getName()));
//        map.animateCamera(CameraUpdateFactory.newLatLng(location));
    }

    public void setCarListLocation(ArrayList<Car> cars) {
        if (map != null) {
            map.clear();
        }

        ArrayList<Marker> markers = new ArrayList<>();

        for (Car car : cars) {
            LatLng location = new LatLng(car.getCoordinates().get(1), car.getCoordinates().get(0));
            markers.add(createMarker(car.getCoordinates().get(1), car.getCoordinates().get(0), car.getName()));
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

    }

    public interface MapFragmentListener {
        void onMapReady();
    }

    public void setFragmentInterface(MapFragmentListener listener) {
        mListener = listener;
    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));

        marker.showInfoWindow();

        return marker;
    }
}