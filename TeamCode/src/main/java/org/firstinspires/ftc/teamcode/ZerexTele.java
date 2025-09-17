package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class ZerexTele extends LinearOpMode {

    public DcMotor FrontRight;
    public DcMotor FrontLeft;
    public DcMotor BackRight;
    public DcMotor BackLeft;
    public DcMotor Intake;
    public DcMotor OutRight;
    public DcMotor OutLeft;

    @Override
    public void runOpMode() throws InterruptedException {

        FrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        BackRight = hardwareMap.get(DcMotor.class, "backRight");
        BackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        OutRight = hardwareMap.get(DcMotor.class, "rightOut");
        OutLeft = hardwareMap.get(DcMotor.class, "leftOut");

        waitForStart();

        while (opModeIsActive()) {

            double drive = gamepad1.left_stick_x;
            double turn = gamepad1.left_stick_y;

            FrontRight.setPower(drive - turn);
            FrontLeft.setPower(drive + turn);
            BackRight.setPower(drive - turn);
            BackLeft.setPower(drive + turn);

            if (gamepad2.left_bumper) {
                Intake.setPower(1);
            } else {
                Intake.setPower(0);
            }
            if (gamepad2.right_bumper) {
                OutRight.setPower(-1);
                OutLeft.setPower(1);
            } else {
                OutLeft.setPower(0);
                OutRight.setPower(0);
            }
         }
    }
}
