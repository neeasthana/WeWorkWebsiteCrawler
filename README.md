# WeWorkWebsiteCrawler
WeWork Backend Coding Challenge that that will fetch each page from a list of urls and determine whether a search term exists on the page

## Usage

Compile Searcher.java file to a jar

Run using the following command:

'''
java -jar NeerajAsthanaWebsiteSearcher.jar **searchTerm** **urls_file**
'''

where **searchTerm** (required) is the term to search for and **urls_file** (optional) is the location of the urls.txt file

## Assumptions

- No repeats within the urls.txt file (will search a single url only once)
- Searching a webpage is not within the scope of the concurrency limit
- Search Term may be a regular expression (as specificed by the java.util.regex library)
- The search can include all text and html on a webpage

## Design Choices

- Semaphore to limit concurrent http requests (20 connections)
