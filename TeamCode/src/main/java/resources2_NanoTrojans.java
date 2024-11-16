import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources2_NanoTrojans {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public CRServo rintakelift = null;
    public CRServo lintakelift = null;

    //servo motors
//    public Servo rhsl = null;
//    public Servo lhsl = null;
    public Servo rhsl = null;
    //2 claws servo motors
    public Servo lhsl = null;
    public Servo claw = null;

    //2 arms servo motors
    public CRServo intakewheels = null;
    public Servo casket = null;



public resources2_NanoTrojans(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    claw = hardwareMap.servo.get("claw");
    intakewheels = hardwareMap.crservo.get("intake");

    rintakelift = hardwareMap.crservo.get("rintakelift");
    lintakelift = hardwareMap.crservo.get("lintakelift");

    lintakelift = hardwareMap.crservo.get("lintakelift");
    rintakelift = hardwareMap.crservo.get("rintakelift");
    rhsl = hardwareMap.servo.get("rhsl");
    lhsl = hardwareMap.servo.get("lhsl");
    casket = hardwareMap.servo.get("casket");

}


}
