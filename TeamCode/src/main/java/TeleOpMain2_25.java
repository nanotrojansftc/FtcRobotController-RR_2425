//package teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.DriveControl;
import org.firstinspires.ftc.teamcode.controls_NanoTrojans;
import org.firstinspires.ftc.teamcode.resources_NanoTrojans;
import org.firstinspires.ftc.teamcode.resources_base_NanoTrojans;

import java.util.concurrent.TimeUnit;


//import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp(name = "TeleOpMain2_2425", group = "TeleOp")



public class TeleOpMain2_25 extends LinearOpMode {


    private final int READ_PERIOD = 1;


    //private DriveControl_NanoTorjan driveControl;
    private DriveControl driveControl;

    private controls_NanoTrojans g2control;


    private resources_NanoTrojans resources;

    private resources_base_NanoTrojans resourcesbase;
    boolean horizontalls = false;
    double clawpos = 0;
    double lhslpos = 0;
    double rhslpos = 0;
    double casketpos = 0;

    BNO055IMU imu;

//    CRServo intakewheel = hardwareMap.get(CRServo.class, "intakewheel");


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

        driveControl = new DriveControl(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack, imu);
        g2control = new controls_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw,
                resources.lhsl, resources.rhsl, resources.rintakelift, resources.lintakelift, resources.intakewheels, resources.casket);

        waitForStart();
        Thread baseControlThread = new Thread(new baseControl());
        Thread hlsThread = new Thread(new hls());
//        Thread armControlThread = new Thread(new armControl());
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
            }

        }//end of class baseControl


    }

    public class hls implements Runnable {
        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.y) {
                    if (!horizontalls) ;
                    {
                        g2control.horizontal_fw();
                    }
                    if (horizontalls) {
                        g2control.horizontal_back();
                    }
                    horizontalls = !horizontalls;
                }
            }
        }


    }// end of thread horizontal linear slides

    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                double lspower = gamepad2.right_stick_y;
                resources.lsRight.setPower(lspower);
                resources.lsLeft.setPower(-lspower);

            }
        }
    }//end of thread lscontrol

    private class active implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {


            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.left_stick_y > 0) {
                    g2control.iwheels();
                }
                if (gamepad2.left_stick_y < 0) {
                    g2control.iwheelback();
                }
                else {
                    g2control.iwheelstop();
                }
//
            }
        }
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
                        g2control.casket_back();
                    }
                    else {
                        g2control.casket_fw();
                    }
                    casketup=!casketup;


                }
                if (gamepad2.a){
                    if (!clawopen){
                        g2control.closeclaw();
                    }
                    else {
                        g2control.openclaw();
                    }
                    clawopen = !clawopen;
                }

                if (gamepad2.dpad_up){
                    g2control.intakeup();
                }
                if (gamepad2.dpad_down){
                    g2control.intakedown();
                }



                boolean enableTel = false;


                if(enableTel) {
//
                    telemetry.addData("Claw position:", clawpos);
                    telemetry.addData("Left Horizontal Slide position:", lhslpos);
                    telemetry.addData("Right Horizontal Slide position:", rhslpos);
                    telemetry.addData("Casket position:",casketpos);






                    telemetry.update();


                }


            }

        }








    }

}//end of big class









