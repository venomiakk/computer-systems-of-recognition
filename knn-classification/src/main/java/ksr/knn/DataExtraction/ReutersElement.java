package ksr.knn.DataExtraction;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;

public class ReutersElement {

    public int id;
    public String cgiSplit;
    public String lewisSplit;
    public String date;
    public List<String> topics = new ArrayList<>();
    public List<String> places = new ArrayList<>();
    public List<String> people = new ArrayList<>();
    public List<String> orgs = new ArrayList<>();
    public List<String> exchanges = new ArrayList<>();
    public List<String> companies = new ArrayList<>();
    public String title;
    public String author;
    public String dateline;
    public String body;
    // classification attributes
    public String label;
    public int index;



    // Constructor without classification attributes
    public ReutersElement(int id, String cgiSplit, String lewisSplit, String date, List<String> topics,
                          List<String> places, List<String> people, List<String> orgs, List<String> exchanges,
                          List<String> companies, String title, String author, String dateline, String body) {
        this.id = id;
        this.cgiSplit = cgiSplit;
        this.lewisSplit = lewisSplit;
        this.date = date;
        this.topics = topics;
        this.places = places;
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;
        this.companies = companies;
        this.title = title;
        this.author = author;
        this.dateline = dateline;
        this.body = body;

        this.body = StringEscapeUtils.unescapeHtml4(this.body);
    }

    // Constructor with classification attributes
    public ReutersElement(int id, String cgiSplit, String lewisSplit, String date, List<String> topics,
                          List<String> places, List<String> people, List<String> orgs, List<String> exchanges,
                          List<String> companies, String title, String author, String dateline, String body,
                          String label, int index) {
        this.id = id;
        this.cgiSplit = cgiSplit;
        this.lewisSplit = lewisSplit;
        this.date = date;
        this.topics = topics;
        this.places = places;
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;
        this.companies = companies;
        this.title = title;
        this.author = author;
        this.dateline = dateline;
        this.body = body;
        this.label = label;
        this.index = index;

        this.body = StringEscapeUtils.unescapeHtml4(this.body);
    }

    @Override
    public String toString() {
        return "ReutersElement{\n" +
                "id=" + id + ",\n" +
                "cgiSplit='" + cgiSplit + "',\n" +
                "lewisSplit='" + lewisSplit + "',\n" +
                "date='" + date + "',\n" +
                "topics=" + topics + ",\n" +
                "places=" + places + ",\n" +
                "people=" + people + ",\n" +
                "orgs=" + orgs + ",\n" +
                "exchanges=" + exchanges + ",\n" +
                "companies=" + companies + ",\n" +
                "title='" + title + "',\n" +
                "author='" + author + "',\n" +
                "dateline='" + dateline + "',\n" +
                "body='" + body + "'\n" +
                "label='" + label + "',\n" +
                "reutersElementIndex=" + index + "\n" +
                '}';
    }

}
