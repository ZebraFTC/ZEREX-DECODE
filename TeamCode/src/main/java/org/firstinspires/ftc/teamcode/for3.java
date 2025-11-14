package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous


public class for3 extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor Shooter;
    public DcMotor TransferBelt;

    public void forward(long duration) {
        FrontRight.setPower(-.5);
        FrontLeft.setPower(-.5);
        BackRight.setPower(-.5);
        BackLeft.setPower(-.5);
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



    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        Shooter = hardwareMap.get(DcMotor.class, "shooter");


        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        Shooter.setPower(-1);
        sleep(5000);
        Shooter.setPower(0);
    }
}
