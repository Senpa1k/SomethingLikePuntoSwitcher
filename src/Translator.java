import okhttp3.*;

import java.io.File;
import java.io.IOException;
import com.google.gson.*;
public class Translator {
    private static final String API_KEY ="2f13e7ede2mshfcb001ac77d3479p1a67bajsn2691806b762f";

    public String translate(String nativeLanguage,String neededLanguage,String text) throws IOException {
        Cache cache = new Cache(new File("cacheDir"),10*1024*1024);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder().cache(cache).build();

        String jsonInput = String.format("{\"q\": \"%s\", \"source\": \"%s\", \"target\": \"%s\"}",text,nativeLanguage,neededLanguage);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonInput);



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
                Gson gson = new Gson();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                String translatedText = jsonObject
                        .getAsJsonObject("data")
                        .getAsJsonObject("translations")
                        .get("translatedText")
                        .getAsString();

                return translatedText;

            }else{
                System.out.println("Error: " + response.code());
                System.out.println("Error Body: " + (response.body() != null ? response.body().string() : "No body"));
            }
        }
        return null;
    }



}
