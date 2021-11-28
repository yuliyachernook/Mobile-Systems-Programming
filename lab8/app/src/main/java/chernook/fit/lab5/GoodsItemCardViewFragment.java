package chernook.fit.lab5;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import chernook.fit.lab5.adapter.GoodsCardAdapter;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsItemCardViewFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private List<GoodsItem> list = new ArrayList<>();
    private GoodsCardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goods_item_card_view, container, false);
        recyclerView = view.findViewById(R.id.cardContainer);
        updateCardContainer();

        return view;
    }

    void setList(List<GoodsItem> list) {
        this.list = list;
    }

    public void updateCardContainer() {
        adapter = new GoodsCardAdapter(context, list);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}