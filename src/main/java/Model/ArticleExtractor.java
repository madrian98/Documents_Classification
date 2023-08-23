package Model;

/**
 Article Extractor
 */


import ViewController.LogicLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleExtractor {
    private List<ArticleFeatures> allArticles;

    //extractor
    public void extractor()
    {
        FeaturesExtractor recognitionService = LogicLoader.getRecognitionService();
        List<ArticleData> DTOs = LogicLoader.getDeserializer().getAllArticles();
        allArticles = DTOs.parallelStream().map(recognitionService::featureRecognizion).collect(Collectors.toList());

    }
    //splitter
    public void splitter(int trainingSetSize) {
        if (trainingSetSize > 100)
        {
            trainingSetSize = 100;
        }
        int splitPoint = allArticles.size() * trainingSetSize / 100;
        List<ArticleFeatures> trainCharacteristics = allArticles.subList(0, splitPoint);
        List<ArticleFeatures> testCharacteristics = allArticles.subList(splitPoint, allArticles.size());
        LogicLoader.getCalculator().setLearningCollection(trainCharacteristics);
        LogicLoader.getClassificationService().setTestCollection(testCharacteristics);
    }


    // checking correction of splitting on training and test size
    public void splitterCorrection(int trainingSetSize)
    {
        int[] sum = new int[Places.NUMBER_OF_PLACES];

        Arrays.fill(sum, trainingSetSize);
        ArrayList<ArticleFeatures> train = new ArrayList<>();
        ArrayList<ArticleFeatures> test = new ArrayList<>();

        for (ArticleFeatures article : allArticles) {
            if (sum[article.getPlaces().id] > 0) {
                sum[article.getPlaces().id]--;
                train.add(article);
            } else {
                test.add(article);
            }
        }
        LogicLoader.getCalculator().setLearningCollection(train);
        LogicLoader.getClassificationService().setTestCollection(test);
    }
}
