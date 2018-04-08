package imagebank.view;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ListImageView extends FlowPane {

	public ListImageView() {
		super();
		this.setPadding(new Insets(10,10,10,10));
		this.setVgap(4);
		this.setHgap(4);
		this.setPrefWrapLength(350);
		this.setStyle("-fx-background-color: DAE6F3;");
	}
	
	/*ImageView pages[] = new ImageView[8];
    for (int i=0; i<8; i++) {
    	Image x = new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image"+i+".jpg",150,150,true,true);
        pages[i] = new ImageView(x);
        flow.getChildren().add(pages[i]);
    }*/

}
