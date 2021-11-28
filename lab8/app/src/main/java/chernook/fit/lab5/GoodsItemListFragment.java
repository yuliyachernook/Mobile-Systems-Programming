package chernook.fit.lab5;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import chernook.fit.lab5.adapter.GoodsCursorAdapter;

public class GoodsItemListFragment extends Fragment {

    private Context context;
    private ListView listView;
    //private GoodsAdapter adapter;
    private GoodsCursorAdapter adapter;
    //private List<GoodsItem> list = new ArrayList<>();
    private Cursor cursor = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_item_list, container, false);
        listView = (ListView)view;

        //updateList(list);
        if (cursor != null)
            updateList(cursor);

        return view;
    }

//    void setList(List<GoodsItem> list) {
//        this.list = list;
//    }
//
//    void updateList(List<GoodsItem> list) {
//        adapter = new GoodsAdapter(context, list);
//        listView.setAdapter(adapter);
//    }


    void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    void updateList(Cursor cursor) {
        adapter = new GoodsCursorAdapter(context, cursor);
        listView.setAdapter(adapter);
    }
}