package com.example.quizme.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizme.R;

public class CategoryActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String[] ALL_CATEGORIES = {
            "Animals", "Food", "Movies",
            "Politics", "Sports"
    };

    private Button mStartGameButton;
    private String mCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Spinner s = (Spinner) findViewById(R.id.categories);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ALL_CATEGORIES);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        mStartGameButton = (Button) findViewById(R.id.startgame_button);

        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO byrjar nyjan leik
                // TODO ef notandi gerir start game en enginn category valinn
                if (mCategory == null) {
                    Toast.makeText(CategoryActivity.this, "Please choose a category", Toast.LENGTH_SHORT).show();
                } else if (mCategory != null) {
                    Intent newGameIntent = new Intent(CategoryActivity.this, DifficultyActivity.class);
                    newGameIntent.putExtra("Category", mCategory);
                    startActivity(newGameIntent);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        // On selecting a spinner item
        mCategory = parent.getItemAtPosition(position).toString();
        System.out.println("CATEGORY VALINN: " + mCategory);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}
