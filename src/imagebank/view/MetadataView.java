package imagebank.view;

import imagebank.model.ImageDB;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MetadataView extends TabPane {

	ImageDB imageDB;
	
	public MetadataView(ImageDB i) {
		super();
		this.imageDB = i;
		this.setStyle("-fx-background-color: yellow;");
		Tab tab1 = new Tab();
		tab1.setText("Tri");
		//tab1.setContent(new Rectangle(1000,1000, Color.LIGHTSTEELBLUE));
		Tab tab2 = new Tab();
		tab2.setText("Ajout");
		//tab2.setContent();
		this.getTabs().addAll(tab1,tab2);
		this.setTabMinWidth(175);
		this.setTabMaxWidth(175);
	}
	
}
