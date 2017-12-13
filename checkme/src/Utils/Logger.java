package Utils;

public class Logger {

	public static boolean isDebug = true;
	private final static Logger logger = new Logger(isDebug);

	public static Logger getInstance(){
		return logger;
	}
	public Logger(boolean debug) {
		isDebug = debug;
	}

	public void log(String message) {
		System.out.println(message);
	}

	public void error(String message) {
		log("-ERROR: " + message);
	}

	public void info(String message) {
		log("" + message);
	}

	public void warning(String message) {
		log("Warn: " + message);
	}

	public void exception(Exception e) {
		error(e.getMessage());
		if (isDebug)
			e.printStackTrace();
	}

	public void debug(String message) {
		if (!isDebug)
			return;

		log("-DEBUG: " + message);
	}
}
