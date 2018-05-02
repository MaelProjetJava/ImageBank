package imagebank.view;

import java.util.Iterator;

import imagebank.model.Image;
import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import imagebank.model.tag.Tag;
import imagebank.model.tag.Tagger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.IntegerStringConverter;

public class MetadataView extends TabPane implements ImageDBListener {

	ImageDB imageDB;
	
	public MetadataView(ImageDB i) {
		super();
		this.imageDB = i;
		this.setStyle("-fx-background-color: #EFEFEF;");
		
		Tab tab1 = new Tab();
		tab1.setClosable(false);
		tab1.setText("Metadata");
		Tab tab2 = new Tab();
		tab2.setClosable(false);
		tab2.setText("Search");
		
		//tab2.setContent();
		this.getTabs().add(tab1);
		this.getTabs().add(tab2);
		
		this.setTabMinWidth(100);
		this.setTabMaxWidth(160);
		this.setTabMinHeight(30);
		this.setTabMaxHeight(30);
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		Image img = imageDB.getCurrentImage();
		//this.getTabs().clear();
		VBox main = new VBox();
		main.setPrefWidth(250);
		for (Tag t : img.getTags()) {
			VBox vbox = new VBox();
			//vbox.setPrefWidth(250);
			String l = t.getName();
			if (t.hasValue()) {
				l = l + ":" + " " + t.getValue();
			}
			
			System.out.println(l);
			Text text = new Text(l);
			//text.setTextAlignment(TextAlignment.CENTER);
			vbox.getChildren().add(text);
			
			Button modify = new Button("Modify");
			vbox.getChildren().add(modify);
			
			modify.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	if (vbox.getChildren().size()==3) {
			    		TextField tf = new TextField();
			    		//tf.setPrefWidth(50);
				    	Button submit = new Button("Submit");
				    	vbox.getChildren().addAll(tf,submit);
				    	
				    	submit.setOnAction(new EventHandler<ActionEvent>() {
						    @Override public void handle(ActionEvent e) {
						    	if (tf.getText()!=null) {
						    		IntegerStringConverter sc = new IntegerStringConverter();
						    		if (t.hasValue()) {
						    			Tagger.untag(img, t);
						    			Tagger.tag(img, Tagger.getTag(t.getName(),sc.fromString(tf.getText())));
						    			
						    		}
						    		else {
						    			Tagger.untag(img, t);
						    			Tagger.getTag(t.getName());
						    		}
						    		vbox.getChildren().removeAll(tf,submit);
						    	}
						    	
						    }
						});
			    	}
			    	
			    }
			});
			
			Button delete = new Button("Delete");
			vbox.getChildren().add(delete);
			
			delete.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	Tagger.untag(img,t);
			    	main.getChildren().remove(vbox);
			    }
			});
			
			
			
			vbox.setSpacing(10);
			main.getChildren().add(vbox);
			main.setSpacing(20);
		}
		
		Button addmetadata = new Button("Add new metadata");
		main.getChildren().add(addmetadata);
		
		addmetadata.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	TextField name = new TextField("Tag Name");
		    	TextField value = new TextField("Tag Value (optional)");
		    	Button submit = new Button("Submit");
		    	main.getChildren().addAll(name,value,submit);
		    	
		    	if (value.getText()==null && name.getText()!=null) {
			    	Tagger.tag(img,Tagger.getTag(name.getText()));
			    	
			    	Text text = new Text(name.getText());
			    	VBox newtag = new VBox();
			    	newtag.getChildren().add(text);
			    	main.getChildren().add(newtag);
		    	}
		    	
		    }
		});
		
		this.getTabs().get(0).setContent(main);
	}
	
}
