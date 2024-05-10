package com.example.unit_converter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LengthActivity extends AppCompatActivity {

    String[] length_unit_list = {"Inch", "Centimeter", "Meter", "Mile", "Kilometer", "Foot", "Yard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Length Converter");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        AutoCompleteTextView autoCompleteTextView_from = findViewById(R.id.autoCompleteTextView_from);
        AutoCompleteTextView autoCompleteTextView_to = findViewById(R.id.autoCompleteTextView_to);

        EditText outlinedTextField_length_unit = findViewById(R.id.length_units);
        TextView length_output_text = findViewById(R.id.textView_length_output);

        Button convert_btn = findViewById(R.id.button_convert_length);
        Button save_btn = findViewById(R.id.button_save_speed); // Ensure this matches your "Save" button ID

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, length_unit_list);
        autoCompleteTextView_from.setAdapter(adapter);
        autoCompleteTextView_to.setAdapter(adapter);

        outlinedTextField_length_unit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        convert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String fromUnit = autoCompleteTextView_from.getText().toString();
                    String toUnit = autoCompleteTextView_to.getText().toString();

                    double enteredUnits = Double.parseDouble(outlinedTextField_length_unit.getText().toString());

                    LengthConverter.Unit fromUnit1 = LengthConverter.Unit.fromString(fromUnit);
                    LengthConverter.Unit toUnit1 = LengthConverter.Unit.fromString(toUnit);

                    LengthConverter converter = new LengthConverter(fromUnit1, toUnit1);
                    double result = converter.convert(enteredUnits);

                    CardView output_card = findViewById(R.id.cardView_length_output);
                    output_card.setVisibility(View.VISIBLE);
                    length_output_text.setText(String.valueOf(result) + " " + toUnit);
                } catch (NumberFormatException e) {
                    outlinedTextField_length_unit.setError("Please enter some value!");
                    outlinedTextField_length_unit.requestFocus();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getBaseContext(), "Select option from dropdown first!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error in conversion!", Toast.LENGTH_LONG).show();
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = autoCompleteTextView_from.getText().toString();
                String toUnit = autoCompleteTextView_to.getText().toString();
                String input = outlinedTextField_length_unit.getText().toString();
                String result = length_output_text.getText().toString();

                if (fromUnit.isEmpty() || toUnit.isEmpty() || input.isEmpty() || result.isEmpty()) {
                    Toast.makeText(LengthActivity.this, "Make sure you've done a conversion first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveConversionToDatabase(fromUnit, toUnit, input, result);
            }
        });
    }

    private void saveConversionToDatabase(String fromUnit, String toUnit, String input, String result) {
        DBHelper dbHelper = new DBHelper(LengthActivity.this);
        boolean isInserted = dbHelper.insertLengthConversion(fromUnit, toUnit, input, result);

        if (isInserted) {
            Toast.makeText(LengthActivity.this, "Conversion saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LengthActivity.this, "Error saving conversion.", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(AreaActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
