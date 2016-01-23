package application;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class EntryPoint extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println(Core.getBuildInformation());
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("test.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Webcam Image Grabber");
			primaryStage.show();
		} catch(Exception e) {
			System.out.println("THERE WAS AN ERROR!!!1!11");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try
		{
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			launch(args);
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
	}
}
