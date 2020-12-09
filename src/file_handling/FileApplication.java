/**
 *
 */
package file_handling;

import file_handling.manager.ConsoleManager;
import file_handling.service.FileService;

/**
 * @author EmericStophe
 *
 */
public class FileApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConsoleManager.getInstance().printToConsole(FileApplication.class.getName() + " - Start", true);
		ConsoleManager.getInstance().consoleLineBreak();

		// instantiate application
		FileService applicationService = new FileService();

		// launch application
		applicationService.run();

		ConsoleManager.getInstance().consoleLineBreak();
		ConsoleManager.getInstance().printToConsole(FileApplication.class.getName() + " - End", true);

		ConsoleManager.getInstance().closeScanner();
	}

}
