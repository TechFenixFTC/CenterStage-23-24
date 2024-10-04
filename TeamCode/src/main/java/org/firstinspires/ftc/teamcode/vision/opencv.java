//package org.firstinspires.ftc.teamcode.vision;
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
//import com.acmerobotics.dashboard.FtcDashboard;
//@Autonomous(name="webcame")
//public class opencv extends LinearOpMode {
//    OpenCvWebcam webcam = null; //declarar a webcam
//    String linha;
//
//    @Override
//    public void runOpMode() {
//
//        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        OpenCvWebcam camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//        FtcDashboard.getInstance().startCameraStream(camera, 0);
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
//        webcam.setPipeline(new Pipeline());
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT); //definindo o tamanho de tela da sua câmera
//            } //logitech
//
//            @Override
//            public void onError(int errorCode) {
//
//            }
//        });
//
//        waitForStart();
//    }
//
//    class Pipeline extends OpenCvPipeline {
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
//
//            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb); //converter o feedback da camera para YCbCr, deixa o desempenho melhor por otimiza-lo
//            telemetry.addLine("Pipeline rodando");
//
////            Rect leftRect = new Rect(1, 1, 639, 1079);
////            Rect rightRect = new Rect(1280, 1, 639, 1079); //altura e largura de cada quadrante, rect() desenha o retangulo
////            Rect midRect = new Rect(640, 1, 639, 1079);
////             Rect leftRect = new Rect(new Point(10, 100), new Point(105, 200));
////             Rect rightRect = new Rect(new Point(120, 100), new Point(205, 200));
////             Rect midRect = new Rect(new Point(220, 100), new Point(310, 200));
//            Rect leftRect = new Rect(1, 1, 422, 716); //w é horiz, h é vert
//            Rect midRect = new Rect(421, 1, 422, 716);  //altura e largura de cada quadrante, rect() desenha o retangulo
//            Rect rightRect = new Rect(841, 1, 422, 716);
//
//            input.copyTo(output);
//            Imgproc.rectangle(output, leftRect, rectColor,2);
//            Imgproc.rectangle(output, rightRect, rectColor, 2); //esse código permite voce vizualizar os
//            //quadrantes na sua driver hub, muito util para testes e verificação
//            Imgproc.rectangle(output, midRect, rectColor, 2); //2
//
//            leftCrop = YCbCr.submat(leftRect);
//            rightCrop = YCbCr.submat(rightRect);
//            midCrop = YCbCr.submat(midRect);
//
//            Core.extractChannel(leftCrop, leftCrop, 1);
//            Core.extractChannel(rightCrop, rightCrop, 1);
//            Core.extractChannel(midCrop, midCrop, 1);//1
//
//            Scalar leftavg = Core.mean(leftCrop);
//            Scalar rightavg = Core.mean(rightCrop);
//            Scalar midavg = Core.mean(midCrop);
//
//            leftavgfin = leftavg.val[0];  //linhas de escaneamento, isso que mexe com os numeros que aparecem
//            rightavgfin = rightavg.val[0];//0 "liga" elas e 1 "desliga"
//            midavgfin = midavg.val[0];
//
//            telemetry.addData("Esquerda", leftavgfin);
//            telemetry.addData("Meio", midavgfin);
//            telemetry.addData("Direita", rightavgfin);
//            telemetry.update();
//
//            if (leftavgfin > rightavgfin && leftavgfin > midavgfin){ // linhas para printar em qual quadrante o
//                //objeto está sendo identificado
//                telemetry.addLine("Esquerda");
//                linha = "Esquerda";
//            } else if (rightavgfin > midavgfin){
//                telemetry.addLine("Direita");
//                linha = "Direita";
//            } else{
//                telemetry.addLine("Meio");
//                linha = "Meio";
//            }
//
//            return (output);
//            }
//        }
//    }
//
