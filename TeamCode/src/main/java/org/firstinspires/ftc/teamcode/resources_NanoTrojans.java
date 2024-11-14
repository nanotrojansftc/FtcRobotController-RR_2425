package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources_NanoTrojans {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public Servo rintakelift = null;
    public Servo lintakelift = null;

    //servo motors
    public Servo rhsl = null;
    //2 claws servo motors
    public Servo lhsl = null;
    public Servo claw = null;

    //2 arms servo motors
    public CRServo intakewheels = null;
    public Servo casket = null;



public resources_NanoTrojans(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    claw = hardwareMap.servo.get("claw");
    intakewheels = hardwareMap.crservo.get("intake");

    rintakelift = hardwareMap.servo.get("rintakelift");
    lintakelift = hardwareMap.servo.get("lintakelift");

    lintakelift = hardwareMap.servo.get("lintakelift");
    rintakelift = hardwareMap.servo.get("rintakelift");
    rhsl = hardwareMap.servo.get("rhsl");
    lhsl = hardwareMap.servo.get("lhsl");
    casket = hardwareMap.servo.get("casket");

}
}
