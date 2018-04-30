package imagebank;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.stream.ImageInputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.*;
import imagebank.model.ImageDB;
import imagebank.view.ListImageView;
import imagebank.view.MainView;
import imagebank.view.MetadataView;

public class Controller {

		Scene scene;
		ListImageView flow;
		MainView main;
		MetadataView meta;
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
			
			//LEFT
			
			ListImageView flow = new ListImageView(imageDB);
			imageDB.addImageDBListener(flow);
			root.setLeft(flow);
			
			ScrollPane scrollpane = new ScrollPane(flow);
			scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    
			scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    
			scrollpane.setFitToHeight(true);
			scrollpane.setFitToWidth(true);
	        root.setLeft(scrollpane);
	        
			//CENTER
			MainView main = new MainView(imageDB);
			imageDB.addImageDBListener(main);
			
			main.left.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (flow.isManaged()) {
		    			flow.setManaged(false);
		    			root.setLeft(null);
		    			File f = new File("/D:/Funky_Creep/workspace/ImageBank/images/right.png");
		    			try {
							main.left.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),50,50,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    		}
		    		else {
		    			flow.setManaged(true);
		    			root.setLeft(scrollpane);
		    			File f = new File("/D:/Funky_Creep/workspace/ImageBank/images/left.png");
		    			try {
							main.left.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),50,50,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    		}
		    	}
		    });
			
			//POURQUOI CA MARCHE PAS???????????????????????????????????????????????????????????????
		    main.right.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (meta.isManaged()) {
		    			meta.setManaged(false);
		    			root.setRight(null);
		    		}
		    		else {
		    			meta.setManaged(true);
		    			root.setRight(meta);
		    		}
		    	}
		    });
		    
			
			root.setCenter(main);
			
			//RIGHT
			MetadataView meta = new MetadataView(imageDB);
			root.setRight(meta);
			
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
			    	imageDB.showPreviousImage();
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
			    	imageDB.showNextImage();
			    }
			});
		    
		    control.getChildren().addAll(previous,next);
		    control.setAlignment(Pos.CENTER);
		    
		    root.addEventFilter(KeyEvent.KEY_PRESSED, event->{
	            if (event.getCode() == KeyCode.LEFT) {
	            	imageDB.showPreviousImage();
	            }
	        });
		    
		    root.addEventFilter(KeyEvent.KEY_PRESSED, event->{
	            if (event.getCode() == KeyCode.RIGHT) {
	            	imageDB.showNextImage();
	            }
	        });
		    
		    root.setBottom(control);
		    
			scene = new Scene(root, 1800,1000);

			primaryStage.setScene(scene);
			primaryStage.show();
			
	}

}