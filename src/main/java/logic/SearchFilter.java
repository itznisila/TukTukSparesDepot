package logic;

import model.Part;
import java.util.ArrayList;
import java.util.List;

public class SearchFilter {

    public List<Part> search(List<Part> parts, String category, Double minPrice, Double maxPrice, String keyword) {
        List<Part> results = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            boolean matches = true;

            if (category != null && !category.isEmpty()) {
                if (!p.getCategory().equalsIgnoreCase(category)) {
                    matches = false;
                }
            }

            if (matches && minPrice != null) {
                if (p.getPrice() < minPrice) {
                    matches = false;
                }
            }

            if (matches && maxPrice != null) {
                if (p.getPrice() > maxPrice) {
                    matches = false;
                }
            }

            if (matches && keyword != null && !keyword.isEmpty()) {
                String keywordLower = keyword.toLowerCase();
                boolean nameMatches = p.getName().toLowerCase().contains(keywordLower);
                boolean codeMatches = p.getCode().toLowerCase().contains(keywordLower);
                if (!nameMatches && !codeMatches) {
                    matches = false;
                }
            }

            if (matches) {
                results.add(p);
            }
        }

        return results;
    }
}


