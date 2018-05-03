package imagebank.view;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import imagebank.Controller;
import imagebank.model.DominantColor;
import imagebank.model.Image;
import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import imagebank.model.tag.Tag;
import imagebank.model.tag.Tagger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.IntegerStringConverter;

public class MetadataView extends TabPane implements ImageDBListener {

	ImageDB imageDB;
	VBox main;
	
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
		
		this.setTabMinWidth(((Controller.WIDTH/5)/2)-30);
		this.setTabMaxWidth(((Controller.WIDTH/5)/2)-30);
		this.setTabMinHeight(30);
		this.setTabMaxHeight(30);
		this.setPrefWidth(Controller.WIDTH/5);
		
		
	}

	@Override
	public void imageDBChanged(ImageDBEvent event) {
		Image img = imageDB.getCurrentImage();
		VBox main = new VBox();
		main.setPadding(new Insets(0,0,0,5));
		ScrollPane scrollpane2 = new ScrollPane(main);
		scrollpane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    
		scrollpane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollpane2.setFitToHeight(true);
		scrollpane2.setFitToWidth(true);
		
		LinkedHashSet hashset = new LinkedHashSet();
		hashset.add(Tagger.getTag("Format"));
		hashset.add(Tagger.getTag("Path"));
		hashset.add(Tagger.getTag("Owner"));
		for (Tag t : img.getTags()) {
			String l = t.getName();
			DominantColor d = new DominantColor();
			for (int i=0;i<d.getAllNameColors().length;i++) {
				if (l==d.getAllNameColors()[i]) {
					hashset.add(t);
				}
			}
			if (l=="Size" || l=="Width" || l=="Height") {
				hashset.add(t);
			}
			Iterator e = hashset.iterator();
			boolean trouve=false;
			while (e.hasNext() && !trouve) {
				if (t.hasTag((Tag)e.next())) {
					hashset.add(t);
					trouve=true;
				}
			}
			VBox vbox = new VBox();
			
			if (t.hasValue()) {
				l = l + ":" + " " + t.getValue();
			}
			
			Text text = new Text(l);
			vbox.getChildren().add(text);
			
			if (!hashset.contains(t) ) {
				Button modify = new Button("Modify");
				vbox.getChildren().add(modify);
				
				modify.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	if (vbox.getChildren().size()==3) {
				    		if (!t.hasValue()) {
				    			TextField name = new TextField();
					    		name.setPromptText("New Tag Name");
					    		Button submit = new Button("Submit");
						    	Button cancel = new Button("Cancel");
						    	vbox.getChildren().addAll(name,submit,cancel);
						    	
						    	cancel.setOnAction(new EventHandler<ActionEvent>() {
						    		@Override public void handle(ActionEvent e) {
						    			vbox.getChildren().removeAll(name,submit,cancel);
						    		}
						    	});
						    	
						    	submit.setOnAction(new EventHandler<ActionEvent>() {
								    @Override public void handle(ActionEvent e) {
								    	if (!name.getText().isEmpty()) {
								    		Tagger.untag(img, t);
								    		Tagger.tag(img,Tagger.getTag(name.getText()));
								    	}
								    	
								    }
								});
						    	
				    		}
				    		
				    		else {
				    			TextField value = new TextField();
				    			value.setPromptText("New Tag Value");
				    			Button submit = new Button("Submit");
						    	Button cancel = new Button("Cancel");
						    	vbox.getChildren().addAll(value,submit,cancel);
						    	
						    	cancel.setOnAction(new EventHandler<ActionEvent>() {
						    		@Override public void handle(ActionEvent e) {
						    			vbox.getChildren().removeAll(value,submit,cancel);
						    		}
						    	});
				    			
						    	submit.setOnAction(new EventHandler<ActionEvent>() {
								    @Override public void handle(ActionEvent e) {
								    	if (!value.getText().isEmpty()) {
								    		IntegerStringConverter sc = new IntegerStringConverter();
								    		Tagger.untag(img, t);
								    		Tagger.tag(img, Tagger.getTag(t.getName(),sc.fromString(value.getText())));
								    	}
								    	
								    }
								});
						    	
				    		}
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
			}
			
			vbox.setSpacing(10);
			main.getChildren().add(vbox);
			main.setSpacing(20);
		}
		
		Button addmetadata = new Button("Add new metadata");
		main.getChildren().add(addmetadata);
		
		addmetadata.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    		TextField name = new TextField();
			    	name.setPromptText("Tag Name");
			    	TextField value = new TextField();
			    	value.setPromptText("Tag Value (optional)");
			    	Button submit = new Button("Submit");
			    	Button cancel = new Button("Cancel");
			    	main.getChildren().addAll(name,value,submit,cancel);
			    	
			    	cancel.setOnAction(new EventHandler<ActionEvent>() {
			    		@Override public void handle(ActionEvent e) {
			    			main.getChildren().removeAll(name,value,submit,cancel);
			    		}
			    	});
			    	
			    	submit.setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					    	if (value.getText().isEmpty() && !name.getText().isEmpty()) {
						    	Tagger.tag(img,Tagger.getTag(name.getText()));
						    	Text text = new Text(name.getText());
						    	VBox newtag = new VBox();
						    	newtag.getChildren().add(text);
						    	main.getChildren().add(newtag);
					    	}
					    	
					    	else if (!value.getText().isEmpty() && !name.getText().isEmpty()) {
					    		IntegerStringConverter sc = new IntegerStringConverter();
					    		Tagger.tag(img, Tagger.getTag(name.getText(),sc.fromString(value.getText())));
						    	Text text = new Text(name.getText());
						    	VBox newtag = new VBox();
						    	newtag.getChildren().add(text);
						    	main.getChildren().add(newtag);
					    	}
					    }
					});
		    		
		    	}
		    	
		});
		
		this.getTabs().get(0).setContent(scrollpane2);
	}
	
}
