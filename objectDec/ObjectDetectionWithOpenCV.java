package Proj2;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class ObjectDetectionWithOpenCV {
    public static void main(String[] args) {
        // Load native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the classifier XML file for face detection
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("data/haarcascade_frontalface_default.xml");

        // Open the default camera (index 0)
        VideoCapture videoCapture = new VideoCapture(0);

        if (!videoCapture.isOpened()) {
            System.out.println("Error: Couldn't open the camera.");
            return;
        }

        // Set the frame width and height
        videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        // Create a window to display the camera feed
        String windowName = "Object Detection";
        org.opencv.highgui.HighGui.namedWindow(windowName);

        // Process frames from the camera
        Mat frame = new Mat();
        MatOfRect faceDetections = new MatOfRect();
        while (true) {
            // Capture frame from the camera
            if (videoCapture.read(frame)) {
                // Detect faces in the frame
                faceDetector.detectMultiScale(frame, faceDetections);
                
                // Draw rectangles around the detected faces
                for (Rect rect : faceDetections.toArray()) {
                    Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
                }

                // Display the frame
                org.opencv.highgui.HighGui.imshow(windowName, frame);
                org.opencv.highgui.HighGui.waitKey(30); // Adjust the delay as needed
            } else {
                System.out.println("Error: Couldn't capture frame from the camera.");
                break;
            }
        }

        // Release the camera and close the window
        videoCapture.release();
        org.opencv.highgui.HighGui.destroyAllWindows();
        
    }
}


