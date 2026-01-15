package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
@Autonomous

public class  ZerexRedFarEncoder extends LinearOpMode {

    //
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
    //public VoltageSensor batteryVoltageSensor;



    @Override
    public void runOpMode() throws InterruptedException{

        FrontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotorEx.class, "backRight");
        BackLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        Intake = hardwareMap.get(DcMotorEx.class, "intake");
        RightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
        LeftShooter = hardwareMap.get(DcMotorEx.class,"leftShooter");
        Kicker = hardwareMap.get(DcMotorEx.class,"kicker");


        //batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

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

        LeftShooter.setVelocityPIDFCoefficients(1.2, 0.12, 0.0, 11.7);


        waitForStart();

        //double startVoltage = batteryVoltageSensor.getVoltage();

        // 30 ticks ~ an inch
        drive(1200,1200,1200,1200, 0.5);
        shoot(2450 ,3000);
        drive(225,-225,225,-225, 0.5);
        drive(500, -500,-500,500, speed);
        Intake.setPower(0.89);
        drive(-1200,-1200,-1200,-1200, 0.30);
        sleep(1000);
        Intake.setPower(0);
        drive(1111,1111,1111,1111, speed);
        drive(-444,444,444,-444, speed);

        drive(-300,300,-300,300  , 0.5);
        drive(-100,-100,-100,-100, speed);

        shoot(2450,3000);
    }

    public void drive(int FrontRightTarget, int FrontLeftTarget, int  BackRightTarget, int BackLeftTarget, double speed){
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


        while (opModeIsActive() && FrontRight.isBusy() || FrontLeft.isBusy() || BackRight.isBusy() || BackLeft.isBusy()) {
            idle();
        }

        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void shoot(double velocity, long time){
        //double currentVoltage = batteryVoltageSensor.getVoltage();
        //double shootPower = power * (12.8 / currentVoltage);

        RightShooter.setPower(velocity/2800);
        LeftShooter.setVelocity(velocity);
        telemetry.addData("Velocity", LeftShooter.getVelocity());
        telemetry.update();
        sleep(2500);
        Intake.setPower(1.0);
        Kicker.setPower(-0.8);
        sleep(time);
        RightShooter.setPower(0);
        LeftShooter.setPower(0);
        Intake.setPower(0);
        Kicker.setPower(0);


    }



}
