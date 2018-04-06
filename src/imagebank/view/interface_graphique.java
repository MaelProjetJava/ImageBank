import java.io.File;

import javax.imageio.stream.ImageInputStream;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;

public class interface_graphique extends Application {
    
	@Override
    public void start(Stage primaryStage) {
		
		BorderPane border = new BorderPane();

		//   ---LEFT---
		ViewFlow flow = new ViewFlow();
		border.setLeft(flow);
		
		ImageView pages[] = new ImageView[8];
	    for (int i=0; i<8; i++) {
	    	Image x = new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image"+i+".jpg",150,150,true,true);
	        pages[i] = new ImageView(x);
	        flow.getChildren().add(pages[i]);
	    }
	    
	    //   ---BOTTOM---
	    ControlButton previous = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/previous.png"));
	    ControlButton next = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/next.png"));
	    
	    ViewControl control = new ViewControl();
	    control.addToBox(previous);
	    control.addToBox(next);
	    border.setBottom(control);
	    
	    //previous.onClickAction();
	    //   ---ACTION BOUTON PREVIOUS---
	    previous.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override public void handle (ActionEvent e) {
	    		//System.out.println("previous");
	    		border.setCenter(new ImageView(new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image1.jpg",150,150,true,true)));
	    	}
	    });
	    
	    //   ---ACTION BOUTON NEXT---
	    next.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override public void handle (ActionEvent e) {
	    		//System.out.println("next");
	    	}
	    });
	    
	    /*HBox hbox = new HBox();
	    hbox.setAlignment(Pos.CENTER);
	    hbox.setStyle("-fx-background-color: green;");
	    //hbox.setHgrow(previous, Priority.ALWAYS);
	    //hbox.setHgrow(next, Priority.ALWAYS);*/
	    //hbox.getChildren().addAll(previous,next);
	    
	    //   ---CENTER---
	    GridPane grid = new GridPane();
	    border.setCenter(grid);
	    grid.setStyle("-fx-background-color: yellow;");
	    
		Scene scene = new Scene(border, 1800,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	
	private void button(String name,File f) {
		Image image = new Image(f.toURI().toString(),50,50,true,true);
	    Button btn = new Button();
	    btn.setGraphic(new ImageView(image));
	}
	
 public static void main(String[] args) {
        launch(args);
    }
}