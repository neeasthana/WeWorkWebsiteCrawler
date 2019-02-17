import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Semaphore;

/**
 * Retreives and parses HTML for a website URL
 * @author neeraj
 *
 */
public class WebsiteSearcher extends Thread{
	
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
			while((line = reader.readLine()) != null) {
				html.append(line);
			}
			
			return html.toString();
		}
		finally {
			reader.close();
			semaphore.release();
		}
	}
	
	public boolean checkIfSearchTermExistsOnWebsite() throws IOException {
		System.out.println(getHTML());
		return false;
	}
	
	public static void main(String[] args) {
		Semaphore s = new Semaphore(1);
		
		WebsiteSearcher searcher = new WebsiteSearcher(s, "https://www.yahoo.com", "something");
		
		try {
			searcher.checkIfSearchTermExistsOnWebsite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
