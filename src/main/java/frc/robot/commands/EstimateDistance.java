package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EstimateDistance {
    private static double cameraHeight = 33.5;

    public static double getDistance(double targetHeight, double cameraAngleFromForward, double cameraAngleToTarget) {
        cameraAngleFromForward = cameraAngleFromForward;
        SmartDashboard.putNumber("cameraAngleFromForward", cameraAngleFromForward);
        SmartDashboard.putNumber("cameraAngleToTarget", cameraAngleToTarget);
        return ((targetHeight - cameraHeight) / Math.tan(cameraAngleFromForward + cameraAngleToTarget));
        // return Math.abs((targetHeight - cameraHeight) /
        // Math.tan(cameraAngleFromForward + cameraAngleToTarget));
    }
}
