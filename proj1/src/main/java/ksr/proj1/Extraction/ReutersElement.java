package ksr.proj1.Extraction;

import ksr.proj1.Models.SetSplit;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;

public class ReutersElement {
    /**
     * Class representing a single Reuters article.
     * Contains all the attributes of the article.
     * Most important attributes:
     * - id: unique identifier of the article taken from the XML file
     * - cgiSplit: split of the article according to the CGI scheme
     * - lewisSplit: split of the article according to the Lewis scheme
     * - body: the content of the article, used for classification
     * - label: label of the article [usa, france, japan, west-germany, uk, canada]
     * - index: index of the article in the classification list
     * Feature attributes:
     * - c1: int, number of characters in the text
     * - c2: float, ratio of punctuation marks to words
     * - c3: string, most common surname in the text
     * - c4: string, most common country in the text
     * - c5: string, most common proper name in the text
     * - c6: int, number of keyword occurrences in the text
     * - c7: string, most common keyword in the text
     * - c8: string, least common keyword in the text
     * - c9: float, ratio of keywords to words in the text
     * - c10: float, ratio of the number of proper names to the number of words in the text
     * - c11: int, number of surnames in the text
     * - c12: int, number of country names in the text
     */

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
    public SetSplit setSplit;
    //features
    public int c1;
    public float c2;
    public String c3;
    public String c4;
    public String c5;
    public int c6;
    public String c7;
    public String c8;
    public float c9;
    public float c10;
    public int c11;
    public int c12;

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
                "index=" + index + "\n" +
                '}';
    }

    public String featuresString(){
        return "Features {\n"+
                "c1: " + c1 + "\n"+
                "c2: " + c2 + "\n"+
                "c3: " + c3 + "\n"+
                "c4: " + c4 + "\n"+
                "c5: " + c5 + "\n"+
                "c6: " + c6 + "\n"+
                "c7: " + c7 + "\n"+
                "c8: " + c8 + "\n"+
                "c9: " + c9 + "\n"+
                "c10: " + c10 + "\n"+
                "c11: " + c11 + "\n"+
                "c12: " + c12 + "\n"+
                "}";
    }
}
