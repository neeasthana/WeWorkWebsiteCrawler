import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Website {

	private String url;

	private String html;

	public Website(String website) {
		this.url = website;
		this.html = null;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the html of the website or null if there is an error retreiving the HTML
	 * @throws IOException
	 */
	public String getHtml() {
		if (this.html == null)
			try {
				requestHTML();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		return html;
	}

	/**
	 * @param html the html to set
	 * @throws IOException
	 */
	public void setHtml(String html) throws IOException {
		this.html = html;
		requestHTML();
	}

	private String requestHTML() throws IOException {
		URL urlObject = new URL("http://" + url);

		BufferedReader reader = null;

		try {
			String line;
			StringBuffer html = new StringBuffer();

			reader = new BufferedReader(new InputStreamReader(urlObject.openStream()));

			// read the website html line by line
			while ((line = reader.readLine()) != null) {
				html.append(line);
			}

			return html.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public boolean searchForTerm(String searchTerm) throws IOException {
		// Retrieve website url's HTML
		String html = getHtml();
		
		// return false immediately if the website html was not able to be retrieved
		if(html == null)
			return false;

		// Use searchTerm as a regular expression pattern
		Pattern pattern = Pattern.compile(searchTerm);

		// Create matcher object to find matches of searchTerm in the HTML
		Matcher matcher = pattern.matcher(html);

		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
}
