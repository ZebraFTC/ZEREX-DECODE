package org.firstinspires.ftc.teamcode.ZeboltsOffseasonTemp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class ZerexTestTele extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    private double drive;
    private double strafe;
    private double turn;
    private final double SPEED = 1.0;


    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        drive = -SPEED * gamepad1.left_stick_y; //y inputs are reversed
        //strafe = SPEED * gamepad1.left_stick_x;
        turn = SPEED * gamepad1.right_stick_x;

        frontLeft.setPower(drive + turn); // +strafe
        frontRight.setPower(drive - turn); // - strafe
        backLeft.setPower(drive + turn); // - strafe
        backRight.setPower(drive - turn); // + strafe
    }
}

