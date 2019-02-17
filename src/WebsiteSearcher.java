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
			// acquire semaphore if available or else what till it is available
			semaphore.tryAcquire();
			
			// check if search term exists within the website's html
			website.searchIfExists(searchTerm);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}
}
