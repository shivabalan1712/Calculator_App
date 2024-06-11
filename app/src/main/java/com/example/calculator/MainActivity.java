package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private final StringBuilder input = new StringBuilder();
    private boolean isLastResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);

        // Set click listeners for digit buttons
        findViewById(R.id.button0).setOnClickListener(onClickListener);
        findViewById(R.id.button1).setOnClickListener(onClickListener);
        findViewById(R.id.button2).setOnClickListener(onClickListener);
        findViewById(R.id.button3).setOnClickListener(onClickListener);
        findViewById(R.id.button4).setOnClickListener(onClickListener);
        findViewById(R.id.button5).setOnClickListener(onClickListener);
        findViewById(R.id.button6).setOnClickListener(onClickListener);
        findViewById(R.id.button7).setOnClickListener(onClickListener);
        findViewById(R.id.button8).setOnClickListener(onClickListener);
        findViewById(R.id.button9).setOnClickListener(onClickListener);

        // Set click listeners for arithmetic operation buttons
        findViewById(R.id.buttonAdd).setOnClickListener(onClickListener);
        findViewById(R.id.buttonSubtract).setOnClickListener(onClickListener);
        findViewById(R.id.buttonMultiply).setOnClickListener(onClickListener);
        findViewById(R.id.buttonDivide).setOnClickListener(onClickListener);

        // Set click listeners for clear and delete buttons
        findViewById(R.id.buttonClear).setOnClickListener(onClickListener);
        findViewById(R.id.buttonDelete).setOnClickListener(onClickListener);

        // Set click listener for equals button
        findViewById(R.id.buttonEqual).setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            switch (buttonText) {
                case "C":
                    input.setLength(0);
                    isLastResult = false;
                    break;
                case "Del":
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                    }
                    break;
                case "=":
                    calculateResult();
                    isLastResult = true;
                    break;
                default:
                    if (isLastResult) {
                        input.setLength(0);
                        isLastResult = false;
                    }
                    input.append(buttonText);
                    break;
            }

            textViewResult.setText(input.toString());
        }
    };

    private void calculateResult() {
        String expression = input.toString();
        try {
            // Evaluate the expression
            double result = evaluateExpression(expression);
            input.setLength(0);
            input.append(result);
        } catch (Exception e) {
            input.setLength(0);
            input.append("Error");
        }
    }

    private double evaluateExpression(String expression) {
        // This is a simple evaluation method, you can use more sophisticated approaches
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        double result = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result /= operand;
                    break;
            }
        }
        return result;
    }
}
