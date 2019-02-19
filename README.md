# WeWorkWebsiteCrawler
WeWork Backend Coding Challenge that that will fetch each page from a list of urls and determine whether a search term exists on the page

## Usage

Compile Searcher.java file to a jar

Run using the following command:

```
java -jar NeerajAsthanaWebsiteSearcher.jar *searchTerm* *urls_file*
```

where *searchTerm* (required) is the term to search for and *urls_file* (optional) is the location of the urls.txt file

## Challenge

https://s3.amazonaws.com/fieldlens-public/Website+Searcher.html

Given a list of urls in urls.txt: https://s3.amazonaws.com/fieldlens-public/urls.txt, write a program that will fetch each page and determine whether a search term exists on the page (this search can be a really rudimentary regex - this part isn't too important).

You can make up the search terms. Ignore the addition information in the urls.txt file.

### Constraints

- Search is case insensitive
- Should be concurrent.
- But! It shouldn't have more than 20 HTTP requests at any given time.
- The results should be writted out to a file results.txt
- Avoid using thread pooling libraries like Executor, ThreadPoolExecutor, Celluloid, or Parallel streams.

The solution must be written in Kotlin or Java.

Sample urls.txt: https://s3.amazonaws.com/fieldlens-public/urls.txt

The solution must be able to be run from the command line (dont assume JDK is available):
java -jar ./jarFileName.jar

## Assumptions

- No repeats within the urls.txt file (will search a single url only once)
- Searching a webpage is not within the scope of the concurrency limit
- Search Term may be a regular expression (as specificed by the java.util.regex library)
- The search can include all text and html on a webpage

## Design Choices

- Semaphore to limit concurrent http requests (20 connections)
