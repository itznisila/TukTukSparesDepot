package logic;

import model.Part;
import java.util.ArrayList;
import java.util.List;

public class LowStockMonitor {

    public static final int LOW_STOCK_THRESHOLD = 10;

    public List<Part> getLowStockItems(List<Part> parts) {
        List<Part> lowStock = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            if (p.getQuantity() < LOW_STOCK_THRESHOLD) {
                lowStock.add(p);
            }
        }

        return lowStock;
    }
}
