package persistence.files;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Currency;
import persistence.CurrencyListLoader;


public class FilesCurrencyListLoader implements CurrencyListLoader{
    
    private final String filename;

    public FilesCurrencyListLoader(String filename) {
        this.filename = filename;
    }
    
   
    @Override
    public Currency[] currencies() {
        List<Currency> list = new ArrayList<>();
         try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            while (true){
                String line = reader.readLine();
                if(line.isEmpty()) break;
                list.add(currencyOf(line));
            }
        } catch (IOException ex) {
        }
        return list.toArray(new Currency[0]);
    }

    private Currency currencyOf(String line) {
        String[] split = line.split(",");
        return new Currency(split[0],split[1],split[2]);
    }
    
}
