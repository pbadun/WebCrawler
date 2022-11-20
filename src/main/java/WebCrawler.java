import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class WebCrawler {

    private final int MAX_DEPTH;
    private HashSet<String> urls;
    private final List<String> keys;

    public WebCrawler(int MAX_DEPTH, List<String> keys) {
        this.MAX_DEPTH = MAX_DEPTH;
        this.urls = new HashSet<>();
        this.keys = keys;
    }

    public void run(String url, int depth) {
        if (urls.contains(url) || depth >= MAX_DEPTH) {
            return;
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("\n");
            Document doc = Jsoup.connect(url).get();
            Elements nUrls = doc.select("a[href]");

            String text = doc
                    .text()
                    .toLowerCase(Locale.ROOT);
            //.replaceAll("[^a-zA-Zа-яА-Я]"," ")
            //.replaceAll("\\s+"," ");
            //System.out.println(text);
            for (String w : keys) {
                //Arrays.stream(text.split(" ")).sorted().forEach(System.out::println);
                // Точный поиск отдельных слов со 100% совпадением.
                //long count = Arrays.stream(text.split("\\s+")).filter(ch -> ch.toLowerCase().equals(w.toLowerCase())).count();
                int lastIndex = 0;
                int count = 0;
                do {
                    lastIndex = text.indexOf(w.toLowerCase(Locale.ROOT), lastIndex + 1);
                    if (lastIndex != -1) {
                        count++;
                    }
                } while (lastIndex != -1);
                sb.append(w);
                sb.append(": ");
                sb.append(count);
                sb.append("\n");
            }
            System.out.println(sb.toString());
            depth++;
            for (Element el : nUrls) {
                run(el.attr("abs:href"), depth);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
