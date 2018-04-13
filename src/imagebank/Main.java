package imagebank;

import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
		ImageView iv = new ImageView();
		Image img = new Image();
		
		iv.setImage(img);
		root.getChildren().add(iv);
		Scene scene  = new Scene(root);
		
	}

	public static void main(String args[]) {
		launch(args);
	}
}
