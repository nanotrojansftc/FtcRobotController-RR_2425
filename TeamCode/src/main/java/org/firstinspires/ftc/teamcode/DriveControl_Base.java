package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


//This class worked for road runner season 2024-2025
public class DriveControl_Base {
    private MecanumDrive drive;

    public DriveControl_Base(HardwareMap hardwareMap)
    {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
    }

    public void driveRobot(double leftStickX, double leftStickY, double rightStickX) {
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -leftStickY,
                        -leftStickX
                ),
                -rightStickX
        ));
        drive.updatePoseEstimate();
        telemetry.addData("x", drive.pose.position.x);
        telemetry.addData("y", drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
        telemetry.update();

    }

}
