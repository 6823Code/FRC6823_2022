package frc.robot.util;

public class LimelightTools {
    public static double distFromTower(double angle){ //returns horizontal distance in meters, angle from -0.5 to 0.5
        final double HEIGHT = 2.6416;
        return Math.sin(0.5 - angle) * (HEIGHT / Math.sin(angle)); //Law of sines
    }
}
