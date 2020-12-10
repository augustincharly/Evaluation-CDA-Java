package file_handling.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManager {

	private String currentPath;
	private StringBuilder sb;

	public FileManager() {
		currentPath = "C:\\Users\\Khele\\Desktop\\test";
		this.sb = new StringBuilder();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public List<File> listFiles() {
		File currentFolder = new File(currentPath);

		File[] files = currentFolder.listFiles();

		return files != null ? Arrays.asList(files) : new LinkedList<>();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public void deleteFileFromList(int index) {
		File currentFolder = new File(currentPath);

		currentFolder.listFiles()[index].delete();
	}

	public void readFileFromList(int index) {
		File currentFolder = new File(currentPath);

		File file = currentFolder.listFiles()[index];

		try (FileInputStream fis = new FileInputStream(file)) {
			int data;
			String content = "";

			while ((data = fis.read()) >= 0) {

				char character = (char) data;
				content += character;
			}

			ConsoleManager.getInstance().printToConsole(content, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void writeFileFromList(int index, String content) {
		File currentFolder = new File(currentPath);

		File file = currentFolder.listFiles()[index];

		try (FileOutputStream fop = new FileOutputStream(file)) {
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			ConsoleManager.getInstance().printToConsole("Contenu écrit!", true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void copyFileFromList(int indexFrom) {

		File currentFolder = new File(currentPath);

		File fileFrom = currentFolder.listFiles()[indexFrom];
		File fileTo = new File(fileFrom.getParent() + "\\copie-" + fileFrom.getName());
		if (!fileTo.exists()) {
			try {
				fileTo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (FileInputStream fis = new FileInputStream(fileFrom);
				BufferedInputStream bis = new BufferedInputStream(fis);
				FileOutputStream fop = new FileOutputStream(fileTo);
				BufferedOutputStream bop = new BufferedOutputStream(fop);) {
			int data;

			while ((data = bis.read()) >= 0) {
				bop.write(data);
			}
			bop.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void copyBenchmark(int indexFrom) {

		File currentFolder = new File(currentPath);

		File fileFrom = currentFolder.listFiles()[indexFrom];

		String size = String.format("%.2f", (double) fileFrom.length() / (1024 * 1024));
		ConsoleManager.getInstance().printToConsole("Size of file: " + size + " Mo", true);

		File fileTo = new File(fileFrom.getParent() + "\\copie-buffered-" + fileFrom.getName());
		if (!fileTo.exists()) {
			try {
				fileTo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Date start = new Date();
		try (FileInputStream fis = new FileInputStream(fileFrom);
				BufferedInputStream bis = new BufferedInputStream(fis);
				FileOutputStream fop = new FileOutputStream(fileTo);
				BufferedOutputStream bop = new BufferedOutputStream(fop);) {
			int data;

			while ((data = bis.read()) >= 0) {
				bop.write(data);
			}
			bop.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		ConsoleManager.getInstance().printToConsole(
				"buffered: " + (float) (new Date().getTime() - start.getTime()) / 1000 + " secondes", true);
		fileTo.delete();

		fileTo = new File(fileFrom.getParent() + "\\copie-non-buffered-" + fileFrom.getName());

		if (!fileTo.exists()) {
			try {
				fileTo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		start = new Date();
		try (FileInputStream fis = new FileInputStream(fileFrom); FileOutputStream fop = new FileOutputStream(fileTo)) {
			int data;

			while ((data = fis.read()) >= 0) {
				fop.write(data);
			}
			fop.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ConsoleManager.getInstance().printToConsole(
				"non buffered: " + (float) (new Date().getTime() - start.getTime()) / 1000 + " secondes", true);
		fileTo.delete();

	}

	/**
	 * Create a .txt file
	 * 
	 * @param name the name of the file to create (with no extension)
	 * @return
	 */
	public File createTxtFile(String name) {
		File file = new File(currentPath + name + ".txt");

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Move the path to a folder
	 * 
	 * @param index of the folder to move into
	 */
	public void enterFolder(int index) {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			if (!file.isFile()) {
				folders.add(file);
			}
		}

		currentPath += folders.get(index).getName() + "\\";
	}

	/**
	 * Go back one folder from the current path
	 */
	public void backOneFolder() {

		List<String> paths = Arrays.asList(currentPath.split(Pattern.quote("\\")));

		paths.remove(paths.size() - 1);

		// paths = paths.subList(0, paths.size() - 1);

		currentPath = String.join("\\", paths);

		if (currentPath.isEmpty()) {
			currentPath = "\\";
		}
	}

	public void createFolder(String name) {
		File file = new File(currentPath + name);

		file.mkdir();
	}

	/**
	 * Get the current path
	 *
	 * @return
	 */
	public String getCurrentPath() {
		return currentPath;
	}

}
