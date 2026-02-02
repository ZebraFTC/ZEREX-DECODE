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
    double p;
    double i;
    double d;
    double f;

    @Override
    public void runOpMode() throws InterruptedException {

        RightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
        LeftShooter = hardwareMap.get(DcMotorEx.class,"leftShooter");

        RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        RightShooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        p = 0;
        i = 0;
        d = 0;
        f = 11.7;

        waitForStart();

        while (opModeIsActive()) {

            // D-pad controls for tuning
            if (gamepad1.dpad_right) {
                p += 0.05;
                sleep(150); // Debounce delay
            }
            if (gamepad1.dpad_left) {
                p -= 0.05;
                sleep(150);
            }
            if (gamepad1.dpad_up) {
                f += 0.05;
                sleep(150);
            }
            if (gamepad1.dpad_down) {
                f -= 0.05;
                sleep(150);
            }

            // Update PIDF coefficients
            LeftShooter.setVelocityPIDFCoefficients(p, i, d, f);

            // Shooter control
            if (gamepad1.a) {
                LeftShooter.setVelocity(2800);
                RightShooter.setPower(LeftShooter.getPower());
            } else {
                LeftShooter.setVelocity(0);
                RightShooter.setPower(0);
            }

            // Telemetry
            telemetry.addData("P", p);
            telemetry.addData("F", f);
            telemetry.addData("Left Velocity", LeftShooter.getVelocity());
            telemetry.addData("Right Velocity", RightShooter.getVelocity());
            telemetry.update();
        }
    }
}