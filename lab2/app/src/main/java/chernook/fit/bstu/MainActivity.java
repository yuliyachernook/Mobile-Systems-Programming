package chernook.fit.bstu;

import static java.lang.Integer.getInteger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCalculateCaloriesClicked(View view) {
        EditText ageInput = findViewById(R.id.editAge);
        EditText weightInput = findViewById(R.id.editWeight);
        EditText heightInput = findViewById(R.id.editHeight);
        if (!validateEditText(ageInput, weightInput, heightInput)) {
            return;
        }
        CaloriesCalculator calculator = new CaloriesCalculator();
        calculator.age = Integer.parseInt(ageInput.getText().toString());
        calculator.weight = Double.parseDouble(weightInput.getText().toString());
        calculator.height = Double.parseDouble(heightInput.getText().toString());
        calculator.gender = getGender();
        calculator.activity = getActivity();
        double result = calculator.calculate();
        String resultMessage = getString(R.string.result_message, result);
        TextView textView = (TextView) findViewById(R.id.resultTextView);
        textView.setText(resultMessage);
    }

    private Gender getGender() {
        RadioGroup genderRadio = findViewById(R.id.genderRadioGroup);
        switch (genderRadio.getCheckedRadioButtonId()) {
            case R.id.maleRadioButton: {
                return Gender.MALE;
            }
            case R.id.femaleRadioButton: {
                return Gender.FEMALE;
            }
        }
        return Gender.MALE;
    }

    private PhysicalActivity getActivity() {
        Spinner spinner = findViewById(R.id.activitySpinner);
        switch (spinner.getSelectedItemPosition()) {
            case 0: {
                return PhysicalActivity.SEDENTARY;
            }
            case 1: {
                return PhysicalActivity.MODERATE;
            }
            case 2: {
                return PhysicalActivity.AVERAGE;
            }
            case 3: {
                return PhysicalActivity.INTENSIVE;
            }
            case 4: {
                return PhysicalActivity.ATHLETE;
            }
        }
        return PhysicalActivity.SEDENTARY;
    }

    private boolean validateEditText(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (!validation(editText)) {
                showErrorToast(editText);
                return false;
            }
        }
        return true;
    }

    private boolean validation(EditText editText) {
        String editTextValue = editText.getText().toString();
        if (editTextValue.equals("")) {
            return false;
        }
        switch (editText.getId()) {
            case R.id.editAge: {
                int age = Integer.parseInt(editTextValue);
                if (age < getIntegerFromResources(R.integer.age_min) || age > getIntegerFromResources(R.integer.age_max)) {
                    return false;
                }
                break;
            }
            case R.id.editHeight: {
                int height = Integer.parseInt(editTextValue);
                if (height < getIntegerFromResources(R.integer.height_min) || height > getIntegerFromResources(R.integer.height_max)) {
                    return false;
                }
            }
            case R.id.editWeight: {
                double weight = Double.parseDouble(editTextValue);
                if (weight < getIntegerFromResources(R.integer.weight_min) || weight > getIntegerFromResources(R.integer.weight_max)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showErrorToast(EditText editText) {
        String errorMessage = "";
        switch (editText.getId()) {
            case R.id.editAge: {
                errorMessage = getString(R.string.age_validation_error,
                        getIntegerFromResources(R.integer.age_min), getIntegerFromResources(R.integer.age_max));
                break;
            }
            case R.id.editHeight: {
                errorMessage = getString(R.string.height_validation_error,
                        getIntegerFromResources(R.integer.height_min), getIntegerFromResources(R.integer.height_max));
                break;
            }
            case R.id.editWeight: {
                errorMessage = getString(R.string.weight_validation_error,
                        getIntegerFromResources(R.integer.weight_min), getIntegerFromResources(R.integer.weight_max));
                break;
            }
        }
        showToast(errorMessage);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 400);
        toast.show();
    }

    private int getIntegerFromResources(int values)
    {
        return getResources().getInteger(values);
    }
}