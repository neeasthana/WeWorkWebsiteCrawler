import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Website {

	private String url;

	public Website(String website) {
		this.url = website;
	}

	public String getWebsite() {
		return url;
	}

	public void setWebsite(String website) {
		this.url = website;
	}

	private String getHTML() throws IOException {
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
			if(reader != null)
				reader.close();
		}
	}

	public boolean searchForTerm(String searchTerm) throws IOException {
		// Retrieve website url's HTML
		String html = getHTML();

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
