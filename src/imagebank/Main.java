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
		//controller.start(primaryStage);
		primaryStage.setTitle("Test exception de l'image");
		StackPane root = new StackPane();
		ImageFile img_f = new ImageFile(System.getProperty("user.home")+"/ImagesProjetJava");
		ArrayList<Image> list_img = img_f.getImagesFile();
		
		/*Image img = new Image(System.getProperty("user.home")+"/ImagesProjetJava/dl3.gif");
		ArrayList<Color> col = img.getDominantColor();
		HBox hbox = new HBox();
		hbox.getChildren().add(new ImageView(img.getFxImage()));
		for(Color c: col)
			hbox.getChildren().add(new Rectangle(50,50, c));*/
		//root.getChildren().add(hbox);
		Scene scene  = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
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
