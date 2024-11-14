package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvWebcam;

//import teamcode.OpenCVExt.LCamConeLocDetection;

public class resources_base_NanoTrojans {
    static final double COUNTS_PER_REVOLUTION = 537.7; // Encoder counts per revolution
    static final double WHEEL_DIAMETER_MM = 96.0; // Wheel diameter in millimeters
    static final double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI; // Wheel circumference
    static final double COUNTS_PER_MM = COUNTS_PER_REVOLUTION / MM_PER_REVOLUTION; // Counts per millimeter
    static final double COUNTS_PER_INCH = COUNTS_PER_MM * 25.4; // Counts per inch
    OpenCvWebcam webcam2;
    //LCamConeLocDetection pipeline2;
    //LCamConeLocDetection.LSideConePosition position2 = LCamConeLocDetection.LSideConePosition.OTHER;
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    //private BNO055IMU imu;
public resources_base_NanoTrojans(HardwareMap hardwareMap){
    leftFront = hardwareMap.get(DcMotor.class,"leftFront");
    rightFront = hardwareMap.get(DcMotor.class, "rightBack");
    leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    rightBack = hardwareMap.get(DcMotor.class, "rightFront");

    // Set motor directions (adjust as needed based on your robot configuration)
    leftFront.setDirection(DcMotor.Direction.REVERSE);
    //rightFront.setDirection(DcMotor.Direction.FORWARD);
    leftBack.setDirection(DcMotor.Direction.REVERSE);
    //rightBack.setDirection(DcMotor.Direction.FORWARD);

    //get imu resource fron control hub
//    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//    parameters.loggingEnabled = true;
//    parameters.loggingTag = "IMU";
//    imu = hardwareMap.get(BNO055IMU.class, "imu");
//    imu.initialize(parameters);

}
}
