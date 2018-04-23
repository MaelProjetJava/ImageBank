package imagebank;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

	private Controller controller;

	@Override
	public void init() {
	controller = new Controller();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller.start(primaryStage);
		StackPane root = new StackPane();
		
		Scene scene  = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
