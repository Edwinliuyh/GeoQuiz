package com.bignerdranch.android.geoquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    Button mTrueButton;
    Button mFalseButton;
    ImageButton mNextButton;
    ImageButton mPrevButton;
    Button mCheatButton;
    TextView mQuestionTextView;
    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX="index";
    private static final String KEY_IsCheater="isCheater";

    TrueFalse[] mAnswerKey = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    boolean []mIsCheater=new boolean[5];

    int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

//      从Bundle中恢复mCurrentIndex
        if (savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater=savedInstanceState.getBooleanArray(KEY_IsCheater);
        }

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updatQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextIndex();
                updatQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPrevButton=(ImageButton)findViewById(R.id.Prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                prevIndex();
                updatQuestion();
            }
        });

        mNextButton=(ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                nextIndex();
                updatQuestion();
            }
        });

        mCheatButton=(Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                启动CheatActivity
                Intent i=new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue=mAnswerKey[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);
                startActivityForResult(i,0);
            }
        });
    }

//    接收子页面回传的intent，知道是否作弊
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data==null){
            return;
        }
        mIsCheater[mCurrentIndex]=data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }

//    保存数据
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_IsCheater,mIsCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    private void prevIndex() {
        if (mCurrentIndex!=0){
            mCurrentIndex=(mCurrentIndex-1)%mAnswerKey.length;
        }else{
            mCurrentIndex=mAnswerKey.length-1;
        }
    }

    private void nextIndex() {
        mCurrentIndex=(mCurrentIndex+1)%mAnswerKey.length;
    }

    private void updatQuestion() {
        int question=mAnswerKey[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressTrue) {
        boolean answerIsTrue = mAnswerKey[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;
        if (mIsCheater[mCurrentIndex]) {
            messageResId = R.string.judgmet_toast;
        } else {
            if (userPressTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


}