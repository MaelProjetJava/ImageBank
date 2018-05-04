package imagebank.view;

import imagebank.model.tag.Tag;

import java.util.ArrayList;
import java.util.Iterator;

import org.omg.IOP.TAG_ORB_TYPE;

import imagebank.model.ImageDB;
import imagebank.model.ImageDBEvent;
import imagebank.model.ImageDBListener;
import imagebank.model.tag.Tagger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SearchView extends Tab {
	
	ImageDB imageDB;
	
	public SearchView(ImageDB i) {
		super("Search");
		this.setClosable(false);
		this.imageDB=i;
		
		VBox main2 = new VBox();
		main2.setPadding(new Insets(0,0,0,5));

		ScrollPane scrollpane2 = new ScrollPane(main2);
		scrollpane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    
		scrollpane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		scrollpane2.setFitToHeight(true);
		
		ArrayList<Tag> tab_recherche = new ArrayList<Tag>(50);
		
		Iterable<Tag> tags = Tagger.getAllTags();
		Iterator<Tag> it = tags.iterator();
		while (it.hasNext()) {
			String s = (String) it.next().getName();
			CheckBox check = new CheckBox(s);
			
			check.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override public void handle(ActionEvent e) {
	    			if (check.isSelected()) {
	    				tab_recherche.add(Tagger.getTag(s));
	    				Tag tab_recherche_array[] = new Tag[tab_recherche.size()];
	    				tab_recherche_array=tab_recherche.toArray(tab_recherche_array);
	    				imageDB.search(tab_recherche_array);
	    			}
	    			else if (!check.isSelected()) {
	    				tab_recherche.remove(Tagger.getTag(s));
	    				Tag tab_recherche_array[] = new Tag[tab_recherche.size()];
	    				tab_recherche_array=tab_recherche.toArray(tab_recherche_array);
	    				imageDB.search(tab_recherche_array);
	    			}
	    			
	    		}
	    	});

			main2.getChildren().add(check);
		}
		
		this.setContent(scrollpane2);
	}

}
