package chernook.fit.lab5;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chernook.fit.lab5.adapter.GoodsAdapter;
import chernook.fit.lab5.entity.GoodsItem;

public class GoodsItemListFragment extends Fragment {

    private Context context;
    private ListView listView;
    private GoodsAdapter adapter;
    private List<GoodsItem> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_item_list, container, false);
        listView = (ListView)view;

        updateList(list);

        return view;
    }

    void setList(List<GoodsItem> list) {
        this.list = list;
    }

    void updateList(List<GoodsItem> list) {
        adapter = new GoodsAdapter(context, list);
        listView.setAdapter(adapter);
    }
}