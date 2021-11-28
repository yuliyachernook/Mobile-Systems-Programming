package chernook.fit.lab5;

import static chernook.fit.lab5.utils.IntentCodeLiteral.ADD_NEW_GOODS_ITEM;
import static chernook.fit.lab5.utils.IntentCodeLiteral.EDIT_GOODS_ITEM;
import static chernook.fit.lab5.utils.IntentCodeLiteral.GOODS_ITEM_RESULT;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import chernook.fit.lab5.adapter.GoodsItemAdapter;
import chernook.fit.lab5.entity.GoodsItem;
import chernook.fit.lab5.utils.GoodItemsContainer;
import chernook.fit.lab5.utils.GoodItemsSortType;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    String pattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        dialog.setTitle("Remove goods");
        dialog.setContentView(R.layout.dialog_goods_remove);
        dialog.findViewById(R.id.dialog_no).setOnClickListener(view -> {
            dialog.dismiss();
        });
        updateList();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pattern", pattern);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pattern = savedInstanceState.getString("pattern");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Information");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (item.getTitle() == "Information") {
            GoodsItem goodItem = GoodItemsContainer.getGoodsItemsList().get(itemId);
            Intent intent = new Intent(this, GoodsInformationActivity.class);
            intent.putExtra("goods", goodItem);
            startActivity(intent);
        }
        else if (item.getTitle() == "Edit") {
            GoodsItem goodItem = GoodItemsContainer.getGoodsItemsList().get(itemId);
            Intent intent = new Intent(this, EditGoodsActivity.class);
            intent.putExtra("goods", goodItem);
            startActivityForResult(intent, EDIT_GOODS_ITEM);
            updateList();
        }
        else if (item.getTitle() == "Remove") {
            UUID id = GoodItemsContainer.getGoodsItemsList().get(itemId).getId();
            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                GoodItemsContainer.removeGoodItem(id);
                dialog.dismiss();
                updateList();
            });
            dialog.show();
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        getMenuInflater().inflate(R.menu.new_goods_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pattern = query;
                updateList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pattern = newText;
                updateList();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                updateList();
                return true;
            case R.id.sort_by_name:
                Toast.makeText(this, "Sort by name", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                updateList();
                return true;
            case R.id.sort_by_find_date:
                Toast.makeText(this, "Sort by date of find", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                updateList();
                return true;
            case R.id.load:
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.importFromJson(this);
                updateList();
                return true;
            case R.id.save:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.exportToJson(this);
                updateList();
                return true;
            case R.id.add:
                Intent intent = new Intent(this, AddGoodsActivity.class);
                startActivityForResult(intent, ADD_NEW_GOODS_ITEM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void updateList() {
        ListView list = findViewById(R.id.goodsList);
        list.removeAllViewsInLayout();
        GoodsItemAdapter adapter = new GoodsItemAdapter(this, GoodItemsContainer.getGoodsItemsList(pattern));
        list.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NEW_GOODS_ITEM && resultCode == GOODS_ITEM_RESULT) {
            GoodsItem goodItem = (GoodsItem) data.getSerializableExtra("goods");
            GoodItemsContainer.addGoodItem(goodItem);
            updateList();
        }
        else if (requestCode == EDIT_GOODS_ITEM && resultCode == GOODS_ITEM_RESULT) {
            GoodsItem goodItem = (GoodsItem) data.getSerializableExtra("goods");
            GoodItemsContainer.updateGoodItem(goodItem);
            updateList();
        }
    }
}