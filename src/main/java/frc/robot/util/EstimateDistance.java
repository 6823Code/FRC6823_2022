package frc.robot.util;

public class EstimateDistance {
    private static double cameraHeight = 33.5;

    public static double getDistance(double targetHeight, 
    double cameraAngleFromForward, double cameraAngleToTarget) {
        //Returns the distance from limelight target based on height 
        //of camera, height of target, angle from vertical center, and 
        //angle from level
        return ((targetHeight - cameraHeight) / Math.tan(cameraAngleFromForward + cameraAngleToTarget));
    }
}
