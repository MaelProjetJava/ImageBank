package imagebank.view;

import javafx.scene.image.*;
import java.io.File;
import java.net.MalformedURLException;

import imagebank.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ControlButton extends Button {

	public ControlButton(File f) {
		super();
		try {
			this.setMaxWidth(100);
			this.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
