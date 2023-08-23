package Model;


/**
 Article deserialization
 */



import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleDeserialization
{

    //input data - routers article
    private String filePath = "src/main/resources/data/articles";
    private List<ArticleData> trainArticles = new ArrayList<>();
    private List<ArticleData> testArticles = new ArrayList<>();
    private List<ArticleData> allArticles = new ArrayList<>();

    private File[] directoryFiles;

    public ArticleDeserialization()
    {

        File direcory = new File(filePath());
        this.directoryFiles = direcory.listFiles();

        getDataFromArticles();
    }

    public String filePath(){
        return filePath;
    }

    public List<ArticleData> getTrainArticles() {
        return trainArticles;
    }

    public List<ArticleData> getTestArticles() {
        return testArticles;
    }
    public List<ArticleData> getAllArticles(){
        return allArticles;
    }



    private void getDataFromArticles(){

        for(File file : directoryFiles) {

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    String article = "";

                    if (line.contains("<REUTERS")) {

                        do {
                            article += line;
                            line = bufferedReader.readLine();

                        } while (!line.equals("</REUTERS>"));


                        String[] temp = article.split("<PLACES>");
                        temp = temp[1].split("</PLACES>");
                        String[] places = temp[0].split("</D>");

                        String cos=places[0].replaceAll("<D>","");
                        if(!placeSelector(cos))
                            continue;
                        if (places.length > 1) {
                            continue;
                        }
                        if(!temp[1].contains("<BODY>"))
                            continue;
                        temp=temp[1].split("<BODY>");
                        temp=temp[1].split("</BODY>");

                        ArticleData articleData =new ArticleData(places[0].replaceAll("<D>",""),temp[0]);
                        this.allArticles.add(articleData);


                    }
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private boolean placeSelector(String countryFromArticle){
        String [] countries={"west-germany", "usa", "france", "uk", "canada", "japan"};
        List<String> countryList=new ArrayList<String>(Arrays.asList(countries));
        for(String country : countryList)
            if(countryFromArticle.equals(country))
                return true;
        return false;
    }

    public void setSelector(int trainingSet)
    {

        int trainingSetPercentage = (int) Math.ceil((allArticles.size()* trainingSet)/100);

        for(int i = 0; i< trainingSetPercentage; i++){
            trainArticles.add(allArticles.get(i));
        }

        for(int i = (trainingSetPercentage); i<allArticles.size(); i++){
            testArticles.add(allArticles.get(i));
        }
    }

}