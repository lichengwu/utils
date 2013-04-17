package oliver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * TODO 方法说明
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-03-03 12:25 PM
 */
public class Iteye2Markdown {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://softbeta.iteye.com";

        String latestPostUrl = baseUrl + "/blog/1185690";

        Elements elements = Jsoup.parse(getHTML(latestPostUrl)).select(".blog_main");

        String title = elements.select(".blog_title").select("h3").select("a").text();

        List<String> categories = new ArrayList<String>();

        Elements categoryElements = elements.select(".blog_categories").select("li");

        for (Element element : categoryElements) {
            categories.add(element.text());
        }

        Elements tagElements = elements.select(".news_tag").select("a");

        List<String> tags = new ArrayList<String>();

        for (Element element : tagElements) {
            tags.add(element.text());
        }

        String nextPostUrl = baseUrl + elements.select(".pre_next").select(".next").attr("href");

        String content = elements.select("#blog_content").html();

        Process process = Runtime.getRuntime().exec("pandoc -f html -t markdown ", new String[]{content});


        System.out.println("tile:" + title);

        System.out.println("categories:" + categories);

        System.out.println("tags:" + tags);

        System.out.println("next:" + nextPostUrl);

        System.out.println("content:" + content);

    }

    /**
     * get html from url
     * 
     * @param urlStr
     * @return
     * @throws IOException
     */
    private static String getHTML(String urlStr) throws IOException {
        StringBuilder html = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // set request params
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0");
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
        conn.setRequestProperty("Accept-Encoding", "GB2312,utf-8;q=0.7,*;q=0.7");
        conn.setRequestProperty("Referrer", "http://movie.douban.com/");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.connect();

        BufferedReader bin = null;

        try {
            InputStream in = conn.getInputStream();

            GZIPInputStream gzin = new GZIPInputStream(in);

            bin = new BufferedReader(new InputStreamReader(gzin, "utf-8"));
            String line;
            while ((line = bin.readLine()) != null) {
                html.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bin.close();
        }

        return html.toString();
    }
}
