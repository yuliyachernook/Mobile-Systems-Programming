package chernook.fit.lab5.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.Date;

import chernook.fit.lab5.GoodsInformationActivity;
import chernook.fit.lab5.MainActivity;
import chernook.fit.lab5.R;
import chernook.fit.lab5.database.DateTypeConverter;
import chernook.fit.lab5.database.UUIDTypeConverter;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsCursorAdapter extends SimpleCursorAdapter {
    private static final int favoriteColor = Color.argb(100, 206, 149, 161);
    private static final int noFavoriteColor = Color.argb(255, 255,255,255);

    private static final int layout = R.layout.goods_item_row;
    private static final String[] from = new String[] { "image", "name", "findDate", "findPlace" };
    private static final int[] to = new int[] { R.id.good_image, R.id.good_name, R.id.good_find_date, R.id.good_find_place };

    public GoodsCursorAdapter(Context context, Cursor c) {
        super(context, layout, c, from, to, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int favoriteIndex = cursor.getColumnIndex("isFavorite");
        boolean favorite = cursor.getInt(favoriteIndex) == 1;

        if (favorite) {
            view.setBackgroundColor(favoriteColor);
        } else {
            view.setBackgroundColor(noFavoriteColor);
        }

        ((Activity)context).registerForContextMenu(view);
        view.setId(cursor.getPosition());
        view.setTag(favorite);
        GoodsItem goodItem = new GoodsItem(
                UUIDTypeConverter.toUUID( cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getBlob(4),
                cursor.getString(3), DateTypeConverter.toDate(cursor.getLong(5)),
                cursor.getString(6), cursor.getString(7), cursor.getInt(8) == 1);
        view.setOnClickListener(view1 -> {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((MainActivity)context).setStateOfFragments(goodItem);
            }
            else {
                Intent intent = new Intent(context, GoodsInformationActivity.class);
                intent.putExtra("goods", goodItem);
                context.startActivity(intent);
            }
        });

        int imageIndex = cursor.getColumnIndex(from[0]);
        int nameIndex = cursor.getColumnIndex(from[1]);
        int findDateIndex = cursor.getColumnIndex(from[2]);
        int findPlaceIndex = cursor.getColumnIndex(from[3]);

        String name = cursor.getString(nameIndex);
        byte[] byteImage = cursor.getBlob(imageIndex);
        Bitmap image = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        Date findDate = DateTypeConverter.toDate(cursor.getLong(findDateIndex));
        String findDateFormat = new SimpleDateFormat("dd.MM.").format(findDate) + findDate.getYear();
        String findPlace = cursor.getString(findPlaceIndex);

        ((ImageView) view.findViewById(to[0])).setImageBitmap(image);
        ((TextView) view.findViewById(to[1])).setText(name);
        ((TextView) view.findViewById(to[2])).setText(findDateFormat);
        ((TextView) view.findViewById(to[3])).setText(findPlace);
    }
}
