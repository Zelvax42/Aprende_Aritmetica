package com.emilioserna.aprendearitmtica;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SumFragment extends Fragment {

    private int indexAnswer = 0;
    private int indexNum1 = 0;
    private int indexNum2 = 1;
    private int result;             // this is the CORRECT result
    private int answer = 0;         // this is the GIVEN answer from the user
    private Button mBackButton;
    private Button mAcceptButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operation, container, false);

        TextView num1 = (TextView) v.findViewById(R.id.num_1_text);
        TextView num2 = (TextView) v.findViewById(R.id.num_2_text);
        TextView answerText = (TextView) v.findViewById(R.id.answer_edit);

        // Set grade
        MainFragment.setText(v, R.id.grade_text, getResources().getString(R.string.grade) + " " + MainFragment.grade);

        // Set Title
        MainFragment.setText(v, R.id.title_text, R.string.option_sum);

        // Set Operator
        MainFragment.setText(v, R.id.operator_text, "+");

        // Randomize numbers
        MainFragment.setRandomizedText(v, indexAnswer, indexNum1, indexNum2);

        mBackButton = (Button) v.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mAcceptButton = (Button) v.findViewById(R.id.accept_button);
        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // It is answered, whether it's correct or incorrect
                MainFragment.answered[indexAnswer] = true;

                int valueNum1 = Integer.valueOf(num1.getText().toString());
                int valueNum2 = Integer.valueOf(num2.getText().toString());

                if (answerText.length() > 0) {
                    answer = Integer.valueOf(answerText.getText().toString());
                    result = MainFragment.nums[indexNum1] + MainFragment.nums[indexNum2];
                    if (result == answer) {
                        MainFragment.grade += 10;
                        Toast.makeText(MainActivity.context, R.string.correct_answer, Toast.LENGTH_SHORT).show();

                        // Set grade
                        MainFragment.setText(v, R.id.grade_text, getResources().getString(R.string.grade) + " " + MainFragment.grade);
                    } else {
                        Toast.makeText(MainActivity.context, R.string.incorrect_answer, Toast.LENGTH_SHORT).show();
                    }

                    // Set correct answer
                    MainFragment.setText(v, R.id.answer_text, String.valueOf(result));

                    //Randomize numbers
                    MainFragment.setRandomizedText(v, indexAnswer, indexNum1, indexNum2);

                } else {
                    Toast.makeText(MainActivity.context, R.string.answer_required, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
