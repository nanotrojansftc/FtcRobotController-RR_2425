
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "TeleOpMain2_2425", group = "TeleOp")

public class TeleOpMain2_25 extends LinearOpMode {

    private final int READ_PERIOD = 1;

    //private DriveControl_NanoTorjan driveControl;
    private DriveControl_NanoTorjan driveControl;
    private controls_NanoTrojans g2control;
    private resources_NanoTrojans resources;

    private resources_base_NanoTrojans resourcesbase;
    boolean horizontalls = false;
    double clawpos = 0;
    double lhslpos = 0;
    double rhslpos = 0;
    double casketpos = 0;

    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        resources = new resources_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();
        clawpos = resources.claw.getPosition();
        lhslpos = resources.lhsl.getPosition();
        rhslpos = resources.rhsl.getPosition();

        casketpos = resources.claw.getPosition();

        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        g2control = new controls_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw,
                resources.lhsl, resources.rhsl, resources.rintakelift, resources.lintakelift, resources.intakewheels, resources.casket);

        waitForStart();
        Thread baseControlThread = new Thread(new baseControl());
        Thread hlsThread = new Thread(new hls());
        //Thread armControlThread = new Thread(new armControl());
        Thread lsControlThread = new Thread(new lsControl());
        Thread activeThread = new Thread(new active());
        Thread armThread = new Thread(new arm());

        //Start 2  threads
        baseControlThread.start();
        hlsThread.start();
        lsControlThread.start();
        activeThread.start();
        armThread.start();

        //empty thead do nothing at this time
        while (opModeIsActive()) {
        }

    }


    // The following is developed by NT , but we are not using it now
    // This is the thread class to control the base of the robot to move arround, this normally is
    // controlled by another person seperated from the base control person
    public class baseControl implements Runnable {

        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                driveControl.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //end of while loop

        }//end of run
    }//end of class baseControl

    public class hls implements Runnable {
        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.y) {
//                    if (!horizontalls) ;
//                    {
//                        g2control.horizontal_fw();
//                        sleep(100);
//
//                    }
//                    if (horizontalls) {
//                        g2control.horizontal_back();
//                        sleep(100);
//                    }
//                    horizontalls = !horizontalls;
                    //g2control.horizontal_fw();
                    resources.rhsl.setPosition(0);
                    resources.lhsl.setPosition(0.1);
                    //g2control.horizontal_fw();
                }
                if (gamepad2.b){
                    //g2control.horizontal_back();

                    resources.rhsl.setPosition(0.30);
                    resources.lhsl.setPosition(0.0);
                }
            }//end of while
        }//end of run
    }// end of thread horizontal linear slides

    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                double lspower = gamepad2.right_stick_y;
                resources.lsRight.setPower(-lspower);
                resources.lsLeft.setPower(-lspower);

            }//end of while
        }//end of run
    }//end of thread lscontrol

    private class active implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                double activepower = gamepad2.left_stick_y;
                resources.intakewheels.setPower(activepower);


            } //end of while loop
        }//end of run
    }//end of thread active intake

    private class arm implements Runnable {
        @Override
        public void run() {
            boolean casketup = false;
            boolean clawopen = false;
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                if (gamepad2.x){
                    if (!casketup ){
                        resources.casket.setPosition(0.5);
                        //g2control.casket_back();
                    }
                    else {
                        resources.casket.setPosition(0.2);
                        //g2control.casket_fw();
                    }
                    casketup=!casketup;


                }
                if (gamepad2.a){
                    if (!clawopen){
                        //g2control.closeclaw();
                        resources.claw.setPosition(1);
                        sleep(100);
                    }
                    else {
                        //g2control.openclaw();
                        resources.claw.setPosition(0.8);
                        sleep(100);
                    }
                    clawopen = !clawopen;
                }

                if (gamepad2.dpad_up){
                    //g2control.intakeup();
                    resources.lintakelift.setPosition(0.5);
                    resources.rintakelift.setPosition(0.5);
                }
                if (gamepad2.dpad_down){
                    //g2control.intakedown();
                    resources.lintakelift.setPosition(0.7);
                    resources.rintakelift.setPosition(0.3);
                }

                //for debugging
                boolean enableTel = false;
                if(enableTel) {
                    telemetry.addData("Claw position:", clawpos);
                    telemetry.addData("Left Horizontal Slide position:", lhslpos);
                    telemetry.addData("Right Horizontal Slide position:", rhslpos);
                    telemetry.addData("Casket position:",casketpos);
                    telemetry.update();
                }
            }//end of while loop
        }//end of run
    }// end of class arm

}//end of big class








