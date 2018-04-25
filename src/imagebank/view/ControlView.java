package imagebank.view;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {

	ControlButton previous = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/previous.png"));
    ControlButton next = new ControlButton(new File("/D:/Funky_Creep/workspace/ImageBank/images/next.png"));
	
	public ControlView() {
		super();
		this.setAlignment(Pos.CENTER);
	    this.setStyle("-fx-background-color: green;");
	    //this.setHgrow(previous, Priority.ALWAYS);
	    //this.setHgrow(next, Priority.ALWAYS);
	    this.getChildren().addAll(previous,next);
	}
}
