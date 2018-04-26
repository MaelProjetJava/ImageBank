package imagebank.view;

import java.io.File;

import imagebank.model.Image;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class MainView extends BorderPane {

	public ControlButton left = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/left.png"));
    public ControlButton right = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/right.png"));
	
	public MainView() {
		super();
		this.setStyle("-fx-background-color: yellow;");
	    HBox hbox1 = new HBox();
		hbox1.getChildren().add(left);
		hbox1.setAlignment(Pos.CENTER);
		HBox hbox2 = new HBox();
		hbox2.getChildren().add(right);
		hbox2.setAlignment(Pos.CENTER);
		this.setLeft(hbox1);
	    this.setRight(hbox2);
	    //this.setCenter(value);
	}
	
	public void displayImage(Image i) {
		ImageView iv = new ImageView();
		iv.setImage(i.getFxImage());
		this.getChildren().add(iv);
	}
}