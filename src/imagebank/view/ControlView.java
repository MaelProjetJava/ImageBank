package imagebank.view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {

	public ControlView() {
		super();
		this.setAlignment(Pos.CENTER);
	    this.setStyle("-fx-background-color: green;");
	    //this.setHgrow(previous, Priority.ALWAYS);
	    //this.setHgrow(next, Priority.ALWAYS);
	}
	
	public void addToBox(ControlButton c) {
		this.getChildren().add(c);
	}
}
