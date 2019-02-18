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
	
	public static final int NUM_CONCURRENT_REQUESTS = 1;
	
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
			threads.add(thread);
			thread.run();
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
		
		String[] rs = {"facebook.com/", "twitter.com/", "google.com/", "youtube.com/", "wordpress.org/", "adobe.com/", "blogspot.com/", "wikipedia.org/", "linkedin.com/", "wordpress.com/", "yahoo.com/", "amazon.com/", "blogspot.com/", "wikipedia.org/", "linkedin.com/", "wordpress.com/", "yahoo.com/", "amazon.com/", "flickr.com/", "pinterest.com/", "tumblr.com/", "w3.org/", "apple.com/", "myspace.com/", "vimeo.com/", "microsoft.com/", "youtu.be/", "qq.com/", "digg.com/", "baidu.com/", "stumbleupon.com/", "addthis.com/", "statcounter.com/", "feedburner.com/", "miibeian.gov.cn/", "delicious.com/", "nytimes.com/", "reddit.com/", "weebly.com/", "bbc.co.uk/", "blogger.com/", "msn.com/", "macromedia.com/", "goo.gl/", "instagram.com/", "gov.uk/", "icio.us/", "yandex.ru/", "cnn.com/", "webs.com/", "google.de/", "t.co/", "livejournal.com/", "imdb.com/", "mail.ru/", "jimdo.com/", "sourceforge.net/", "go.com/", "tinyurl.com/", "vk.com/", "google.co.jp/", "fc2.com/", "free.fr/", "joomla.org/", "creativecommons.org/", "typepad.com/", "networkadvertising.org/", "technorati.com/", "sina.com.cn/", "hugedomains.com/", "about.com/", "theguardian.com/", "yahoo.co.jp/", "nih.gov/", "huffingtonpost.com/", "google.co.uk/", "mozilla.org/", "51.la/", "aol.com/", "ebay.com/"};
		
		
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
