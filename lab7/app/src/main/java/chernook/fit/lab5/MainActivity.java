package chernook.fit.lab5;

import static chernook.fit.lab5.utils.IntentCodeLiteral.ADD_NEW_GOODS_ITEM;
import static chernook.fit.lab5.utils.IntentCodeLiteral.EDIT_GOODS_ITEM;
import static chernook.fit.lab5.utils.IntentCodeLiteral.GOODS_ITEM_RESULT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chernook.fit.lab5.entity.GoodsItem;
import chernook.fit.lab5.utils.DatabaseGoodsItemsContainer;
import chernook.fit.lab5.utils.GoodsItemsFavoriteSelection;
import chernook.fit.lab5.utils.JsonGoodItemsContainer;
import chernook.fit.lab5.utils.GoodItemsSortType;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;
    private String pattern = "";
    private FragmentTransaction fragmentTransaction;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStateOfFragments(null);
    }

    public void setStateOfFragments(GoodsItem goodsItem) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        Context context = this;
        executor.execute(() -> {
            Cursor cursor = DatabaseGoodsItemsContainer.getGoodItemsListByPatternAsCursor(context);
            handler.post(() -> {
                GoodsItemListFragment listFragment = new GoodsItemListFragment();
                listFragment.setCursor(cursor);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, listFragment).commitAllowingStateLoss();

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    if (goodsItem != null) {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        GoodsItemDetailFragment detailFragment = new GoodsItemDetailFragment();
                        fragmentTransaction.replace(R.id.fragment_detail_view, detailFragment);
                        detailFragment.setSelectedGoodItem(goodsItem);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                }
                else {
                    if (goodsItem != null) {
                        Intent intent = new Intent(context, GoodsInformationActivity.class);
                        intent.putExtra("goods", goodsItem);
                        startActivity(intent);
                    }
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        Boolean favorite = (boolean) v.getTag();
        if (!favorite)
            menu.add(0, v.getId(), 0, "Favored");
        else
            menu.add(0, v.getId(), 0, "Unfavored");

        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        int itemId = item.getItemId();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        Context context = this;

        if (item.getTitle() == "Information") {
//            GoodsItem goodsItem = JsonGoodItemsContainer.getGoodsItemsList().get(itemId);
//            setStateOfFragments(goodsItem);
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                        Intent intent = new Intent(context, GoodsInformationActivity.class);
                        intent.putExtra("goods", goodItem);
                        startActivity(intent);
                    }
                    else {
                        setStateOfFragments(goodItem);
                    }
                });
            });
        }
        else if (item.getTitle() == "Favored") {
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    goodItem.setIsFavorite(true);
                    DatabaseGoodsItemsContainer.updateGoodItem(context, goodItem);
                    setStateOfFragments(null);
                });
            });
        }
        else if (item.getTitle() == "Unfavored") {
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    goodItem.setIsFavorite(false);
                    DatabaseGoodsItemsContainer.updateGoodItem(context, goodItem);
                    setStateOfFragments(null);
                });
            });
        }
        else if (item.getTitle() == "Edit") {
//            GoodsItem goodItem = JsonGoodItemsContainer.getGoodsItemsList().get(itemId);
//            Intent intent = new Intent(this, EditGoodsActivity.class);
//            intent.putExtra("goods", goodItem);
//            startActivityForResult(intent, EDIT_GOODS_ITEM);
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    Intent intent = new Intent(context, EditGoodsActivity.class);
                    intent.putExtra("goods", goodItem);
                    startActivityForResult(intent, EDIT_GOODS_ITEM);
                });
            });
        }
        else if (item.getTitle() == "Remove") {
//            UUID id = JsonGoodItemsContainer.getGoodsItemsList().get(itemId).getId();
//            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
//                JsonGoodItemsContainer.removeGoodItem(id);
//                dialog.dismiss();
//            });
//            dialog.show();
            UUID id = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(this).get(itemId).getId();

            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                executor.execute(() -> {
                    DatabaseGoodsItemsContainer.removeGoodItem(context, id);
                    handler.post(() -> {
                        dialog.dismiss();
                        setStateOfFragments(null);
                    });
                });
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
       // getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        getMenuInflater().inflate(R.menu.new_goods_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQuery(DatabaseGoodsItemsContainer.getPattern(), false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //JsonGoodItemsContainer.setPattern(query);
                DatabaseGoodsItemsContainer.setPattern(query);
                setStateOfFragments(null);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //JsonGoodItemsContainer.setPattern(newText);
                DatabaseGoodsItemsContainer.setPattern(newText);
                setStateOfFragments(null);
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
                //JsonGoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                setStateOfFragments(null);
                return true;
            case R.id.sort_by_name:
                Toast.makeText(this, "Sort by name", Toast.LENGTH_SHORT).show();
                //JsonGoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                setStateOfFragments(null);
                return true;
            case R.id.sort_by_find_date:
                Toast.makeText(this, "Sort by date of find", Toast.LENGTH_SHORT).show();
                //JsonGoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                setStateOfFragments(null);
                return true;
            case R.id.standard_favorites_settings:
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.ALL);
                setStateOfFragments(null);
                return true;
            case R.id.active_favorites_settings:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.FAVORITES);
                setStateOfFragments(null);
                return true;
            case R.id.disactive_favorites_settings:
                Toast.makeText(this, "No favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.NO_FAVORITES);
                setStateOfFragments(null);
                return true;
//            case R.id.load:
//                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
//                JsonGoodItemsContainer.importFromJson(this);
//                setStateOfFragments(null);
//                return true;
//            case R.id.save:
//                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
//                JsonGoodItemsContainer.exportToJson(this);
//                return true;
            case R.id.add:
                Intent intent = new Intent(this, AddGoodsActivity.class);
                startActivityForResult(intent, ADD_NEW_GOODS_ITEM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Context context = this;

        if (requestCode == ADD_NEW_GOODS_ITEM && resultCode == GOODS_ITEM_RESULT) {
            GoodsItem goodItem = (GoodsItem) data.getSerializableExtra("goods");
            //JsonGoodItemsContainer.addGoodItem(goodItem);
            executor.execute(() -> {
                DatabaseGoodsItemsContainer.addGoodItem(context, goodItem);
            });
        }
        else if (requestCode == EDIT_GOODS_ITEM && resultCode == GOODS_ITEM_RESULT) {
            GoodsItem goodItem = (GoodsItem) data.getSerializableExtra("goods");
            //JsonGoodItemsContainer.updateGoodItem(goodItem);
            executor.execute(() -> {
                DatabaseGoodsItemsContainer.updateGoodItem(context, goodItem);
            });
        }
    }
}