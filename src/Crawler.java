import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * 
 */

/**
 * @author neeraj
 *
 */
public class Crawler {
	
	public static final int NUM_CONCURRENT_REQUESTS = 20;
	
	private Semaphore semaphore;
	
	/**
	 * 
	 * @param semaphore
	 */
	public Crawler(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	/**
	 * 
	 */
	public Crawler() {
		this.semaphore = new Semaphore(NUM_CONCURRENT_REQUESTS);
	}
	
	/**
	 * 
	 */
	public ArrayList<String> readWebsites(){
		return null;
	}
	
	/**
	 * 
	 * @param url - url to be parsed
	 */
	private String getWebsiteHTML(String url) {
		return null;
	}
}
