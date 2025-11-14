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
    public DcMotor Shooter;
    public DcMotor TransferBelt;

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
        Shooter = hardwareMap.get(DcMotor.class, "shooter");
        TransferBelt = hardwareMap.get(DcMotor.class, "belt");

        //reverse wheel directions on the right
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        TransferBelt.setDirection(DcMotorSimple.Direction.REVERSE);
        Shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double drive = driveSpeed * gamepad1.left_stick_y;
            double turn = -driveSpeed * gamepad1.right_stick_x;
            double strafe = driveSpeed * gamepad1.left_stick_x;
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
            if (gamepad2.rightBumperWasPressed()) {
                RBispressed = 1 - RBispressed;
            }

            Shooter.setPower(RBispressed * shootSpeed);

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
                shootSpeed = 0.25;
            }
            //50% speed
            else if (gamepad2.dpad_left) {
                shootSpeed = 0.5;
            }
            //75% speed
            else if (gamepad2.dpad_right) {
                shootSpeed = 0.75;
            }
            //100% speed
            else if (gamepad2.dpad_up) {
                shootSpeed = 1;
            }
            //50% speed
            else {
                shootSpeed = 0.5;
            }


            if (gamepad1.right_trigger > 0) {
                driveSpeed = .5;
            }
            else {
                driveSpeed = 1;
            }
        }
    }
}

