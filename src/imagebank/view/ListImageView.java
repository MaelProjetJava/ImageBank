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
		//this.setPadding(new Insets(5,5,5,5));
		//this.setVgap(0);
		//this.setHgap(0);
		this.setPrefWidth(350);
		this.setStyle("-fx-background-color: DAE6F3;");
		
		/*
	        Rectangle2D square = new Rectangle2D(50,50,150,150);
	        pages[i].setViewport(square);
	    */
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		this.getChildren().clear();
		for (int i=0;i<imageDB.getSelectedImages().size();i++) {
			ImageView iv = new ImageView();
			imagebank.model.Image img = imageDB.getSelectedImages().get(i);
			iv.setImage(img.getFxImage());
			iv.setFitWidth(150);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
	        iv.setCache(true);
			Button ib = new Button();
			ib.setGraphic(iv);
			final int index = i;
			
			ib.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	imageDB.setCurrentImage(index);
			    	//System.out.println(imageDB.getSelectedImages().get(index).getName());
			    }
			});
			
			this.getChildren().add(ib);
		}
	}
}