import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Retreives and parses HTML for a website URL
 * 
 * @author neeraj
 *
 */
public class WebsiteSearcher extends Thread {

	private Semaphore semaphore;

	private Website website;

	private String searchTerm;

	private boolean result;

	/**
	 * 
	 * @param semaphore
	 * @param website
	 * @param searchTerm
	 */
	public WebsiteSearcher(Semaphore semaphore, Website website, String searchTerm) {
		this.semaphore = semaphore;
		this.website = website;
		this.searchTerm = searchTerm;
	}

	@Override
	public void run() {
		try {
			System.out.println(website.getUrl());
			
			// acquire semaphore if available or else what till it is available
			semaphore.tryAcquire();
			
			System.out.println("Searching: " + website.getUrl());
			
			website.getHtml();
			
			semaphore.release();
			
			// check if search term exists within the website's html
			boolean exists = website.searchForTerm(searchTerm);

			result = exists;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
