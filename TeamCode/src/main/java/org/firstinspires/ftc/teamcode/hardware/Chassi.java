package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
@TeleOp(name="Chassi")

public class Chassi extends OpMode {

		//Nomeando
		public DcMotor FD;
		public DcMotor FE;
		public DcMotor TD;
		public DcMotor TE;
		
		//Potências e HardwareMap somente na classe robô
		
		@Override
		public void init (){
		}
		@Override
		public void loop(){
		}
		
		public Chassi(DcMotor FD, DcMotor FE, DcMotor TD, DcMotor TE){
				
			 this.FD = FD;
			 this.FE = FE;
			 this.TD = TD;
			 this.TE = TE;
			 
			 this.FD.setDirection(DcMotorSimple.Direction.REVERSE);
			 this.TD.setDirection(DcMotorSimple.Direction.REVERSE);
			 
			 this.FD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
			 this.FE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
			 this.TD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
			 this.TE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		}

}
