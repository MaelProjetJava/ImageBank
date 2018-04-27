package imagebank.view;

import java.io.File;
import java.net.MalformedURLException;

import imagebank.model.Image;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainView extends BorderPane implements ImageDBListener {

	public ControlButton left = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/left.png"));
    public ControlButton right = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/right.png"));
	
	public MainView() {
		super();
		this.setStyle("-fx-background-color: yellow;");
	    HBox hbox1 = new HBox();
		hbox1.getChildren().add(left);
		hbox1.setAlignment(Pos.CENTER);
		this.setLeft(hbox1);
		HBox hbox2 = new HBox();
		hbox2.getChildren().add(right);
		hbox2.setAlignment(Pos.CENTER);
	    this.setRight(hbox2);
	}
	
	public void displayImage(Image i) {
		System.out.println("cc");
		/*ImageView iv = new ImageView();
		iv.setImage(i.getFxImage());
		iv.setFitWidth(1200);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
        iv.setCache(true);
		this.setCenter(iv);*/
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		
	}
}