package imagebank.view;

import java.io.File;
import java.net.MalformedURLException;

import imagebank.Controller;
import imagebank.model.Image;
import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainView extends BorderPane implements ImageDBListener {
	
	ImageDB imageDB;

	public ControlButton left = new ControlButton(new File("images/left.png"));
    public ControlButton right = new ControlButton(new File("images/right.png"));
	
	public MainView(ImageDB i) {
		super();
		this.imageDB=i;
		this.setStyle("-fx-background-color: grey;");
	    HBox hbox1 = new HBox();
		hbox1.getChildren().add(left);
		hbox1.setAlignment(Pos.CENTER);
		this.setLeft(hbox1);
		HBox hbox2 = new HBox();
		hbox2.getChildren().add(right);
		hbox2.setAlignment(Pos.CENTER);
	    this.setRight(hbox2);
	}
	
	public void displayImage(Image i) {
		ImageView iv = new ImageView();
		iv.setImage(i.getFxImage());
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
        iv.setCache(true);
		this.setCenter(iv);
		if (Controller.leftClosed && Controller.rightClosed) {
			if (i.getFxImage().getWidth()/(Controller.WIDTH-100)<2) {
				iv.setPreserveRatio(false);
				iv.setFitHeight(Controller.HEIGHT-100);
				iv.setFitWidth(Controller.WIDTH-100);
			}
			else {
				iv.setFitWidth(Controller.WIDTH-100);
			}
		}
		else if ((Controller.leftClosed && !Controller.rightClosed) || (!Controller.leftClosed && Controller.rightClosed)) {
			iv.setFitWidth(Controller.WIDTH/2 + Controller.WIDTH/5 - 30);
		}
		else {
			iv.setFitWidth(Controller.WIDTH/2-30);
			//iv.setFitHeight(5/6*Controller.HEIGHT);
		}
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		Image img = imageDB.getCurrentImage();
		displayImage(img);
	}
}
