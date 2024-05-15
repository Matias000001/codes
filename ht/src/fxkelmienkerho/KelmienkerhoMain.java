package fxkelmienkerho;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma Kelmienkerho ohjelmassa
 * 
 * @author Matias
 * @version 11.5.2024
 *
 */
public class KelmienkerhoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KelmienkerhoGUIView.fxml"));
			final Pane root = ldr.load();
			final KelmienkerhoGUIController kelmienkerhoCtrl = (KelmienkerhoGUIController) ldr.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("kelmienkerho.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Kelmienkerho");
			Kerho kerho = new Kerho();
			kelmienkerhoCtrl.setKerho(kerho);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Pääohjelma
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}