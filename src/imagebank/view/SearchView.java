package imagebank.view;

import imagebank.model.tag.Tag;

import java.util.ArrayList;
import java.util.Iterator;

import org.omg.IOP.TAG_ORB_TYPE;

import imagebank.model.ImageDB;
import imagebank.model.tag.Tagger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchView extends Tab {
	
	public SearchView(ImageDB i) {
		super("Search");
		ComboBox<String> format_box = new ComboBox<String>();
		ComboBox<Integer> size_box = new ComboBox<Integer>();
		ComboBox<String> color_box = new ComboBox<String>();
		ComboBox<String> color_box2 = new ComboBox<String>();
		ComboBox<Integer> width_box = new ComboBox<Integer>();
		ComboBox<Integer> height_box = new ComboBox<Integer>();
		
		format_box.getItems().addAll("JPEG", "PNG");
		size_box.getItems().addAll(20480, 40960, 61440, 81920, 192512);
		color_box.getItems().addAll(
				"Red","Brown","Yellow","Green",
				"Cyan","DarkCyan","Blue","DarkMagenta",
				"Magenta","White","LightGray","Gray",
				"DarkGray","Black");
		color_box2.getItems().addAll(
				"Red","Brown","Yellow","Green",
				"Cyan","DarkCyan","Blue","DarkMagenta",
				"Magenta","White","LightGray","Gray",
				"DarkGray","Black");
		width_box.getItems().addAll(500, 800, 1024, 1600, 1920);
		height_box.getItems().addAll(500, 600, 720, 1200, 1080);
		
		String[] tag_names = {"Format", "Color 1", "Color 2", "Size", "Width", "Height"};
		ComboBox[] comboboxes = {format_box, color_box, color_box2, size_box, width_box, height_box};
		ArrayList<Tag> criteria = new ArrayList<Tag>();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(5);
		for(int j=0; j<tag_names.length; j++) {
			Text t = new Text(tag_names[j]);
			grid.add(t, 1, j+1);
			grid.add(comboboxes[j], 2, j+1);
		}
		Button b = new Button("search");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				for(int j=0; j<comboboxes.length; j++) {
					System.out.println(comboboxes[j].getValue());
					if(comboboxes[j].getValue()!=null) {
						
					}
				}
			}
		});
		grid.add(b, 1, 7);
		this.setContent(grid);
	}
}
