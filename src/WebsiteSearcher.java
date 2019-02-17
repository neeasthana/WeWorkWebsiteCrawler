/**
 * Retreives and parses HTML for a website URL
 * @author neeraj
 *
 */
public class WebsiteSearcher extends Thread{
	
	private String url;
	
	private String searchTerm;
	
	public WebsiteSearcher(String url, String searchTerm) {
		this.url = url;
		this.searchTerm = searchTerm;
	}
	
	private String getHTML() {
		return null;
	}
	
	public boolean checkIfSearchTermExistsOnWebsite() {
		return false;
	}
}
