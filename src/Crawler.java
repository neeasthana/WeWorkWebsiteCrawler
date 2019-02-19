import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static final int NUM_CONCURRENT_REQUESTS = 20;
	
	private Semaphore semaphore;
	
	private String urlsFile;
	
	private List<String> websites;
	
	/**
	 * Constructor
	 * 
	 * @param semaphore is the semaphore to control the number of concurrent http requests
	 * @param urlsFile is the urls.txt that contains all of the urls to search for the search term
	 * @throws IOException if urlsFile is unable to be read
	 * @throws FileNotFoundException if urlsFile does not exist
	 */
	public Crawler(Semaphore semaphore, String urlsFile) throws FileNotFoundException, IOException {
		this.semaphore = semaphore;
		this.urlsFile = urlsFile;
		loadWebsites();
	}

	/**
	 * Default constructor using the NUM_CONCURRENT_REQUESTS to create the semaphore for number of concurrent http requests
	 * 
	 * @param urlsFile is the urls.txt that contains all of the urls to search for the search term
	 * @throws IOException if urlsFile is unable to be read
	 * @throws FileNotFoundException if urlsFile does not exist
	 */
	public Crawler(String urlsFile) throws FileNotFoundException, IOException {
		this.semaphore = new Semaphore(NUM_CONCURRENT_REQUESTS);
		this.urlsFile = urlsFile;
		loadWebsites();
	}
	
	/**
	 * Default Constructor using current directory's urls.txt file as the urlsFile 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Crawler() throws FileNotFoundException, IOException {
		// Get current working directory
		this(System.getProperty("user.dir") + "/urls.txt");
	}

	private void loadWebsites() throws FileNotFoundException, IOException {
		WebsiteFileReader reader = new WebsiteFileReader(urlsFile);
		this.websites = reader.getWebsiteUrls();
	}
	
	/**
	 * 
	 * @return file location of urls.txt file that is supplied
	 */
	public String getUrlsFile() {
		return urlsFile;
	}
	
	/**
	 * Sets the location of the urls.txt file and loads the website urls specified in that file
	 * 
	 * @param urlsFile is the urls.txt that contains all of the urls to search for the search term
	 * @throws IOException if urlsFile is unable to be read
	 * @throws FileNotFoundException if urlsFile does not exist
	 */
	public void setUrlsFile(String urlsFile) throws FileNotFoundException, IOException {
		this.urlsFile = urlsFile;
		loadWebsites();
	}

	public List<String> search(String term) throws InterruptedException, IOException{
		ArrayList<WebsiteSearcher> threads = new ArrayList<WebsiteSearcher>();
		
		for(String s : websites) {
			WebsiteSearcher thread = new WebsiteSearcher(semaphore, new Website(s), term);
			threads.add(thread);
			thread.run();
		}
		
		List<String> results = compileResults(threads);
		
		writeResultsToFile(results);
		
		return results;
	}
	
	/**
	 * Waits as threads complete execution and gathers results of urls that contain the search term
	 * 
	 * @param websiteSearchers threads that execute the retreival of html and the search for the search term
	 * @return website urls that contain the search term
	 * @throws InterruptedException if thread joining is interrupted in any way
	 */
	private List<String> compileResults(List<WebsiteSearcher> websiteSearchers) throws InterruptedException{
		List<String> results = new ArrayList<String>();
		
		for(WebsiteSearcher thread : websiteSearchers) {
			thread.join();
			if(thread.containsSearchTerm())
				results.add(thread.getWebsite().getUrl());
		}
		
		return results;
	}
	
	/**
	 * writes urls to results.txt in the current directory
	 * the method will override any previous results.txt file
	 * 
	 * @param urls to write to results.txt
	 * @throws IOException if writer is unable to be opened
	 */
	private void writeResultsToFile(List<String> urls) throws IOException {
		// Get current working directory
		String currentDirectory = System.getProperty("user.dir");
		
		// Open Writer to the results file
		// Set to override the results.txt file
	    BufferedWriter writer = new BufferedWriter(new FileWriter(currentDirectory + "/results.txt", false));
	    
	    // Write to the results file line by line
	    for(String s : urls)
	    	writer.append(s + "\n");
	    
	    // Close the writer
	    writer.close();
	}
}
