package fachrian.fachrian_library.lib;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class MapsMaster {
    public MapsMaster(FragmentActivity fragmentActivity, OnMapReadyCallback onMapReadyCallback, int mapId) {
        SupportMapFragment mapFragment =
                (SupportMapFragment) fragmentActivity.getSupportFragmentManager().findFragmentById(mapId);
        mapFragment.newInstance(getOptions());
        mapFragment.getMapAsync(onMapReadyCallback);
    }

    public  GoogleMapOptions getOptions() {
        return new GoogleMapOptions()
                .liteMode(true)
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
                .scrollGesturesEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false);
    }

    public  void showLocation(GoogleMap map, LatLng latLng, String title) {

        map.addMarker(new MarkerOptions().position(latLng).title(title).draggable(false));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 1500, null);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
    }
}
