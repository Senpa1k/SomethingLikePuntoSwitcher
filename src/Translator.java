import okhttp3.*;

import java.io.File;
import java.io.IOException;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Translator {
    private final static Logger logger = LogManager.getLogger(Translator.class);
    private static final String API_KEY ="2f13e7ede2mshfcb001ac77d3479p1a67bajsn2691806b762f";

    public String translate(String neededLanguage,String text) throws IOException {
        Cache cache = new Cache(new File("cacheDir"),10*1024*1024);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder().cache(cache).build();

        String nativeLanguage = detecting(text);

        String jsonInputForTranslate = String.format("{\"q\": \"%s\", \"source\": \"%s\", \"target\": \"%s\"}",text,nativeLanguage,neededLanguage);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonInputForTranslate);


        Request request = new Request.Builder()
                .url("https://deep-translate1.p.rapidapi.com/language/translate/v2")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-key", API_KEY)
                .addHeader("x-rapidapi-host", "deep-translate1.p.rapidapi.com")
                .build();

        try(Response response = okHttpClient.newCall(request).execute()){
            if (response.isSuccessful() && response.body()!= null){
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                logger.info("Text was translated");
                return jsonObject
                        .getAsJsonObject("data")
                        .getAsJsonObject("translations")
                        .get("translatedText")
                        .getAsString();

            }else{
                System.out.println("Error: " + response.code());
                System.out.println("Error Body: " + (response.body() != null ? response.body().string() : "No body"));
            }
        }
        return null;
    }

    private String detecting(String text)  {
        String jsonInputForDetecting = String.format("{\"q\": \"%s\"}", text);
        OkHttpClient okHttpClient  = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonInputForDetecting);

        Request request = new Request.Builder()
                .url("https://deep-translate1.p.rapidapi.com/language/translate/v2/detect")
                .post(requestBody)
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-key", API_KEY)
                .addHeader("x-rapidapi-host", "deep-translate1.p.rapidapi.com")
                .build();


        try(Response response = okHttpClient.newCall(request).execute()) {
            if (response.body()!= null && response.isSuccessful()){
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonObject dataObject = jsonObject.getAsJsonObject("data");
                JsonArray detectionArray = dataObject.getAsJsonArray("detections");
                JsonObject firstDetection = detectionArray.get(0).getAsJsonObject();
                logger.info("Language was detected");
                return firstDetection.get("language").getAsString();

            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return null;
    }



}
