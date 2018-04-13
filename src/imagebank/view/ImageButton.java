package imagebank.view;

import javafx.scene.control.Button;
import javafx.scene.image.*;
import java.io.File;
import java.net.MalformedURLException;

public class ImageButton extends Button {

	//String nom;
	//File chemin;
	
	public ImageButton(String s, File f) {
		super();
		//this.nom=s;
		//this.chemin=f;
		
		/*this.setMaxWidth(150);
		this.setMaxHeight(150);
		this.setText(s);
		try {
			this.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),50,50,true,true)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}*/
	}
	
}
