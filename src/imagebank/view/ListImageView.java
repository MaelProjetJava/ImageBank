package imagebank.view;
import java.io.File;
import java.util.List;

import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import imagebank.model.ImageFile;
import imagebank.model.NotDirectoryException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import imagebank.Controller;

public class ListImageView extends TilePane implements ImageDBListener {

	ImageDB imageDB;
	
	public ListImageView(ImageDB i) {
		super();
		this.imageDB=i;
		this.setPrefColumns(2);
		this.setPrefWidth(300);
		this.setStyle("-fx-background-color: #EFEFEF;");
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		this.getChildren().clear();
		for (int i=0;i<imageDB.getSelectedImages().size();i++) {
			ImageView iv = new ImageView();
			imagebank.model.Image img = imageDB.getSelectedImages().get(i);
			iv.setImage(img.getFxImage());
			iv.setFitWidth(125);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
	        iv.setCache(true);
			Button ib = new Button();
			ib.setGraphic(iv);
			final int index = i;
			
			ib.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	imageDB.setCurrentImage(index);
			    }
			});
			
			this.getChildren().add(ib);
		}
	}
}