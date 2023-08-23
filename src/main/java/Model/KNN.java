package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 KNN Algorithm
 */

public class KNN {
    private List<ArticleFeatures> learningCollection;
    Methods methods;

    public List<ArticleFeatures> getLearningCollection() {
        return learningCollection;
    }

    public void setLearningCollection(List<ArticleFeatures> learningCollection) {
        this.learningCollection = learningCollection;
    }


    public Places classification(ArticleFeatures article, Metrics metrics, int K, double[] weights) {
        ArrayList<ArticleCompared> articles = new ArrayList<>(learningCollection.size());
        Places result = Places.USA;

        for (ArticleFeatures learningArticle : learningCollection) {
            double[] distance = new double[ArticleFeatures.NUMBER_OF_TRAITS];

            if(article.getSentencesAmount() >= learningArticle.getSentencesAmount()){
                distance[0] = 1 - (double) learningArticle.getSentencesAmount() / article.getSentencesAmount();
            }else{
                distance[0] = 1 - (double) article.getSentencesAmount() / learningArticle.getSentencesAmount();
            }

            if(article.getDigitsAmount() >= learningArticle.getDigitsAmount()){
                distance[1] = 1 - (double) learningArticle.getDigitsAmount() / article.getDigitsAmount();
            }else{
                distance[1] = 1 - (double) article.getDigitsAmount() / learningArticle.getDigitsAmount();
            }

            if(article.getShortWordsAmount() >= learningArticle.getShortWordsAmount()){
                distance[2] = 1 - (double) learningArticle.getShortWordsAmount() / article.getShortWordsAmount();
            }else{
                distance[2] = 1 - (double) article.getShortWordsAmount() / learningArticle.getShortWordsAmount();
            }

            if(article.getLongWordsAmount() >= learningArticle.getLongWordsAmount()){
                distance[3] = 1 - (double) learningArticle.getLongWordsAmount() / article.getLongWordsAmount();
            }else{
                distance[3] = 1 - (double) article.getLongWordsAmount() / learningArticle.getLongWordsAmount();
            }

            if(article.getNormalWordsAmount() >= learningArticle.getNormalWordsAmount()){
                distance[4] = 1 - (double) learningArticle.getNormalWordsAmount() / article.getNormalWordsAmount();
            }else{
                distance[4] = 1 - (double) article.getNormalWordsAmount() / learningArticle.getNormalWordsAmount();
            }

            if(article.getTextLength() >= learningArticle.getTextLength()){
                distance[5] = 1 - (double) learningArticle.getTextLength() / article.getTextLength();
            }else{
                distance[5] = 1 - (double) article.getTextLength() / learningArticle.getTextLength();
            }

            if(article.getCurrencyFormats().equals(learningArticle.getCurrencyFormats())){
                distance[6] = 0;
            }else{
                distance[6] = 1;
            }

            if(article.getDateFormats().equals(learningArticle.getDateFormats())){
                distance[7] = 0;
            }else{
                distance[7] = 1;
            }

            if(article.getLengthUnitFormats().equals(learningArticle.getLengthUnitFormats())){
                distance[8] = 0;
            }else{
                distance[8] = 1;
            }

            if(article.getTemperatureUnitFormats().equals(learningArticle.getTemperatureUnitFormats())){
                distance[9] = 0;
            }else{
                distance[9] = 1;
            }

            for(int i = 0; i < ArticleFeatures.NUMBER_OF_TRAITS; i++){
                distance[i] *= weights[i];
            }

            articles.add(new ArticleCompared(learningArticle, metrics.process(distance)));
        }

        articles.sort(Comparator.comparing(articleCompared -> articleCompared.distance));

        if (K > articles.size()) {
            K = articles.size();
        }

        int max = 0;
        int[] counts = new int[Places.values().length];
        for(int i = 0; i < K; i++)
        {
            counts[articles.get(i).article.getPlaces().id]++;

            if(counts[articles.get(i).article.getPlaces().id] > max){
                max = counts[articles.get(i).article.getPlaces().id];
                result = articles.get(i).article.getPlaces();
            }
        }

        return result;
    }


    private static class ArticleCompared
    {
        ArticleFeatures article;
        Double distance;

        public ArticleCompared(ArticleFeatures article, double distance) {
            this.article = article;
            this.distance = distance;
        }
    }

    public void setMethod(Methods methods)
    {
        this.methods = methods;
    }

}