package ksr.proj1.Classifier.Measures;

public class NgramMethod implements WordsSimilarityMeasure {

    @Override
    public double calc(String word1, String word2, int minLength, int maxLength) {
        String tempWord1 = word1.replaceAll("\\s+","");
        String tempWord2 = word2.replaceAll("\\s+","");
        int lenght = Math.min(tempWord1.length(), tempWord2.length());
        int N = Math.max(tempWord1.length(), tempWord2.length());
        int sum = 0;
        for (int i = minLength; i <= maxLength; i++) {
            for (int j = 0; j < lenght - i + 1; j++) {
                String ngram1 = tempWord1.substring(j, j + i);
                if (tempWord2.toLowerCase().contains(ngram1.toLowerCase())) {
                    sum++;
                }
            }

        }
        //TODO: check if fNn1n2 is necessary
        double fNn1n2 = (double) 2 /((N-minLength + 1)*(N-minLength +2) - (N-maxLength + 1)*(N-maxLength));
        return sum * ((double) 2 /((N-minLength + 1)*(N-minLength +2) - (N-maxLength + 1)*(N-maxLength)));
    }
}
