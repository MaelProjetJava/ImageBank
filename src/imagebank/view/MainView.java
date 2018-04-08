package imagebank.view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainView extends BorderPane {

	public MainView() {
		super();
		this.setStyle("-fx-background-color: yellow;");
		ControlButton left = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/left.png"));
	    ControlButton right = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/right.png"));
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
	
	
}
