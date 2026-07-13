package parser;

import model.Part;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InventoryParser {

    public List<Part> parseFile(String filePath) {
        List<Part> parts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Part part = parseLine(line);
                parts.add(part);
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + filePath);
        }

        return parts;
    }

    private Part parseLine(String line) {
        String protectedLine = line.replaceAll("(?<=[A-Za-z]{3}\\s\\d{1,2}),(?=\\s*\\d{4})", "§");
        String[] fields = protectedLine.split("[,|;]", -1);

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        String code = fields.length > 0 ? fields[0] : "UNKNOWN";
        String name = fields.length > 1 ? fields[1] : "Unnamed Part";
        String dealerName = fields.length > 2 && !fields[2].isEmpty() ? fields[2] : "Unknown Dealer";
        double price = fields.length > 3 ? parsePrice(fields[3]) : 0.0;
        int quantity = fields.length > 4 ? parseQuantity(fields[4]) : 0;
        String category = fields.length > 5 ? normalizeCategory(fields[5]) : "Uncategorized";
        LocalDate dateAdded = fields.length > 6 ? parseDate(fields[6].replace("§", ",")) : LocalDate.now();
        String imagePath = fields.length > 7 ? fields[7] : "";

        return new Part(code, name, dealerName, price, quantity, category, dateAdded, imagePath);
    }

    private double parsePrice(String rawPrice) {
        String cleaned = rawPrice.replace("Rs.", "").replace("Rs", "").trim();
        if (cleaned.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseQuantity(String rawQuantity) {
        try {
            return Integer.parseInt(rawQuantity.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String normalizeCategory(String rawCategory) {
        String lower = rawCategory.trim().toLowerCase();
        if (lower.isEmpty()) {
            return "Uncategorized";
        }
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private LocalDate parseDate(String rawDate) {
        String cleaned = rawDate.trim();
        String[] patterns = {
                "yyyy-MM-dd",
                "dd/MM/yyyy",
                "yyyy/MM/dd",
                "dd-MM-yyyy",
                "dd-MMM-yyyy",
                "MMM dd, yyyy"
        };

        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, java.util.Locale.ENGLISH);
                return LocalDate.parse(cleaned, formatter);
            } catch (Exception e) {
                continue;
            }
        }

        return LocalDate.now();
    }
}