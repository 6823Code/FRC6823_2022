package frc.robot.util;

public class LimelightTools {
    public static double distFromTower(double angle){ //returns horizontal distance in meters, angle from -0.5 to 0.5, should only receive values between zero and 0.25
        final double HEIGHT = 2.6416;
        final double LL_HEIGHT = 0.7;
        double heightDiff = HEIGHT - LL_HEIGHT;
        return Math.sin((0.25 - angle) * Math.PI) * heightDiff / Math.sin(angle * Math.PI); //Law of sines
    }
}
