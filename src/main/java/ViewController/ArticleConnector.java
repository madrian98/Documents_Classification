package ViewController;

/**
 Article Connector
 */

import Model.Places;
import Model.ArticleFeatures;

public class ArticleConnector {
    ArticleFeatures article;
    Places country;

    public ArticleConnector(ArticleFeatures article, Places country)
    {
        this.article = article;
        this.country = country;
    }
}
