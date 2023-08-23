package Model;



import java.util.*;





public class FeaturesExtractor {


    public int sentencesAmount(ArticleData articleData)
    {

        int count = 0;
        List<String> splittedText = new ArrayList<>(Arrays.asList(articleData.getBody().split("[\\.!?]")));


        count = splittedText.size();


        return count;
    }

    public int digitsAmount(ArticleData articleData)
    {

        String numbersInText = articleData.getBody().replaceAll("[^0-9]", "");

        return numbersInText.length();
    }

    public int shortWordsAmount(ArticleData articleData){

        int count = 0;
        String text = articleData.getBody().replaceAll("[^A-Za-z\\s]", "");
        List<String> words = articleData.textSplitter(text);

        for(String word : words){
            if(word.length() < 4 && !word.isEmpty()){
                count++;
            }
        }
        return count;
    }

    public int longWordsAmount(ArticleData articleData)
    {

        int count = 0;
        String text = articleData.getBody().replaceAll("[^A-Za-z\\s]", "");
        List<String> words = articleData.textSplitter(text);

        for(String word : words){
            if(word.length() > 10 && !word.isEmpty()){
                count++;
            }
        }
        return count;
    }

    public int normalWordsAmount(ArticleData articleData)
    {

        int count = 0;
        String text = articleData.getBody().replaceAll("[^A-Za-z\\s]", "");
        List<String> words = articleData.textSplitter(text);

        for(String word : words){
            if(word.length() < 11 && word.length() > 3 && !word.isEmpty()){
                count++;
            }
        }
        return count;
    }


    public int wordsAmount(ArticleData articleData){

        List<String> words = articleData.textSplitter(articleData.getBody());

        return words.size();
    }


    public String currencyAmount(ArticleData articleData)
    {

        String mostCommonCurrency = "";
        List<String> textWithNoStopWords = articleData.removeStopWords();
        List<String> textWithCurrencyCodes = new ArrayList<String>();
        List<String> currencyList = new ArrayList<String>();

        for(Locale locale : Locale.getAvailableLocales()){
            Currency currency = null;
            try{
                currency = Currency.getInstance(locale);
                currencyList.add(currency.getCurrencyCode());
            }catch (IllegalArgumentException e){
                continue;
            }
        }

        for(String word : textWithNoStopWords){
            if(currencyList.contains(word)){
                textWithCurrencyCodes.add(word);
            }
        }

        mostCommonCurrency = mostCommonName(textWithCurrencyCodes);

        return mostCommonCurrency;
    }

    public String dateFormatAmount(ArticleData articleData)
    {
        String mostCommonDateFormat = "";
        List<String> words = articleData.textSplitter(articleData.getBody());
        List<String> dateFormatsList = new ArrayList<String>();

        for(String word : words){

            if(findOutDateFormat(word) != null) {
                List<String> temp = new ArrayList<String>(Arrays.asList(word.split("[-./]")));
                int correctFormat = 0;
                if(temp.size() == 3){
                    for(String s : temp){
                        if(Integer.parseInt(s) > 12){
                            correctFormat++;
                        }
                    }
                }

                if(correctFormat > 1){
                    dateFormatsList.add(findOutDateFormat(word));
                }

            }
        }
        mostCommonDateFormat = mostCommonName(dateFormatsList);


        return mostCommonDateFormat;
    }

    public String lengthUnitAmount(ArticleData articleData)
    {
        String mostCommonLengthUnit = "";
        //length units
        List<String> length_SI_and_Imperial =
                List.of("meter", "m", "kilometer", "km", "millimeter", "mm", "centimeter", "cm",
                        "decimeter", "dm", "micrometer", "μm", "nanometer", "nm",
                        "meters", "kilometers", "millimeters", "centimeters",
                        "decimeters", "micrometers", "nanometers","thou", "mil", "line", "inch", "foot", "yard", "mile", "league",
                        "mils", "lines", "inches", "feet", "yards", "miles", "leagues" );


        List<String> words = articleData.textSplitter(articleData.getBody().replaceAll("[^A-Za-z\\s]", ""));
        List<String> lengthUnitsFromArticle = new ArrayList<String>();

        for(String word : words)
        {

            if(length_SI_and_Imperial.contains(word)) {
                lengthUnitsFromArticle.add(word);
            }
        }
        mostCommonLengthUnit = mostCommonName(lengthUnitsFromArticle);


        return mostCommonLengthUnit;
    }

    public String temperatureUnitAmount(ArticleData articleData)
    {
        String mostCommonTemperatureUnit = "";
        List<String> words = articleData.textSplitter(articleData.getBody().replaceAll("[^A-Za-z\\s]", ""));

        List<String> temperatureUnits = List.of("K", "C", "F", "Kelvin", "Celcius", "Fahrenheit");
        List<String> temperatureUnitsFromArticle = new ArrayList<String>();

        for(String word : words){

            if(temperatureUnits.contains(word)) {
                temperatureUnitsFromArticle.add(word);
            }
        }
        mostCommonTemperatureUnit = mostCommonName(temperatureUnitsFromArticle);

        return mostCommonTemperatureUnit;
    }

    public FeaturesExtractor()
    {
    }
    public ArticleFeatures featureRecognizion(ArticleData DTO)
    {
        return new ArticleFeatures(DTO.getPlace(), sentencesAmount(DTO), digitsAmount(DTO), shortWordsAmount(DTO),
                longWordsAmount(DTO),normalWordsAmount(DTO) ,wordsAmount(DTO), currencyAmount(DTO),
                dateFormatAmount(DTO), lengthUnitAmount(DTO), temperatureUnitAmount(DTO));
    }


    private static final Map<String, String> DATE_FORMAT_MAP = new HashMap<String, String>()
    {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{1,2}.\\d{1,2}.\\d{4}$", "dd.MM.yyyy");
        put("^\\d{1,2}-\\d{1,2}-\\d{2}$", "dd-MM-yy");
        put("^\\d{1,2}.\\d{1,2}.\\d{2}$", "dd.MM.yy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{4}.\\d{1,2}.\\d{1,2}$", "yyyy.MM.dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{1,2}/\\d{1,2}/\\d{2}$", "MM/dd/yy");
    }};

    public static String findOutDateFormat(String dateString)
    {
        for (String regexp : DATE_FORMAT_MAP.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_MAP.get(regexp);
            }
        }
        return null;
    }

    private String mostCommonName(List<String> list)
    {

        String word = "";
        Map<String,Integer> map = new HashMap<String, Integer>();
        Set<String> FrequencyOfWords = new HashSet<>(list);

        for(String string : FrequencyOfWords){
            map.put(string, Collections.frequency(list, string));
        }

        if(!map.isEmpty()) {
            word = Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }
        return word;
    }


}