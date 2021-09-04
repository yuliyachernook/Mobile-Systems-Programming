package chernook.fit.bstu.by;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import chernook.fit.bstu.by.text.TextFunction;

public class MainActivity extends AppCompatActivity {
    //TODO add a new method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int count = 0; count < 10; count++) {
            Log.d("MainActivity", " counter =" + count);
        }
        displayText();

    }

    private void displayText() {
        TextFunction tf = new TextFunction();
        TextView nt = findViewById(R.id.newtest);
        nt.setText(tf.getValue());
    }
}