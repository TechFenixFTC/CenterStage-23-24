package org.firstinspires.ftc.teamcode.hardware;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class SistemaLinear extends OpMode {
    DcMotor sistemalinear;


    @Override
    public void init(){
    }

    @Override
    public void loop(){
    }
    //  // não precisa chamar no agregador, pois o mesmo modo de sistema se usa no autonomo e no teleop
    public SistemaLinear(DcMotor sistemalinear){
        this.sistemalinear = sistemalinear;
    }

    public void subir(double velocidade){
        this.sistemalinear.setPower(velocidade);
    }
    public void descer(double velocidade){
        this.sistemalinear.setPower(velocidade  * -1);
    }




}
