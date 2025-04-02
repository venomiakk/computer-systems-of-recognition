package ksr.proj1.Utils;

import ksr.proj1.DataExtraction.ReutersElement;

import java.util.List;

public class SetSplit {
    public List<ReutersElement> trainSet;
    public List<ReutersElement> testSet;

    public SetSplit(List<ReutersElement> articles, int numberOfArticles, int percentageOfTrainSet) {
        //TODO change this to have more even distribution of classes
        this.trainSet = articles.subList(0, numberOfArticles * percentageOfTrainSet / 100);
        this.testSet = articles.subList(numberOfArticles * percentageOfTrainSet / 100, numberOfArticles);
    }
}
