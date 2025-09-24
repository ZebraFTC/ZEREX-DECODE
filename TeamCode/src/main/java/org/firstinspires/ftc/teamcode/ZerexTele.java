package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class ZerexTele extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor OutRight;
    public DcMotor OutLeft;
    public Servo Kicker;
    double speed;

    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        OutRight = hardwareMap.get(DcMotor.class, "rightOut");
        OutLeft = hardwareMap.get(DcMotor.class, "leftOut");
        Kicker = hardwareMap.get(Servo.class,"kicker");

        waitForStart();

        while (opModeIsActive()) {

            double drive = gamepad1.left_stick_x;
            double turn = gamepad1.left_stick_y;

            FrontRight.setPower(drive + turn);
            FrontLeft.setPower(-drive - turn);
            BackRight.setPower(drive + turn);
            BackLeft.setPower(-drive - turn);

            if (gamepad2.left_bumper) {
                Intake.setPower(1);
            } else {
                Intake.setPower(0);
            }
            if (gamepad2.right_bumper) {
                OutRight.setPower(-speed);
                OutLeft.setPower(speed);
            } else {
                OutLeft.setPower(0);
                OutRight.setPower(0);
            }
            if (gamepad2.y) {
                Intake.setPower(-1);
            }
            else {
                Intake.setPower(0);
            }
            if (gamepad2.dpad_down){
                speed = 0.25;
            }
            else if (gamepad2.dpad_left){
                speed = 0.5;
            }
            else if (gamepad2.dpad_right){
                speed = 0.75;
            }
            else if(gamepad1.dpad_up){
                speed = 1;
            }
            else {
                speed = 0;
            }
            if (gamepad2.a){
                Kicker.setPosition(0.1);
            }
            if (gamepad2.b){
                Kicker.setPosition(0.4);
            }
            }
         }
    }

