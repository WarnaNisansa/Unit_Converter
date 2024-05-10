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

public class MassActivity extends AppCompatActivity {

    String[] mass_unit_list = {"Tonne", "Kilogram", "Gram", "Milligram", "Pound", "Ounce"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Mass Converter");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        AutoCompleteTextView autoCompleteTextView_from = findViewById(R.id.autoCompleteTextView_from);
        AutoCompleteTextView autoCompleteTextView_to = findViewById(R.id.autoCompleteTextView_to);

        EditText outlinedTextField_mass_unit = findViewById(R.id.mass_units);
        TextView mass_output_text = findViewById(R.id.textView_mass_output);

        Button convert_btn = findViewById(R.id.button_convert_mass);
        Button save_btn = findViewById(R.id.button_save_mass);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mass_unit_list);

        autoCompleteTextView_from.setAdapter(adapter);
        autoCompleteTextView_to.setAdapter(adapter);

        outlinedTextField_mass_unit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                    double enteredUnits = Double.parseDouble(outlinedTextField_mass_unit.getText().toString());

                    MassConverter.Unit fromUnit1 = MassConverter.Unit.fromString(fromUnit);
                    MassConverter.Unit toUnit1 = MassConverter.Unit.fromString(toUnit);

                    MassConverter converter = new MassConverter(fromUnit1, toUnit1);
                    double result = converter.convert(enteredUnits);

                    CardView output_card = findViewById(R.id.cardView_mass_output);
                    output_card.setVisibility(View.VISIBLE);
                    mass_output_text.setText(String.valueOf(result) + " " + toUnit);
                } catch (NumberFormatException e) {
                    outlinedTextField_mass_unit.setError("Please enter some value!");
                    outlinedTextField_mass_unit.requestFocus();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getBaseContext(), "Select option from dropdown first!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error in conversion!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Add the event handler for the save button
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = autoCompleteTextView_from.getText().toString();
                String toUnit = autoCompleteTextView_to.getText().toString();
                String input = outlinedTextField_mass_unit.getText().toString();
                String result = mass_output_text.getText().toString();

                if (fromUnit.isEmpty() || toUnit.isEmpty() || input.isEmpty() || result.isEmpty()) {
                    Toast.makeText(MassActivity.this, "Make sure you've done a conversion first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the conversion result to the database (add your SQLite implementation here)
                saveConversionToDatabase(fromUnit, toUnit, input, result);
            }
        });
    }

    // Placeholder method for database implementation
    private void saveConversionToDatabase(String fromUnit, String toUnit, String input, String result) {
        // Implement your database save functionality here
        Toast.makeText(MassActivity.this, "Conversion saved!", Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(AreaActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
