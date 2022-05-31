package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EstimateDistance {
    private static double cameraHeight = 59.3725; //33.5

    public static double getDistance(double targetHeight, 
    double cameraAngleFromForward, double cameraAngleToTarget) {
        //Returns the distance from limelight target based on height 
        //of camera, height of target, angle from vertical center in radians, and 
        //limelight angle from horizontal in radians
        //SmartDashboard.putNumber("cameraAngleFromForward", cameraAngleFromForward);
        //SmartDashboard.putNumber("cameraAngleToTarget", cameraAngleToTarget);
        double distance = ((targetHeight - cameraHeight) / Math.tan(cameraAngleFromForward + cameraAngleToTarget));
        SmartDashboard.putNumber("Distance", distance);
        return distance;
    }
}
