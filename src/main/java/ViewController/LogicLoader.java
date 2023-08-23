package ViewController;

import Model.ArticleDeserialization;
import Model.ArticleExtractor;
import Model.FeaturesExtractor;
import Model.ArticleTestCollection;
import Model.KNN;

/**
 Loading logic into View Packet
 */

public class LogicLoader {
    private static final KNN calculator = new KNN();
    private static final ArticleDeserialization deserializer = new ArticleDeserialization();
    private static final FeaturesExtractor recognitionService = new FeaturesExtractor();
    private static final ArticleTestCollection classificationService = new ArticleTestCollection();
    private static final ArticleExtractor extractionService = new ArticleExtractor();

    public static ArticleTestCollection getClassificationService() {
        return classificationService;
    }

    public static ArticleExtractor getExtractionService() {
        return extractionService;
    }
    public static KNN getCalculator() {
        return calculator;
    }
    public static FeaturesExtractor getRecognitionService() {
        return recognitionService;
    }

    public static ArticleDeserialization getDeserializer() {
        return deserializer;
    }

}