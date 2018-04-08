package imagebank.view;

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
		ListImageView flow = new ListImageView();
		border.setLeft(flow);
		
		ImageView pages[] = new ImageView[8];
	    for (int i=0; i<8; i++) {
	    	Image x = new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image"+i+".jpg",150,150,true,true);
	        pages[i] = new ImageView(x);
	        flow.getChildren().add(pages[i]);
	    }
	    
	    //   ---BOTTOM---
	    ControlView control = new ControlView();
	    
	    ControlButton previous = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/previous.png"));
	    ControlButton next = new ControlButton(new File("/D:/Funky_Creep/workspace/ProjetJava/images/next.png"));
	    
	    control.addToBox(previous);
	    control.addToBox(next);
	    border.setBottom(control);
	    
	    previous.onClickAction();
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
	    
	    MainView main = new MainView();
	    border.setCenter(main);
	    
		Scene scene = new Scene(border, 1800,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
 public static void main(String[] args) {
        launch(args);
    }
}