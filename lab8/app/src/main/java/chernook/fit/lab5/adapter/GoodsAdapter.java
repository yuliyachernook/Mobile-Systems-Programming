package chernook.fit.lab5.adapter;

import static chernook.fit.lab5.MainActivity.NewStateReason.UPDATE_LIST;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chernook.fit.lab5.GoodsInformationActivity;
import chernook.fit.lab5.MainActivity;
import chernook.fit.lab5.R;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsAdapter extends BaseAdapter {

    private static final int favoriteColor = Color.argb(100, 255, 235, 59);
    private static final int noFavoriteColor = Color.argb(255, 255,255,255);

    Context context;
    List<GoodsItem> objects;
    LayoutInflater inflater;

    public GoodsAdapter(Context context, List<GoodsItem> goodsItemList) {
        this.context = context;
        this.objects = goodsItemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.goods_item_row, parent, false);
        }

        GoodsItem goodsItem = (GoodsItem) getItem(i);

        ((ImageView) view.findViewById(R.id.good_image)).setImageBitmap(goodsItem.getImageAsBitmap());
        ((TextView) view.findViewById(R.id.good_name)).setText(goodsItem.getName());
        ((TextView) view.findViewById(R.id.good_find_date)).setText(goodsItem.getFindDateInFormat());
        ((TextView) view.findViewById(R.id.good_find_place)).setText(goodsItem.getFindPlace());

        if (goodsItem.isFavorite()) {
            view.setBackgroundColor(favoriteColor);
        } else {
            view.setBackgroundColor(noFavoriteColor);
        }

        ((Activity)context).registerForContextMenu(view);
        view.setId(i);
        view.setTag(goodsItem.isFavorite());
        view.setOnClickListener(view1 -> {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                 ((MainActivity)context).setStateOfFragments(UPDATE_LIST, goodsItem);
            }
            else {
                Intent intent = new Intent(context, GoodsInformationActivity.class);
                intent.putExtra("goods", goodsItem);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
