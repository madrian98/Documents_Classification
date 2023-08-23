package Model;

/**
 Article features
 */


public class ArticleFeatures
{
    public static final int NUMBER_OF_TRAITS = 10;
    private Places places;

    private int sentencesAmount;
    private int digitsAmount;
    private int shortWordsAmount; // words shorter than 4 letters
    private int longWordsAmount; // words longer than 10 letters
    private int normalWordsAmount; // words in between <4,10> letters
    private int textLength;
    private String currencyFormats;
    private String dateFormats;
    private String lengthUnitFormats;
    private String temperatureUnitFormats;

    public ArticleFeatures(Places places, int sentencesAmount, int digitsAmount, int shortWordsAmount,
                           int longWordsAmount, int normalWordsAmount, int textLength, String currencyFormats, String dateFormats,
                           String lengthUnitFormats, String temperatureUnitFormats)
    {
        this.places = places;
        this.sentencesAmount = sentencesAmount;
        this.digitsAmount = digitsAmount;
        this.shortWordsAmount = shortWordsAmount;
        this.longWordsAmount = longWordsAmount;
        this.normalWordsAmount = normalWordsAmount;
        this.textLength = textLength;
        this.currencyFormats = currencyFormats;
        this.dateFormats = dateFormats;
        this.lengthUnitFormats = lengthUnitFormats;
        this.temperatureUnitFormats = temperatureUnitFormats;
    }


    public Places getPlaces() {
        return places;
    }

    public int getSentencesAmount() {
        return sentencesAmount;
    }

    public int getDigitsAmount() {
        return digitsAmount;
    }

    public int getShortWordsAmount() {
        return shortWordsAmount;
    }

    public int getLongWordsAmount() {
        return longWordsAmount;
    }

    public int getNormalWordsAmount() {
        return normalWordsAmount;
    }

    public int getTextLength() {
        return textLength;
    }

    public String getCurrencyFormats() {
        return currencyFormats;
    }

    public String getDateFormats() {
        return dateFormats;
    }

    public String getLengthUnitFormats() {
        return lengthUnitFormats;
    }

    public String getTemperatureUnitFormats() {
        return temperatureUnitFormats;
    }
}
