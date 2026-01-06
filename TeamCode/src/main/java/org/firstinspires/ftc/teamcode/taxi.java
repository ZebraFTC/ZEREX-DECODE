package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous


public class taxi extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor Shooter;
    public DcMotor Transfer;






    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        Shooter = hardwareMap.get(DcMotor.class, "shooter");
        Transfer = hardwareMap.get(DcMotor.class, "belt");


        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        forward(666, .5);// taxi

    }

    public void forward(long duration, double power) {
        FrontRight.setPower(-power);
        FrontLeft.setPower(-power);
        BackRight.setPower(-power);
        BackLeft.setPower(-power);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void backwards(long duration) {
        FrontRight.setPower(0.5);
        FrontLeft.setPower(0.5);
        BackRight.setPower(0.5);
        BackLeft.setPower(0.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void left(long duration) {
        FrontRight.setPower(-0.5);
        FrontLeft.setPower(0.5);
        BackRight.setPower(-0.5);
        BackLeft.setPower(0.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void right(long duration) {
        FrontRight.setPower(0.5);
        FrontLeft.setPower(-0.5);
        BackRight.setPower(0.5);
        BackLeft.setPower(-0.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void shoot(double power, long time){
        Shooter.setPower(-power);
        sleep(2500);
        Intake.setPower(1.0);
        Transfer.setPower(1.0);
        sleep(time);
        turnOff();
    }

    public void turnOff(){
        Intake.setPower(0);
        Transfer.setPower(0);
        Shooter.setPower(0);
    }
}
