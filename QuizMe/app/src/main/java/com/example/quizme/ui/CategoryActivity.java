package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.quizme.R;

public class CategoryActivity extends AppCompatActivity {

    private Button mStartGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Spinner spinner = (Spinner) findViewById(R.id.categories);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String category = spinner.getSelectedItem().toString(); // TODO laga, virkar ekki
        System.out.println("VALINN CATEGORY: " + category);

        mStartGameButton = (Button) findViewById(R.id.startgame_button);

        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO byrjar nyjan leik
                Intent newGameIntent = new Intent(CategoryActivity.this, DifficultyActivity.class);
                newGameIntent.putExtra("Category", category);
                startActivity(newGameIntent);
            }
        });
    }
}
