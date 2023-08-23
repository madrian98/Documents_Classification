package Model;

import ViewController.ArticleConnector;
import ViewController.LogicLoader;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 Test collection
 */
public class ArticleTestCollection {
    private List<ArticleFeatures> testCollection;

    public List<ArticleFeatures> getTestCollection()
    {
        return testCollection;
    }

    public void setTestCollection(List<ArticleFeatures> testCollection) {
        this.testCollection = testCollection;
    }


    public List<ArticleConnector> classifyCollection(Metrics metrics, int K, double[] feateure_weight, Methods methods) {
        if (testCollection == null || testCollection.isEmpty()) {
            return Collections.emptyList();
        }

        LogicLoader.getCalculator().setMethod(methods);

        return testCollection.stream()
                .map(testArticle ->
                {
                    Places country = LogicLoader.getCalculator().classification(testArticle, metrics, K, feateure_weight);
                    return new ArticleConnector(testArticle, country);
                })
                .collect(Collectors.toList());
    }
}

