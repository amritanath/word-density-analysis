package WordDensityAnalysis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageParser {
	
	private URL url;
    
    public PageParser(String url){
        validateURL(url);
    }
    
    private void validateURL(String url){
        try {
            this.url=new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("url provided is incorrect and it can not be parsed");
            e.printStackTrace();
        }
    }
    
    public Document getDoc(){
        Document doc=null;
        try {
            doc=Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            System.out.println("Jsoup can not get document given the url: "+url.toString());
            e.printStackTrace();
        }
        return doc;
    }
}
