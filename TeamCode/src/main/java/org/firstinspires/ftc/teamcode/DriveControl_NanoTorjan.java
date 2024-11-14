package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DriveControl_NanoTorjan {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    //private BNO055IMU imu;
    private Orientation angles;


    double y ; // Remember, this is reversed!
    double x ; // Counteract imperfect strafing
    double rx;

    double denominator;

    double leftFrontPower ;
    double leftBackPower ;
    double rightFrontPower ;
    double rightBackPower ;

    public DriveControl_NanoTorjan(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        this.leftFront = fl;
        this.rightFront = fr;
        this.leftBack = bl;
        this.rightBack = br;
        //.imu = imuInstance;
    }

    public void driveRobot(double leftStickX, double leftStickY, double rightStickX) {
         y = -leftStickY; // Remember, this is reversed!
         x = leftStickX * 1.1; // Counteract imperfect strafing
         rx = rightStickX;   // Right Stick X value is used to control rotation of the roberts

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //Victor: max value for all the settings, Left Y, left X and Right x
         denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        // Here are possible scenarios
        // for wheel moving direction follow the following link
        // https://www.gobilda.com/strafer-chassis-kit-140mm-mecanum-wheels/
        // 1. Only move forward and backward
        //    then x and rx value is 0
        // 2. only Strafing
        //    then y and rx will be 0

//         leftFrontPower = ((y - x - rx) / denominator)*0.5;
//         leftBackPower = ((y + x - rx) / denominator)*0.5;
//        rightFrontPower = ((y - x + rx) / denominator)*0.5;
//         rightBackPower = ((y + x + rx) / denominator)*0.5;

        leftFrontPower = (y - x - rx) / denominator;
        leftBackPower = (y + x - rx) / denominator;
        rightFrontPower = (y - x + rx) / denominator;
        rightBackPower = (y + x + rx) / denominator;


        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }
}