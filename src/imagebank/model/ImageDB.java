package imagebank.model;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.*;
import imagebank.model.tag.Tagger;

public class ImageDB {

	private File savedDB;
	private ImageFile images;
	private ArrayList<Image> selectedImages;
	private ArrayList<ImageDBListener> listeners;

	public ImageDB(File imagesDirectory) throws IOException {
		savedDB = new File(imagesDirectory, "imagedb.dat");
		restoreTagger();

		images = new ImageFile(imagesDirectory.getAbsolutePath());
		selectedImages = images.getImagesFile().values().stream()
					.collect(Collectors
						.toCollection(ArrayList::new));

		listeners = new ArrayList<>();
	}

	private final void restoreTagger() throws IOException {
		if (!savedDB.exists() || !savedDB.isFile())
			return;

		try (FileInputStream fis = new FileInputStream(savedDB);
			ObjectInputStream stream = new ObjectInputStream(fis)
		) {
			Tagger tagger = (Tagger) stream.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(savedDB);
		ObjectOutputStream stream = new ObjectOutputStream(fos);
		stream.writeObject(Tagger.getInstance());
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
}
