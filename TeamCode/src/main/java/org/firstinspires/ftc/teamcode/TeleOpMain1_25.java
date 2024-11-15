package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp(name = "TeleOpMain1_2425", group = "TeleOp")

public class TeleOpMain1_25 extends LinearOpMode {

    private DriveControl_NanoTorjan baseDriveControl;
    private controls_NanoTrojans g2control;
    private resources_NanoTrojans resources;
    private resources_base_NanoTrojans resourcesbase;

    double clawpos = 0;
    double lhslpos = 0;
    double rhslpos = 0;
    double casketpos = 0;

    //BNO055IMU imu;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        resources = new resources_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.loggingEnabled = true;
//        parameters.loggingTag = "IMU";
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);

        clawpos = resources.claw.getPosition();
        lhslpos = resources.lhsl.getPosition();
        rhslpos = resources.rhsl.getPosition();

        casketpos = resources.claw.getPosition();

        baseDriveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        g2control = new controls_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw,
                       resources.lhsl, resources.rhsl, resources.rintakelift, resources.lintakelift, resources.intakewheels, resources.casket);

        waitForStart();
        Thread baseControlThread = new Thread(new baseControl());
//        Thread armControlThread = new Thread(new armControl());
//        Thread lsControlThread = new Thread(new lsControl());
//        Thread lsControl2 = new Thread(new lsControl2());

        //Start 2  threads , this use the nano_trojan base drive algorithem
        //baseControlThread.start();

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        while (opModeIsActive())
        {
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));

            drive.updatePoseEstimate();

            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.update();
        }

    }


    // The following is developed by NT , but we are not using it now
    // This is the thread class to control the base of the robot to move arround, this normally is
    // controlled by another person seperated from the base control person
    // use the nano_trojan base drive algorithem
    public class baseControl implements Runnable {
        //boolean droneLaunced = false;

        @Override
        public void run() {
            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                telemetry.addData("x", gamepad1.left_stick_x);
                telemetry.addData("y", gamepad1.left_stick_y);
                baseDriveControl.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

            }

        }//end of run
    }//end of class baseControl


}//end of big class  TeleOpMain1_25









