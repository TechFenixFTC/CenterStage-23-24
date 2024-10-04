package org.firstinspires.ftc.teamcode.autonomos;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Chassi;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.hardware.Garra;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;
import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.bosch.BNO055IMU;
import java.lang.Math;
@Disabled
@Autonomous
public class RoboAutonomous extends LinearOpMode {
    //imu
    BNO055IMU imu;
    Orientation angles;
    BNO055IMU.Parameters parameters;
    double Kp = 1;
    double target;
    double potenciaMFD = 1.00;// Frontal direito
    double potenciaMFE = 1.00;// Frontal esquerdo
    double potenciaMTD = 1.00; // Traseiro direito
    double potenciaMTE = 1.00; // Traseiro esquerdo
    public Chassi chassi;
    public Garra garra;
    public Rotativo rotativo;
    @Override
    public void runOpMode() throws InterruptedException {
    }
    public RoboAutonomous(DcMotor motorFD, DcMotor motorFE, DcMotor motorTD, DcMotor motorTE, BNO055IMU imu) {
        this.chassi = new Chassi(motorFD, motorFE, motorTD, motorTE);
        this.imu = imu;
        this.parameters = new BNO055IMU.Parameters();
        this.parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
    }


    public void runToPosition() {
        this.chassi.FD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.chassi.FE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.chassi.TD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.chassi.TE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void parar() {
        this.chassi.FD.setPower(0);
        this.chassi.FE.setPower(0);
        this.chassi.TD.setPower(0);
        this.chassi.TE.setPower(0);

        this.chassi.FD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.chassi.FE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.chassi.TD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.chassi.TE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void perfilrobot() {

        while (chassi.FD.isBusy() && chassi.FE.isBusy() && chassi.TD.isBusy() && chassi.TE.isBusy()) {
            // analisa se os motores estão ocupados "Busy = ocupado"
            telemetry.addData("Motores em funcionamento", "");
        }
    }

    public void andar(int ticks, double velocidade, double target) {
        // Set up da medição do IMU, coloquei pra medir em graus
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        // Mudo o modo de utilização dos motores
        chassi.FD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.FE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("angulo:",angles.firstAngle);

        // se a posição alvo for menor que a posição atual tras
        if (ticks < chassi.FD.getCurrentPosition()) {

            while (ticks < chassi.FD.getCurrentPosition() && ticks < chassi.FE.getCurrentPosition() && ticks < chassi.TD.getCurrentPosition() && ticks < chassi.TE.getCurrentPosition() && !isStopRequested()) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                double correction = (target - angles.firstAngle) * -0.01;


                this.chassi.FD.setPower(velocidade - correction);
                this.chassi.FE.setPower(velocidade + correction);
                this.chassi.TD.setPower(velocidade - correction);
                this.chassi.TE.setPower(velocidade + correction);


            }
        }
        // se a posição alvo for maior que a posição atual
        if (ticks > chassi.FD.getCurrentPosition()) {

            while (ticks > chassi.FD.getCurrentPosition() && ticks > chassi.FE.getCurrentPosition() && ticks > chassi.TD.getCurrentPosition() && ticks > chassi.TE.getCurrentPosition()) {

                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                double correction = (target - angles.firstAngle) * -0.01;


                this.chassi.FD.setPower(velocidade - correction);
                this.chassi.FE.setPower(velocidade + correction);
                this.chassi.TD.setPower(velocidade - correction);
                this.chassi.TE.setPower(velocidade + correction);


            }
        }
        parar();
    }
//    public void andarTeste(int ticks, double velocidade, double target, Telemetry telemetry, BNO055IMU imu) {
//        // Set up da medição do IMU, coloquei pra medir em graus
//        //angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        //imu.resetDeviceConfigurationForOpMode();
//        // Mudo o modo de utilização dos motores
//        chassi.FD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        chassi.FE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        chassi.TD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        chassi.TE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        // se a posição alvo for menor que a posição atual tras
//        if (ticks < chassi.FD.getCurrentPosition()) {
//
//            while (ticks < chassi.FD.getCurrentPosition() && ticks < chassi.FE.getCurrentPosition() && ticks < chassi.TD.getCurrentPosition() && ticks < chassi.TE.getCurrentPosition() && !isStopRequested()) {
//                //angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                double correction = (target - imu.getRobotOrientation(AxesReference.INTRINSIC,AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle) * -0.01;
//
//
//                this.chassi.FD.setPower(velocidade - correction);
//                this.chassi.FE.setPower(velocidade + correction);
//                this.chassi.TD.setPower(velocidade - correction);
//                this.chassi.TE.setPower(velocidade + correction);
//
//
//            }
//        }
//        // se a posição alvo for maior que a posição atual
//        if (ticks > chassi.FD.getCurrentPosition()) {
//
//            while (ticks > chassi.FD.getCurrentPosition() && ticks > chassi.FE.getCurrentPosition() && ticks > chassi.TD.getCurrentPosition() && ticks > chassi.TE.getCurrentPosition()) {
//
//                //angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                double correction = (target - imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle) * -0.01;
//                telemetry.addData("Angulo Medido", imu.getRobotOrientation(AxesReference.INTRINSIC,AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
//                telemetry.update();
//                this.chassi.FD.setPower(velocidade - correction);
//                this.chassi.FE.setPower(velocidade + correction);
//                this.chassi.TD.setPower(velocidade - correction);
//                this.chassi.TE.setPower(velocidade + correction);
//
//
//            }
//        }
//        parar();
//    }
    public void andarLado(int ticks, double velocidade, double target) {
        // Set up da medição do IMU, coloquei pra medir em graus
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        // Mudo o modo de utilização dos motores
        chassi.FD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.FE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // se a posição alvo for menor que a posição atual
        if (ticks < chassi.FD.getCurrentPosition()) {

            while (ticks < chassi.FD.getCurrentPosition() && ticks < chassi.FE.getCurrentPosition() && ticks < chassi.TD.getCurrentPosition() && ticks < chassi.TE.getCurrentPosition() && !isStopRequested()) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                double correction = (target - angles.firstAngle) * 0.08;

                this.chassi.FD.setPower(velocidade - correction);
                this.chassi.FE.setPower((velocidade + correction) * -1);
                this.chassi.TD.setPower((velocidade - correction) * -1);
                this.chassi.TE.setPower(velocidade + correction);

            }
        }

        // se a posição alvo for maior que a posição atual
        if (ticks > chassi.FD.getCurrentPosition()) {

            while (ticks > chassi.FD.getCurrentPosition() && ticks > chassi.FE.getCurrentPosition() && ticks > chassi.TD.getCurrentPosition() && ticks > chassi.TE.getCurrentPosition()) {

                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                double correction = (target - angles.firstAngle) * 0.08;


                this.chassi.FD.setPower(velocidade + correction);
                this.chassi.FE.setPower((velocidade - correction) * -1);
                this.chassi.TD.setPower((velocidade + correction) * -1);
                this.chassi.TE.setPower(velocidade - correction);
            }
        }


        parar();
    }
    public void curva(int ticks, double velocidade) {
        // Set up da medição do IMU, coloquei pra medir em graus
        // Mudo o modo de utilização dos motores
        chassi.FD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.FE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // se a posição alvo for menor que a posição atual
        if (ticks < chassi.FD.getCurrentPosition()) {

            while (ticks < chassi.FD.getCurrentPosition() && ticks < chassi.FE.getCurrentPosition() && ticks < chassi.TD.getCurrentPosition() && ticks < chassi.TE.getCurrentPosition() && !isStopRequested()) {

                this.chassi.FD.setPower(velocidade);
                this.chassi.FE.setPower((velocidade) * -1);
                this.chassi.TD.setPower((velocidade));
                this.chassi.TE.setPower(velocidade  * -1);

            }
        }

        // se a posição alvo for maior que a posição atual
        if (ticks > chassi.FD.getCurrentPosition()) {

            while (ticks > chassi.FD.getCurrentPosition() && ticks > chassi.FE.getCurrentPosition() && ticks > chassi.TD.getCurrentPosition() && ticks > chassi.TE.getCurrentPosition()) {


                this.chassi.FD.setPower(velocidade  * -1);
                this.chassi.FE.setPower((velocidade));
                this.chassi.TD.setPower((velocidade) * -1);
                this.chassi.TE.setPower(velocidade);
            }
        }


        parar();
    }
    public void virar(double AnguloAlvo, double min, double velocidadeMaxima, Telemetry telemetry, BNO055IMU imur) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double anguloMedido =  angles.firstAngle * -1;
        double anguloInicial = anguloMedido;
        double percursoTotal = AnguloAlvo - anguloInicial;

        chassi.FD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.FE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassi.TE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        if (AnguloAlvo > anguloInicial) {



            while (AnguloAlvo > anguloMedido && anguloMedido != AnguloAlvo && !isStopRequested()) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                anguloMedido = -1 * angles.firstAngle;
                double percursoFeito = anguloMedido - anguloInicial;


                //telemetry.addData("Angulo medido: ", angles.firstAngle);
                double resultado = percursoFeito/percursoTotal;

                if (velocidadeMaxima - (resultado * velocidadeMaxima) < min) {
                    chassi.FE.setPower(min * potenciaMFD);
                    chassi.FD.setPower(-1 * min * potenciaMFE);
                    chassi.TE.setPower(min * potenciaMTD);
                    chassi.TD.setPower(-1 * min * potenciaMTE);
                } else {
                    chassi.FE.setPower(velocidadeMaxima - (resultado * velocidadeMaxima));
                    chassi.FD.setPower((-1 * (velocidadeMaxima - (resultado * velocidadeMaxima))));
                    chassi.TE.setPower(velocidadeMaxima - (resultado * velocidadeMaxima));
                    chassi.TD.setPower(-1 * (velocidadeMaxima - (resultado * velocidadeMaxima)));
                }
            }
        } else {

            while (AnguloAlvo < anguloMedido && anguloMedido != AnguloAlvo && !isStopRequested()) {

                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                anguloMedido = -1 * angles.firstAngle;
                double percursoFeito = anguloMedido - anguloInicial;


                telemetry.addData("Angulo medido: ", angles.firstAngle);
                double resultado =  (percursoFeito/percursoTotal);
                telemetry.update();
                if (velocidadeMaxima - (resultado * velocidadeMaxima) < min) {
                    chassi.FE.setPower(-1 * min * potenciaMFD);
                    chassi.FD.setPower( min * potenciaMFE);
                    chassi.TE.setPower(-1 * min * potenciaMTD);
                    chassi.TD.setPower(min * potenciaMTE);
                } else {
                    chassi.FE.setPower(-1 * velocidadeMaxima - (resultado * velocidadeMaxima));
                    chassi.FD.setPower(velocidadeMaxima - (resultado * velocidadeMaxima));
                    chassi.TE.setPower(-1 * velocidadeMaxima - (resultado * velocidadeMaxima));
                    chassi.TD.setPower( (velocidadeMaxima - (resultado * velocidadeMaxima)));
                }
            }
        }
        parar();
    }


}







