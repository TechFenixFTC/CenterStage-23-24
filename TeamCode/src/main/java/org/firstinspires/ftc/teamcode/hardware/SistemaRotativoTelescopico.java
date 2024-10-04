package org.firstinspires.ftc.teamcode.hardware;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp

public class SistemaRotativoTelescopico extends OpMode {
    public DcMotor motorRotativo;
    public DcMotor motorsistemalinear;
    public Rotativo rotativo;
    public SistemaLinear sistemalinear;
    public Garra garra;
    public Servo garra1;
    public Servo garra2;
    public Servo angulo1;
    public Servo angulo2;
    public ColorSensor sensor1;
    public ColorSensor sensor2;
    public int targetPosition = 0;
    public boolean posInicial = true;
    public boolean IndoProIntake = false;
    public boolean IndoProBackDrop = false;
    public boolean IndoProInitial = false;
    @Override
    public void init (){
    }
    @Override
    public void loop(){
    }

    public SistemaRotativoTelescopico(DcMotor motorsistemalinear, DcMotor motorRotativo, Servo garra1, Servo garra2, Servo angulo1, Servo angulo2){
        this.motorsistemalinear = motorsistemalinear;
        this.motorRotativo = motorRotativo;
        this.angulo2 = angulo2;
        this.angulo1 = angulo1;
        this.garra1 = garra1;
        this.garra2 = garra2;

        this.garra = new Garra(garra1,garra2,angulo1,angulo2);
        this.sistemalinear = new SistemaLinear(motorsistemalinear);
        this.rotativo = new Rotativo(motorRotativo);



    }
//    public boolean canRetract(){
//
//
//        return true;
//    }
    public void moverLinear(double velocidade, String sentido) {
        // negativo estica
        // positivo retrai
        if (sentido.equals("esticar")) {
            int regulador = -1;
                if(motorsistemalinear.getCurrentPosition() >= -700) {
                motorsistemalinear.setPower(velocidade * regulador);
            }
        }
        if (sentido.equals("retrair")) {
            int regulador = 1;
            if(motorsistemalinear.getCurrentPosition() <= -700) {
                motorsistemalinear.setPower(velocidade * regulador);
            }
        }


    }
    public void LinearIntake(double velocidade){
            if(motorsistemalinear.getCurrentPosition() <= -700 ) {
                motorsistemalinear.setPower(velocidade * -1);

            }else{


            }

    }
    public void LinearBack(double velocidade){
        if(motorsistemalinear.getCurrentPosition() >= -700 ) {
            motorsistemalinear.setPower(velocidade * -1);
        }else{

        }
        }

    public void LinearInitial(double velocidade){
        if(motorsistemalinear.getCurrentPosition() >= -700 ) {
            motorsistemalinear.setPower(velocidade * -1);

        }else{

        }
    }


    public void RotativoBack(double velocidade){
        targetPosition = -900;
        if(motorRotativo.getCurrentPosition() > targetPosition) {
            motorRotativo.setPower(velocidade * -1);
            this.IndoProInitial = false;
            this.IndoProIntake = false;
            this.IndoProBackDrop = true;
        }

        else {
            paradaLinear();
            this.IndoProBackDrop = false;
        }

//        if(motorRotativo.getCurrentPosition() < -500){
//            motorRotativo.setPower(velocidade * -1);
//            IndoProBackDrop = true;
//
//        }else{
//            IndoProBackDrop = true;
//        }
//        if(motorRotativo.getCurrentPosition() > -600){
//            motorRotativo.setPower(velocidade * 1);
//            IndoProBackDrop = true;
//        }else{
//                motorRotativo.setPower(0);
//                IndoProBackDrop = false;
//        }

    }
   public void RotativoInitial(double velocidade){
        targetPosition =  -40;
        if(motorRotativo.getCurrentPosition() < targetPosition){
            motorRotativo.setPower(velocidade * -1 );

            IndoProInitial = true;
            IndoProIntake = false;
            IndoProBackDrop = false;
        }else{
            IndoProInitial = false;
        }

       // else{
       //    paradaLinear();
       //    IndoProInitial = false;
       // }
    }
    public void RotativoIntake(double velocidade){
        targetPosition = -100;

        if(motorRotativo.getCurrentPosition()  <  targetPosition){
            if(motorRotativo.getCurrentPosition() > -300) {
                motorRotativo.setPower(velocidade * -1 * 0.1);
            }
            else if(motorRotativo.getCurrentPosition() > -700){
                motorRotativo.setPower(velocidade * -1 * 0.2);
            }
            else {
                motorRotativo.setPower(velocidade * -1 );
            }

            IndoProIntake = true;
            IndoProInitial = false;
            IndoProBackDrop = false;
        }

       else{
           IndoProIntake = false;
        }


        //else{
        //paradaLinear();
        //    IndoProIntake = false;
        // }

    }
    public void GarraInital(){

    }
    public void GarraBack(){

    }
    public void GarraIntake(){
        angulo1.setPosition(0.12);
    }
    public double aceleracaoRotativo(){
        return 0.6;
    }
    public double paradaLinear(){

            double currentPosition = motorRotativo.getCurrentPosition(); //0
            double erro =  ( (currentPosition - targetPosition) * 100) / targetPosition * 100;
            double Kp = 0.0001;
            motorRotativo.setPower(erro * Kp);
            return erro;
    }
}