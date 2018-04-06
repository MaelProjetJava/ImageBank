import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class ViewFlow extends FlowPane {

	public ViewFlow() {
		super();
		this.setPadding(new Insets(10,10,10,10));
		this.setVgap(4);
		this.setHgap(4);
		this.setPrefWrapLength(350);
		this.setStyle("-fx-background-color: DAE6F3;");
	}
	

}
