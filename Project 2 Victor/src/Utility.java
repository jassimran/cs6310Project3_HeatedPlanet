/**
 * Created by fhaynes on 10/4/14.
 */
public abstract class Utility {
    public static float getLatitudeCircum(double latitude, double earthRadius) {
        double latRadius = earthRadius * Math.sin(Math.toRadians(90d - latitude));
        return (float) (2d * latRadius * Math.PI);
    }

    public static float getTrapezoidArea(double topLength, double bottomLength, double height) {
        return (float) ((.5 * height) * (topLength + bottomLength));
    }

    public static float getTrapezoidSideLen(double topLength, double bottomLength, double height) {
        return (float) Math.sqrt(Math.pow((Math.abs(topLength - bottomLength) / 2), 2) + Math.pow(height, 2));
    }

    public static float getDistToEquator(double latitude, double earthRadius) {
        return (float) (earthRadius * Math.sin(Math.toRadians(latitude)));
    }
}

