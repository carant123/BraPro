package projeto.undercode.com.proyectobrapro.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Level on 26/03/2017.
 */

public class CalculatePolygonArea {



    private static final double EARTH_RADIUS = 6371000;// meters

    static final String LOG_TAG = CalculatePolygonArea.class.getSimpleName();



    public double calculateAreaOfGPSPolygonOnEarthInSquareMeters(

            final List<LatLng> locations) {

        return calculateAreaOfGPSPolygonOnSphereInSquareMeters(locations,

                EARTH_RADIUS);

    }



    private double calculateAreaOfGPSPolygonOnSphereInSquareMeters(

            final List<LatLng> locations, final double radius) {

        if (locations.size() < 3) {

            return 0;

        }



        final double diameter = radius * 2;

        final double circumference = diameter * Math.PI;

        final List<Double> listY = new ArrayList<Double>();

        final List<Double> listX = new ArrayList<Double>();

        final List<Double> listArea = new ArrayList<Double>();

        // calculate segment x and y in degrees for each point

        final double latitudeRef = locations.get(0).latitude;

        final double longitudeRef = locations.get(0).longitude;

        for (int i = 1; i < locations.size(); i++) {

            final double latitude = locations.get(i).latitude;

            final double longitude = locations.get(i).longitude;

            listY.add(calculateYSegment(latitudeRef, latitude, circumference));

            Log.d(LOG_TAG,

                    String.format("Y %s: %s", listY.size() - 1,

                            listY.get(listY.size() - 1)));

            listX.add(calculateXSegment(longitudeRef, longitude, latitude,

                    circumference));

            Log.d(LOG_TAG,

                    String.format("X %s: %s", listX.size() - 1,

                            listX.get(listX.size() - 1)));

        }



        // calculate areas for each triangle segment

        for (int i = 1; i < listX.size(); i++) {

            final double x1 = listX.get(i - 1);

            final double y1 = listY.get(i - 1);

            final double x2 = listX.get(i);

            final double y2 = listY.get(i);

            listArea.add(calculateAreaInSquareMeters(x1, x2, y1, y2));

            Log.d(LOG_TAG,

                    String.format("area %s: %s", listArea.size() - 1,

                            listArea.get(listArea.size() - 1)));

        }



        // sum areas of all triangle segments

        double areasSum = 0;

        for (final Double area : listArea) {

            areasSum = areasSum + (area/10000);
            Log.d("Math.abs(area)",String.valueOf(Math.abs(area)));
            Log.d("areasSum",String.valueOf(areasSum));
            Log.d("hectareas",String.valueOf(area/10000));

        }



        // get absolute value of area, it can't be negative

        //return Math.abs(areasSum);// Math.sqrt(areasSum * areasSum);

        return Math.abs(areasSum);// Math.sqrt(areasSum * areasSum);

        //return areasSum;

    }



    private  Double calculateAreaInSquareMeters(final double x1,

                                                      final double x2, final double y1, final double y2) {

        return (y1 * x2 - x1 * y2) / 2;

    }



    private  double calculateYSegment(final double latitudeRef,

                                            final double latitude, final double circumference) {

        return (latitude - latitudeRef) * circumference / 360.0;

    }



    private  double calculateXSegment(final double longitudeRef,

                                            final double longitude, final double latitude,

                                            final double circumference) {

        return (longitude - longitudeRef) * circumference

                * Math.cos(Math.toRadians(latitude)) / 360.0;

    }



}