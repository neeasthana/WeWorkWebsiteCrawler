import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author neeraj
 *
 */
public class WebsiteFileReader {

	private String fileLocation;
	
	public WebsiteFileReader(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
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
	
	public static void main(String [] args) {
		WebsiteFileReader reader = new WebsiteFileReader("/home/neeraj/Documents/Projects/WeWorkWebsiteCrawler/src/urls.txt");
		try {
			System.out.println(reader.getWebsiteUrls());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
