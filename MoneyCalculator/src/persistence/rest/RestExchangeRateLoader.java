package persistence.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader{

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return new ExchangeRate(from, to, read(from.getCode(), to.getCode()));
        } catch (IOException ex) {
            return null;
        }
    }

    private double read(String from, String to) throws IOException {
        String line = read(new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + from.toLowerCase() + "/" + to.toLowerCase() + ".json")); 
        int indexTo =  line.indexOf(to.toLowerCase()) + to.length()+3;
        String valueAsText = line.substring(indexTo, line.length()-2);
        System.out.println(valueAsText);
        return Double.parseDouble(valueAsText);
    }

    private String read(URL url) throws IOException {
        InputStream is = url.openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}