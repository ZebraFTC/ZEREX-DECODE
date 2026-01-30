package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class ZerexRedFarApril extends LinearOpMode {
    AprilTagTest aprilTagTest = new AprilTagTest();
    public DcMotorEx FrontRight;
    public DcMotorEx FrontLeft;
    public DcMotorEx BackRight;
    public DcMotorEx BackLeft;
    public DcMotorEx Intake;
    public DcMotorEx RightShooter;
    public DcMotorEx LeftShooter;
    public DcMotorEx Kicker;
    private int FrontRightPosition;
    private int FrontLeftPosition;
    private int BackRightPosition;
    private int BackLeftPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        FrontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotorEx.class, "backRight");
        BackLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        Intake = hardwareMap.get(DcMotorEx.class, "intake");
        RightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
        LeftShooter = hardwareMap.get(DcMotorEx.class, "leftShooter");
        Kicker = hardwareMap.get(DcMotorEx.class, "kicker");

        aprilTagTest.init(hardwareMap, telemetry);

        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeft.setTargetPositionTolerance(15);
        FrontRight.setTargetPositionTolerance(15);
        BackLeft.setTargetPositionTolerance(15);
        BackRight.setTargetPositionTolerance(15);

        FrontRightPosition = 0;
        FrontLeftPosition = 0;
        BackRightPosition = 0;
        BackLeftPosition = 0;

        double speed = 0.75;

        LeftShooter.setVelocityPIDFCoefficients(1.2, 0.15, 0.0, 11.7);

        waitForStart();

        drive(1111,1111,1111,1111, 0.7);
        continuousAlignToTag(24,7,2,3);
        autoAlignRange(24,30,1);
        shoot(2100 ,3000);
        drive(230,-230,230,-230, 0.7);
        drive(600,-600,-600,600 , 0.7);
        Intake.setPower(0.78);
        drive(-999,-999,-999,-999, 0.25);
        drive(111,111,111,111,0.25);
        sleep(500);
        Intake.setPower(0);
        drive(1000,1000,1000,1000, 0.7);
        drive(-350,350,350,-400, 0.7);
        drive(-250,250,-250,250, 0.7);
        drive(-100,-100,-100,-100 , 0.7);
        continuousAlignToTag(24,7,2 ,3);
        autoAlignRange(24,30,1);
        shoot(2500,3000);
        drive(233,-233,233,-233  , 0.7);
        drive(800,-800,-800,800,1.0);

    }

    public void drive(int FrontRightTarget, int FrontLeftTarget, int BackRightTarget, int BackLeftTarget, double speed) {
        FrontRightPosition += FrontRightTarget;
        FrontLeftPosition += FrontLeftTarget;
        BackRightPosition += BackRightTarget;
        BackLeftPosition += BackLeftTarget;

        FrontRight.setTargetPosition(FrontRightPosition);
        FrontLeft.setTargetPosition(FrontLeftPosition);
        BackRight.setTargetPosition(BackRightPosition);
        BackLeft.setTargetPosition(BackLeftPosition);

        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontRight.setPower(speed);
        FrontLeft.setPower(speed);
        BackRight.setPower(speed);
        BackLeft.setPower(speed);

        double timeout = getRuntime() + 5.0;

        while (opModeIsActive() && (FrontRight.isBusy() || FrontLeft.isBusy() || BackRight.isBusy() || BackLeft.isBusy()) && getRuntime() < timeout) {
            aprilTagTest.update();
            idle();
        }

        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void shoot(double velocity, long time) {
        RightShooter.setPower(velocity / 2800);
        LeftShooter.setVelocity(velocity);

        double spinTime = getRuntime() + 2.5;
        while (opModeIsActive() && getRuntime() < spinTime) {
            telemetry.addData("Actual Velocity", LeftShooter.getVelocity());
            telemetry.update();
        }

        Intake.setPower(1.0);
        Kicker.setPower(-0.8);

        double fireTime = getRuntime() + (time / 1000.0);
        while (opModeIsActive() && getRuntime() < fireTime) {
            telemetry.addData("Actual Velocity", LeftShooter.getVelocity());
            telemetry.update();
        }

        RightShooter.setPower(0);
        LeftShooter.setVelocity(0);
        Intake.setPower(0);
        Kicker.setPower(0);

        telemetry.addData("Actual Velocity", LeftShooter.getVelocity());
        telemetry.update();
    }

    public void continuousAlignToTag(int targetId, double targetBearing, double tolerance, double maxDuration) {
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double endTime = getRuntime() + maxDuration;
        boolean aligned = false;
        int consecutiveAlignedCount = 0;
        final int REQUIRED_ALIGNED_READINGS = 2;

        while (opModeIsActive() && getRuntime() < endTime && !aligned) {
            aprilTagTest.update();
            AprilTagDetection detectedTag = aprilTagTest.getTagBySpecificId(targetId);

            if (detectedTag == null) {
                FrontRight.setPower(0);
                FrontLeft.setPower(0);
                BackRight.setPower(0);
                BackLeft.setPower(0);
                consecutiveAlignedCount = 0;

                telemetry.addData("Status", "Searching for tag...");
                telemetry.addData("Target Tag ID", targetId);
                telemetry.update();
                continue;
            }

            double currentBearing = detectedTag.ftcPose.bearing;
            double error = targetBearing - currentBearing;

            if (Math.abs(error) <= tolerance) {
                consecutiveAlignedCount++;
                if (consecutiveAlignedCount >= REQUIRED_ALIGNED_READINGS) {
                    aligned = true;
                    FrontRight.setPower(0);
                    FrontLeft.setPower(0);
                    BackRight.setPower(0);
                    BackLeft.setPower(0);

                    telemetry.addData("Status", "ALIGNED!");
                    telemetry.addData("Final Bearing", "%.2f", currentBearing);
                    telemetry.update();
                    break;
                }

                FrontRight.setPower(0);
                FrontLeft.setPower(0);
                BackRight.setPower(0);
                BackLeft.setPower(0);

                telemetry.addData("Status", "Verifying alignment...");
                telemetry.addData("Stable Count", "%d/%d", consecutiveAlignedCount, REQUIRED_ALIGNED_READINGS);
            } else {
                consecutiveAlignedCount = 0;

                double turnPower;
                if (Math.abs(error) < 0.5) {
                    turnPower = 0;
                } else {
                    turnPower = error * 0.05;
                    if (Math.abs(turnPower) < 0.15 && Math.abs(turnPower) > 0) {
                        turnPower = Math.signum(turnPower) * 0.15;
                    }
                    turnPower = Math.max(-0.6, Math.min(0.6, turnPower));
                }

                FrontRight.setPower(turnPower);
                FrontLeft.setPower(-turnPower);
                BackRight.setPower(turnPower);
                BackLeft.setPower(-turnPower);

                telemetry.addData("Status", "Aligning...");
            }

            telemetry.addData("Current Bearing", "%.2f°", currentBearing);
            telemetry.addData("Target Bearing", "%.2f°", targetBearing);
            telemetry.addData("Error", "%.2f°", error);
            telemetry.addData("Time Remaining", "%.1fs", endTime - getRuntime());
            telemetry.update();
        }

        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);

        if (!aligned) {
            telemetry.addData("Warning", "Alignment timeout - not fully aligned");
            telemetry.update();
        }
    }

    public void alignToTag(int targetId, double targetBearing, double tolerance) {
        aprilTagTest.update();
        AprilTagDetection detectedTag = aprilTagTest.getTagBySpecificId(targetId);

        if (detectedTag == null) {
            telemetry.addData("Align Status", "Tag not found");
            telemetry.update();
            return;
        }

        double currentBearing = detectedTag.ftcPose.bearing;
        double timeout = getRuntime() + 3.0;

        while (opModeIsActive() && Math.abs(currentBearing - targetBearing) > tolerance && getRuntime() < timeout) {
            aprilTagTest.update();
            detectedTag = aprilTagTest.getTagBySpecificId(targetId);

            if (detectedTag == null) {
                break;
            }

            currentBearing = detectedTag.ftcPose.bearing;
            double error = targetBearing - currentBearing;
            double turnPower = Math.max(-0.3, Math.min(0.3, error * 0.02));

            FrontRight.setPower(-turnPower);
            FrontLeft.setPower(turnPower);
            BackRight.setPower(-turnPower);
            BackLeft.setPower(turnPower);

            telemetry.addData("Current Bearing", "%.2f", currentBearing);
            telemetry.addData("Target Bearing", "%.2f", targetBearing);
            telemetry.addData("Error", "%.2f", error);
            telemetry.update();
        }

        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
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
