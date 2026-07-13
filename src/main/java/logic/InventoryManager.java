package logic;

import model.Part;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private List<Part> parts;

    public InventoryManager() {
        this.parts = new ArrayList<>();
    }

    public InventoryManager(List<Part> parts) {
        this.parts = parts;
    }

    public List<Part> getParts() {
        return parts;
    }


    public boolean addPart(Part p) {
        if (p==null) {
            System.out.println("Cannot add null part");
            return false;
        }

        if (p.getPrice() <= 0) {
            System.out.println("Rejected: price must be greater than 0 (code=" + p.getCode() + ")");
            return false;
        }

        if (p.getQuantity() < 0) {
            System.out.println("Rejected: quantity cannot be negative (code=" + p.getCode() + ")");
            return false;
        }

        for (Part existing : parts) {
            if (existing.getCode().equals(p.getCode())) {
                System.out.println("Rejected: duplicate part code '" + p.getCode() + "'");
                return false;
            }
        }

        parts.add(p);
        System.out.println("Added part: " + p.getCode());
        return true;
    }

    public boolean updatePart(String code, Part updated) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equalsIgnoreCase(code)) {
                // Validate the updated data too
                if (updated.getPrice() <= 0 || updated.getQuantity() < 0) {
                    System.out.println("Update rejected: invalid price/quantity for " + code);
                    return false;
                }
                parts.set(i, updated);
                System.out.println("Updated part: " + code);
                return true;
            }
        }
        System.out.println("Update failed: part code '" + code + "' not found.");
        return false;
    }

    public boolean deletePart(String code) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equalsIgnoreCase(code)) {
                parts.remove(i);
                System.out.println("Deleted part: " + code);
                return true;
            }
        }
        System.out.println("Delete failed: part code '" + code + "' not found.");
        return false;
    }

    public void sortByCategoryThenCode(List<Part> parts) {
        for (int i = 1; i < parts.size(); i++) {
            Part key = parts.get(i);
            int j = i - 1;

            while (j >= 0 && comparePartsForSort(parts.get(j), key) > 0) {
                parts.set(j + 1, parts.get(j));
                j--;
            }
            parts.set(j + 1, key);
        }
    }

    private int comparePartsForSort(Part a, Part b) {
        int categoryCompare = a.getCategory().compareToIgnoreCase(b.getCategory());
        if (categoryCompare != 0) {
            return categoryCompare;
        }
        return a.getCode().compareToIgnoreCase(b.getCode());
    }

    public int getTotalItemCount() {
        int total = 0;
        for (Part p : parts) {
            total += p.getQuantity();
        }
        return total;
    }

    public double getTotalInventoryValue() {
        double total = 0.0;
        for (Part p : parts) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }
}