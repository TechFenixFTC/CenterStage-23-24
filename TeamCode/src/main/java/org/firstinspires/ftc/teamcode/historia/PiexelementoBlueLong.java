package org.firstinspires.ftc.teamcode.historia;

import java.util.Random;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.autonomos.RoboAutonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
/*
@Autonomous(name = "Blue Long")

public class PiexelementoBlueLong extends LinearOpMode {

    DcMotor motorFE;
    DcMotor motorFD;
    DcMotor motorTD;
    DcMotor motorTE;
    BNO055IMU imu;
    Orientation angles;
    BNO055IMU.Parameters parameters;

    public void runOpMode() throws InterruptedException {




        //hardwaremap
        motorFD = hardwareMap.dcMotor.get("motorFD");
        motorFE = hardwareMap.dcMotor.get("motorFE");
        motorTD = hardwareMap.dcMotor.get("motorTD");
        motorTE = hardwareMap.dcMotor.get("motorTE");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

        // motorFD.setDirection(DcMotorSimple.Direction.REVERSE);
        // motorTD.setDirection(DcMotorSimple.Direction.REVERSE);



        // falta hardware map da garra,linear e rotativo

        RoboAutonomous robo = new RoboAutonomous(motorFD, motorFE, motorTD, motorTE,imu);
//			Random gerador = new Random();

        robo.parar();
        waitForStart();


//			int randomNum = (int)(Math.random() * 2);  // 0 to 3
        // identificar april tag

        // esquerda
//			if(randomNum==0) {
        robo.andar(500, 0.5, 0.0);
        sleep(100);
       // robo.CurvaTeste(0.7,0.4,87);
        sleep(1000);
        robo.andar(500,0.5, 90);

        // robo.curvaImu(-60,0.4);
//				sleep(3000);
//				robo.andar(1050, 0.4, -90);
        //robo.curva(-700,0.5);


//				robo.virar(-90, -0.50);
//				// deixar o pixel
//				robo.virar(-90, 0.50);
//				robo.andar(500, 0.50, 0.1);
//				//deixar o pixel
////			}
//			// centro
////			if(randomNum==1) {
//				robo.andar(500, 0.50, 0.1); // ir para trás da linha
//
//				// deixar o pixel
//				robo.andarLado(500, 0.50, 0.1);
//				robo.virar(90, 0.50);
//				//robo.linear.subir(0.50);
//				// deixar o pixel
////			}
//
//			// direita
////			if(randomNum==2) {
//				robo.andar(500, 0.50, 0.1);
//				robo.virar(90, 0.50);
//				// deixar o pixel
//				robo.andar(500, 0.50, 0.1);
//				//deixar o pixel
////			}



    }
}

 */

