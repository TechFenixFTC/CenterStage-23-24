//package org.firstinspires.ftc.teamcode.vision;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//@Autonomous(name="WebCamm", group="Linear OpMode")
//public class opencv2 extends LinearOpMode {
//    OpenCvWebcam webcam = null;
//    String pos;
//
//
//    @Override
//    public void runOpMode() {
//
//        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
//
//        webcam.setPipeline(new Pipeline());
//
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
//            }
//
//            @Override
//            public void onError(int errorCode) {
//
//            }
//        });
//
//        waitForStart();
//
//        switch (pos){
//            case "Esquerda":
//
//                //autonomo da esquerda
//                break;
//            case "Meio":
//
//                //autonomo da esquerda
//                telemetry.addLine("Meio");
//                break;
//            case "Direita":
//                //autonomo da esquerda
//                break;
//        }
//    }
//    class Pipeline extends OpenCvPipeline{
//        Mat YCbCr = new Mat();
//        Mat leftCrop;
//        Mat rightCrop;
//        Mat midCrop;
//        double leftavgfin;
//        double rightavgfin;
//        double midavgfin;
//        Mat output = new Mat();
//        Scalar rectColor = new Scalar(0.0, 0.0, 255.0);
//        public Mat processFrame(Mat input){
//            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
//            telemetry.addLine("Pipeline rodando");
//
////            Rect leftRect = new Rect(70, 400, 250, 250);
////            Rect rightRect = new Rect(1640, 400, 250, 250);
////            Rect midRect = new Rect(700, 400, 350, 250);
//            Rect rightRect = new Rect(1, 1, 422, 716);
//            Rect leftRect = new Rect(841, 1, 422, 718);
//            Rect midRect = new Rect(421, 1, 422, 718);
//
//            input.copyTo(output);
//            Imgproc.rectangle(output, leftRect, rectColor, 2);
//            Imgproc.rectangle(output, rightRect, rectColor, 2);
//            Imgproc.rectangle(output, midRect, rectColor, 2);
//
//            leftCrop = YCbCr.submat(leftRect);
//            rightCrop = YCbCr.submat(rightRect);
//            midCrop = YCbCr.submat(midRect);
//
//            Core.extractChannel(leftCrop, leftCrop, 1);
//            Core.extractChannel(rightCrop, rightCrop, 1);
//            Core.extractChannel(midCrop, midCrop, 1);
//
//            Scalar leftavg = Core.mean(leftCrop);
//            Scalar rightavg = Core.mean(rightCrop);
//            Scalar midavg = Core.mean(midCrop);
//
//            leftavgfin = leftavg.val[0];
//            rightavgfin = rightavg.val[0];
//            midavgfin = midavg.val[0];
//
//            telemetry.addData("Direita", rightavgfin);
//            telemetry.addData("Meio", midavgfin);
//            telemetry.addData("Esquerda", leftavgfin);
//            telemetry.update();
//
//            if (leftavgfin > rightavgfin && leftavgfin > midavgfin){ // linhas para printar em qual quadrante o
//                //objeto está sendo identificado
//                telemetry.addLine("Esquerda");
//               pos = "Esquerda";
//            } else if (rightavgfin > midavgfin){
//                telemetry.addLine("Direita");
//               pos = "Direita";
//            } else{
//                telemetry.addLine("Meio");
//               pos = "Meio";
//            }
//
//            return (output);
//        }
//    }
//}
