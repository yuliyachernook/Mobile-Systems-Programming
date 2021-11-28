package chernook.fit.lab5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import chernook.fit.lab5.entity.GoodsItem;

public class GoodsInformationActivity extends AppCompatActivity {

    GoodsItem goodsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_information);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        Bundle arguments = getIntent().getExtras();

        if(getIntent().getExtras()!=null) {
            goodsItem = (GoodsItem) arguments.getSerializable("goods");

            ((TextView) findViewById(R.id.good_id)).setText(goodsItem.getId().toString());
            ((TextView) findViewById(R.id.good_name)).setText(goodsItem.getName());
            ((TextView) findViewById(R.id.good_description)).setText(goodsItem.getDescription());
            ((TextView) findViewById(R.id.good_find_place)).setText(goodsItem.getFindPlace());
            //((ImageView) findViewById(R.id.good_image)).setImageBitmap(goodsItem.getImageAsBitmap());
            ((TextView) findViewById(R.id.good_find_date)).setText(goodsItem.getFindDateInFormat());
            ((TextView) findViewById(R.id.good_finder)).setText(goodsItem.getFinder());
            ((TextView) findViewById(R.id.good_receipt_place)).setText(goodsItem.getReceiptPlace());

            ImageFragment switchFragment = new ImageFragment();
            switchFragment.setStartImage(goodsItem.getImageAsBitmap());
            getSupportFragmentManager().beginTransaction().add(R.id.good_image_fragment, switchFragment).commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}