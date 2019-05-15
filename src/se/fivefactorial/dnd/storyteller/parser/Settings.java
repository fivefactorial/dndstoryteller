package se.fivefactorial.dnd.storyteller.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

	private static final String WINDOWS = "/AppData/Roaming/DnDStoryteller";

	private static final String PROPERTIES = "properties";

	private static final String PROPERTIES_LAST_OPENED = "last.opened";

	private File appdata;
	private Properties properties;

	public Settings() throws FileNotFoundException, IOException {
		loadAppdataFolder();
		loadProperties();
	}

	private void loadAppdataFolder() {
		String os = System.getProperty("os.name");
		os = os.toLowerCase();
		if (os.contains("win")) {
			appdata = new File(new File(System.getProperty("user.home")), WINDOWS);
		} else {
			System.err.printf("Unsupported OS %s. Terminating\n", os);
		}

		if (!appdata.exists())
			appdata.mkdirs();
	}

	private void loadProperties() throws FileNotFoundException, IOException {
		File propertiesFile = new File(appdata, PROPERTIES);
		if (!propertiesFile.exists())
			propertiesFile.createNewFile();

		properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
	}

	private void save() {

		try {
			File propertiesFile = new File(appdata, PROPERTIES);
			if (!propertiesFile.exists())
				propertiesFile.createNewFile();
			properties.store(new FileOutputStream(propertiesFile), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getLastOpened() {
		String path = properties.getProperty(PROPERTIES_LAST_OPENED);
		if (path == null) {
			return new File(System.getProperty("user.home"));
		} else {
			return new File(path);
		}
	}

	public void setLastOpened(File file) {
		properties.setProperty(PROPERTIES_LAST_OPENED, file.getAbsolutePath());
		save();
	}
}
