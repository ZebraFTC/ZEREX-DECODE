package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class ZerexTele2 extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor RightShooter;
    public DcMotorEx LeftShooter;
    public DcMotor Kicker;
    double shootSpeed;
    double driveSpeed;
    int RBispressed;

    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        RightShooter = hardwareMap.get(DcMotor.class, "rightShooter");
        LeftShooter = hardwareMap.get(DcMotorEx.class,"leftShooter");
        Kicker = hardwareMap.get(DcMotor.class,"kicker");

        //reverse wheel directions on the right
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        LeftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LeftShooter.setVelocityPIDFCoefficients(1.2, 0.12, 0.0, 11.7);

        waitForStart();

        while (opModeIsActive()) {

            double drive = driveSpeed * gamepad1.left_stick_y;
            double turn = -driveSpeed * gamepad1.right_stick_x;
            double strafe = driveSpeed * gamepad1.left_stick_x;
            FrontRight.setPower(drive - turn + strafe);
            FrontLeft.setPower(drive + turn - strafe);
            BackRight.setPower(drive - turn - strafe);
            BackLeft.setPower(drive + turn + strafe);


            //transfer
            if (gamepad2.left_trigger > 0) {
                Intake.setPower(1);
            } else {
                Intake.setPower(0);
            }

            //shooter
            if (gamepad2.rightBumperWasPressed()) { 
                RBispressed = 1 - RBispressed;
            }

            double leftVelocity = RBispressed * shootSpeed * 2800;

            RightShooter.setPower(leftVelocity / 2800);
            LeftShooter.setVelocity(leftVelocity);


            //kicker
            if(gamepad2.right_trigger > 0){
                Kicker.setPower(-1);
            } else {
                Kicker.setPower(0);
            }

            //reverse intake
            if (gamepad2.y) {
                Intake.setPower(-1);
            } else {
                Intake.setPower(0);
            }

            //25% speed
            if (gamepad2.dpad_down) {
                shootSpeed = 0.25;
            }
            //50% speed
            else if (gamepad2.dpad_left) {
                shootSpeed = 0.5;
            }
            //75% speed
            else if (gamepad2.dpad_right) {
                shootSpeed = 0.75 ;
            }
            //100% speed
            else if (gamepad2.dpad_up) {
                shootSpeed = 1.0;
            }
            //100% speed
            else {
                shootSpeed = 1.0;
            }


            if (gamepad1.right_trigger > 0) {
                driveSpeed = .5;
            }
            else {
                driveSpeed = 1;
            }

            if (gamepad2.x){
                Kicker.setPower(-1);
                sleep(50);
                Kicker.setPower(0);
            }

            if (gamepad2.b){
                LeftShooter.setPower(1);
                RightShooter.setPower(1);
            } else {
                LeftShooter.setPower(0);
                RightShooter.setPower(0);
            }

        }
    }
}

