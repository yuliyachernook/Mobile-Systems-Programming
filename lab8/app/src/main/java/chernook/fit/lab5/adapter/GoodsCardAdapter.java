package chernook.fit.lab5.adapter;


import static chernook.fit.lab5.MainActivity.NewStateReason.SELECT_INFO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import chernook.fit.lab5.BR;
import chernook.fit.lab5.GoodsInformationActivity;
import chernook.fit.lab5.MainActivity;
import chernook.fit.lab5.R;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsCardAdapter extends RecyclerView.Adapter<GoodsCardAdapter.GoodItemHolder> {

    private final Context context;
    private final List<GoodsItem> goodsItems;

    public GoodsCardAdapter(Context context, List<GoodsItem> goodItems) {
        this.goodsItems = goodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public GoodItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding vdb = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.goods_item_card,
                parent, false
        );
        return new GoodItemHolder(vdb);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodItemHolder holder, int position) {
        GoodsItem goodsItem = goodsItems.get(position);
        holder.goodItemBinding.setVariable(BR.goods, goodsItem);
        ImageView imageView = holder.goodItemBinding.getRoot().findViewById(R.id.imageCard);
        imageView.setImageBitmap(goodsItem.getImageAsBitmap());
        holder.goodsItem = goodsItem;

        ((Activity)context).registerForContextMenu(holder.goodItemBinding.getRoot());
        holder.goodItemBinding.getRoot().setId(position);
        holder.goodItemBinding.getRoot().setTag(goodsItem.isFavorite());
    }

    @Override
    public int getItemCount() {
        return goodsItems.size();
    }

    class GoodItemHolder extends RecyclerView.ViewHolder {

        ViewDataBinding goodItemBinding;
        GoodsItem goodsItem;

        public GoodItemHolder(@NonNull ViewDataBinding goodItemBinding){
            super(goodItemBinding.getRoot());
            this.goodItemBinding = goodItemBinding;

            goodItemBinding.getRoot().setOnClickListener(view -> {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((MainActivity)context).setStateOfFragments(SELECT_INFO, goodsItem);
                }
                else {
                    Intent intent = new Intent(context, GoodsInformationActivity.class);
                    intent.putExtra("goods", goodsItem);
                    context.startActivity(intent);
                }
            });
        }
    }
}
