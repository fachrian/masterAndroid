package fachrian.fachrian_library.lib;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.text.DecimalFormat;


/**
 * Created by Fachrian on 24/06/2016.
 */

 public class LocationController {
    double latitude = 0;
    double longitude = 0;
    Context context;

    public LocationController(Context context) {
        this.context = context;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            SharedPreferences.setLONGI(context, String.valueOf((Double) location.getLatitude()));
            SharedPreferences.setLATI(context, String.valueOf((Double) location.getLongitude()));
        }

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double distance(double latA, double lngA) {
        Location locationA = new Location("point A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("point B");


        if (latitude != 0 && longitude != 0) {
            locationB.setLatitude(latitude);
            locationB.setLongitude(longitude);
        } else if (SharedPreferences.getLONGI(context) != null
                && SharedPreferences.getLATI(context) != null
                ) {
            locationB.setLatitude(Double.valueOf(SharedPreferences.getLATI(context)));
            locationB.setLongitude(Double.valueOf(SharedPreferences.getLONGI(context)));
        } else {
            return -1;
        }

        double x = locationA.distanceTo(locationB) / 1000;
        try {
            DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
            String dx = df.format(x);
            return Double.valueOf(dx);
        } catch (Exception e) {

            return x;
        }
    }

    public void taskGetLocation(final Context context, int interval) {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (SharedPreferences.getLONGI(context) != null && SharedPreferences.getLATI(context) != null) {
                    double lastLng = 0;
                    double lastLat = 0;
                    try {
                        lastLng = Double.valueOf(SharedPreferences.getLONGI(context));
                        lastLat = Double.valueOf(SharedPreferences.getLATI(context));
                    } catch (Exception e) {

                    }
                    SharedPreferences.setLONGI(context, String.valueOf((Double) location.getLatitude()));
                    SharedPreferences.setLATI(context, String.valueOf((Double) location.getLongitude()));

                    if (lastLng != 0 && lastLat != 0) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        if (distance(lastLat, lastLng) > 0.1) {
                            ResController.processUpdateLokasi(context);
                        }
                        LogController.display(context, "lastLng " + lastLng);
                        LogController.display(context, "lastLat " + lastLat);
                        LogController.display(context, "latitude " + latitude);
                        LogController.display(context, "longitude " + longitude);
                        LogController.display(context, "distance " + distance(lastLat, lastLng));
                    }

                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval, 0, locationListener);
    }

}

    double longitude = 0;
    Context context;

    public LocationMaster(Context context) {
        this.context = context;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
           Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            SharedPreferences.setLONGI(context, String.valueOf((Double) location.getLatitude()));
            SharedPreferences.setLATI(context, String.valueOf((Double) location.getLongitude()));
        }

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double distance(double latA, double lngA, double latB, double lngB) {
        Location locationA = new Location("point A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("point B");
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);

        double x = locationA.distanceTo(locationB) / 1000;
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        return Double.valueOf(df.format(x));
    }

    public void taskGetLocation(final Context context, int interval) {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
//                if (SharedPreferences.getLONGI(context) != null && SharedPreferences.getLATI(context) != null) {
//                    double lastLng = 0;
//                    double lastLat = 0;
//                    try {
//                        lastLng = Double.valueOf(SharedPreferences.getLONGI(context));
//                        lastLat = Double.valueOf(SharedPreferences.getLATI(context));
//                    } catch (Exception e) {
//
//                    }
//
//                    SharedPreferences.setLONGI(context, String.valueOf((Double) location.getLatitude()));
//                    SharedPreferences.setLATI(context, String.valueOf((Double) location.getLongitude()));
//
//                    if (lastLng != 0 && lastLat != 0) {
//                        latitude = location.getLatitude();
//                        longitude = location.getLatitude();
//                        if (distance(lastLat, lastLng) > 0.1) {
//                            //update to server
//                            ResController.processUpdateLokasi(context);
//                        }
//                    }
//
//                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval, 0, locationListener);
    }

}
