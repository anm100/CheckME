package ocrsdk;

public class ProcessingSettings {

	public String asUrlParams() {
		String params = String.format("language=%s&exportFormat=%s", language, outputFormat);

		if (options != null && !options.isEmpty()) {
			params += "&" + options;
		}

		return params;
	}

	/**
	 * Set extra options directly passed to RESTful call.
	 */
	public void setOptions(String newOptions) {
		options = newOptions;
	}

	public String getOptions() {
		return options;
	}


	/**
	 * Extra options passed directly to RESTful call.
	 */


	public enum OutputFormat {
		txt, rtf, docx, xlsx, pptx, pdfSearchable, pdfTextAndImages, xml
	}

	public void setOutputFormat(OutputFormat format) {
		outputFormat = format;
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
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

	private String language = "CMC7";
	private String options = null;
	private OutputFormat outputFormat = OutputFormat.txt;
}
