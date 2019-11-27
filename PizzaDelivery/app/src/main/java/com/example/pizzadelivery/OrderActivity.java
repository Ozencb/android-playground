package com.example.pizzadelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    ImageView ivSize;
    SeekBar sbSize;
    RadioGroup rgCrust;
    RadioButton selectedRb;
    CheckBox cbExtra1, cbExtra2, cbExtra3, cbExtra4, cbExtra5, cbExtra6;
    Button btnIncrementDrink, btnDecrementDrink, btnOrder;
    LinearLayout llSize;
    Spinner spPizzaType, spBorder, spDrinks;
    TextView tvDrinkQuantity;

    String name, lastName, selectedPizza, selectedBorder, selectedDrink, selectedCrust;
    int selectedSize;
    int selectedDrinkQuantity = 1;
    float finalPrice = 0f;

    String[] selectedIngredients = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_order);

        name = getIntent().getExtras().getString("Name");
        lastName = getIntent().getExtras().getString("Lastname");


        ivSize = findViewById(R.id.ivSize);

        sbSize = findViewById(R.id.sbSize);

        rgCrust = findViewById(R.id.rgCrust);

        cbExtra1 = findViewById(R.id.cbExtra1);
        cbExtra2 = findViewById(R.id.cbExtra2);
        cbExtra3 = findViewById(R.id.cbExtra3);
        cbExtra4 = findViewById(R.id.cbExtra4);
        cbExtra5 = findViewById(R.id.cbExtra5);
        cbExtra6 = findViewById(R.id.cbExtra6);

        btnIncrementDrink = findViewById(R.id.btnIncrementDrink);
        btnDecrementDrink = findViewById(R.id.btnDecrementDrink);
        btnOrder = findViewById(R.id.btnOrder);

        llSize = findViewById(R.id.llSize);

        spPizzaType = findViewById(R.id.spPizzaType);
        spBorder = findViewById(R.id.spBorder);
        spDrinks = findViewById(R.id.spDrinks);

        tvDrinkQuantity = findViewById(R.id.tvDrinkQuantity);
        tvDrinkQuantity.setText("" + selectedDrinkQuantity);

        sbSize.setMax(2);
        sbSize.setProgress(0);


        String[] pizzas = {
                "Napoletana", "Sicilian", "Lazio", "Margherita", "Marinara"
        };

        String[] borders = {
                "Klasik", "Peynirli", "Sosisli"
        };

        String[] drinks = {
                "Kola", "Fanta", "Gazoz", "Ayran"
        };


        final CheckBox[] ingredients = {cbExtra1, cbExtra2, cbExtra3, cbExtra4, cbExtra5, cbExtra6};


        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();

                if (progress == 0) {
                    llSize.getLayoutParams().height = (int) convertDpToPixel(50, context);
                    llSize.getLayoutParams().width = (int) convertDpToPixel(50, context);
                    selectedSize = 0;
                    llSize.requestLayout();
                } else if (progress == 1) {
                    llSize.getLayoutParams().height = (int) convertDpToPixel(75, context);
                    llSize.getLayoutParams().width = (int) convertDpToPixel(75, context);
                    selectedSize = 1;
                    llSize.requestLayout();
                } else {
                    llSize.getLayoutParams().height = (int) convertDpToPixel(100, context);
                    llSize.getLayoutParams().width = (int) convertDpToPixel(100, context);
                    selectedSize = 2;
                    llSize.requestLayout();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, pizzas);
        pizzaTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spPizzaType.setSelection(0);
        spPizzaType.setAdapter(pizzaTypeAdapter);


        ArrayAdapter<String> borderAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, borders);
        borderAdapter.setDropDownViewResource(R.layout.spinner_item);
        spBorder.setSelection(0);
        spBorder.setAdapter(borderAdapter);


        ArrayAdapter<String> drinksAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, drinks);
        drinksAdapter.setDropDownViewResource(R.layout.spinner_item);
        spDrinks.setSelection(0);
        spDrinks.setAdapter(drinksAdapter);

        btnIncrementDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDrinkQuantity = selectedDrinkQuantity + 1;

                if (selectedDrinkQuantity >= 9) {
                    selectedDrinkQuantity = 9;
                    Toast.makeText(getApplicationContext(), "Tek seferde 9'dan daha fazla içecek siparişi veremezsiniz.", Toast.LENGTH_SHORT).show();
                }

                tvDrinkQuantity.setText("" + selectedDrinkQuantity);
            }
        });

        btnDecrementDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDrinkQuantity = selectedDrinkQuantity - 1;

                if (selectedDrinkQuantity <= 0) {
                    selectedDrinkQuantity = 0;
                }

                tvDrinkQuantity.setText("" + selectedDrinkQuantity);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPizza = spPizzaType.getSelectedItem().toString();
                selectedBorder = spBorder.getSelectedItem().toString();
                selectedDrink = spDrinks.getSelectedItem().toString();

                selectedRb = findViewById(rgCrust.getCheckedRadioButtonId());
                selectedCrust = selectedRb.getText().toString();

                int i = 0;
                for (CheckBox item : ingredients) {
                    if (item.isChecked()) {
                        selectedIngredients[i] = item.getText().toString();
                        i++;
                    }
                }

                Intent successIntent = new Intent(getApplicationContext(), SuccessActivity.class);

                successIntent.putExtra("Name", name);
                successIntent.putExtra("Lastname", lastName);
                successIntent.putExtra("Pizza", selectedPizza);
                successIntent.putExtra("Size", selectedSize);
                successIntent.putExtra("Border", selectedBorder);
                successIntent.putExtra("Drink", selectedDrink);
                successIntent.putExtra("DrinkQuantity", selectedDrinkQuantity);
                successIntent.putExtra("Crust", selectedCrust);
                successIntent.putExtra("Ingredients", selectedIngredients);
                successIntent.putExtra("Price", finalPrice);
                startActivity(successIntent);
            }
        });

    }
}

