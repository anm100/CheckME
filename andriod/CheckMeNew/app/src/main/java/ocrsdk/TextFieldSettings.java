package ocrsdk;

/*
 * Settings for processing text field via processTextField call
 */
public class 	TextFieldSettings {
	public String asUrlParams() {
		// For all possible parameters, see documentation at
		// http://ocrsdk.com/documentation/apireference/processTextField/
		return String.format("language=%s&textType=%s&left=%s&top=%s&right=%s&bottom=%s", language, textType, 0, 1500, 20000, 3000);
	}

	/*
	 * Set recognition language. You can set any language listed at
	 * http://ocrsdk.com/documentation/specifications/recognition-languages/ or
	 * set comma-separated combination of them.
	 * 
	 * Examples: English English,ChinesePRC English,French,German
	 */
	public void setLanguage(String newLanguage) {
		language = newLanguage;
	}

	public String getLanguage() {
		return language;
	}

	public String getTextType() {
		return textType;
	}

	public void setTextType(String newTextType) {
		textType = newTextType;
	}

	private String language = "CMC7";
	private String textType = "cmc7";
}
