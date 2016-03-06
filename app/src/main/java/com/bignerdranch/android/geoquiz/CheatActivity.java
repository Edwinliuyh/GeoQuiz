package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dell on 2016/3/6.
 */
public class CheatActivity extends Activity{

    public static final String EXTRA_ANSWER_IS_TRUE=
            "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN=
            "com.bignerdranch.android.geoquiz.answer_shown";
    public static final String KEY_IS_ANSWER_SHOWN=
            "com.bignerdranch.android.geoquiz.is_answer_shown";
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerTextView;
    private boolean isAnswerShown;

    private void setAnswerShownResut(boolean isAnswerShown){
        Intent data= new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        isAnswerShown=false;

//        从Bundle中恢复mCurrentIndex
        if (savedInstanceState!=null){
            isAnswerShown=savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN,false);
        }

//      接收主页面intent
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView=(TextView)findViewById(R.id.answerTextView);
        mShowAnswer=(Button)findViewById(R.id.showAnswer);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                isAnswerShown=true;
                setAnswerShownResut(isAnswerShown);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_ANSWER_SHOWN,isAnswerShown);
    }
}
