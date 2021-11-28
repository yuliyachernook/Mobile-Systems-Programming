package chernook.fit.lab5;

import static chernook.fit.lab5.utils.IntentCodeLiteral.GOODS_ITEM_RESULT;
import static chernook.fit.lab5.utils.IntentCodeLiteral.TAKE_PHOTO;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Date;

import chernook.fit.lab5.entity.GoodsItem;


public class AddGoodsActivity extends AppCompatActivity {

    GoodsItem goodsItem = new GoodsItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO: {
                if (data == null)
                    break;
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ImageView imageView = findViewById(R.id.good_image);
                    imageView.setImageBitmap(bitmap);
                    goodsItem.setImageFromBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), TAKE_PHOTO);
        }
    }

    public void add(View view) {
        EditText nameField = findViewById(R.id.good_name);
        EditText descriptionField = findViewById(R.id.good_description);
        EditText findPlaceField = findViewById(R.id.good_find_place);
        //ImageView imageField = findViewById(R.id.good_image);
        DatePicker findDateField = findViewById(R.id.good_find_date);
        EditText finderField = findViewById(R.id.good_finder);
        EditText receiptPlaceField = findViewById(R.id.good_receipt_place);

        goodsItem.setName(nameField.getText().toString());
        goodsItem.setDescription(descriptionField.getText().toString());
        goodsItem.setFindPlace(findPlaceField.getText().toString());
        goodsItem.setFindDate(new Date(findDateField.getYear(),
                findDateField.getMonth(), findDateField.getDayOfMonth()));
        goodsItem.setFinder(finderField.getText().toString());
        goodsItem.setReceiptPlace(receiptPlaceField.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("goods", goodsItem);
        setResult(GOODS_ITEM_RESULT, intent);
        finish();
    }
}