package chernook.fit.lab5.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chernook.fit.lab5.GoodsInformationActivity;
import chernook.fit.lab5.R;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsAdapter extends BaseAdapter {

    Context context;
    List<GoodsItem> objects;
    LayoutInflater inflater;

    public GoodsAdapter(Context context, List<GoodsItem> products) {
        this.context = context;
        this.objects = products;
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

        ((Activity)context).registerForContextMenu(view);
        view.setId(i);
        view.setTag(goodsItem.getId());
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, GoodsInformationActivity.class);
            intent.putExtra("goods", goodsItem);
            context.startActivity(intent);
        });

        return view;
    }
}
