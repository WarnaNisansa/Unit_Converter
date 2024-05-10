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

public class TemperatureActivity extends AppCompatActivity {

    // Declare member variables
    String[] temperature_unit_list = {"Celsius", "Fahrenheit", "Kelvin", "Rankine", "Reaumur"};
    AutoCompleteTextView autoCompleteTextView_from, autoCompleteTextView_to;
    EditText outlinedTextField_temperature_unit;
    TextView temperature_output_text;
    Button convert_btn, save_btn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        // Configure action bar
        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Temperature Converter");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        // Initialize database helper
        dbHelper = new DBHelper(this);

        // Find UI elements by their IDs
        autoCompleteTextView_from = findViewById(R.id.autoCompleteTextView_from);
        autoCompleteTextView_to = findViewById(R.id.autoCompleteTextView_to);
        outlinedTextField_temperature_unit = findViewById(R.id.temperature_units);
        temperature_output_text = findViewById(R.id.textView_temperature_output);
        convert_btn = findViewById(R.id.button_convert_temperature);
        save_btn = findViewById(R.id.button_save_temperature);

        // Setup the adapter for unit dropdown lists
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temperature_unit_list);
        autoCompleteTextView_from.setAdapter(adapter);
        autoCompleteTextView_to.setAdapter(adapter);

        // Add focus change listener to hide the keyboard
        outlinedTextField_temperature_unit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        // Convert button click listener
        convert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String fromUnit = autoCompleteTextView_from.getText().toString();
                    String toUnit = autoCompleteTextView_to.getText().toString();
                    double enteredUnits = Double.parseDouble(outlinedTextField_temperature_unit.getText().toString());

                    TemperatureConverter.Unit fromUnit1 = TemperatureConverter.Unit.fromString(fromUnit);
                    TemperatureConverter.Unit toUnit1 = TemperatureConverter.Unit.fromString(toUnit);

                    TemperatureConverter converter = new TemperatureConverter(fromUnit1, toUnit1);
                    double result = converter.convert(enteredUnits);

                    CardView output_card = findViewById(R.id.cardView_temperature_output);
                    output_card.setVisibility(View.VISIBLE);
                    temperature_output_text.setText(String.valueOf(result) + " " + toUnit);
                } catch (NumberFormatException e) {
                    outlinedTextField_temperature_unit.setError("Please enter some value!");
                    outlinedTextField_temperature_unit.requestFocus();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getBaseContext(), "Select option from dropdown first!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error in conversion!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Save button click listener
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConversion();
            }
        });
    }

    // Method to hide the keyboard when the view loses focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Method to save the conversion result in the database
    private void saveConversion() {
        String fromUnit = autoCompleteTextView_from.getText().toString();
        String toUnit = autoCompleteTextView_to.getText().toString();
        String inputValue = outlinedTextField_temperature_unit.getText().toString();
        String outputValue = temperature_output_text.getText().toString();

        if (fromUnit.isEmpty() || toUnit.isEmpty() || inputValue.isEmpty() || outputValue.isEmpty()) {
            Toast.makeText(this, "Conversion data is incomplete!", Toast.LENGTH_LONG).show();
            return;
        }

        boolean inserted = dbHelper.insertTemperatureConversion(fromUnit, toUnit, inputValue, outputValue);
        if (inserted) {
            Toast.makeText(this, "Conversion saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save conversion!", Toast.LENGTH_SHORT).show();
        }
    }
}
