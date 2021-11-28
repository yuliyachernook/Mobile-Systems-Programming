package chernook.fit.lab5;

import static chernook.fit.lab5.utils.IntentCodeLiteral.GOODS_ITEM_RESULT;
import static chernook.fit.lab5.utils.IntentCodeLiteral.TAKE_PHOTO;

import android.app.Dialog;
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
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Date;

import chernook.fit.lab5.entity.GoodsItem;

public class EditGoodsActivity extends AppCompatActivity {

    GoodsItem goodsItem;
    Dialog dialog;

    EditText nameField;
    EditText descriptionField;
    EditText findPlaceField;
    ImageView imageField;
    DatePicker findDateField;
    EditText finderField;
    EditText receiptPlaceField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods);

        nameField = findViewById(R.id.good_name);
        descriptionField = findViewById(R.id.good_description);
        findPlaceField = findViewById(R.id.good_find_place);
        imageField = findViewById(R.id.good_image);
        findDateField = findViewById(R.id.good_find_date);
        finderField = findViewById(R.id.good_finder);
        receiptPlaceField = findViewById(R.id.good_receipt_place);

        Bundle arguments = getIntent().getExtras();

        if (getIntent().getExtras() != null) {
            goodsItem = (GoodsItem) arguments.getSerializable("goods");
            nameField.setText(goodsItem.getName());
            descriptionField.setText(goodsItem.getDescription());
            findPlaceField.setText(goodsItem.getFindPlace());
            imageField.setImageBitmap(goodsItem.getImageAsBitmap());
            Date d = goodsItem.getFindDate();
            findDateField.updateDate(d.getYear(), d.getMonth(), d.getDate());
            finderField.setText(goodsItem.getFinder());
            receiptPlaceField.setText(goodsItem.getReceiptPlace());
        }


        dialog = new Dialog(this);
        dialog.setTitle("Edit goods");
        dialog.setContentView(R.layout.dialog_goods_edit);
        dialog.findViewById(R.id.dialog_no).setOnClickListener(view -> {
            dialog.dismiss();
        });
        TextView text = dialog.findViewById(R.id.good_id);

        dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
            dialog.dismiss();

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
        });


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

    public void edit(View button) {
        dialog.show();
    }
}