package imagebank;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.stream.ImageInputStream;
import javafx.application.Application;
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
import imagebank.model.ImageDB;
import imagebank.view.ListImageView;
import imagebank.view.MainView;

public class Controller {

		Scene scene;
		ListImageView flow;
		MainView main;
		ImageDB imageDB;
		
		public Controller() {
			try {
				imageDB = new ImageDB(new File("/D:/Funky_Creep/workspace/ImageBank/images/liste_images"));
			} catch (IOException e) {
				System.out.println("Erreur");
				e.printStackTrace();
			}
		}
		
	    public void start(final Stage primaryStage) {
			
			primaryStage.setTitle("Projet Java Galerie Photos");
			
			BorderPane root = new BorderPane();
			
			ListImageView flow = new ListImageView(imageDB);
			imageDB.addImageDBListener(flow);
			root.setLeft(flow);
			
			MainView main = new MainView();
			imageDB.addImageDBListener(main);
			
			main.left.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (flow.isManaged()) {
		    			flow.setManaged(false);
		    		}
		    		else {
		    			flow.setManaged(true);
		    		}
		    	}
		    });
		    
		    /*main.right.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (flow2.isManaged()) {
		    			//flow2.setVisible(false);
		    			flow2.setManaged(false);
		    		}
		    		else {
		    			//flow2.setVisible(true);
		    			flow2.setManaged(true);
		    		}
		    	}
		    });*/
		    
			
			root.setCenter(main);
			
			//BOTTOM
		    HBox control = new HBox();
		    Button previous = new Button();
		    File f1 = new File("/D:/Funky_Creep/workspace/ImageBank/images/previous.png");
		    try {
				previous.setGraphic(new ImageView(new Image(f1.toURI().toURL().toString(),50,50,true,true)));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		    
		    previous.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        //main.displayImage(image);
			    	//imageDB.show
			    }
			});
		    
		    Button next = new Button();
		    File f2 = new File("/D:/Funky_Creep/workspace/ImageBank/images/next.png");
		    try {
				next.setGraphic(new ImageView(new Image(f2.toURI().toURL().toString(),50,50,true,true)));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		    
		    next.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        //main.displayImage(image);
			    	//imageDB.show
			    }
			});
		    
		    control.getChildren().addAll(previous,next);
		    control.setAlignment(Pos.CENTER);
		    root.setBottom(control);
		    
			//ListImageView flow2 = new ListImageView(imageDB);
			//root.setRight(flow2);
		    
			scene = new Scene(root, 1800,1000);

			primaryStage.setScene(scene);
			primaryStage.show();
			
	}

}