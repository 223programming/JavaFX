package application;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class testController {
	@FXML
	private Button start_btn;
	
	@FXML
	private Button close_btn;
	
	@FXML
	private ImageView image_frame;
	
	private ScheduledExecutorService timer;
	private VideoCapture capture = new VideoCapture();
	private boolean cameraActive = false;
	
	@FXML
	public void startCamera()	{
		
		if (!this.cameraActive)	{
			this.capture.open(0);
			
			if (this.capture.isOpened())	{
				
				this.cameraActive = true;
				
				
				Runnable frameGrabber = new Runnable()	{
					@Override
					public void run()	{
						Image imageToShow = grabFrame();
						image_frame.setImage(imageToShow);
					}
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				
			}
		}
	}
	public void stopCamera()	{
		if (this.cameraActive)
		{
			this.cameraActive = false;
			try	
			{
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
			this.capture.release();
			this.image_frame.setImage(null);
		}
	}
	
	private Image grabFrame()
	{
		Image imageToShow = null;
		Mat frame = new Mat();
		
		if (this.capture.isOpened())
		{
			try
			{
				this.capture.read(frame);
				if (!frame.empty())
				{
					//Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
					Core.flip(frame, frame, 0);
					imageToShow = mat2image(frame);
				}
			}
			catch (Exception e)
			{
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		return imageToShow;
	}
	
	private Image mat2image(Mat frame)
	{
		MatOfByte buffer = new MatOfByte();
		Imgcodecs.imencode(".png", frame, buffer);
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	public void closeProgram()
	{
		stopCamera();
		Stage main = (Stage) close_btn.getScene().getWindow();
		main.close();
		System.exit(0);
	}
	
}
	




