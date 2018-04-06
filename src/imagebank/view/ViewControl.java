import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import ControlButton.java;

public class ViewControl extends HBox {

	public ViewControl() {
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
