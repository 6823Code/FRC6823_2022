// package frc.robot.commands;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import frc.robot.NavXHandler;
// import frc.robot.subsystems.LimeLightSubsystem;
// import frc.robot.subsystems.ShooterSubsystem;
// import frc.robot.subsystems.SwerveDriveSubsystem;

// public class DeterminePathandDoItCommand extends SequentialCommandGroup {
// private SwerveDriveSubsystem swerveDriveSubsystem;
// private NavXHandler navXHandler;
// private LimeLightSubsystem limeLightSubsystem;
// private ShooterSubsystem shooterSubsystem;
// private double angleToLemon;

// public DeterminePathandDoItCommand(SwerveDriveSubsystem swerveDriveSubsystem,
// NavXHandler navXHandler,
// LimeLightSubsystem limeLightSubsystem, ShooterSubsystem shooterSubsystem) {

// this.swerveDriveSubsystem = swerveDriveSubsystem;
// this.navXHandler = navXHandler;
// this.limeLightSubsystem = limeLightSubsystem;
// this.shooterSubsystem = shooterSubsystem;
// addRequirements(swerveDriveSubsystem, limeLightSubsystem);
// }

// private double margin = 3;
// private int path;

// @Override
// public void execute() {

// angleToLemon = AutoCommandGroup.getAngleToLemon();

// if (Math.abs(angleToLemon - 0.0) < margin) { // if it sees a ball directly
// ahead C3
// path = 1;
// } else if (Math.abs(angleToLemon - -11.3) < margin) {
// path = 3;
// } else {

// addCommands(new RotateDegrees(swerveDriveSubsystem, navXHandler, 27.0));

// addCommands(new ScanFieldForLemons(limeLightSubsystem));

// addCommands(new WaitCommand(0.5));

// angleToLemon = AutoCommandGroup.getAngleToLemon();
// if (Math.abs(angleToLemon - 26.5) < margin) {
// path = 2;
// } else {
// addCommands(new RotateDegrees(swerveDriveSubsystem, navXHandler, 0));
// path = 4;
// }

// }

// SmartDashboard.putNumber("Auto Path", path);
// SmartDashboard.putNumber("Auto Angle To Lemon", angleToLemon);

// // path plans
// if (path == 1) {
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // first ball

// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // second ball

// addCommands(new RotateLeftUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // third ball

// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// addCommands(new RotateToZero(swerveDriveSubsystem, navXHandler));
// addCommands(new GoForward(swerveDriveSubsystem, 2));
// } else if (path == 2) {// already facing ball
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // first ball

// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // second ball

// addCommands(new RotateLeftUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // third ball

// addCommands(new RotateToZero(swerveDriveSubsystem, navXHandler));
// addCommands(new GoForward(swerveDriveSubsystem, 2));

// } else if (path == 3) {
// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // first ball

// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new RotateLeftUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // second ball

// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // third ball

// addCommands(new RotateToZero(swerveDriveSubsystem, navXHandler));
// addCommands(new GoForward(swerveDriveSubsystem, 2));

// } else {
// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // first ball

// addCommands(new RotateLeftUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // second ball

// addCommands(new RotateRightUntillSeeBall(swerveDriveSubsystem,
// limeLightSubsystem));
// // addCommands(new GoForwardUntilSeeLemon(swerveDriveSubsystem,
// // limeLightSubsystem));
// addCommands(new LimeLightPickupBall(swerveDriveSubsystem, shooterSubsystem,
// limeLightSubsystem, 0));
// // third ball

// addCommands(new RotateToZero(swerveDriveSubsystem, navXHandler));
// addCommands(new GoForward(swerveDriveSubsystem, 2));

// }

// }
// }
