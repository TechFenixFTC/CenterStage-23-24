package org.firstinspires.ftc.teamcode.teleop;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;
import org.firstinspires.ftc.teamcode.teleop.RoboTeleop;


/*
 * Não usem esse crianças, é só uma brincadeira que eu to fazendo
 *
 * */

@TeleOp(name="Sacanagem.exe")

public class Sacanagem extends OpMode{
    DcMotor motorFD;
    DcMotor motorFE;
    DcMotor motorTD;
    DcMotor motorTE;

    DcMotor motorRotativo;
    public Rotativo rotativo;
    public DcMotor hang1;
    public DcMotor hang2;

    public Servo garra1;
    public Servo garra2;
    public Servo angulo1;
    public Servo angulo2;
    public Servo drone;
    public DcMotor motorsistemalinear;

    public SistemaLinear sistemalinear;
    String rEstado = "aberto";
    String lEstado = "aberto";
    boolean HangSubindo = false;
    boolean indoProOutake = false;
    boolean indoProIntake = false;
    boolean indoProInitial = false;
    double invertHang = -1;


    double posServoAng = 0.79;
    double currentPosRotativo = 0;
    boolean leftBumperUltimoEstado;
    @Override
    public void init(){
        motorFD = hardwareMap.dcMotor.get("motorFD");
        motorFE = hardwareMap.dcMotor.get("motorFE");
        motorTD = hardwareMap.dcMotor.get("motorTD");
        motorTE = hardwareMap.dcMotor.get("motorTE");
        motorFE.setDirection(DcMotorSimple.Direction.REVERSE);
        motorTE.setDirection(DcMotorSimple.Direction.REVERSE);
        drone = hardwareMap.servo.get("drone");
        garra1 = hardwareMap.servo.get("garra1");
        garra2 = hardwareMap.servo.get("garra2");
        garra1.setDirection(Servo.Direction.REVERSE);
        angulo1 = hardwareMap.servo.get("angulo1");
        angulo2 = hardwareMap.servo.get("angulo2");
        angulo2.setDirection(Servo.Direction.REVERSE);
        motorsistemalinear = hardwareMap.dcMotor.get("sistemalinear");
        sistemalinear = new SistemaLinear(motorsistemalinear);
        motorRotativo = hardwareMap.dcMotor.get("rotativo");
        rotativo = new Rotativo(motorRotativo);
        hang1 = hardwareMap.dcMotor.get("hang1");
        hang2 = hardwareMap.dcMotor.get("hang2");
        //hang2.setDirection(DcMotorSimple.Direction.REVERSE);

        leftBumperUltimoEstado = gamepad2.left_bumper;

    }

    @Override
    public void loop() {
        //currentPosRotativo = motorsistemalinear.getCurrentPosition();
        telemetry.addData("linear:", motorsistemalinear.getCurrentPosition());
        telemetry.addData("ROTATIVO:", motorRotativo.getCurrentPosition());
        telemetry.addData("HANG1:", hang1.getCurrentPosition());
        telemetry.addData("HANG2:", hang2.getCurrentPosition());
        telemetry.addData("fd:", motorFD.getCurrentPosition());
        telemetry.addData("Indo pro Intake:", indoProIntake);
        telemetry.addData("Indo pro Outake:", indoProOutake);

        telemetry.addData("angulo: ", posServoAng);




        /*========================================*\
                            RESET
        /*==========================================*/
        if(gamepad2.back) {
            motorsistemalinear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorsistemalinear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


        if(gamepad2.start) {
            motorRotativo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRotativo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorsistemalinear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorsistemalinear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

       /*========================================*\
                            CHASSI
        /*==========================================*/
        // andar reto
        if(gamepad1.left_stick_x != 0) {
            motorFD.setPower(gamepad1.left_stick_x);
            motorFE.setPower(gamepad1.left_stick_x);
            motorTD.setPower(gamepad1.left_stick_x);
            motorTE.setPower(gamepad1.left_stick_x);

        }
        else if(!((gamepad1.left_trigger != 0) && (gamepad1.right_trigger != 0) && (gamepad1.left_stick_x != 0) && gamepad1.right_stick_y != 0 )) {
            motorFD.setPower(0);
            motorFE.setPower(0);
            motorTD.setPower(0);
            motorTE.setPower(0);
        }
        // andar lado
        if(gamepad1.right_stick_y != 0) {
            motorFD.setPower(gamepad1.right_stick_y  * -1);
            motorFE.setPower(gamepad1.right_stick_y ); //-1
            motorTD.setPower(gamepad1.right_stick_y ); //-1
            motorTE.setPower(gamepad1.right_stick_y *  -1);
        }
        // Virar pra direita
        if(gamepad1.right_trigger != 0){
            motorFD.setPower(gamepad1.right_trigger);
            motorFE.setPower(gamepad1.right_trigger *  -1);
            motorTD.setPower(gamepad1.right_trigger);
            motorTE.setPower(gamepad1.right_trigger * -1);
        }

        if(gamepad1.left_trigger != 0){
            motorFD.setPower(gamepad1.left_trigger * -1);
            motorFE.setPower(gamepad1.left_trigger);
            motorTD.setPower(gamepad1.left_trigger * -1);
            motorTE.setPower(gamepad1.left_trigger);
        }



        /*========================================*\
                 PRESETS DE POSIÇÃO DO RLT
        /*==========================================*/
        if(gamepad2.a || indoProInitial == true) { // pos inicial
            indoProIntake = false;
            indoProOutake = false;
            indoProInitial = true;
            //rotativo
            // posicao inicial
                if (motorRotativo.getCurrentPosition() < -700) {
                    //rapido
                    rotativo.virar(0.7);
                }else if (motorRotativo.getCurrentPosition() > -50) {
                    //devagar
                    rotativo.virar(0);
                }
                else if (motorRotativo.getCurrentPosition() > -400) {
                    //devagar
                    rotativo.virar(0.25);
                }
                // inicial
            if(motorRotativo.getCurrentPosition() > -400) { // o rotativo já está em baixo

                if(motorsistemalinear.getCurrentPosition() > 10) {// ele ainda não chegou na pos alvo
                    sistemalinear.descer(0.5);
                }
                else {
                    sistemalinear.subir(0);
                }

            }

            if(gamepad2.left_stick_y == 0) {

                angulo2.setPosition(0.84);
            }
        }

        if(gamepad2.b || indoProIntake == true) { // Posição de Intake
            indoProIntake = true;
            indoProOutake = false;
            indoProInitial = false;
            //rotativo
            // posicao inicial
            if (motorRotativo.getCurrentPosition() < -700) {
                //rapido
                rotativo.virar(0.7);
            }
            if (motorRotativo.getCurrentPosition() > -400) {
                //devagar
                rotativo.virar(0.25);
            }


            //linear

            if(motorRotativo.getCurrentPosition() > -400) { // o rotativo já está em baixo

                if(motorsistemalinear.getCurrentPosition() < 890) {// ele ainda não chegou na pos alvo
                    sistemalinear.subir(0.5);
                }
                else {// chegou na posição
                    sistemalinear.subir(0);

                }

            }
            //garra
            if(gamepad2.left_stick_y == 0) {

                angulo2.setPosition(0.84);
            }


        }



        if(gamepad2.y || indoProOutake == true) { //POSIÇÃO DE low Outake
            indoProIntake = false;
            indoProInitial = false;
            indoProOutake = true;
            // pos back
            // 450
            if (motorRotativo.getCurrentPosition() > -2400) {

                rotativo.virar(-0.8);
                //subir
            }

            // GARRA
            if(gamepad2.left_stick_y == 0) {
                angulo2.setPosition(0.72);
            }


        }






        /*========================================*\
                         LINEAR
        /*==========================================*/

    if (gamepad2.dpad_up && motorsistemalinear.getCurrentPosition() < 900) {


           sistemalinear.subir(0.5);

    }
    else if(gamepad2.dpad_down){

            sistemalinear.descer(0.3);

    } else if(!indoProIntake && !indoProInitial) {
            sistemalinear.subir(0);
    }

        /*========================================*\
                         ROTATIVO
        /*==========================================*/
        if(gamepad2.dpad_left) { // desce
            if (motorRotativo.getCurrentPosition() > -600) {
                rotativo.virar(0.05);

            }else {
                rotativo.virar(0.85);
            }

        }
         else if(gamepad2.dpad_right) { // sobe

            if(motorRotativo.getCurrentPosition() > -700){
                rotativo.virar(-0.85);

            }else{
                rotativo.virar(-0.15);
            }
        }
        else{ // parar
            if(motorRotativo.getCurrentPosition() < -2400 && !indoProIntake) { // não está embaixo
                rotativo.virar(0.05);



            }else if(motorRotativo.getCurrentPosition() > -800 && !indoProOutake){
                rotativo.virar(-0.12);
            }
        }


        /*========================================*\
                            GARRA
        /*==========================================*/

        // inclinacao da garra
        if(gamepad2.left_stick_y != 0) {
            indoProOutake = false;
            indoProIntake = false;
        }
        if(gamepad2.left_stick_y < 0) {
            if(posServoAng>0) {
                posServoAng-= 0.005;
            }
            //angulo1.setPosition(posServoAng);
            angulo2.setPosition(posServoAng);

        }
        if(gamepad2.left_stick_y > 0) {
            if(posServoAng < 1.0) {
                posServoAng+= 0.005;
            }

            // angulo1.setPosition(posServoAng);
            angulo2.setPosition(posServoAng);

        }

        // Garra da esquerda
        if(gamepad2.right_trigger > 0){ //abrir
            garra2.setPosition(0.5);
        }
        if(gamepad2.right_bumper){ //fechar

            garra2.setPosition(0);

        }

        // Garra da direita
        if(gamepad2.left_trigger > 0){ //abrir
            garra1.setPosition(0.75);
        }
        if(gamepad2.left_bumper){// fechar

            garra1.setPosition(0.4);

        }

        leftBumperUltimoEstado = gamepad2.left_bumper;

        //DRONEEEEEEEEE
        if(gamepad1.right_bumper){
            drone.setPosition(0);
        }
        if(gamepad1.left_bumper){
            drone.setPosition(1);
        }

//        //HAAAAAAAAANG
//        if(gamepad1.x){
//            hang1.setPower(0.4);
//            hang2.setPower(0.4);
//
//        }
//
//        else if (gamepad1.y) {
//            hang1.setPower(-0.4);
//            hang2.setPower(-0.4);
//        }
//        else{
//            hang1.setPower(0);
//            hang2.setPower(0);
//        }
        //HAAAAAAAAANG
        if(gamepad1.x){ //robo sobe
            hang1.setPower(0.7);
            hang2.setPower(-0.7);
            telemetry.addData("hang","estou sendo apertado");


        }

        else if (gamepad1.y) { //robo desce
            hang1.setPower(-0.7);
            hang2.setPower(0.7);
        }
        else{
            hang1.setPower(0);
            hang2.setPower(0);
        }
    }
    }


