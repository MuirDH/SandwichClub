package com.dragonnedevelopment.sandwichclub.utils;

/*
 * SandwichClub Created by Muir on 20/02/2018.
 */

import android.util.Log;

import com.dragonnedevelopment.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {

        final String NAME;
        final String IMAGE;
        final String DESCRIPTION;
        final List<String> INGREDIENTS = new ArrayList<>();
        final List<String> AKA = new ArrayList<>();
        final String ORIGIN;

        try {
            Sandwich sandwich = new Sandwich();
            JSONObject root = new JSONObject(json);

            if (root.has("name")) {
                // check that the name node exists
                JSONObject nameJsonObject = root.getJSONObject("name");

                // get main name for sandwich
                if (nameJsonObject.has("mainName")) {
                    // check that the mainname node exists
                    NAME = nameJsonObject.optString("mainName");
                    sandwich.setMainName(NAME);
                }

                //Image
                if (root.has("image")) {
                    // check that the image node exists
                    IMAGE = root.optString("image");
                    Log.e(TAG, IMAGE);
                    sandwich.setImage(IMAGE);
                }

                // Description
                if (root.has("description")) {
                    // check that the description node exists
                    DESCRIPTION = root.optString("description");
                    Log.e(TAG, DESCRIPTION);
                    sandwich.setDescription(DESCRIPTION);
                }

                // Ingredients
                if (root.has("ingredients")) {
                    // check that the ingredients node exists
                    JSONArray ingredientsJsonArray = root.optJSONArray("ingredients");

                    // convert JsonArray of INGREDIENTS to List of String for INGREDIENTS
                    for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                        INGREDIENTS.add(ingredientsJsonArray.optString(i) + "\n");
                        Log.e(TAG, INGREDIENTS.get(i));
                    }
                    sandwich.setIngredients(INGREDIENTS);
                }

                // AKA
                if (nameJsonObject.has("alsoKnownAs")) {
                    // check that the alsoKnownAs node exists
                    JSONArray akaJsonArray = nameJsonObject.optJSONArray("alsoKnownAs");

                    // convert JsonArray of AKA to List of Strings for AKA
                    for (int i = 0; i < akaJsonArray.length(); i++) {
                        AKA.add(akaJsonArray.optString(i) + "\n");
                        Log.e(TAG, AKA.get(i));
                    }
                    sandwich.setAlsoKnownAs(AKA);
                }

                // Origin
                if (root.has("placeOfOrigin")) {
                    // check that the placeOfOrigin node exists
                    ORIGIN = root.optString("placeOfOrigin");
                    Log.e(TAG, ORIGIN);
                    sandwich.setPlaceOfOrigin(ORIGIN);
                }

                return sandwich;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error in parsing JSON data");
            Log.e(TAG, "Error\t" + e.getMessage());
            return null;
        }

    }
}