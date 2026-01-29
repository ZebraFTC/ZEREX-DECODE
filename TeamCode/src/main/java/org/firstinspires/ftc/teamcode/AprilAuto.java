package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilAuto extends OpMode {
    AprilTagTest aprilTagTest = new AprilTagTest();

    @Override
    public void init() {
        aprilTagTest.init(hardwareMap,telemetry);
    }

    @Override
    public void loop() {
        aprilTagTest.update();
        AprilTagDetection id20 = aprilTagTest.getTagBySpecificId(20);

        if (id20 != null) {
            telemetry.addData("id20 string", id20.toString());
        } else {
            telemetry.addData("id20 status", "Tag 20 not found");
        }

        telemetry.update();
    }
}
