package com.bq.zowi.components;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.makerboxdialogs.MakerBox;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.utils.ResourceResolver;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class QuizView extends FrameLayout {
    private Context context;
    private QuizEventListener quizEventListener;
    private NonSwipeableViewPager viewpager;

    public interface QuizEventListener {
        void onError(int i, int i2);

        void onQuizComplete();

        void onSuccess(int i, int i2);
    }

    public QuizView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public QuizView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public QuizView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void configureQuizQuestions(final ArrayList<ProjectViewModel.TestQuestionViewModel> testQuestions) {
        for (ProjectViewModel.TestQuestionViewModel testQuestion : testQuestions) {
            View questionView = LayoutInflater.from(this.context).inflate(R.layout.quiz_question_view, (ViewGroup) null);
            MakerBox makerBox = (MakerBox) questionView.findViewById(R.id.quiz_question_makerbox);
            TextView questionTextView = (TextView) makerBox.findViewById(R.id.quiz_question_title_textview);
            questionTextView.setText(ResourceResolver.getStringByResourceId(testQuestion.questionResourceId, getResources(), this.context.getPackageName()));
            LinearLayout answersLinearLayout = (LinearLayout) makerBox.findViewById(R.id.quiz_question_answers_linearlayout);
            int answerIndex = 0;
            for (ProjectViewModel.TestAnswerViewModel testAnswer : testQuestion.answers) {
                View answerView = LayoutInflater.from(this.context).inflate(R.layout.quiz_answer_row_view, (ViewGroup) null);
                if (answerIndex % 2 == 0) {
                    answerView.setBackgroundColor(getResources().getColor(R.color.maker_box_odd_row_background));
                } else {
                    answerView.setBackgroundColor(getResources().getColor(R.color.maker_box_even_row_background));
                }
                View answerRowView = answerView.findViewById(R.id.quiz_answer_row);
                TextView answerTextView = (TextView) answerView.findViewById(R.id.quiz_answer_row_textview);
                answerTextView.setText(ResourceResolver.getStringByResourceId(testAnswer.answerResourceId, getResources(), this.context.getPackageName()));
                ImageButton answerButton = (ImageButton) answerView.findViewById(R.id.quiz_answer_row_button);
                AnswerRowViewClickListener answerRowViewClickListener = new AnswerRowViewClickListener(testQuestion, testQuestions, testAnswer);
                answerRowView.setOnClickListener(answerRowViewClickListener);
                if (answerButton != null) {
                    answerButton.setOnClickListener(answerRowViewClickListener);
                }
                answerIndex++;
                answersLinearLayout.addView(answerView);
            }
            this.viewpager.addView(questionView);
        }
        PagerAdapter pagerAdapter = new PagerAdapter() { // from class: com.bq.zowi.components.QuizView.1
            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return testQuestions.size();
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object object) {
                return view == ((View) object);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup container, int position) {
                return QuizView.this.viewpager.getChildAt(position);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        };
        this.viewpager.setAdapter(pagerAdapter);
    }

    public void setQuizEventListener(QuizEventListener quizEventListener) {
        this.quizEventListener = quizEventListener;
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.component_quiz_view, (ViewGroup) this, true);
        this.viewpager = (NonSwipeableViewPager) findViewById(R.id.component_quiz_view_pager);
    }

    private class AnswerRowViewClickListener implements View.OnClickListener {
        private final ProjectViewModel.TestAnswerViewModel testAnswer;
        private final ProjectViewModel.TestQuestionViewModel testQuestion;
        private final ArrayList<ProjectViewModel.TestQuestionViewModel> testQuestions;

        public AnswerRowViewClickListener(ProjectViewModel.TestQuestionViewModel testQuestion, ArrayList<ProjectViewModel.TestQuestionViewModel> testQuestions, ProjectViewModel.TestAnswerViewModel testAnswer) {
            this.testQuestion = testQuestion;
            this.testQuestions = testQuestions;
            this.testAnswer = testAnswer;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.testAnswer.isCorrect) {
                QuizView.this.viewpager.setCurrentItem(QuizView.this.viewpager.getCurrentItem() + 1);
                if (QuizView.this.quizEventListener != null) {
                    QuizView.this.quizEventListener.onSuccess(this.testQuestions.indexOf(this.testQuestion) + 1, this.testQuestions.size());
                    if (this.testQuestions.indexOf(this.testQuestion) == this.testQuestions.size() - 1) {
                        QuizView.this.quizEventListener.onQuizComplete();
                        return;
                    }
                    return;
                }
                return;
            }
            if (QuizView.this.quizEventListener != null) {
                QuizView.this.quizEventListener.onError(this.testQuestions.indexOf(this.testQuestion) + 1, this.testQuestions.size());
            }
        }
    }
}
