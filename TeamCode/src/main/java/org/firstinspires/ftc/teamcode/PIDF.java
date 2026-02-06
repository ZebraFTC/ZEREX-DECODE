package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class PIDF extends LinearOpMode {

    public DcMotorEx RightShooter;
    public DcMotorEx LeftShooter;
    public DcMotor Intake;
    public DcMotor Kicker;
    double p;
    double i;
    double d;
    double f;
    int shootSpeed;

    @Override
    public void runOpMode() throws InterruptedException {

        RightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
        LeftShooter = hardwareMap.get(DcMotorEx.class,"leftShooter");
        Intake = hardwareMap.get(DcMotor.class,"intake");
        Kicker = hardwareMap.get(DcMotor.class, "kicker");

        RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        RightShooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        p = 0;
        i = 0;
        d = 0;
        f = 11.7;

        waitForStart();

        shootSpeed = 2000;
        while (opModeIsActive()) {

            // D-pad controls for tuning
            if (gamepad1.dpad_right) {
                p += 0.0005;
                sleep(250); // Debounce delay
            }
            if (gamepad1.dpad_left) {
                p -= 0.0001;
                sleep(250);
            }
            if (gamepad1.dpad_up) {
                f += 0.05;
                sleep(250);
            }
            if (gamepad1.dpad_down) {
                f -= 0.05;
                sleep(250);
            }
            if (gamepad1.left_bumper) {
                shootSpeed -= 10;
                sleep(250);
            }
            if (gamepad1.right_bumper) {
                shootSpeed += 10;
                sleep(250);
            }

            // Update PIDF coefficients
            LeftShooter.setVelocityPIDFCoefficients(p, i, d, f);

            // Shooter control
            if (gamepad1.a) {
                LeftShooter.setVelocity(shootSpeed);
                RightShooter.setPower(LeftShooter.getPower());
            } else {
                LeftShooter.setVelocity(0);
                RightShooter.setPower(0);
            }

            if (gamepad1.b) {
                Intake.setPower(1);
                Kicker.setPower(-1);
            } else {
                Intake.setPower(0);
                Kicker.setPower(0);
            }

            // Telemetry
            telemetry.addData("P", p);
            telemetry.addData("F", f);
            telemetry.addData("Left Velocity", LeftShooter.getVelocity());
            telemetry.addData("Right Velocity", RightShooter.getVelocity());
            telemetry.addData("shootSpeed",shootSpeed);
            telemetry.update();
        }
    }
}