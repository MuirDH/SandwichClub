package com.dragonnedevelopment.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonnedevelopment.sandwichclub.model.Sandwich;
import com.dragonnedevelopment.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * SandwichClub Created by Muir on 20/02/2018.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static final String TAG = "DetailActivity";

    private ImageView sandwichImageView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView akaTextView;
    private TextView placeOfOriginTextView;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // assign variables to the correct views in the activity_detail.xml
        sandwichImageView = findViewById(R.id.image_iv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        akaTextView = findViewById(R.id.also_known_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) closeOnError();

        int position = 0;
        if (intent != null) position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        switch (position) {
            case DEFAULT_POSITION:
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        setTitle(sandwich.getMainName());
        populateUI();
    }

    /* if the sandwich data is not available, a toast appears to let the user know that
    something has gone wrong */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Image
        if (!TextUtils.isEmpty(sandwich.getImage())) {

            // get the image of the sandwich (located in the JSON) and load it into the image view
            Picasso.with(this).load(sandwich.getImage()).into(sandwichImageView);
            Log.e(TAG, "Image Url\t" + sandwich.getImage());
        }

        // Description
        if (!TextUtils.isEmpty(sandwich.getDescription())) {

            // get the description of the sandwich and put it in the correct text view
            descriptionTextView.setText(sandwich.getDescription());
        } else
            // if there isn't a description, the tv is updated to reflect that
            descriptionTextView.setText(R.string.default_description_text);

        // Ingredients
        List<String> ingredients = sandwich.getIngredients();
        // check if the array is empty
        if (ingredients.size() > 0) for (int i = 0; i < ingredients.size(); i++) {
            // check if the array item is empty
            if (!TextUtils.isEmpty(ingredients.get(i))) {
                // append list of sandwich ingredients to correct text view
                ingredientsTextView.append(ingredients.get(i));
                Log.e(TAG, ingredients.get(i));
            }
        }
        else
            // if there are no ingredients listed, the tv is updated to reflect that
            ingredientsTextView.setText(R.string.default_ingredients_text);

        // Other Names Known By
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        // check if the Array is empty
        if (alsoKnownAs.size() > 0) for (int i = 0; i < alsoKnownAs.size(); i++) {
            // check if the array item is empty
            if (!TextUtils.isEmpty(alsoKnownAs.get(i))) {
                // append other known names to the text view
                akaTextView.append(alsoKnownAs.get(i));
                Log.e(TAG, alsoKnownAs.get(i));
            }
        }
        else
            // if the sandwich is not known by other names, the tv is updated to reflect this
            akaTextView.setText(R.string.default_aka_text);

        // Place of Origin
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            // set the origin tv according to the text in the JSON
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else
            // if there is no known origin, the tv is updated to reflect this
            placeOfOriginTextView.setText(R.string.default_origin_text);

    }
}
