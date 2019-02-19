import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles operations of reading the urls from the urls.txt file
 * 
 * @author neeraj
 *
 */
public class WebsiteFileReader {

	private String fileLocation;
	
	/**
	 * Constructor
	 * 
	 * @param fileLocation of urls.txt file
	 */
	public WebsiteFileReader(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	/**
	 * Reads urls from the urls.txt file
	 * 
	 * @return list of urls found in the urls.txt file
	 * @throws FileNotFoundException if file is not able to be retrieved
	 * @throws IOException if file is unable to be parsed
	 */
	public List<String> getWebsiteUrls() throws FileNotFoundException, IOException{
		FileReader fileReader = new FileReader(fileLocation);
		
		try (BufferedReader br = new BufferedReader( fileReader )) {
			List<String> results = new ArrayList<String>();
			
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] lineElements = line.split(",");
		    	
		    	String url = lineElements[1];
		    	
		    	if(!url.equals("\"URL\""))
		    		results.add(url);
		    }
		    
		    return results;
		}finally {
			fileReader.close();
		}
	}
}
