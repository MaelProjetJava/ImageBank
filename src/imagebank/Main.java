package imagebank;

import imagebank.model.Image;
import imagebank.model.ImageFile;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class Main extends Application {

	private Controller controller;

	@Override
	public void init() {
		controller = new Controller();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller.start(primaryStage);
	}

	public static void main(String args[]) {
		try {
			launch(args);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
