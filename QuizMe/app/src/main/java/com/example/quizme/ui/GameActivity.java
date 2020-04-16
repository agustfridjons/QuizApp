package com.example.quizme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.quizMe.GameResultsDatabaseHelper;
import com.example.quizme.quizMe.Question;
import com.example.quizme.quizMe.QuestionDatabaseHelper;
import com.example.quizme.quizMe.SessionManager;
import com.example.quizme.quizMe.UserDatabaseHelper;
import com.example.quizme.ui.Adapters.EasyModeAdapter;
import com.example.quizme.ui.items.EasyModeItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;

public class GameActivity extends AppCompatActivity {

    QuestionDatabaseHelper db;
    GameResultsDatabaseHelper dbgame;
    UserDatabaseHelper dbuser;

    private RecyclerView.Recycler mQuestionList;
    private RecyclerView mHintListView;
    private EasyModeAdapter mAdapter;
    private Button mButtonOne, mButtonTwo, mButtonThree, mButtonFour, mButtonHard, mButtonStartGame;
    private TextView mQuestion, mQuestionNumber, mPointsCounter;
    private EditText mUserAnswer;
    private String correctAnswer;
    private int numCorrectAnswers = 0;
    private int questionCounter = 1;

    private SessionManager mSession;

    private String mUniqueId = null;

    private RecyclerView.LayoutManager mLayoutManager;


    // Before user finishes looking at questions and answers when in 'Easy mode', easeDone is set to false
    private boolean easyDone = false;

    private ArrayList<EasyModeItem> mHintList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get chosen difficulty from DifficultyActivity
        String difficulty = getIntent().getStringExtra("Difficulty");
        System.out.println("Þetta er difficulty vonandi skil ekki HAHAHA " + difficulty);
        String challengerName = getIntent().getStringExtra("challengerName");
        String gameID = getIntent().getStringExtra("gameID");
        System.out.println("challenger name: "+challengerName + " gameID: "+ gameID);
        // Set layout according to difficulty chosen
        if (difficulty.equals("Easy")) {
            setContentView(R.layout.activity_game_easy);
        } else if (difficulty.equals("Medium") || easyDone) {
            setContentView(R.layout.activity_game_medium);
        } else if (difficulty.equals("Hard")) {
            setContentView(R.layout.activity_game_hard);
        }


        db = new QuestionDatabaseHelper(this);
        dbgame = new GameResultsDatabaseHelper(this);
        dbuser = new UserDatabaseHelper(this);

        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTwo = (Button) findViewById(R.id.button_two);
        mButtonThree = (Button) findViewById(R.id.button_three);
        mButtonFour = (Button) findViewById(R.id.button_four);
        mButtonHard = (Button) findViewById(R.id.button_hard);
        mButtonStartGame = (Button) findViewById(R.id.start_game_button);
        mQuestion = (TextView) findViewById(R.id.question);
        mQuestionNumber = (TextView) findViewById(R.id.question_number);
        mPointsCounter = (TextView) findViewById(R.id.points);
        mUserAnswer = (EditText) findViewById(R.id.userAnswer);

        mHintListView = (RecyclerView) findViewById(R.id.list_recycler_view_hints);

        mSession = new SessionManager(GameActivity.this);
        if (gameID != null) {
            mUniqueId = gameID;
        } else {
            mUniqueId = UUID.randomUUID().toString();
        }


        // Get chosen category from DifficultyActivity (CategoryActivity)
        String category = getIntent().getStringExtra("Category");
        System.out.println("GameAct fær inn: " + category);

        // Get a list of Question objects from the database helper
        Stack<Question> allQuestions = db.getQuestions(category);
        System.out.println("SPURNINGAR: " + allQuestions.size() + " " + allQuestions);
        Stack<Question> questions = getSevenQuestions(allQuestions);

        System.out.println("Lengd "+questions.size());

        //System.out.println("FYRSTA SPURNING ER: " + currentQuestion.getQuestion());
        System.out.println("DIFFICULTY VALINN: " + difficulty);

        // Easy mode, user gets to view questions and answers before starting the game
        if (difficulty.equals("Easy")) {

            // TableLayout table = (TableLayout) findViewById(R.id.question_table);

            // Add all 7 questions and answers to the table layout
            for (int i = 0; i < questions.size(); i++) {
                mHintList.add(new EasyModeItem(questions.get(i).getQuestion(), questions.get(i).getCorrectAnswer()));
                System.out.println("HALLOOOOOOO " + mHintList);

            }

            for (int i = 0; i < questions.size(); i++) {
                System.out.println("FIRST: Question nr. " + i + ":" + questions.get(i).getQuestion());
            }

            // Create a RecyclerView list of CardView items with mHintList data
            mHintListView.setVisibility(View.VISIBLE);
            createRecyclerViewList();

            mButtonStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Buin að skoða spurningar og svör");
                    // TODO byrja leik
                    setContentView(R.layout.activity_game_medium);
                    playMediumGame(category, challengerName, questions, difficulty);
                }
            });
        }


        // Medium mode, user chooses between four different options for each question
        if (difficulty.equals("Medium") || easyDone) {
            //String currentQuestion, String category, String challengerName, Stack<Question> questions)
            playMediumGame(category, challengerName, questions, difficulty);
        }


        // Hard mode, user inputs the answer
        if (difficulty.equals("Hard")) {
            Question currentQuestion = questions.pop();

            mQuestion.setText(currentQuestion.getQuestion());
            correctAnswer = currentQuestion.getCorrectAnswer();

            mPointsCounter.setText(" 0");
            mQuestionNumber.setText(questionCounter + " / 7");

            View.OnClickListener event = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Answer from user
                    String userAnswer = mUserAnswer.getText().toString();

                    if (userAnswer.toLowerCase().equals(correctAnswer.toLowerCase())) {
                        Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                        numCorrectAnswers++;
                        mPointsCounter.setText(" " + numCorrectAnswers);
                        dbgame.addGameResults(mQuestion.getText().toString(), correctAnswer, category, difficulty,true,mSession.getSession(),challengerName,mUniqueId);

                    } else {
                        Toast.makeText(GameActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        dbgame.addGameResults(mQuestion.getText().toString(), correctAnswer, category, difficulty, false,mSession.getSession(),challengerName,mUniqueId);

                    }

                    if (questions.isEmpty()) {
                        mQuestionNumber.setText("Questions done");
                        Intent gameResultsIntent = new Intent(GameActivity.this, GameResultsActivity.class);
                        startActivity(gameResultsIntent);
                    } else {
                        mUserAnswer.getText().clear();
                        mQuestionNumber.setText(questionCounter + " / " + 7);
                        Question currentQuestion = questions.pop(); //TODO Gera meira random
                        mQuestion.setText(currentQuestion.getQuestion());
                        correctAnswer = currentQuestion.getCorrectAnswer();
                    }
                }
            };

            mButtonHard.setOnClickListener(event);

        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(GameActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(GameActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

    }

    private void createRecyclerViewList() {
        mHintListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EasyModeAdapter(mHintList);

        mHintListView.setLayoutManager(mLayoutManager);
        mHintListView.setAdapter(mAdapter);
/*
        mAdapter.setOnItemClickListener(new EasyModeAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                System.out.println("HALLOO HEIMUR SIDDIDUUUDUDUDUDU");
            }
        });*/
    }

    private static Stack<Question> getSevenQuestions(Stack<Question> allQuestions) {
        Stack<Question> sevenQuestions = new Stack<Question>();
        int nrOfQuestions = allQuestions.size();

        for (int i = 0; i < 7; i++) {
            Random r = new Random();
            int randInt = r.nextInt(nrOfQuestions);
            nrOfQuestions--;

            sevenQuestions.push(allQuestions.remove(randInt));
        }

        return sevenQuestions;
    }

    private static String[] randomizeAnswers(String correctAnswer, String[] wrongAnswers) {
        String[] Answers = {correctAnswer, wrongAnswers[0], wrongAnswers[1], wrongAnswers[2]};
        int i = 0;
        int x;
        int y;

        String temp;
        while (i < 10) {
            x = (int)(Math.random() * 4);
            y = (int)(Math.random() * 4);
            if (x != y) {
                temp = Answers[x];
                Answers[x] = Answers[y];
                Answers[y] = temp;
            }
            i++;
        }
        return Answers;
    }

    private void playMediumGame(String category, String challengerName, Stack<Question> questions, String difficulty) {
       /*(*) if (difficulty.equals("Easy")) {
            setContentView(R.layout.activity_game_medium);
        }*/

        Question currentQuestion = questions.pop();

        mQuestion = (TextView) findViewById(R.id.question);
        mQuestionNumber = (TextView) findViewById(R.id.question_number);
        mPointsCounter = (TextView) findViewById(R.id.points);

        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTwo = (Button) findViewById(R.id.button_two);
        mButtonThree = (Button) findViewById(R.id.button_three);
        mButtonFour = (Button) findViewById(R.id.button_four);

        String[] randomAnswers = randomizeAnswers(currentQuestion.getCorrectAnswer(), currentQuestion.getWrongAnswers());

        /*for (int i = 0; i < randomAnswers.length; i++) {
            System.out.println("Answer " + 1 + ": " + randomAnswers[i]);
        }*/

        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question nr. " + i + ":" + questions.get(i).getQuestion());
        }

        System.out.println("STARTING MEDIUM GAME");
        System.out.println("QUESTION OG ANSWER: " + currentQuestion.getQuestion() + currentQuestion.getCorrectAnswer());
        mQuestion.setText(currentQuestion.getQuestion());
        mButtonOne.setText(randomAnswers[0]);
        mButtonTwo.setText(randomAnswers[1]);
        mButtonThree.setText(randomAnswers[2]);
        mButtonFour.setText(randomAnswers[3]);

        correctAnswer = currentQuestion.getCorrectAnswer();

        mPointsCounter.setText(" 0");
        mQuestionNumber.setText(questionCounter + " / 7");

        // Answer from user
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) findViewById(v.getId());
                questionCounter++;
                if (buttonClicked.getText().toString().trim().equals(correctAnswer.trim())) {
                    Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    numCorrectAnswers++;
                    mPointsCounter.setText(" " + numCorrectAnswers);
                    dbgame.addGameResults(mQuestion.getText().toString(), correctAnswer, category, difficulty,true, mSession.getSession(), challengerName, mUniqueId);
                } else {
                    Toast.makeText(GameActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    dbgame.addGameResults(mQuestion.getText().toString(), correctAnswer, category, difficulty,false, mSession.getSession(), challengerName, mUniqueId);

                }

                if (questions.isEmpty()) {
                    if (challengerName != null){
                        dbuser.addChallenge(challengerName,mSession.getSession(), mUniqueId);
                    }
                    Intent gameResultsIntent = new Intent(GameActivity.this, GameResultsActivity.class);
                    startActivity(gameResultsIntent);
                } else {
                    mQuestionNumber.setText(questionCounter + " / " + 7);
                    Question currentQuestion = questions.pop(); //TODO Gera meira random
                    String[] randomAnswers = randomizeAnswers(currentQuestion.getCorrectAnswer(), currentQuestion.getWrongAnswers());
                    mQuestion.setText(currentQuestion.getQuestion());
                    mButtonOne.setText(randomAnswers[0]);
                    mButtonTwo.setText(randomAnswers[1]);
                    mButtonThree.setText(randomAnswers[2]);
                    mButtonFour.setText(randomAnswers[3]);
                    correctAnswer = currentQuestion.getCorrectAnswer();
                }

            }
        };

        mButtonOne.setOnClickListener(event);
        mButtonTwo.setOnClickListener(event);
        mButtonThree.setOnClickListener(event);
        mButtonFour.setOnClickListener(event);
    }

}