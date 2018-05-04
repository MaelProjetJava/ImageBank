package imagebank;

import javafx.application.Application;
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
	}

	@Override
	public void stop() throws Exception {
		controller.stop();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
