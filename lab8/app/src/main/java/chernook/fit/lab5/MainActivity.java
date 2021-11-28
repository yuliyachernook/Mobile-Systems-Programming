package chernook.fit.lab5;

import static chernook.fit.lab5.MainActivity.NewStateReason.*;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chernook.fit.lab5.entity.GoodsItem;
import chernook.fit.lab5.utils.DatabaseGoodsItemsContainer;
import chernook.fit.lab5.utils.GoodsItemsFavoriteSelection;
import chernook.fit.lab5.utils.GoodItemsSortType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private Dialog dialog;
    private FragmentTransaction fragmentTransaction;

    public enum NewStateReason {
        UPDATE_LIST,
        SELECT_INFO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

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
        setStateOfFragments(UPDATE_LIST, null);
    }

    public void setStateOfFragments(NewStateReason newStateReason, GoodsItem goodsItem) {

        if (newStateReason == UPDATE_LIST) {
            List<GoodsItem> list = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(this);
            GoodsItemCardViewFragment cardFragment = new GoodsItemCardViewFragment();
            cardFragment.setList(list);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, cardFragment).commitAllowingStateLoss();
        }
        if (newStateReason == SELECT_INFO) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                GoodsItemDetailFragment detailFragment = new GoodsItemDetailFragment();
                fragmentTransaction.replace(R.id.fragment_detail_view, detailFragment);
                detailFragment.setSelectedGoodItem(goodsItem);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
            else {
                Intent intent = new Intent(this, GoodsInformationActivity.class);
                intent.putExtra("goods", goodsItem);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                        Intent intent = new Intent(context, GoodsInformationActivity.class);
                        intent.putExtra("goods", goodItem);
                        startActivity(intent);
                    }
                    else {
                        setStateOfFragments(SELECT_INFO, goodItem);
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
                    setStateOfFragments(UPDATE_LIST, null);
                });
            });
        }
        else if (item.getTitle() == "Unfavored") {
            executor.execute(() -> {
                GoodsItem goodItem = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(context).get(itemId);
                handler.post(() -> {
                    goodItem.setIsFavorite(false);
                    DatabaseGoodsItemsContainer.updateGoodItem(context, goodItem);
                    setStateOfFragments(UPDATE_LIST, null);
                });
            });
        }
        else if (item.getTitle() == "Edit") {
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
            UUID id = DatabaseGoodsItemsContainer.getGoodItemsListByPattern(this).get(itemId).getId();

            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                executor.execute(() -> {
                    DatabaseGoodsItemsContainer.removeGoodItem(context, id);
                    handler.post(() -> {
                        dialog.dismiss();
                        setStateOfFragments(UPDATE_LIST,null);
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
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        //getMenuInflater().inflate(R.menu.sort_menu, menu);
        //getMenuInflater().inflate(R.menu.new_goods_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQuery(DatabaseGoodsItemsContainer.getPattern(), false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DatabaseGoodsItemsContainer.setPattern(query);
                setStateOfFragments(UPDATE_LIST, null);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DatabaseGoodsItemsContainer.setPattern(newText);
                setStateOfFragments(UPDATE_LIST, null);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        GoodsItemCardViewFragment cardFragment = new GoodsItemCardViewFragment();
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sort_by_name:
                Toast.makeText(this, "Sort by name", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sort_by_find_date:
                Toast.makeText(this, "Sort by date of find", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.standard_favorites_settings:
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.ALL);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.active_favorites_settings:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.FAVORITES);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.disactive_favorites_settings:
                Toast.makeText(this, "No favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodsItemsContainer.setFavoriteSelection(GoodsItemsFavoriteSelection.NO_FAVORITES);
                setStateOfFragments(UPDATE_LIST, null);
                drawer.closeDrawer(GravityCompat.START);
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
            executor.execute(() -> {
                DatabaseGoodsItemsContainer.addGoodItem(context, goodItem);
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
            });
        }
        else if (requestCode == EDIT_GOODS_ITEM && resultCode == GOODS_ITEM_RESULT) {
            GoodsItem goodItem = (GoodsItem) data.getSerializableExtra("goods");
            executor.execute(() -> {
                DatabaseGoodsItemsContainer.updateGoodItem(context, goodItem);
                DatabaseGoodsItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
            });
        }
    }
}