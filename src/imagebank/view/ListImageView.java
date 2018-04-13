package imagebank.view;
import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ListImageView extends TilePane {

	public ListImageView() {
		super();
		this.setPadding(new Insets(0,0,0,0));
		this.setVgap(0);
		this.setHgap(0);
		this.setPrefWidth(300);
		//this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: DAE6F3;");
		
		ImageView pages[] = new ImageView[8];
	    for (int i=0; i<8; i++) {
	    	//ImageButton i = new ImageButton("left",new File("/D:/Funky_Creep/workspace/ProjetJava/images/left.png"));
	    	
	    	Image x = new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image"+i+".jpg",150,150,true,true);
	        pages[i] = new ImageView(x);
	        pages[i].setImage(x);
	        pages[i].setPreserveRatio(true);
	        pages[i].setSmooth(true);
	        
	        
	        
	        
	        
	        //pages[i].setFitWidth(150);
	        //pages[i].setFitHeight(150);
	        //Rectangle2D square = new Rectangle2D(50,50,150,150);
	        //pages[i].setViewport(square);*/
	        this.getChildren().add(pages[i]);
	    }
	}

}