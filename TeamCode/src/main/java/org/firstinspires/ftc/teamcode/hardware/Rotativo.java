package org.firstinspires.ftc.teamcode.hardware;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp

public class Rotativo extends OpMode {
    DcMotor rotativo;


    // não precisa chamar no agregador, pois o mesmo modo de rotativo  se usa no autonomo e no teleop
    public void init(){
    }
    public void loop(){
    }

    public Rotativo(DcMotor rotativo){

        this.rotativo = rotativo;
    }
    public void virar(double velocidade) {
        this.rotativo.setPower(velocidade);
    }
}
