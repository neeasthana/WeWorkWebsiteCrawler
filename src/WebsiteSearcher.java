import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Retreives and parses HTML for a website URL
 * 
 * @author neeraj
 *
 */
public class WebsiteSearcher extends Thread {

	private Semaphore semaphore;

	private String website;

	private String searchTerm;

	/**
	 * 
	 * @param semaphore
	 * @param url
	 * @param searchTerm
	 */
	public WebsiteSearcher(Semaphore semaphore, String website, String searchTerm) {
		this.semaphore = semaphore;
		this.website = website;
		this.searchTerm = searchTerm;
	}

	public String getHTML() throws IOException {
		URL url = new URL(website);

		BufferedReader reader = null;

		try {
			// acquire semaphore if available or else what till it is available
			semaphore.tryAcquire();

			String line;
			StringBuffer html = new StringBuffer();

			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// read the website html line by line
			while ((line = reader.readLine()) != null) {
				html.append(line);
			}

			return html.toString();
		} finally {
			reader.close();
			semaphore.release();
		}
	}

	/**
	 * Check if search term exists on within the HTML of a website using a regular
	 * expression search
	 * 
	 * @return true if the search term is found in the website's HTML, false
	 *         otherwise
	 * @throws IOException if cannot retrieve the url
	 */
	public boolean checkIfSearchTermExistsOnWebsite() throws IOException {
		// Retrieve website url's HTML 
		String html = getHTML();

		// Use searchTerm as a regular expression pattern
		Pattern pattern = Pattern.compile(searchTerm);

		// Create matcher object to find matches of searchTerm in the HTML
		Matcher matcher = pattern.matcher(html);
		
		if (matcher.find()) {
			return true;
		} 
		else {
			return false;
		}
	}

	public static void main(String[] args) {
		Semaphore s = new Semaphore(1);

		WebsiteSearcher searcher = new WebsiteSearcher(s, "https://www.yahoo.com", "yahoo");

		try {
			searcher.checkIfSearchTermExistsOnWebsite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
