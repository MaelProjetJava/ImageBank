package imagebank.view;
import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import imagebank.Controller;

public class ListImageView extends TilePane implements ImageDBListener {

	ImageDB imageDB;
	
	public ListImageView(ImageDB i) {
		super();
		this.imageDB=i;
		this.setPrefColumns(2);
		this.setPrefWidth(Controller.WIDTH/5);
		this.setStyle("-fx-background-color: #EFEFEF;");
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		this.getChildren().clear();
		for (int i=0;i<imageDB.getSelectedImages().size();i++) {
			ImageView iv = new ImageView();
			imagebank.model.Image img = imageDB.getSelectedImages().get(i);
			iv.setImage(img.getFxImage());
			iv.setFitWidth(((Controller.WIDTH/5)/2)-30);
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