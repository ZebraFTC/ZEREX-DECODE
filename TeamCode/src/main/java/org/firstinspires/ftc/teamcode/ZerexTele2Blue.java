package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp

public class ZerexTele2Blue extends LinearOpMode {

    AprilTagTest aprilTagTest = new AprilTagTest();
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
    boolean isAutoAligning = false;
    boolean isRangeAligning = false;

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

        aprilTagTest.init(hardwareMap, telemetry);

        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        LeftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LeftShooter.setVelocityPIDFCoefficients(1.2, 0.12, 0.0, 12.25);

        waitForStart();

        while (opModeIsActive()) {
            // Update AprilTag detection every loop
            aprilTagTest.update();

            if (gamepad1.a) {
                isAutoAligning = true;
                isRangeAligning = false;
            }

            if (gamepad1.y) {
                isRangeAligning = true;
                isAutoAligning = false;
            }

            if (gamepad1.b || Math.abs(gamepad1.left_stick_y) > 0.1 ||
                    Math.abs(gamepad1.right_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_x) > 0.1) {
                isAutoAligning = false;
                isRangeAligning = false;
            }
            if (isAutoAligning) {
                autoAlign(20, 0, 1.5);
                telemetry.addData("Mode", "AUTO ALIGNING (BEARING)");
            } else if (isRangeAligning) {
                autoAlignRange(20, 40, 2.0);
                telemetry.addData("Mode", "AUTO ALIGNING (RANGE)");
            } else {
                double drive = driveSpeed * gamepad1.left_stick_y;
                double turn = -driveSpeed * gamepad1.right_stick_x;
                double strafe = driveSpeed * gamepad1.left_stick_x;
                FrontRight.setPower(drive - turn + strafe);
                FrontLeft.setPower(drive + turn - strafe);
                BackRight.setPower(drive - turn - strafe);
                BackLeft.setPower(drive + turn + strafe);
                telemetry.addData("Mode", "MANUAL DRIVE");
            }


            if (gamepad2.left_trigger > 0) {
                Intake.setPower(1);
            } else {
                Intake.setPower(0);
            }

            if (gamepad2.rightBumperWasPressed()) {
                RBispressed = 1 - RBispressed;
            }

            double leftVelocity = RBispressed * shootSpeed * 2800;

            RightShooter.setPower(leftVelocity / 2800);
            LeftShooter.setVelocity(leftVelocity);


            if(gamepad2.right_trigger > 0){
                Kicker.setPower(-1);
            } else {
                Kicker.setPower(0);
            }

            if (gamepad2.y) {
                Intake.setPower(-1);
            } else {
                Intake.setPower(0);
            }

            if (gamepad2.dpad_down) {
                shootSpeed = 0.25;
            }
            else if (gamepad2.dpad_left) {
                shootSpeed = 0.5;
            }
            else if (gamepad2.dpad_right) {
                shootSpeed = 0.75 ;
            }
            else if (gamepad2.dpad_up) {
                shootSpeed = 1.0;
            }
            else {
                shootSpeed = 0.80;

            }


            if (gamepad1.right_trigger > 0) {
                driveSpeed = .5;
            }
            else {
                driveSpeed = 1;
            }

            if (gamepad2.x){
                Kicker.setPower(-1);
                sleep(150);
                Kicker.setPower(0);
                sleep(500);
            }
            if (gamepad2.b){
                LeftShooter.setPower(1);
                RightShooter.setPower(1);
            } else {
                LeftShooter.setPower(0);
                RightShooter.setPower(0);
            }

            // Display AprilTag range only when both bumpers are pressed
            if (gamepad1.left_bumper && gamepad1.right_bumper) {
                displayAprilTagRange(20);
            } else {

            }

            telemetry.addData("Press A", "Auto-align bearing");
            telemetry.addData("Press Y", "Auto-align to 50\" range");
            telemetry.addData("Press B or move", "Cancel alignment");
            telemetry.update();

        }
    }

    public void displayAprilTagRange(int targetId) {
        AprilTagDetection detectedTag = aprilTagTest.getTagBySpecificId(targetId);

        telemetry.addLine("===== APRILTAG INFO =====");

        if (detectedTag == null) {
            telemetry.addData("Tag ID " + targetId, "NOT DETECTED");
            telemetry.addData("Range", "---");
            telemetry.addData("Bearing", "---");
            telemetry.addData("Elevation", "---");
        } else {
            telemetry.addData("Tag ID", detectedTag.id);
            if (detectedTag.metadata != null) {
                telemetry.addData("Tag Name", detectedTag.metadata.name);
            }

            if (detectedTag.ftcPose != null) {
                telemetry.addData("Range", "%.2f inches", detectedTag.ftcPose.range);
                telemetry.addData("Bearing", "%.2f°", detectedTag.ftcPose.bearing);
                telemetry.addData("Elevation", "%.2f°", detectedTag.ftcPose.elevation);
                telemetry.addData("X Position", "%.2f inches", detectedTag.ftcPose.x);
                telemetry.addData("Y Position", "%.2f inches", detectedTag.ftcPose.y);
            }
        }
        telemetry.addLine("========================");
    }

    public void autoAlign(int targetId, double targetBearing, double tolerance) {
        aprilTagTest.update();
        AprilTagDetection detectedTag = aprilTagTest.getTagBySpecificId(targetId);

        if (detectedTag == null) {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackRight.setPower(0);
            BackLeft.setPower(0);

            telemetry.addData("Align Status", "Searching for tag...");
            telemetry.addData("Target Tag ID", targetId);
            return;
        }

        double currentBearing = detectedTag.ftcPose.bearing;
        double error = targetBearing - currentBearing;

        if (Math.abs(error) <= tolerance) {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackRight.setPower(0);
            BackLeft.setPower(0);

            telemetry.addData("Align Status", "ALIGNED!");
            telemetry.addData("Current Bearing", "%.2f°", currentBearing);
        } else {
            double turnPower;
            if (Math.abs(error) < 0.5) {
                turnPower = 0;
            } else {
                turnPower = error * 0.02;

                if (Math.abs(turnPower) < 0.1 && Math.abs(turnPower) > 0) {
                    turnPower = Math.signum(turnPower) * 0.1;
                }

                turnPower = Math.max(-0.3, Math.min(0.3, turnPower));
            }

            FrontRight.setPower(turnPower);
            FrontLeft.setPower(-turnPower);
            BackRight.setPower(turnPower);
            BackLeft.setPower(-turnPower);

            telemetry.addData("Align Status", "Aligning...");
            telemetry.addData("Current Bearing", "%.2f°", currentBearing);
            telemetry.addData("Target Bearing", "%.2f°", targetBearing);
            telemetry.addData("Error", "%.2f°", error);
        }
    }

    public void autoAlignRange(int targetId, double targetRange, double tolerance) {
        aprilTagTest.update();
        AprilTagDetection detectedTag = aprilTagTest.getTagBySpecificId(targetId);

        if (detectedTag == null) {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackRight.setPower(0);
            BackLeft.setPower(0);

            telemetry.addData("Range Align Status", "Searching for tag...");
            telemetry.addData("Target Tag ID", targetId);
            return;
        }

        double currentRange = detectedTag.ftcPose.range;
        double error = targetRange - currentRange;

        if (Math.abs(error) <= tolerance) {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackRight.setPower(0);
            BackLeft.setPower(0);

            telemetry.addData("Range Align Status", "ALIGNED!");
            telemetry.addData("Current Range", "%.2f inches", currentRange);
            telemetry.addData("Target Range", "%.2f inches", targetRange);
        } else {
            double drivePower;
            if (Math.abs(error) < 1.0) {
                drivePower = 0;
            } else {
                drivePower = error * 0.015;

                if (Math.abs(drivePower) < 0.1 && Math.abs(drivePower) > 0) {
                    drivePower = Math.signum(drivePower) * 0.1;
                }

                drivePower = Math.max(-0.5, Math.min(0.5, drivePower));
            }

            // Drive forward/backward to adjust range
            FrontRight.setPower(drivePower);
            FrontLeft.setPower(drivePower);
            BackRight.setPower(drivePower);
            BackLeft.setPower(drivePower);

            telemetry.addData("Range Align Status", "Aligning...");
            telemetry.addData("Current Range", "%.2f inches", currentRange);
            telemetry.addData("Target Range", "%.2f inches", targetRange);
            telemetry.addData("Error", "%.2f inches", error);
            telemetry.addData("Drive Power", "%.2f", drivePower);
        }
    }
}