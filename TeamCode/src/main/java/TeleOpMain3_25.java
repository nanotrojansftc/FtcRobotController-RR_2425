import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.DriveControl_NanoTorjan;
import org.firstinspires.ftc.teamcode.controls_NanoTrojans;
import org.firstinspires.ftc.teamcode.resources_NanoTrojans;
import org.firstinspires.ftc.teamcode.resources_base_NanoTrojans;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "TeleOpMain3_2425", group = "TeleOp")

public class TeleOpMain3_25 extends LinearOpMode {

    private final int READ_PERIOD = 1;

    //private DriveControl_NanoTorjan driveControl;
    private DriveControl_NanoTorjan driveControl;
    private controls2_NanoTrojans g2control;
    private resources2_NanoTrojans resources;

    private resources_base_NanoTrojans resourcesbase;
    boolean horizontalls = false;
    boolean ls = false;
    double clawpos = 0;
    double lhslpos = 0;
    double rhslpos = 0;
    double casketpos = 0;
    double ractivepos = 0;
    double lactivepos = 0;

    BNO055IMU imu;


    @Override
    public void runOpMode() throws InterruptedException {
        resources = new resources2_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();



        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        g2control = new controls2_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw,
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
                driveControl.driveRobot(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //end of while loop

        }//end of run
    }//end of class baseControl

    public class hls implements Runnable {
        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                    resources.rhsl.setPosition(0);
                    resources.lhsl.setPosition(0);

//                double hlsbool = gamepad2.right_trigger;
////                if (hlsbool >=0.7){
////                    resources.rhsl.setPower(0);
////                    resources.lhsl.setPower(0);
////                }
//                resources.rhsl.setPower(hlsbool);
//                resources.lhsl.setPower(-hlsbool);
//
//                double hlsboolback = gamepad2.left_trigger;
//                resources.rhsl.setPower(-hlsboolback);
//                resources.lhsl.setPower(hlsboolback);
//                if (gamepad2.right_bumper){
//                    horizontalls=true;
//                }
//                if (gamepad2.left_bumper) {
//                    horizontalls=false;
//                }

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
//                    g2control.horizontal_fw();
                    resources.rhsl.setPosition(0.15);
                    resources.lhsl.setPosition(0.7);
//                    g2control.horizontal_fw();
                }
                if (gamepad2.b){
                    //g2control.horizontal_back();

                    resources.rhsl.setPosition(0.65);
                    resources.lhsl.setPosition(0.1);
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
//                if (horizontalls = false){
//                    double lspower = gamepad2.right_stick_y;
//                    resources.lsRight.setPower(-lspower);
//                    resources.lsLeft.setPower(-lspower);
//
//                }



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
                double activepower = gamepad2.left_stick_x;
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
                        resources.claw.setPosition(0.8);
                        sleep(250);
                        resources.casket.setPosition(0.25);
                        sleep(250);
                        //g2control.casket_back();
                    }
                    else {
                        resources.casket.setPosition(0.4);
                        sleep(250);
                        //g2control.casket_fw();
                    }
                    casketup=!casketup;


                }
                if (gamepad2.left_bumper){
                    resources.claw.setPosition(1);
//
//                    if (!clawopen){
//                        //g2control.closeclaw();
//                        resources.casket.setPosition(0.25);
//                        sleep(250);
//                        resources.claw.setPosition(1);
//                        sleep(100);
//                    }
//                    else {
//                        //g2control.openclaw();
//                        resources.claw.setPosition(0.8);
//                        sleep(100);
//                    }
//                    clawopen = !clawopen;
                }
                if (gamepad2.right_bumper){
                    resources.claw.setPosition(0.8);
                }

//                if (gamepad2.dpad_up){
//                    //g2control.intakeup();
//
//                }
                boolean liftpower = gamepad2.dpad_up;
                boolean neglift = gamepad2.dpad_down;
                if (liftpower){
                    resources.lintakelift.setPower(1);
                    resources.rintakelift.setPower(-1);

                }
                if (neglift){
                    resources.lintakelift.setPower(-1);
                    resources.rintakelift.setPower(1);
                }
                else
                    resources.lintakelift.setPower(0);
                    resources.rintakelift.setPower(0);

//                if (gamepad2.dpad_down){
//                    //g2control.intakedown();
//                    resources.lintakelift.setPosition(0.97);
//                    resources.rintakelift.setPosition(0.53);
//                }
//                if (gamepad2.dpad_right){
//                    resources.lintakelift.setPosition(0.95);
//                    resources.rintakelift.setPosition(0.55);
//                }

                //for debugging
                boolean enableTel = true;
                if(enableTel) {
//                    telemetry.addData("Claw position:", clawpos);
//                    telemetry.addData("Left Horizontal Slide position:", lhslpos);
//                    telemetry.addData("Right Horizontal Slide position:", rhslpos);
//                    telemetry.addData("Casket position:",casketpos);
                    telemetry.addData("right intake pos", ractivepos);
                    telemetry.addData("leftintake pos", lactivepos);

                    telemetry.update();


                }
            }//end of while loop
        }//end of run
    }// end of class arm

}//end of big class









