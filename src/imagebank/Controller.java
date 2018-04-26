package imagebank;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
import imagebank.view.ControlView;
import imagebank.view.ListImageView;
import imagebank.view.MainView;

public class Controller {

		Scene scene;
		MainView current;
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
			
			MainView main = new MainView();
			BorderPane border = new BorderPane();
			
			ListImageView flow = new ListImageView(imageDB,main);
			border.setLeft(flow);
			
		    ControlView control = new ControlView();
		    border.setBottom(control);
		    
			//ListImageView flow2 = new ListImageView(imageDB);
			//border.setRight(flow2);
		    
		    //MainView main = new MainView();
		    main.left.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override public void handle (ActionEvent e) {
		    		if (flow.isManaged()) {
		    			//flow.setVisible(false);
		    			flow.setManaged(false);
		    		}
		    		else {
		    			//flow.setVisible(true);
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
		    
		    border.setCenter(main);
			scene = new Scene(border, 1800,1000);

			primaryStage.setScene(scene);
			primaryStage.show();
			
	}

}