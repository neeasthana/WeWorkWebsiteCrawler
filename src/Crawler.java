import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 
 */

/**
 * @author neeraj
 *
 */
public class Crawler {
	
	public static final int NUM_CONCURRENT_REQUESTS = 5;
	
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
	
	public List<String> crawl(List<String> websites) throws InterruptedException{
		List<String> results = new ArrayList<String>();
		
		ArrayList<WebsiteSearcher> threads = new ArrayList<WebsiteSearcher>();
		
		for(String s : websites) {
			WebsiteSearcher thread = new WebsiteSearcher(semaphore, new Website(s), "head");
			thread.run();
			threads.add(thread);
		}
		
		for(WebsiteSearcher thread : threads) {
			thread.join();
			if(thread.isResult())
				results.add(thread.getWebsite().getWebsite());
		}
		
		return results;
	}
	
	public static void main(String[] args){
		ArrayList<String> results = new ArrayList<String>();
		
		String[] rs = {"facebook.com/", "twitter.com/", "google.com/", "youtube.com/", "wordpress.org/", "adobe.com/", "blogspot.com/", "wikipedia.org/", "linkedin.com/", "wordpress.com/", "yahoo.com/", "amazon.com/"};
		
		
		for(String s : rs)
			results.add(s);
		
		Crawler crawl = new Crawler();
		try {
			System.out.println(crawl.crawl(results));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
