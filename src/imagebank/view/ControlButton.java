import javafx.scene.image.*;
import java.io.File;
import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ControlButton extends Button {

	public ControlButton(File f) {
		super();
		try {
			this.setMaxWidth(100);
			this.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),50,50,true,true)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void onClickAction() {
		new EventHandler<ActionEvent>() {
	    	@Override public void handle (ActionEvent e) {
	    		System.out.println("cc");
	    	}
	};
	}
}
