import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<String> ls = new ArrayList<>();
        ls.add("Musk");
        ls.add("Tesla");
        ls.add("Gigafactory");
        ls.add("Elon Musk"); // ничего не найдет - т.к. это составное слова
        System.out.println();
        new WebCrawler(2, ls).run("https://en.wikipedia.org/wiki/Elon_Musk", 0);
    }

}
