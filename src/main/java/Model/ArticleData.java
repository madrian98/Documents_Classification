package Model;

/**
 Text splitting+Stop words
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleData {


    private String places;
    private String body;
    private List<String> stopWords;

    public ArticleData(String places, String body)
    {
        this.places = places;
        this.body = body;
    }

    public String getPlaces(){
        return places;
    }
    public String getBody(){
        return body;
    }

    public Places getPlace(){
        Places place;
        switch(places)
        {
            case "west-germany":
                place = Places.WEST_GERMANY;
                break;
            case "usa":
                place = Places.USA;
                break;
            case "france":
                place = Places.FRANCE;
                break;
            case "uk":
                place = Places.UK;
                break;
            case "canada":
                place = Places.CANADA;
                break;
            case "japan":
                place = Places.JAPAN;
                break;
            default:
                throw new IllegalStateException("Incorrect country name: " + places);
        }
        return place;
    }


    public List<String> textSplitter(String text){

        List<String> wordsList = new ArrayList<String>(Arrays.asList(text.split(" ")));

        return wordsList;
    }


    private void getStopWordsFromFile(){

        this.stopWords = new ArrayList<String>();
        File file = new File("src/main/resources/data/stop_words.txt");

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                this.stopWords.add(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String> removeStopWords()
    {

        String text = getBody().replaceAll("[^A-Za-z\\s]", "");
        List<String> splittedText = textSplitter(text);
        List<String> textWithNoStopWord = new ArrayList<String>();
        getStopWordsFromFile();

        for(String word : splittedText){
            if(!stopWords.contains(word.toLowerCase()) && !word.isEmpty()){
                textWithNoStopWord.add(word);
            }
        }
        return textWithNoStopWord;
    }

    @Override
    public String toString(){

        return "ArticleData{places:'" + getPlaces() + "'" + ", body:'" + getBody() +"'}";
    }


}
