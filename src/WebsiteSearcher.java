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

	// Result of the search (whether search term is in the website's HTML)
	private boolean containsSearchTerm;

	/**
	 * Constructor
	 * 
	 * @param semaphore to handle concurrency
	 * @param website url to search for term on
	 * @param searchTerm to look for in website html
	 */
	public WebsiteSearcher(Semaphore semaphore, Website website, String searchTerm) {
		this.semaphore = semaphore;
		this.website = website;
		this.searchTerm = searchTerm;
	}

	/**
	 * Single thread of execution. Retrieve's websites HTML (concurrently) and then searches for searchTerm within the website's HTML
	 */
	@Override
	public void run() {
		try {			
			// acquire semaphore if available or else what till it is available
			semaphore.tryAcquire();
			
			website.getHtml();
			
			semaphore.release();
			
			// check if search term exists within the website's html
			boolean exists = website.searchForTerm(searchTerm);

			// Set result of search
			containsSearchTerm = exists;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	/**
	 * 
	 * @return website url to search for term on
	 */
	public Website getWebsite() {
		return website;
	}

	/**
	 * 
	 * * @param website url to search for term on
	 */
	public void setWebsite(Website website) {
		this.website = website;
	}

	/**
	 * 
	 * @return searchTerm to look for in website html
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * 
	 * @param searchTerm to look for in website html
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	/**
	 * 
	 * @return whether the searchTerm existed within the website's HTML after the thread has completed running
	 */
	public boolean containsSearchTerm() {
		return this.containsSearchTerm;
	}

}
