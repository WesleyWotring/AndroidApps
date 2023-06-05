package com.example.TipCalculatorApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
/*
In Class Assignment 02
InClass02
Wesley Wotring and Zachary Hall
 */


public class MainActivity extends AppCompatActivity {



    Button calculate;
    Button clear;
    RadioGroup buttonGroup;
    TextView discountedPriceValue;
    EditText editTextNumberDecimalTicketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear = findViewById(R.id.clearButton);
        calculate = findViewById(R.id.buttonCalculate);
        buttonGroup = findViewById(R.id.buttonGroup);
        discountedPriceValue = findViewById(R.id.textViewDiscountedPriceValue);
        editTextNumberDecimalTicketPrice = findViewById(R.id.editTextNumberDecimalTicketPrice);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = buttonGroup.getCheckedRadioButtonId();
                String value = editTextNumberDecimalTicketPrice.getText().toString();

                if (value.matches("")) {
                    //Toast.makeText(MainActivity.this,getString(R.string.toastMsg) , Toast.LENGTH_LONG).show();
                }
                try {
                    double value2 = Double.parseDouble(value);
                    if (value2 <= 0) {
                        //Toast.makeText(MainActivity.this, getString(R.string.toastMsg), Toast.LENGTH_LONG).show();
                    }

                    if (checkId == R.id.radioButton5) {
                        value2 = value2 * .95;
                        String strDouble = String.format("%.2f", value2);
                        value2 = Double.parseDouble(strDouble);


                    } else if (checkId == R.id.radioButton10) {
                        value2 = value2 * .9;
                        String strDouble = String.format("%.2f", value2);
                        value2 = Double.parseDouble(strDouble);


                    } else if (checkId == R.id.radioButton15) {
                        value2 = value2 * .85;
                        String strDouble = String.format("%.2f", value2);
                        value2 = Double.parseDouble(strDouble);


                    } else if (checkId == R.id.radioButton20) {
                        value2 = value2 * .80;
                        String strDouble = String.format("%.2f", value2);
                        value2 = Double.parseDouble(strDouble);


                    } else if (checkId == R.id.radioButton50) {
                        value2 = value2 * .50;
                        String strDouble = String.format("%.2f", value2);
                        value2 = Double.parseDouble(strDouble);

                    }
                    String valueFinal = String.valueOf(value2);
                    discountedPriceValue.setText(valueFinal);
                } catch (Exception whatever) {
                    //Toast.makeText(MainActivity.this,getString(R.string.toastMsg) , Toast.LENGTH_LONG).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumberDecimalTicketPrice.setText("");
                MainActivity.this.recreate();
                buttonGroup.clearCheck();

            }
        });

        }
    }
