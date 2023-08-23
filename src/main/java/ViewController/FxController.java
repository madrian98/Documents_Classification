package ViewController;

import Model.Metrics;
import Model.Places;
import Model.Methods;
import Model.ArticleFeatures;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 FX-Controller
 */

public class FxController implements Initializable {

    public TextField KValue;
    public Button btn1;
    public Text loadText;
    public Text trainSizeText;
    public TextField trainSetSize;
    public Button calculateBtn;
    public Text calculateText;
    public TextField sentencesAmountWeight;
    public TextField digitsAmountWeight;
    public TextField shortWordsAmountWeight;
    public TextField longWordsAmountWeight;
    public TextField normalWordsAmountWeight;
    public TextField textLengthWeight;
    public TextField currencyWeight;
    public TextField dateFormatWeight;
    public TextField lengthUnitWeight;
    public TextField temperatureUnitWeight;
    public Text accuracyDisplay;
    public Text precisionDisplay;
    public Text recallDisplay;
    public Text F1Display;
    public ToggleGroup methodToggle;
    public RadioButton euclidesBtn;
    public RadioButton manhattanBtn;
    public RadioButton chebyshevBtn;
    public ToggleGroup countryToggle;
    public RadioButton WestGermanyBtn;
    public RadioButton USABtn;
    public RadioButton FranceBtn;
    public RadioButton UKBtn;
    public RadioButton CanadaBtn;
    public RadioButton JapanBtn;
    public RadioButton nGramBtn;
    List<ArticleConnector> articleConnectors;
    public static final ExecutorService es = Executors.newFixedThreadPool(1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numbersOnlyTextField(trainSetSize);
        numbersOnlyTextField(sentencesAmountWeight);
        numbersOnlyTextField(digitsAmountWeight);
        numbersOnlyTextField(shortWordsAmountWeight);
        numbersOnlyTextField(longWordsAmountWeight);
        numbersOnlyTextField(normalWordsAmountWeight);
        numbersOnlyTextField(textLengthWeight);
        numbersOnlyTextField(currencyWeight);
        numbersOnlyTextField(dateFormatWeight);
        numbersOnlyTextField(lengthUnitWeight);
        numbersOnlyTextField(temperatureUnitWeight);
    }

    public void changeLearnSizeText(ActionEvent actionEvent)
    {
        trainSizeText.setText("Determinate a % of articles in learning set");
    }

    public void readArticleDataFromFile() {
        es.submit(() -> {
            loadText.setText("Loading...");
            LogicLoader.getExtractionService().extractor();
            loadText.setText("Articles loaded");
        });
    }

    private void numbersOnlyTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void calculate() {
        es.submit(() -> {
            calculateText.setText("Calculating...");
            Metrics metrics;
            Methods methods;
            methods = Methods.N_GRAM;

            LogicLoader.getExtractionService().splitter(Integer.parseInt(trainSetSize.getText()));

            int[] testSum = new int[Places.NUMBER_OF_PLACES];
            int[] learnSum = new int[Places.NUMBER_OF_PLACES];
            for (ArticleFeatures article : LogicLoader.getClassificationService().getTestCollection()) {
                testSum[article.getPlaces().id]++;
            }
            for (ArticleFeatures article : LogicLoader.getCalculator().getLearningCollection()) {
                learnSum[article.getPlaces().id]++;
            }



            Toggle selectedToggle = methodToggle.getSelectedToggle();
            if (euclidesBtn.equals(selectedToggle)) {
                metrics = Metrics.EUCLIDES;
            } else if (manhattanBtn.equals(selectedToggle)) {
                metrics = Metrics.MANHATTAN;
            } else {
                metrics = Metrics.CHEBYSHEV;
            }

            double[] weights = new double[10];
            weights[0] = Double.parseDouble(sentencesAmountWeight.getText());
            weights[1] = Double.parseDouble(digitsAmountWeight.getText());
            weights[2] = Double.parseDouble(shortWordsAmountWeight.getText());
            weights[3] = Double.parseDouble(longWordsAmountWeight.getText());
            weights[4] = Double.parseDouble(normalWordsAmountWeight.getText());
            weights[5] = Double.parseDouble(textLengthWeight.getText());
            weights[6] = Double.parseDouble(currencyWeight.getText());
            weights[7] = Double.parseDouble(dateFormatWeight.getText());
            weights[8] = Double.parseDouble(lengthUnitWeight.getText());
            weights[9] = Double.parseDouble(temperatureUnitWeight.getText());

            articleConnectors = LogicLoader.getClassificationService().classifyCollection(metrics, Integer.parseInt(KValue.getText()), weights, methods);
            calculateValues();

            calculateText.setText("Classification finished");
        });
    }

    private static final DecimalFormat df3 = new DecimalFormat("#.###");

    public void calculateValues() {
        int t = 0;
        int N = articleConnectors.size();
        int TP = 0;
        int FP = 0;
        int FN = 0;
        double accuracy;
        double precision;
        double recall;
        double F1;
        Toggle selectedToggle = countryToggle.getSelectedToggle();
        Places country;
        if (WestGermanyBtn.equals(selectedToggle)) {
            country = Places.WEST_GERMANY;
        } else if (USABtn.equals(selectedToggle)) {
            country = Places.USA;
        } else if (FranceBtn.equals(selectedToggle)) {
            country = Places.FRANCE;
        } else if (UKBtn.equals(selectedToggle)) {
            country = Places.UK;
        } else if (CanadaBtn.equals(selectedToggle)) {
            country = Places.CANADA;
        } else {
            country = Places.JAPAN;
        }

        for (ArticleConnector cArticle : articleConnectors) {
            if (cArticle.article.getPlaces().equals(cArticle.country)) {
                t++;
            }
            if (cArticle.country.equals(country)) {
                if (cArticle.article.getPlaces().equals(cArticle.country)) {
                    TP++;
                } else {
                    FP++;
                }
            }
            if (cArticle.article.getPlaces().equals(country)) {
                if (!cArticle.article.getPlaces().equals(cArticle.country)) {
                    FN++;
                }
            }
        }
        accuracy = (double) t / N;
        precision = (double) TP / (TP + FP);
        recall = (double) TP / (TP + FN);
        F1 = 2 * precision * recall / (precision + recall);

        accuracyDisplay.setText("Accuracy = " + df3.format(accuracy));
        precisionDisplay.setText("Precision = " + df3.format(precision));
        recallDisplay.setText("Recall = " + df3.format(recall));
        F1Display.setText("F1 = " + df3.format(F1));

    }

}