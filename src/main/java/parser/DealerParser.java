package parser;

import model.Dealer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealerParser {

    public List<Dealer> parseFile(String filePath) {
        List<Dealer> dealers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Dealer dealer = parseLine(line);
                dealers.add(dealer);
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + filePath);
        }

        return dealers;
    }

    private Dealer parseLine(String line) {
        String delimiter = detectDelimiter(line);
        String[] fields = line.split(java.util.regex.Pattern.quote(delimiter));

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        String code = fields.length > 0 ? fields[0] : "UNKNOWN";
        String name = fields.length > 1 ? fields[1] : "Unnamed Dealer";
        String phone = fields.length > 2 && !fields[2].isEmpty() ? fields[2] : "No Phone";
        String location = fields.length > 3 ? fields[3] : "Unknown Location";

        return new Dealer(code, name, phone, location);
    }

    private String detectDelimiter(String line) {
        if (line.contains("|")) {
            return "|";
        } else if (line.contains(";")) {
            return ";";
        } else {
            return ",";
        }
    }
}