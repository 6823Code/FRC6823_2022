// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import frc.robot.subsystems.LimeLightSubsystem;
// import frc.robot.subsystems.ShooterSubsystem;
// import frc.robot.subsystems.SwerveDriveSubsystem;

// public class AutoAim3dAuto extends SequentialCommandGroup {
// private LimeLightSubsystem limeLightSubsystem;
// private SwerveDriveSubsystem swerveDriveSubsystem;
// private ShooterSubsystem shooterSubsystem;

// public AutoAim3dAuto(LimeLightSubsystem limeLightSubsystem, ShooterSubsystem
// shooterSubsystem,
// SwerveDriveSubsystem swerveDriveSubsystem, int position) {
// this.shooterSubsystem = shooterSubsystem;
// this.limeLightSubsystem = limeLightSubsystem;
// this.swerveDriveSubsystem = swerveDriveSubsystem;

// double distance = -56;
// double rpm = 8500;

// if (position == 0) {
// // distance = -56;
// // rpm = 5925;
// distance = -56;
// rpm = 5900;
// } else if (position == 1) {
// // distance = -105;
// // rpm = 6575;
// distance = -105;
// rpm = 6700;

// } else if (position == -1) {
// // distance = -37
// // distance = 6000
// distance = -43;
// rpm = 8750;
// }

// // super.addCommands(new MoveTo3d(swerveDriveSubsystem, limeLightSubsystem,
// 0,
// // distance), new ParallelRaceGroup(
// // new Shoot(shooterSubsystem, rpm, .8, 7), new JustAim(swerveDriveSubsystem,
// // limeLightSubsystem)));
// super.addCommands(
// new SequentialCommandGroup(new MoveTo3d(swerveDriveSubsystem,
// limeLightSubsystem, 0, distance),
// new WaitCommand(0.5), new Shoot(shooterSubsystem, rpm, .2, 8,
// swerveDriveSubsystem)));
// }
// }
// // package frc.robot.commands;

// // import edu.wpi.first.wpilibj.command.WaitCommand;
// // import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
// // import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// // import frc.robot.subsystems.LimeLightSubsystem;
// // import frc.robot.subsystems.ShooterSubsystem;
// // import frc.robot.subsystems.SwerveDriveSubsystem;

// // public class AutoAim3dAuto extends SequentialCommandGroup {
// // private LimeLightSubsystem limeLightSubsystem;
// // private SwerveDriveSubsystem swerveDriveSubsystem;
// // private ShooterSubsystem shooterSubsystem;

// // public AutoAim3dAuto(LimeLightSubsystem limeLightSubsystem,
// ShooterSubsystem
// // shooterSubsystem,
// // SwerveDriveSubsystem swerveDriveSubsystem, int position) {
// // this.shooterSubsystem = shooterSubsystem;
// // this.limeLightSubsystem = limeLightSubsystem;
// // this.swerveDriveSubsystem = swerveDriveSubsystem;

// // double distance = -56;
// // double rpm = 8500;

// // if (position == 0) {
// // distance = -56;
// // rpm = 5925;
// // } else if (position == 1) {
// // distance = -125;
// // rpm = 6575;
// // } else if (position == -1) {
// // distance = -43;
// // rpm = 8500;
// // }

// // // super.addCommands(new MoveTo3d(swerveDriveSubsystem,
// limeLightSubsystem,
// // 0,
// // // distance),
// // // new Shoot(shooterSubsystem, rpm, .8, 7), new
// JustAim(swerveDriveSubsystem,
// // // limeLightSubsystem)));
// // super.addCommands(new MoveTo3d(swerveDriveSubsystem, limeLightSubsystem,
// 0,
// // distance));
// // super.addCommands(new WaitCommand(0.5));
// // super.addCommands(new Shoot(shooterSubsystem, rpm, .8, 7));
// // }

// // // public void setPosition(int position) {
// // // super.clearGroupedCommands();
// // // double distance = -56;
// // // double rpm = 8500;

// // // if (position == 0) {
// // // distance = -56;
// // // rpm = 8500;
// // // } else if (position == 1) {
// // // distance = -170;
// // // rpm = 8850;
// // // }

// // // super.addCommands(new MoveTo3d(swerveDriveSubsystem,
// limeLightSubsystem,
// // 0,
// // // distance),
// // // new Shoot(shooterSubsystem, rpm));
// // // }
// // }
