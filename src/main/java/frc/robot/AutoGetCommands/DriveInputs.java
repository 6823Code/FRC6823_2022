package frc.robot.AutoGetCommands;


public class DriveInputs {
    
    public final double[] input;
    public final double time;

    public DriveInputs(double x, double y, double spin, double time)
    {
        input = new double[3];
        input[0] = x;
        input[1] = y;
        input[2] = spin;
        this.time = time;
    }
}
