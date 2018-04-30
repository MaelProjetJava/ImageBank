package imagebank.view;

import java.util.Iterator;

import imagebank.model.Image;
import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import imagebank.model.tag.Tag;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MetadataView extends TabPane implements ImageDBListener {

	ImageDB imageDB;
	
	public MetadataView(ImageDB i) {
		super();
		this.imageDB = i;
		this.setStyle("-fx-background-color: #EFEFEF;");
		
		Tab tab1 = new Tab();
		tab1.setClosable(false);
		tab1.setText("Metadata");
		Tab tab2 = new Tab();
		tab2.setClosable(false);
		tab2.setText("Ajout");
		
		//tab2.setContent();
		this.getTabs().add(tab1);
		this.getTabs().add(tab2);
		
		this.setTabMinWidth(125);
		this.setTabMaxWidth(125);
		this.setTabMinHeight(30);
		//this.setTabMaxHeight(20);
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		Image img = imageDB.getCurrentImage();
		//this.getTabs().clear();
		VBox vbox = new VBox();
		for (Tag t : img.getTags()) {
			String l = t.getName();
			if (t.hasValue()) {
				l = l + ":" + " " + t.getValue();
			}
			System.out.println(l);
			vbox.getChildren().add(new Text(l));
		}
		
		this.getTabs().get(0).setContent(vbox);
	}
	
}
