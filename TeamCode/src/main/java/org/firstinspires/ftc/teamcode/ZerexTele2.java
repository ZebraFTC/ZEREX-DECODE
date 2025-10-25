package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class ZerexTele2 extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
//  public DcMotor OutRight;
//  public DcMotor OutLeft;
    public DcMotor TransferBelt;
//    public Servo Kicker;
    double speed;

    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        //OutRight = hardwareMap.get(DcMotor.class, "rightOut");
        //OutLeft = hardwareMap.get(DcMotor.class, "leftOut");
        TransferBelt = hardwareMap.get(DcMotor.class, "belt");

        //reverse wheel directions on the right
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
         TransferBelt.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();

        while (opModeIsActive()) {

            double drive = 0.6 * gamepad1.left_stick_y;
            double turn = -0.6 * gamepad1.right_stick_x;
            double strafe = 0.6 * gamepad1.left_stick_x;

            FrontRight.setPower(drive - turn + strafe);
            FrontLeft.setPower(drive + turn - strafe);
            BackRight.setPower(drive - turn - strafe);
            BackLeft.setPower(drive + turn + strafe);

            //intake
            if (gamepad2.left_bumper) {
                Intake.setPower(1);
                TransferBelt.setPower(1);
            } else {
                Intake.setPower(0);
                TransferBelt.setPower(0);
            }

            //shooter
            // if (gamepad2.right_bumper) {
            //  OutRight.setPower(-speed);
            //  OutLeft.setPower(speed);
            // }
            //  else {
            // OutLeft.setPower(0);
            // OutRight.setPower(0);
            //}

            //reverse intake
            if (gamepad2.y) {
                Intake.setPower(-1);
                TransferBelt.setPower(-1);
            } else {
                Intake.setPower(0);
                TransferBelt.setPower(0);
            }

            //25% speed
            if (gamepad2.dpad_down) {
                speed = 0.25;
            }
            //50% speed
            else if (gamepad2.dpad_left) {
                speed = 0.5;
            }
            //75% speed
            else if (gamepad2.dpad_right) {
                speed = 0.75;
            }
            //100% speed
            else if (gamepad2.dpad_up) {
                speed = 1;
            }
            //45% speed
            else {
                speed = 0.45;
            }

            if (gamepad2.a) {
//                Kicker.setPosition(0.7); // kicker up position
            } else {
//                Kicker.setPosition(.95); //kicker down position
            }

        }
    }
}

