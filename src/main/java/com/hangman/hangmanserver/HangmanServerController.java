package com.hangman.hangmanserver;


import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class HangmanServerController {
    private String word;
    String updatedDash ="";
    String dash ="";
    StringBuilder str = new StringBuilder();

    @GetMapping("/api/word")
    public String  getWord() throws IOException, InterruptedException {
        this.setWord();
        return this.word;
    }

    @GetMapping("/api/worddash")
    public String  getWordDash() throws IOException, InterruptedException {
        String val = this.getWord();
        StringBuilder str1 = new StringBuilder();
            for (int i = 0; i < val.length(); i++) {
                str1.append("_ ");
            }
            dash= str1.toString();
            updatedDash = dash;
            str.delete(0,str.length());
            str.append(dash);
            System.out.println("Word"+this.word);
            return dash;
    }
    @PostMapping ("/api/character")
    public String  sendChar(@RequestBody String a)  {
        JSONObject obj = new JSONObject();
        Boolean valueMatched =true;
        Boolean success = false;

         if (this.word.indexOf(a) != -1){
              updatedDash =  this.checkLetter(a,this.word);
              success = this.checkSuccess(updatedDash);
         } else{
             valueMatched = false;
         }
         obj.put("updatedDash",updatedDash);
         obj.put("valueMatched",valueMatched);
         obj.put("success",success);
         obj.put("word",this.word);
         return obj.toString();
    }

    public Boolean checkSuccess(String data){
        Boolean isComplete= false;
        data = data.replaceAll(" ", "") ;
        if (data.equalsIgnoreCase(this.word) ){
            isComplete = true;
        }
        return isComplete;
    }
    public String checkLetter(String a, String w){


        for (int i = 0; i < w.length(); i++) {
            if (w.charAt(i) == a.charAt(0)){
                str.replace(i+i,i+i+1, a);
            }

        }
         updatedDash = str.toString();
        return updatedDash;
    }

    public void setWord() throws IOException, InterruptedException {
        final String regex = "(?<=\\\").+?(?=\\\")";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-word-api.herokuapp.com/word"))
                .header("X-RapidAPI-Host", "random-words5.p.rapidapi.com")
                .header("X-RapidAPI-Key", "1d5dd50c1amsh7d97474122900dap113e42jsnb83cf3791f45")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        final Matcher matcher = pattern.matcher(response.body());
        while (matcher.find())
        {
            this.word = matcher.group(0);
        }

    }
}
