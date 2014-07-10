package wyq.appengine.component.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
//import wyq.appengine.Component;
//import wyq.appengine.ExceptionHandler;

public class TextFile extends File /* implements Component */{

	/**
	 * 
	 */
	 private static final long serialVersionUID = -2188324052240230771L;

	public static final String LINE_SEP = System.getProperty("line.separator");

	// private ExceptionHandler exceptionHandler;

	public TextFile(File parent, String child) {
		super(parent, child);
	}

	public TextFile(String parent, String child) {
		super(parent, child);
	}

	public TextFile(URI uri) {
		super(uri);
	}

	public TextFile() {
		super(getRealPath(TextFile.class, null));
	}

	public TextFile(Class<?> c) {
		super(getRealPath(c, null));
	}

	public TextFile(String name) {
		super(getRealPath(null, name));
	}

	public TextFile(Class<?> c, String name) {
		super(getRealPath(c, name));
	}

	protected static String getRealPath(Class<?> c, String name) {
		if (c != null) {
			if (name == null) {
				name = c.getSimpleName();
			}

			URL resource = c.getResource(name);
			if (resource != null) {
				return resource.getFile();
			}

			resource = c.getResource("/" + name);
			if (resource != null) {
				return resource.getFile();
			}
		}
		return name;
	}

	public String readAll() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(LINE_SEP);
			}
		} catch (FileNotFoundException e) {
			// exceptionHandler.handle(e);
			e.printStackTrace();
		} catch (IOException e) {
			// exceptionHandler.handle(e);
			e.printStackTrace();
		}
		return sb.toString();
	}

	// public ExceptionHandler getExceptionHandler() {
	// return exceptionHandler;
	// }
	//
	// public void setExceptionHandler(ExceptionHandler exceptionHandler) {
	// this.exceptionHandler = exceptionHandler;
	// }
}
