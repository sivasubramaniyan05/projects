package Proj2;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FaceDetector {

	public static void main(String[] args) {
		System.loadLibrary("opencv_java490");
		Mat image=Imgcodecs.imread("image/face.jpg");
		
		//create method for detect and save
		
		detectAndSave(image);
		
	}
	private static void detectAndSave(Mat image) {
		//create some objects;
		MatOfRect faces=new MatOfRect();
		
		//convert to gray Scale;
		Mat grayFrame=new Mat();
		Imgproc.cvtColor(image, grayFrame,Imgproc.COLOR_BGR2GRAY);
		
		//improve contrast for better result;
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		int height=grayFrame.height();
		int absoluteFaceSize=0;
		if(Math.round(height*0.2f)>0) {
			absoluteFaceSize=Math.round(height*0.2f);
		}
		//Detect faces;
		CascadeClassifier faceCascade= new CascadeClassifier();
		
		//lode trained data file
		faceCascade.load("data/haarcascade_frontalface_default.xml");
		faceCascade.detectMultiScale(grayFrame,faces, 1.1, 2, 0|Objdetect.CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize,absoluteFaceSize),new Size());
		
		//write the file
		
		Rect[] faceArray=faces.toArray();
		for(int i=0;i<faceArray.length;i++) {
			//draw rect 
			Imgproc.rectangle(image, faceArray[i], new Scalar(0, 0, 255),3);
			
		}
		Imgcodecs.imwrite("image/output.jpg",image);
		System.out.println("write success:"+faceArray.length);
		}

}
