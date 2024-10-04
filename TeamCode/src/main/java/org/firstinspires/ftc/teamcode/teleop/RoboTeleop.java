package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.hardware.Garra;
import org.firstinspires.ftc.teamcode.hardware.Chassi;

import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;
import org.firstinspires.ftc.teamcode.hardware.SistemaRotativoTelescopico;

@Disabled
@TeleOp
public class RoboTeleop extends LinearOpMode {
    public SistemaRotativoTelescopico sistemarotativortelescopico;
    public Chassi chassi;
    public Garra garra;
    public SistemaLinear sistemaLinear;
    public Rotativo rotativo;



    @Override
    public void runOpMode() throws InterruptedException {

    }

    public RoboTeleop(Servo garra1, Servo garra2, Servo angulo1, Servo angulo2, DcMotor motorRotativo, DcMotor motorsistemalinear, DcMotor motorFD, DcMotor motorFE, DcMotor motorTD, DcMotor motorTE) {

        this.sistemarotativortelescopico = new SistemaRotativoTelescopico(motorsistemalinear,motorRotativo,garra1,garra2,angulo1,angulo2);
        this.chassi = new Chassi(motorFD, motorFE, motorTD, motorTE);
    }

    public void IntakePosition() {

       // sistemarotativortelescopico.LinearInitial(0.5);
       sistemarotativortelescopico.RotativoIntake(-0.6);
       sistemarotativortelescopico.GarraIntake();
       //sistemarotativortelescopico.LinearIntake(0.5);
        //sistemarotativortelescopico.paradaLinear();

        // dentro dele ele executar outras funções

        // linear esticado
        // garra em Pos para pegar o pixel

    }
    public void BackDropPosition() {

        //  sistemarotativortelescopico.LinearBack(0.5);
          sistemarotativortelescopico.RotativoBack(0.6);
//          sistemarotativortelescopico.LinearBack(-0.4);

         // sistemarotativortelescopico.paradaLinear();


            // linear esticado
            // rotativo angulado
            //garra em pos para deixar o pixel
    }
    public void InitialPosition() {

       //   sistemarotativortelescopico.LinearInitial(0.5);

     //   sistemarotativortelescopico.LinearInitial(0.4);
        sistemarotativortelescopico.RotativoInitial(-0.6);

         // sistemarotativortelescopico.paradaLinear();
            // linear retraído
            // garra levantada para cima
            // só acontecera se o sensor da garra dectectar o pixel
    }


    }
