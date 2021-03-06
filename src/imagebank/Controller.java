package imagebank;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		String s = "images/";
		public static boolean leftClosed;
		public static boolean rightClosed;
		public static int WIDTH=800;
		public static int HEIGHT=600;
		
		public Controller() {
			try {
				imageDB = new ImageDB(new File(s+"liste_images"));
			} catch (IOException e) {
				System.out.println("Erreur");
				e.printStackTrace();
			}
		}
		
	    public void start(final Stage primaryStage) {
			
			primaryStage.setTitle("Projet Java Galerie Photos");
			
			BorderPane root = new BorderPane();
			
			//LEFT
			flow = new ListImageView(imageDB);
			imageDB.addImageDBListener(flow);
			root.setLeft(flow);
			
			ScrollPane scrollpane = new ScrollPane(flow);
			scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    
			scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			scrollpane.setFitToHeight(true);
			scrollpane.setFitToWidth(true);
	        root.setLeft(scrollpane);
	        
			//CENTER
			main = new MainView(imageDB);
			imageDB.addImageDBListener(main);
			
			main.left.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (flow.isManaged()) {
		    			flow.setManaged(false);
		    			root.setLeft(null);
		    			File f = new File(s+"right.png");
		    			try {
							main.left.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    			leftClosed=true;
		    		}
		    		else {
		    			flow.setManaged(true);
		    			root.setLeft(scrollpane);
		    			File f = new File(s+"left.png");
		    			try {
							main.left.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    			leftClosed=false;
		    		}
		    	}
		    });
			
		    main.right.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (meta.isManaged()) {
		    			meta.setManaged(false);
		    			root.setRight(null);
		    			File f = new File(s+"left.png");
		    			try {
							main.right.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    			rightClosed=true;
		    		}
		    		else {
		    			meta.setManaged(true);
		    			root.setRight(meta);
		    			File f = new File(s+"right.png");
		    			try {
							main.right.setGraphic(new ImageView(new Image(f.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
		    			rightClosed=false;
		    		}
		    	}
		    });
		    
			root.setCenter(main);
			
			//RIGHT
			meta = new MetadataView(imageDB);
			imageDB.addImageDBListener(meta);
			
			meta.addEventFilter(KeyEvent.KEY_PRESSED, event ->{
				if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
					event.consume();
				}
			});
			
			root.setRight(meta);
			
			//BOTTOM
		    HBox control = new HBox();
		    Button previous = new Button();
		    File f1 = new File(s+"previous.png");
		    try {
				previous.setGraphic(new ImageView(new Image(f1.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		    
		    previous.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	imageDB.showPreviousImage();
			    }
			});
		    
		    Button next = new Button();
		    File f2 = new File(s+"next.png");
		    try {
				next.setGraphic(new ImageView(new Image(f2.toURI().toURL().toString(),Controller.WIDTH/25,Controller.WIDTH/25,true,true)));
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
		    control.setStyle("-fx-background-color: #EFEFEF;");
		    
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
		    
			scene = new Scene(root,WIDTH,HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.show();
			
	}

	public void stop() throws Exception {
		imageDB.save();
	}
}
