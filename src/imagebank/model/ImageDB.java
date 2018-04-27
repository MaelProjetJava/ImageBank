package imagebank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.io.*;
import imagebank.model.tag.Tagger;
import imagebank.model.tag.TaggerListener;
import imagebank.model.tag.TaggerEvent;
import imagebank.model.tag.Tag;

public class ImageDB implements TaggerListener {

	private File savedDB;
	private ImageFile images;
	private ArrayList<Image> selectedImages;
	private int currentImageIndex;
	private ArrayList<ImageDBListener> listeners;

	public ImageDB(File imagesDirectory) throws IOException {
		savedDB = new File(imagesDirectory, "imagedb.dat");
		restoreDB();

		selectedImages = images.getImagesFile().values().stream()
					.collect(Collectors
						.toCollection(ArrayList::new));

		currentImageIndex = 0;
		listeners = new ArrayList<>();
		Tagger.getInstance().addTaggerListener(this);
	}

	private final void restoreDB() throws IOException {
		if (!savedDB.exists() || !savedDB.isFile()) {
			images = new ImageFile(
				savedDB.getParentFile().getAbsolutePath());
			return;
		}

		try (FileInputStream fis = new FileInputStream(savedDB);
			ObjectInputStream stream = new ObjectInputStream(fis)
		) {
			Tagger tagger = (Tagger) stream.readObject();
			images = (ImageFile) stream.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(savedDB);
		ObjectOutputStream stream = new ObjectOutputStream(fos);
		stream.writeObject(Tagger.getInstance());
		stream.writeObject(images);
		stream.close();
	}

	public void addImageDBListener(ImageDBListener listener)
						throws NullPointerException {
		if (listener == null)
			throw new NullPointerException();

		listeners.add(listener);
		listener.imageDBChanged(new ImageDBEvent(this));
	}

	public void removeImageDBListener(ImageDBListener listener) {
		listeners.remove(listener);
	}

	private void notifyChanges() {
		ImageDBEvent event = new ImageDBEvent(this);

		for (ImageDBListener listener : listeners)
			listener.imageDBChanged(event);
	}

	@Override
	public void tagsChanged(TaggerEvent event) {
		notifyChanges();
	}

	public List<Image> getSelectedImages() {
		return Collections.unmodifiableList(selectedImages);
	}

	public Image getCurrentImage() {
		if (selectedImages.size() == 0)
			return null;
		else
			return selectedImages.get(currentImageIndex);
	}

	public void setCurrentImage(int index) {
		if (index < 0 || index >= selectedImages.size()) {
			throw new IndexOutOfBoundsException(
							String.valueOf(index));
		}

		currentImageIndex = index;
	}

	public void showNextImage() {
		if (selectedImages.size() == 0)
			return;

		currentImageIndex = (currentImageIndex + 1)
						% selectedImages.size();
		notifyChanges();
	}

	public void showPreviousImage() {
		if (selectedImages.size() == 0)
			return;

		currentImageIndex--;
		if (currentImageIndex < 0)
			currentImageIndex = selectedImages.size() - 1;
		notifyChanges();
	}

	private boolean filter(Image image, Tag criteria[]) {
		for (Tag tag : criteria) {
			if (!image.hasTag(tag))
				return false;
		}

		return true;
	}

	public void search(Tag criteria[]) {
		selectedImages = images.getImagesFile().values().stream()
			.filter(i -> filter(i, criteria))
			.collect(Collectors.toCollection(ArrayList::new));
	}
}
