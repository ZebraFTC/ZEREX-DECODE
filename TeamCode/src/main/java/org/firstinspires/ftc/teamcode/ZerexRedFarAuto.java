package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous


public class ZerexRedFarAuto extends LinearOpMode  {

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

        backwards(750);
        shoot(-.777, 3000);
        backwards(222);
        right(222);
        sleep(3000);
        Intake.setPower(1);
        Transfer.setPower(1);

        forward(1111,.5);
        forward(1000,.125);
        turnOff();
        backwards(900);
        Transfer.setPower(-1);
        sleep(1000);
        turnOff();
        left(133);
        shoot(-.777,3000);

    }

    public void forward(long duration, double speed) {
        FrontRight.setPower(-speed);
        FrontLeft.setPower(-speed);
        BackRight.setPower(-speed);
        BackLeft.setPower(-speed);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void backwards(long duration) {
        FrontRight.setPower(.5);
        FrontLeft.setPower(.5);
        BackRight.setPower(.5);
        BackLeft.setPower(.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void left(long duration) {
        FrontRight.setPower(-.5);
        FrontLeft.setPower(.5);
        BackRight.setPower(-.5);
        BackLeft.setPower(.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void right(long duration) {
        FrontRight.setPower(.5);
        FrontLeft.setPower(-.5);
        BackRight.setPower(.5);
        BackLeft.setPower(-.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void leftStrafe(long duration){
        FrontRight.setPower(.5);
        FrontLeft.setPower(-.5);
        BackRight.setPower(-.5);
        BackLeft.setPower(.5);
        sleep(duration);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void shoot(double power, long time){
        Shooter.setPower(-power);
        sleep(2000);
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
