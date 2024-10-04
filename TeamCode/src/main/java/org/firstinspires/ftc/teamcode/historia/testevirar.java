package org.firstinspires.ftc.teamcode.historia;

import java.util.Random;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.autonomos.RoboAutonomous;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
@Disabled
@Autonomous(name = "teste curva :)")

public class testevirar extends LinearOpMode {

    DcMotor motorFE;
    DcMotor motorFD;
    DcMotor motorTD;
    DcMotor motorTE;
    BNO055IMU imu;
    Orientation angles;
    BNO055IMU.Parameters parameters;

    DistanceSensor distance;

    RoboAutonomous robo;

    public void runOpMode() throws InterruptedException {

        telemetry.addData("init","ok" );


        //hardwaremap
        motorFD = hardwareMap.dcMotor.get("motorFD");
        motorFE = hardwareMap.dcMotor.get("motorFE");
        motorTD = hardwareMap.dcMotor.get("motorTD");
        motorTE = hardwareMap.dcMotor.get("motorTE");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robo = new RoboAutonomous(motorFD, motorFE, motorTD, motorTE,imu);
        telemetry.addData("Angulo:", angles.firstAngle);
        telemetry.update();

        waitForStart();


        sleep(500);
        robo.andarLado(500, 1, 0);
        sleep(1000);
        robo.andarLado(-400, -1, 0);
        //robo.virar(-90, 0.1, 0.45, telemetry, imu);






    }
}


