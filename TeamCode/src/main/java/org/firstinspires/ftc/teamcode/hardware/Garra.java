package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;


@Disabled
@TeleOp

public class Garra extends OpMode{
    public Servo garra1;
    public Servo garra2;
    public Servo angulo1;
    public Servo angulo2;


    public void init(){
    }

    public void loop(){
    }
    public Garra(Servo garra1, Servo garra2, Servo angulo1, Servo angulo2){
        this.garra1 = garra1;
        this.garra2 = garra2;
        this.angulo1 = angulo1;
        this.angulo2 = angulo2;

    }

    // não precisa chamar no agregador, pois o mesmo modo de garra se usa no autonomo e no teleop

    public void abrirUm(){
        this.garra1.setPosition(1);
    }
    public void fecharUm(){
        this.garra1.setPosition(0);
    }
    public void abrirDois(){
        this.garra2.setPosition(1);
    }
    public void fecharDois(){
        this.garra2.setPosition(0);
    }
    public void posUm(){
        this.angulo1.setPosition(0); this.angulo2.setPosition(0);
    }
    public void posDois(){
        this.angulo1.setPosition(0.50); this.angulo2.setPosition(0.50);
    }
    public void posTres()
    {
        this.angulo1.setPosition(1); this.angulo2.setPosition(1);
    }
    public static boolean[] HaveObject() {
        boolean[] estado = {true, false};
        // true: tem um objeto
        // false: não tem um objeto
        return estado;
    }


    public static String[] HaveObjects(){
        String[] estadoT = {"", ""};

        if(HaveObject()[0] ==  true){
            estadoT[0] = "tem objeto";

        }
        if(HaveObject()[1] == false){
            estadoT[1] = "não tem objeto";

        }



        return estadoT;
    }


    public static void posTwo() {



        int pixelsColetados = 0;
        for(int i = 0; i < HaveObject().length; i++ ) {

            if(HaveObject()[i] == true) {

                pixelsColetados++;
            }
        }

        if(pixelsColetados  == 0) {
            System.out.print("Não faz senido retrair pois não há pixels");
        }
    }
}


