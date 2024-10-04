package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;
@Disabled
@TeleOp(name="Tes")
public class Teleop3Teste extends OpMode{
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

    public RoboTeleop teleop;

    double posServoAng = 0.5;

    boolean leftBumperUltimoEstado;
    @Override
    public void init(){
        RoboTeleopFactory(hardwareMap);



        leftBumperUltimoEstado = gamepad2.left_bumper;


    }

    public RoboTeleop RoboTeleopFactory(HardwareMap hardwareMap){
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

        return new RoboTeleop(garra1,garra2,angulo1,angulo2,motorRotativo,motorsistemalinear,motorFD,motorFE,motorTD, motorTE);
    }



    @Override
    public void loop() {



        telemetry.addData("linear:", motorsistemalinear.getCurrentPosition());
        telemetry.addData("angulo: ", posServoAng);
                //telemetry.addData("erro", teleop.sistemarotativortelescopico.paradaLinear());
                telemetry.addData("rotativo",motorRotativo.getCurrentPosition());
         telemetry.addData("back", teleop.sistemarotativortelescopico.IndoProBackDrop);
         telemetry.addData("inicial", teleop.sistemarotativortelescopico.IndoProInitial);
         telemetry.addData("intake", teleop.sistemarotativortelescopico.IndoProIntake);
         telemetry.addData("Hang1", hang1.getCurrentPosition());
         telemetry.addData("Hang2", hang2.getCurrentPosition());
//        if (gamepad1.right_bumper) {
//            garra2.setPosition(1);
//            telemetry.addData("Garra lado direito abrindo", "");
//        }
//        if (gamepad1.left_bumper) {
//            garra1.setPosition(0);
//            telemetry.addData("Garra lado esquerdo abrindo", "");
//        } else if (gamepad1.right_trigger > 0) {
//            garra2.setPosition(0);
//            telemetry.addData("Garra lado direito fechando", "");
//        }
////        } else if (gamepad1.left_trigger > 0){
////            garra1.setPosition(1);
////            telemetry.addData("Garra lado esquerdo fechando", "");
////´]





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

        } else if(!((gamepad1.left_trigger != 0) && (gamepad1.right_trigger != 0) && (gamepad1.left_stick_x != 0) && gamepad1.right_stick_y != 0 )) {
            motorFD.setPower(0);
            motorFE.setPower(0);
            motorTD.setPower(0);
            motorTE.setPower(0);
        }
        // andar lado
        if(gamepad1.right_stick_y != 0) {
            motorFD.setPower(gamepad1.right_stick_y  * -1);
            motorFE.setPower(gamepad1.right_stick_y );
            motorTD.setPower(gamepad1.right_stick_y );
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


        /*========================================*\
                 PRESETS DE POSIÇÃO DO RLT
        /*==========================================*/
            if(gamepad2.a || teleop.sistemarotativortelescopico.IndoProInitial == true){
                teleop.InitialPosition();
                telemetry.addData("","Estou indo pro initial");

//
//            }
//
//            if(gamepad2.b || teleop.sistemarotativortelescopico.IndoProIntake == true) {
//                teleop.IntakePosition();
//                telemetry.addData("","Estou indo pro intake");
//            }
//
            if(gamepad2.y || teleop.sistemarotativortelescopico.IndoProBackDrop == true) {
                teleop.BackDropPosition();
                telemetry.addData("","Estou indo pro backdrop");
            }

            if (!teleop.sistemarotativortelescopico.IndoProInitial && !teleop.sistemarotativortelescopico.IndoProIntake && !teleop.sistemarotativortelescopico.IndoProBackDrop) {
                telemetry.addLine("Parando");
                if(motorRotativo.getCurrentPosition() > -700){
                    //em baixo
                   motorRotativo.setPower(0.15 * -1);

                }
//                else {
//                    // na posição do backdrop
//                    teleop.sistemarotativortelescopico.paradaLinear();
//                }

                if(gamepad1.a || teleop.sistemarotativortelescopico.IndoProInitial == true){
                    telemetry.addData("Botão A", "estou sendo apertado");
                    teleop.InitialPosition();


            }






        /*========================================*\
                         LINEAR
        /*==========================================*/

//        if (gamepad2.x && motorsistemalinear.getCurrentPosition() > -900) {
//
//        sistemalinear.subir(0.5);
//            telemetry.addData("linear","estou esticando/funcionando");
//        }
//        else if(gamepad2.y){
//            sistemalinear.descer(-0.5);
//
////        }
//        else {
//            if(motorsistemalinear.getCurrentPosition() > 200 || motorsistemalinear.getCurrentPosition() < -200) {
//                sistemalinear.subir(0.05);
//            }else {
//                sistemalinear.subir(0);
//            }
        //}

        /*========================================*\
                         ROTATIVO
        /*==========================================*/

        /*
        if(gamepad2.dpad_down) { // desce
            if (motorRotativo.getCurrentPosition() > -600) {
                rotativo.virar(0.05);

            }



        } else if(gamepad2.dpad_up) { // sobe

            if(motorRotativo.getCurrentPosition() < -700){
                rotativo.virar(-0.85);

            }else{
                rotativo.virar(-0.15);
            }


        }
        else{ // parar
            if(motorRotativo.getCurrentPosition() < -400) {
                if (motorsistemalinear.getCurrentPosition() > 100 || motorsistemalinear.getCurrentPosition() < -100){ //linear parcialmente extendido
                    rotativo.virar(-0.15);
                }
                else  if (motorsistemalinear.getCurrentPosition() > 400 || motorsistemalinear.getCurrentPosition() < -400){
                    rotativo.virar(-0.2); // muito extentido
                }
                else { // linear não estendido
                    rotativo.virar(-0.05);
                }

            } else if (motorRotativo.getCurrentPosition() > -60) {
                rotativo.virar(0);

            }

            else if(motorRotativo.getCurrentPosition() > -600){
                rotativo.virar(-0.05);

            }


            else{
                rotativo.virar(0);
            }
        }

*/

        /*========================================*\
                            GARRA
        /*==========================================*/

        //inclinacao da garra
        if(gamepad2.left_stick_y != 0) {
            //indoProOutake = false;
           // indoProIntake = false;
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
            garra2.setPosition(0.75);
        }
        if(gamepad2.right_bumper){ //fechar

            garra2.setPosition(0.2);

        }

        // Garra da direita
        if(gamepad2.left_trigger > 0){ //abrir
            garra1.setPosition(0.75);
        }
        if(gamepad2.left_bumper){// fechar

            garra1.setPosition(0.4);

        }


        // Garra da direita
        if(gamepad2.left_trigger > 0){ //abrir
            garra1.setPosition(0.75);
        }
       if(gamepad2.left_bumper){// fechar

           garra1.setPosition(0.4);

       }
//
//        leftBumperUltimoEstado = gamepad2.left_bumper;

        //DRONEEEEEEEEE
//        if(gamepad1.right_bumper){
//            drone.setPosition(0);
//       }
//        if(gamepad1.left_bumper){
//            drone.setPosition(1);
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
        }
    }
}
