package ksr;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {



    public ArrayList<String> getExchanges() {
        return exchanges;
    }

    public ArrayList<String> getOrgs() {
        return orgs;
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public ArrayList<String> getPlaces() {
        return places;
    }
    ArrayList<String> exchanges = new ArrayList<String>();

    ArrayList<String> orgs = new ArrayList<String>();
    ArrayList<String> people = new ArrayList<String>();
    ArrayList<String> places = new ArrayList<String>();

    public Data(){

        try {
            String exchangeFilePath = "proj1/src/main/resources/info/all-orgs-strings.lc.txt";
            File myObj = new File(exchangeFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                exchanges.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            String orgsFilePath = "proj1/src/main/resources/info/all-orgs-strings.lc.txt";
            File myObj = new File(orgsFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                orgs.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            String peopleFilePath = "proj1/src/main/resources/info/all-people-strings.lc.txt";
            File myObj = new File(peopleFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                people.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            String placesFilePath = "proj1/src/main/resources/info/all-places-strings.lc.txt";
            File myObj = new File(placesFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                places.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
