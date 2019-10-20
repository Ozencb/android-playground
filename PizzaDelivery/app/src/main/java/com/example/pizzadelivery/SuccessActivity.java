package com.example.pizzadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Arrays;

public class SuccessActivity extends AppCompatActivity {

    String name, lastName, selectedPizza, selectedBorder, selectedDrink, selectedCrust, successMessage, resultMessage, ingredients;
    String size = "Küçük";
    int drinkQuantity, selectedSize;
    int ingredientCount = 0;
    float finalPrice = 20;
    String[] selectedIngredients = new String[6];

    TextView tvResult, tvPrice, tvSuccess;

    private static String strArrayToStr(String[] theArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < theArray.length; i++) {
            if(theArray[i] != null){
                String item = theArray[i];
                sb.append(item);
                if (i >= 0) {
                    sb.append(", ");
                }
            }

        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_success);


        name = getIntent().getExtras().getString("Name");
        lastName = getIntent().getExtras().getString("Lastname");
        selectedPizza = getIntent().getExtras().getString("Pizza");
        selectedSize = getIntent().getExtras().getInt("Size");
        selectedBorder = getIntent().getExtras().getString("Border");
        selectedDrink = getIntent().getExtras().getString("Drink");
        selectedCrust = getIntent().getExtras().getString("Crust");
        drinkQuantity = getIntent().getExtras().getInt("DrinkQuantity");
        finalPrice = getIntent().getExtras().getFloat("Price");
        selectedIngredients = getIntent().getExtras().getStringArray("Ingredients");

        ingredients = strArrayToStr(selectedIngredients);

        tvSuccess = findViewById(R.id.tvSuccess);
        tvResult = findViewById(R.id.tvResult);
        tvPrice = findViewById(R.id.tvPrice);

        for(String item : selectedIngredients){
            if(item != null){
                ingredientCount++;
            }
        }

        if(ingredientCount > 2){
            finalPrice += ingredientCount * 1.5;
        }

        if(drinkQuantity >= 0){
            finalPrice += drinkQuantity * 3;
        }

        if(selectedSize == 1){
            finalPrice += 5;
            size = "Orta";
        }

        else if(selectedSize == 2){
            finalPrice += 10;
            size = "Büyük";
        }

        successMessage = "Hoş geldiniz, " + name + " " + lastName;

        resultMessage = "\nSipariş listeniz:\n";
        resultMessage += "\n- " + size + " boy, " + selectedBorder + " kenarlı ve " + selectedCrust.toLowerCase() + " hamurlu " +  selectedPizza;

        if(ingredientCount > 0){
            resultMessage += "\n- Ekstra olarak: " + ingredients;
        }
        resultMessage += "\n- " + drinkQuantity + " adet " + selectedDrink + "\n\n";

        tvSuccess.setText(successMessage);
        tvResult.setText(resultMessage);
        tvPrice.setText("Toplam fiyat\n" + finalPrice);
    }
}
