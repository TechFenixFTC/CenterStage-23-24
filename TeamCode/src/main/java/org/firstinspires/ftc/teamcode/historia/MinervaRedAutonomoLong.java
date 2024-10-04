/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
package org.firstinspires.ftc.teamcode.historia;

import android.util.Size;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.autonomos.RoboAutonomous;
import org.firstinspires.ftc.teamcode.hardware.Rotativo;
import org.firstinspires.ftc.teamcode.hardware.SistemaLinear;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
/*
@Autonomous(name = "RED LONG", group = "Concept")

public class MinervaRedAutonomoLong extends LinearOpMode {

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

    BHI260IMU imu;
    Orientation angles;
    BHI260IMU.Parameters parameters;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "MINERVARED.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/myCustomModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "vermelho","vermelho2"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
/*

    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */

/*
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() throws InterruptedException {

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

        RoboAutonomous robo = new RoboAutonomous(motorFD, motorFE, motorTD, motorTE,imu);

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        //telemetria

        if (!isStarted()) {
            while (!isStarted() && !isStopRequested()) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }
        boolean propFound = false;
        String propLocation = "";

        robo.parar();
        waitForStart();

        telemetryTfod();

        garra2.setPosition(0.75);
        garra1.setPosition(0.75);
        sleep(1000);
        angulo2.setPosition(0.48);

        robo.virar(-90, 0.2, 1);
        robo.andar(1200, 0.3, 0);

        if(propLocation.equals("center")){
            //meio
        } else if(propLocation.equals("right")){
            //direita
        } else{
            //esquerda
        }
         //retrair linear

        robo.virar(90, 0.2,1);
        robo.andar(2800, 0.3,0);
        robo.virar(90, 0.2,1);
        robo.andar(600, 0.3,0);
        robo.virar(-90, 0.2, 1);
        robo.andar(400, 0.2,0);

        //virar linear, angular garra e colocar no backdrop

        robo.andar(3000, 0.3, 0);
        robo.virar(-90, 0.2, 1);
        robo.andar(500, 0.3, 0);
        robo.virar(90,0.2, 1 );
        robo.andar(500, 0.3,0);
        sleep(1000);
        while (motorRotativo.getCurrentPosition() >= -500){
            motorRotativo.setPower(-0.5);
        }
        motorRotativo.setPower(-0.2);
        angulo2.setPosition(0.5);
        sleep(1000);
        robo.andar(400, 0.5, 0 );

        robo.andar(1500, 0.3, 90);
        while (motorsistemalinear.getCurrentPosition() >= -700){
            motorsistemalinear.setPower(-0.5);
        }
        motorsistemalinear.setPower(0.1);
        motorRotativo.setPower(0.1);
        sleep(200);

        garra2.setPosition(0.2);
        garra1.setPosition(0.2);
        sleep(1000);

        //estaciona

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */

/*
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */


/*
    public void telemetryTfod() {
        boolean propFound = false;
        String propLocation = "";

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

        // Step through the list of recognitions and look for pixel
        for (Recognition recognition : currentRecognitions) {
            if (recognition.getLeft() < 100) {
                propLocation = "left";
                propFound = true;
                telemetry.addData("ESQUERDAAA", "");
                break;
            }
            else if (recognition.getLeft() < 450) {
                propLocation = "center";
                propFound = true;
                telemetry.addData("MEIOOOO", "");
                break;
            }
            else {
                propLocation = "right";
                propFound = true;
                telemetry.addData("DIREITAAA", "");
                break;
            }
        }    // end for() loop


    }   // end method telemetryTfod()

}   // end class

*/
