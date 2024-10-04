package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;


/*
 * Não usem esse crianças, é só uma brincadeira que eu to fazendo
 *
 * */

@TeleOp(name="Sacanagem 2: O Teleop agora é outro")

public class Sacanagem2 extends OpMode{
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
    double invertHang = -1;
    boolean indoProOutake = false;

    String outakePos = "None";
    boolean indoProIntake = false;
    boolean indoProInitial = false;

    public double rotativoTargetPosition = 0;
    public double linearTargetPosition = 10;

    public double anguloDaGarraTargetPosition = 0.79;

    public double garra1TargetPosition = 0;
    public double garra2TargetPosition = 0;

    BNO055IMU imu;
    Orientation angles;
    BNO055IMU.Parameters parameters;


    double posServoAng = 0.79;
    double currentPosRotativo = 0;
    boolean leftBumperUltimoEstado;

    boolean hangLooper = false;
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
        hang1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //hang2.setDirection(DcMotorSimple.Direction.REVERSE);

        leftBumperUltimoEstado = gamepad2.left_bumper;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
//        imu = hardwareMap.get(BHI260IMU.class, "imu");
//        parameters = imu.getParameters();
//        //parameters.angleUnit = BHI260IMU.AngleUnit.DEGREES;
//        imu.initialize(parameters);
    }
    @Override
    public void loop() {
        //currentPosRotativo = motorsistemalinear.getCurrentPosition();
        motorTE.getMode();
        telemetry.addData("linear:", motorsistemalinear.getCurrentPosition());
        telemetry.addData("ROTATIVO:", motorRotativo.getCurrentPosition());
        telemetry.addData("HANG1:", hang1.getCurrentPosition());
        telemetry.addData("HANG2:", hang2.getCurrentPosition());
        telemetry.addData("fd:", motorFD.getCurrentPosition());
        telemetry.addData("Indo pro Intake:", indoProIntake);
        telemetry.addData("Indo pro Outake:", indoProOutake);

        telemetry.addData("angulo da garra var: ", posServoAng);
        telemetry.addData("angulo da garra Controlador do servo: ", angulo2.getController().getServoPosition(1));
        telemetry.addData("angulo da garra servo: ", angulo2.getPosition());
        telemetry.addData("angulo da garra Porta: ", angulo2.getPortNumber());

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("Angulo:", angles.firstAngle);
        telemetry.update();
        //telemetry.addLine(String.valueOf(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)));
        //telemetry.addData("IMU", angles.firstAngle);



        /*========================================*\
                            RESET
        /*==========================================*/
        this.reset();

       /*========================================*\
                            CHASSI
        /*==========================================*/
        this.chassi();
        /*========================================*\
                 PRESETS DE POSIÇÃO DO RLT
        /*==========================================*/
        if(gamepad2.right_stick_button || indoProInitial == true) { // pos inicial
           outakePos = "none";
           this.inicial();
        }

        if(gamepad2.b || indoProIntake == true) { // Posição de Intake
            outakePos = "none";
            this.intake();
        }
        if(gamepad2.a || (indoProOutake == true && outakePos.equals("low"))) { //POSIÇÃO DE LOW Outake
            outakePos = "low";
            this.lowOutTake();
        }
        if(gamepad2.x || (indoProOutake == true && outakePos.equals("mid") )) { //POSIÇÃO DE MID Outake
            outakePos = "mid";

            this.midOutTake();
        }
        if(gamepad2.y || (indoProOutake == true && outakePos.equals("high"))) { //POSIÇÃO DE HIGH Outake
            outakePos = "high";
            this.highOutTake();
        }

        /*========================================*\
                         LINEAR
        /*==========================================*/
        this.linearManual();
        /*========================================*\
                         ROTATIVO
        /*==========================================*/
        this.rotativoManual();
        /*========================================*\
                            GARRA
        /*==========================================*/
        this.garra();
        // DRONE
        this.drone();

        // HANG
        this.hang();
    }

    public void reset() {
        if(gamepad2.back) {
            motorsistemalinear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorsistemalinear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            hang1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hang1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            hang2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hang2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            //imu.resetYaw();
        }


        if(gamepad2.start) {
            motorRotativo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRotativo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            // 0.80 ~ 0.79
            // -29074

            // -30000
            // 0.74
        }

    }
    public void chassi() {
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
            motorFD.setPower(gamepad1.right_trigger - 0.2);
            motorFE.setPower((gamepad1.right_trigger *  -1) + 0.2);
            motorTD.setPower(gamepad1.right_trigger - 0.2);
            motorTE.setPower((gamepad1.right_trigger * -1) + 0.2);
        }

        if(gamepad1.left_trigger != 0){
            motorFD.setPower((gamepad1.left_trigger * -1) + 0.2);
            motorFE.setPower(gamepad1.left_trigger - 0.2);
            motorTD.setPower((gamepad1.left_trigger * -1) + 0.2);
            motorTE.setPower(gamepad1.left_trigger - 0.2);
        }



    }


   // SISTEMA LINEAR ROTATIVO TELESCÓPICO

    public void intake(){
        indoProIntake = true;
        indoProOutake = false;
        indoProInitial = false;
        this.setGarrasParaAbrir();


        // ROTATIVO
            rotativoTargetPosition = 0;

            if (motorRotativo.getCurrentPosition() < -700) {
                //rapido
                rotativo.virar(0.7);
            }





        // LINEAR
            linearTargetPosition = 10;
            if(motorRotativo.getCurrentPosition() > -400) { // o rotativo já está em baixo

                if(motorsistemalinear.getCurrentPosition() < linearTargetPosition) {// ele ainda não chegou na pos alvo
                    sistemalinear.subir(0.5);
                }
                else {// chegou na posição
                   sistemalinear.subir(0);

                }

            }

        //GARRA
            // ANGULO
            anguloDaGarraTargetPosition = 0.53;


            angulo2.setPosition(anguloDaGarraTargetPosition);







    }

    //posição intake 0.409



    public void inicial(){
        indoProIntake = false;
        indoProOutake = false;
        indoProInitial = true;
        //rotativo
        // posicao inicial
        if (motorRotativo.getCurrentPosition() < -2000) {
            //rapido
            rotativo.virar(0.9);
        }
        else if(motorRotativo.getCurrentPosition() < -1300){
            rotativo.virar(0.3);

        }else if (motorRotativo.getCurrentPosition() > -50) {
            //devagar
            rotativo.virar(0);
        }
        else {
            //devagar
            rotativo.virar(0.15);
        }
        // inicial
        linearTargetPosition = 10;
        if(motorRotativo.getCurrentPosition() > -400) { // o rotativo já está em baixo

            if(motorsistemalinear.getCurrentPosition() > 10) {// ele ainda não chegou na pos alvo
                sistemalinear.descer(0.5);
            }
            else {
                sistemalinear.subir(0);
                linearTargetPosition =  motorsistemalinear.getCurrentPosition();
            }

        } else {
            if(gamepad2.left_stick_y == 0) {

                angulo2.setPosition(0.53);
            }
        }


    }


    public void lowOutTake() {
        indoProIntake = false;
        indoProInitial = false;
        indoProOutake = true;


        //ROTATIVO
        rotativoTargetPosition = -3100;
        //linear
        linearTargetPosition = 800;
        if(motorsistemalinear.getCurrentPosition() >= 0.9 * linearTargetPosition) {

            if (motorRotativo.getCurrentPosition() > (0.6 * rotativoTargetPosition)) { // rápido, menos que 60% do caminho

                rotativo.virar(-0.80);

            }
            else if(motorRotativo.getCurrentPosition() > 0.9 * rotativoTargetPosition) { //devagar, mais que 60% do caminho
                rotativo.virar(-0.15);
            }
            else {
                // indoProOutake =  false;
            }


        }


        // LINEAR

            if(motorsistemalinear.getCurrentPosition() < linearTargetPosition) {
                sistemalinear.subir(0.5);
            }else {
                sistemalinear.subir(0);
            }

        // GARRA
        anguloDaGarraTargetPosition = 1;
        if(gamepad2.left_stick_y == 0) {
            if(motorRotativo.getCurrentPosition() < (0.2 * rotativoTargetPosition)) {
                angulo2.setPosition(anguloDaGarraTargetPosition);
            }

        }



    }

    public void midOutTake(){
        indoProIntake = false;
        indoProInitial = false;
        indoProOutake = true;

        rotativoTargetPosition = -2700;
        if(motorsistemalinear.getCurrentPosition() >= 0.9 * linearTargetPosition) {

            if (motorRotativo.getCurrentPosition() > (0.6 * rotativoTargetPosition)) { // rápido, menos que 60% do caminho

                rotativo.virar(-0.80);

            }
            else if(motorRotativo.getCurrentPosition() > 0.9 * rotativoTargetPosition) { //devagar, mais que 60% do caminho
                rotativo.virar(-0.15);
            }
            else {
                // indoProOutake =  false;
            }

        linearTargetPosition = 900;
        }
        if(motorsistemalinear.getCurrentPosition() < linearTargetPosition) {
            sistemalinear.subir(0.5);
        }else {
            sistemalinear.subir(0);
        }
        anguloDaGarraTargetPosition = 1.0;
        if(gamepad2.left_stick_y == 0) {
            if(motorRotativo.getCurrentPosition() < (0.2 * rotativoTargetPosition)) {
                angulo2.setPosition(anguloDaGarraTargetPosition);
            }

        }
    }

    public void highOutTake() {

    }
    public void linearManual() {

        if (gamepad2.dpad_up && motorsistemalinear.getCurrentPosition() < 900) {

            linearTargetPosition = motorsistemalinear.getTargetPosition();
            sistemalinear.subir(0.5);

        }
        else if(gamepad2.dpad_down){
            linearTargetPosition = motorsistemalinear.getTargetPosition();
                sistemalinear.descer(0.3);


        } else if(!indoProIntake && !indoProInitial && linearTargetPosition == motorsistemalinear.getTargetPosition()) {
            sistemalinear.subir(0);
        }

    }
    public void rotativoManual() {
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
            this.logicaDeParadaRotativo();
        }


    }

    public void logicaDeParadaRotativo() {
        if(motorRotativo.getCurrentPosition() < -2400 && !indoProIntake) { // não está embaixo

            if(motorsistemalinear.getCurrentPosition() > 800) {
                rotativo.virar(0.16);
            } else if(motorsistemalinear.getCurrentPosition() > 400) {
                rotativo.virar(0.15);
            }
            else
            {
                rotativo.virar(0.05);
            }



        }else if(motorRotativo.getCurrentPosition() > -40 && !indoProOutake){
            rotativo.virar(0);
        }else if(motorRotativo.getCurrentPosition() > -900 && !indoProOutake){
            rotativo.virar(-0.12);
        }
    }


    public void garra() {
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

    }


    //sets
    public void setGarrasParaAbrir() {
       garra1TargetPosition =  0.75;
       garra2TargetPosition = 0.5;
    }

    public void setGarrasParaFechar() {
        garra1TargetPosition = 0.4;
        garra2TargetPosition = 0;
    }

    public void executarGarras() {
        garra1.setPosition(garra1TargetPosition);
        garra2.setPosition(garra2TargetPosition);
    }





    // OUTROS SISTEMAS

    public void drone() {
        if(gamepad1.right_bumper){
            drone.setPosition(0);
        }
//        if(gamepad1.left_bumper){
//            drone.setPosition(1);
//        }

    }

    public void hang() {

        if(gamepad1.x || hangLooper ){ //robo desce

            if(hang2.getCurrentPosition() < -300)   {
                hangLooper = true;
                hang1.setPower(0.7);
                hang2.setPower(0.7);
            }else {
                hang1.setPower(0.2);
                hang2.setPower(0.2);
            }
            telemetry.addData("hang","estou sendo apertado");


        }

        else if (gamepad1.y) { //robo sobe
                hangLooper = false;
                hang1.setPower(-0.7);
                hang2.setPower(-0.7);


        }
        else{
            hang1.setPower(0);
            hang2.setPower(0);
        }
    }
}



