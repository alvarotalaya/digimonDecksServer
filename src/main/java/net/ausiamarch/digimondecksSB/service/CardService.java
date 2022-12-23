package net.ausiamarch.digimondecksSB.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.transaction.Transactional;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import net.ausiamarch.digimondecksSB.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.entity.CardEntity;
import net.ausiamarch.digimondecksSB.exception.CannotPerformOperationException;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.CardRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CardService {

    @Autowired
    CardRepository oCardRepository;

    @Autowired
    AuthService oAuthService;

    public Long getAllCards() throws IOException, ParseException {
        //oAuthService.OnlyAdmins();

        String[] colors = {"red", "blue", "yellow", "green", "black", "purple","white"};

        for(int i = 0; i < colors.length; i++){
            URL url = new URL("https://digimoncard.io/api-public/search.php?color=" + colors[i] +"&series=Digimon");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int responsecode = con.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
              
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
              
               //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                   inline += scanner.nextLine();
                }
                
                //Close the scanner
                scanner.close();
            
                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);
            
                //Get the required object from the above created object
                JSONObject obj = (JSONObject) data_obj.get("Global");
            
                //Get the required data using its key
                System.out.println(obj.get("TotalRecovered"));

                JSONArray arr = (JSONArray) data_obj.get("Countries");

                for (int j = 0; j < arr.size(); j++) {

                    JSONObject actualCard = (JSONObject) arr.get(i);
                } 
            }   
        }

        return oCardRepository.count();
    }

/*
https://digimoncard.io/api-public/search.php?color=red&series=Digimon Card Game
*/

}

