import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main executable that reads command line arguments and organizes the search
 * @author neeraj
 *
 */
public class Searcher {

	public static void main(String [] args) throws FileNotFoundException, IOException, InterruptedException {
		
		if(args.length <= 0)
			throw new IllegalArgumentException("Searcher needs at least one argument specifying the term to search for. Optionally a second argument can be provided to specify a custom urls.txt file detailing the urls to search on");
		
		// Retrieve search term
		String searchTerm = args[0];
		
		Crawler crawler ;
		
		// Retrieve urls.txt location from command line if present and initial Crawler
		if(args.length > 1) {
			String fileName = args[1];
			crawler = new Crawler(fileName);
		}
		else {
			crawler = new Crawler();
		}
		
		// Perform the website search crawl and write to results.txt file
		crawler.search(searchTerm);
	}
	
}
