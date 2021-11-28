package chernook.fit.lab5.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import chernook.fit.lab5.entity.GoodsItem;


public class GoodItemsContainer {

    private final static String FILE_NAME = "goods_data.json";
    private static GoodItemsSortType sortType = GoodItemsSortType.NO_SORT;
    private static List<GoodsItem> goodItems = new ArrayList<>();

    public static void addGoodItem(GoodsItem goodItem) {
        if (goodItem.getId() == null || goodItems.stream().anyMatch(t -> t.getId().equals(goodItem.getId()))) {
            UUID uuid = UUID.randomUUID();
            goodItem.setId(uuid);
        }
        goodItems.add(goodItem);
    }

    public static void updateGoodItem(GoodsItem newGoodItem) {
        if (newGoodItem.getId() != null && goodItems.stream().anyMatch(t -> t.getId().equals(newGoodItem.getId()))) {
            GoodsItem oldGoodItem = getGoodItem(newGoodItem.getId());
            oldGoodItem.setName(newGoodItem.getName());
            oldGoodItem.setDescription(newGoodItem.getDescription());
            oldGoodItem.setFindPlace(newGoodItem.getFindPlace());
            oldGoodItem.setImage(newGoodItem.getImage());
            oldGoodItem.setFindDate(newGoodItem.getFindDate());
            oldGoodItem.setFinder(newGoodItem.getFinder());
            oldGoodItem.setReceiptPlace(newGoodItem.getReceiptPlace());
        }
    }

    public static  void removeGoodItem(UUID id) {
        goodItems.removeIf(t -> t.getId() == null);
        if (id != null)
            goodItems.removeIf(t -> t.getId().equals(id));
    }

    public static GoodsItem getGoodItem(UUID id) {
        if (id != null)
            return goodItems.stream().filter(t -> t.getId().equals(id)).findFirst().get();
        else
            throw new NullPointerException();
    }

    public static List<GoodsItem> getGoodsItemsList() {
        switch (sortType) {
            case SORT_BY_NAME:
                return goodItems.stream().sorted(Comparator.comparing(GoodsItem::getName)).collect(Collectors.toList());
            case SORT_BY_FIND_DATE:
                return goodItems.stream().sorted(Comparator.comparing(GoodsItem::getFindDate)).collect(Collectors.toList());
            default:
                return goodItems;
        }
    }

    public static List<GoodsItem> getGoodsItemsList(String pattern) {
        String filter = "(\\w*)" + pattern.toLowerCase(Locale.ROOT) + "(\\w*)";
        switch (sortType) {
            case SORT_BY_NAME:
                return goodItems.stream()
                        .filter(t -> t.getName().toLowerCase(Locale.ROOT).matches(filter))
                        .sorted(Comparator.comparing(GoodsItem::getName)).collect(Collectors.toList());
            case SORT_BY_FIND_DATE:
                return goodItems.stream()
                        .filter(t -> t.getName().toLowerCase(Locale.ROOT).matches(filter))
                        .sorted(Comparator.comparing(GoodsItem::getFindDate)).collect(Collectors.toList());
            default:
                return goodItems.stream()
                        .filter(t -> t.getName().toLowerCase(Locale.ROOT).matches(filter)).collect(Collectors.toList());
        }
    }

    public static GoodItemsSortType getSortType() {
        return sortType;
    }

    public static void setSortType(GoodItemsSortType newSortType) {
        sortType = newSortType;
    }

    public static boolean exportToJson(Context context) {

        Gson gson = new Gson();
        GoodsItemsList shell = new GoodsItemsList(goodItems);
        String jsonString = gson.toJson(shell);

        FileOutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static boolean importFromJson(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream inputStream = null;

        try {
            inputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            goodItems = gson.fromJson(streamReader, GoodsItemsList.class).getGoods();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private static class GoodsItemsList {

        private List<GoodsItem> goods;
        public GoodsItemsList(List<GoodsItem> goods) {
            this.goods = goods;
        }
        List<GoodsItem> getGoods() {
            return goods;
        }
        void setGoods(List<GoodsItem> goods) {
            this.goods = goods;
        }
    }

}
