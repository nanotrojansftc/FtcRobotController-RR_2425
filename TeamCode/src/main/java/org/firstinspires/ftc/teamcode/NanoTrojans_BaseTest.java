package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "ChassisTest", group = "TeleOp")

public class NanoTrojans_BaseTest extends LinearOpMode {

    //private final int READ_PERIOD = 1;

    private DriveControl_NanoTorjan driveControl;
    private resources_base_NanoTrojans resourcesbase;


    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {
        //resources = new resources2_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.loggingEnabled = true;
//        parameters.loggingTag = "IMU";
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);

//        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
//        rateLimit.expire();

        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);

        waitForStart();
        Thread baseControlThread = new Thread(new baseControl());
        baseControlThread.start();

        //empty thead do nothing at this time
        while (opModeIsActive()) {
        }

    }


    public class baseControl implements Runnable {

        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                driveControl.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //end of while loop

        }//end of run
    }//end of class baseControl



}//end of big class









