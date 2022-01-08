package chernook.fit.lab5;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import chernook.fit.lab5.entity.GoodsItem;

public class GoodsItemDetailFragment extends Fragment {

    private Context context;
    private View view;
    private GoodsItem goodsItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        if (savedInstanceState != null)
            goodsItem = (GoodsItem) savedInstanceState.getSerializable("goods");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (goodsItem != null)
            outState.putSerializable("goods", goodsItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_item_detail, container, false);

        if (goodsItem != null)
            applySelectedGoodItem();

        return view;
    }

    void setSelectedGoodItem(GoodsItem goodItem) {
        this.goodsItem = goodItem;
    }

    private void applySelectedGoodItem() {

        TextView idField = view.findViewById(R.id.good_id);
        TextView nameField = view.findViewById(R.id.good_name);
        TextView descriptionField = view.findViewById(R.id.good_description);
        TextView findPlaceField = view.findViewById(R.id.good_find_place);
        ImageView imageField = view.findViewById(R.id.good_image);
        TextView findDateField = view.findViewById(R.id.good_find_date);
        TextView finderField = view.findViewById(R.id.good_finder);
        TextView receiptPlaceField = view.findViewById(R.id.good_receipt_place);

        idField.setText(goodsItem.getId().toString());
        nameField.setText(goodsItem.getName());
        descriptionField.setText(goodsItem.getDescription());
        findPlaceField.setText(goodsItem.getFindPlace());
        imageField.setImageBitmap(goodsItem.getImageAsBitmap());
        findDateField.setText(goodsItem.getFindDateInFormat());
        finderField.setText(goodsItem.getFinder());
        receiptPlaceField.setText(goodsItem.getReceiptPlace());
    }
}