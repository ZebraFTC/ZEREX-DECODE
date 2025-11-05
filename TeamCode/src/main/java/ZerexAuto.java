import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous


public class ZerexAuto extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor OutRight;
    public DcMotor OutLeft;
    public DcMotor TransferBelt;

    public void forward(long duration) {
        FrontRight.setPower(1);
        FrontLeft.setPower(1);
        BackRight.setPower(1);
        BackLeft.setPower(1);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void backwards(long duration) {
        FrontRight.setPower(-1);
        FrontLeft.setPower(-1);
        BackRight.setPower(-1);
        BackLeft.setPower(-1);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void left(long duration) {
        FrontRight.setPower(-1);
        FrontLeft.setPower(1);
        BackRight.setPower(-1);
        BackLeft.setPower(1);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void right(long duration) {
        FrontRight.setPower(1);
        FrontLeft.setPower(-1);
        BackRight.setPower(1);
        BackLeft.setPower(-1);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }



    @Override
    public void runOpMode() throws InterruptedException {


        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        OutRight = hardwareMap.get(DcMotor.class, "shooterRight");
        OutLeft  = hardwareMap.get(DcMotor.class, "shooterLeft");

        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        forward(3000);
        left(1000);
        Intake.setPower(1);
        forward(4000);
        Intake.setPower(0);
        backwards(3000);
        right(1000);
        forward(3000);
        //shoot
    }
}
