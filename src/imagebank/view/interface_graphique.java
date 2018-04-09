package imagebank.view;

import java.io.File;

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

public class interface_graphique extends Application {
    
	Scene scene0,scene1,scene2,scene3;
	
	@Override
    public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Projet Java Galerie Photos");
		
		//Scene0
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
	    border.setBottom(control);
	    
	    //   ---RIGHT---
		ListImageView flow2 = new ListImageView();
		border.setRight(flow2);
	    
	    //   ---CENTER---
	    MainView main = new MainView();
	    main.left.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override public void handle (ActionEvent e) {
	    		primaryStage.setScene(scene1);
	    	}
	    });
	    main.right.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override public void handle (ActionEvent e) {
	    		primaryStage.setScene(scene2);
	    	}
	    });
	    
	    border.setCenter(main);
	    
		scene0 = new Scene(border, 1800,1000);

		//Scene1
		BorderPane border2 = new BorderPane();
		
		//   ---BOTTOM---
		ControlView control2 = new ControlView();
		border2.setBottom(control2);

		//   ---RIGHT---
		ListImageView flow3 = new ListImageView();
		border2.setRight(flow3);

		//   ---CENTER---
		MainView main2 = new MainView();
		main2.left.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene0);
			}
		});

		main2.right.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene3);
			}
		});

		border2.setCenter(main2);

		scene1 = new Scene(border2, 1800,1000);
		
		//Scene2

		BorderPane border3 = new BorderPane();

		//   ---LEFT---
		ListImageView flow4 = new ListImageView();
		border3.setLeft(flow4);

		ImageView pages2[] = new ImageView[8];
		for (int i=0; i<8; i++) {
			Image x = new Image("file:///D:/Funky_Creep/workspace/ProjetJava/images/image"+i+".jpg",150,150,true,true);
			pages2[i] = new ImageView(x);
			flow4.getChildren().add(pages2[i]);
		}
		//   ---BOTTOM---
		ControlView control3 = new ControlView();
		border3.setBottom(control3);


		//   ---CENTER---
		MainView main3 = new MainView();

		main3.left.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene3);
			}
		});

		main3.right.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene0);
			}
		});

		border3.setCenter(main3);

		scene2 = new Scene(border3, 1800,1000);

		//Scene3

		BorderPane border4 = new BorderPane();

		//   ---BOTTOM---
		ControlView control4 = new ControlView();
		border4.setBottom(control4);


		//   ---CENTER---
		MainView main4 = new MainView();

		main4.left.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene2);
			}
		});

		main4.right.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle (ActionEvent e) {
				primaryStage.setScene(scene1);
			}
		});

		border4.setCenter(main4);

		scene3 = new Scene(border4, 1800,1000);

		primaryStage.setScene(scene0);
		primaryStage.show();
	}
	
 public static void main(String[] args) {
        launch(args);
    }
}