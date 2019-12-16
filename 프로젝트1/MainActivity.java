package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Scanner;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton radioButtonDecimal = (RadioButton)findViewById(R.id.buttonDecimal);
        RadioButton radioButtonHeximal = (RadioButton)findViewById(R.id.buttonHeximal);

        radioButtonDecimal.setOnClickListener(this);
        radioButtonHeximal.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        TextView textResult = (TextView)findViewById(R.id.result);
        int resultSize = textResult.getText().length();

        if( !TextUtils.isEmpty(textResult.getText()) ) {

            switch (view.getId()) {
                case R.id.buttonDecimal: {
                    if(result%1.0 == 0)
                        textResult.setText("="+String.format("%.0f",result));
                    else
                        textResult.setText("="+String.format("%.2f",result));
                    break;
                }
                case R.id.buttonHeximal: {
                    textResult.setText("="+String.format("%X",(int)result));
                    break;
                }
            }
        }
        else if ( TextUtils.isEmpty(textResult.getText()) )
            return;
    }

    public void onButtonClick(View view) {
        RadioButton radioButtonHeximal = (RadioButton)findViewById(R.id.buttonHeximal);
        TextView textExpression = (TextView)findViewById(R.id.expression);
        TextView textResult = (TextView)findViewById(R.id.result);
        Calc calc = new Calc();

        if( !TextUtils.isEmpty(textResult.getText()) ) {
            textResult.setText("");
            textExpression.setText("");
        }

        switch (view.getId()) {
            case R.id.leftParenthesis: {
                textExpression.append("(");
                break;
            }
            case R.id.rightParenthesis: {
                textExpression.append(")");
                break;
            }
            case R.id.num0: {
                textExpression.append("0");
                break;
            }
            case R.id.num1: {
                textExpression.append("1");
                break;
            }
            case R.id.num2: {
                textExpression.append("2");
                break;
            }
            case R.id.num3: {
                textExpression.append("3");
                break;
            }
            case R.id.num4: {
                textExpression.append("4");
                break;
            }
            case R.id.num5: {
                textExpression.append("5");
                break;
            }
            case R.id.num6: {
                textExpression.append("6");
                break;
            }
            case R.id.num7: {
                textExpression.append("7");
                break;
            }
            case R.id.num8: {
                textExpression.append("8");
                break;
            }
            case R.id.num9: {
                textExpression.append("9");
                break;
            }
            case R.id.cancel: {
                if( !TextUtils.isEmpty(textResult.getText()) ) {
                    textExpression.setText("");
                    textResult.setText("");
                }
                else if( !TextUtils.isEmpty(textExpression.getText()) ) {
                    String curStr = textExpression.getText().toString();
                    textExpression.setText(curStr.substring(0,curStr.length()-1));
                }
                break;
            }
            case R.id.plus: {
                textExpression.append("+");
                break;
            }
            case R.id.minus: {
                textExpression.append("-");
                break;
            }
            case R.id.mult: {
                textExpression.append("×");
                break;
            }
            case R.id.div: {
                textExpression.append("/");
                break;
            }case R.id.equal: {
                //result = calculateResult

                result = calc.calculate(textExpression.getText().toString());
                if( radioButtonHeximal.isChecked() )
                    textResult.setText("="+String.format("%X",(int)result));
                else {
                    if (result % 1.0 == 0)
                        textResult.setText("=" + String.format("%.0f", result));
                    else
                        textResult.setText("=" + String.format("%.2f", result));
                }
                break;

                //String[] str = textExpression.getText().toString().split("");
                //textResult.setText(str[1]);
            }
            default: {
                break;
            }
        }
    }

}

class Calc {
    private Stack<Double> operand;
    private Stack<String> operator;
    public Calc() {
        operand = new Stack<Double>();
        operator = new Stack<String>();

    }
    public double calculate(String expression) {

        String[] expChar = expression.split("");
        int i;
        for(i=1; i<expChar.length; i++) {
            if( Character.isDigit(expChar[i].charAt(0)) || expChar[i] == "." ) {
                StringBuilder op = new StringBuilder();
                int j = 0;
                while( Character.isDigit(expChar[i+j].charAt(0)) ) {
                    op.append( expChar[i+j] );

                    j++;
                    if( i+j >= expChar.length ) {
                        j--;
                        break;
                    }
                }

                operand.push( Double.parseDouble(op.toString()) );
                System.out.println("operand stack = "+operand.peek());
            }
            else {
                switch (expChar[i]) {
                    case "+": {
                        if(operator.empty())
                            operator.push("+");
                        else {
                            if (operator.peek().equals("×"))
                                mult();
                            else if (operator.peek().equals("/"))
                                div();
                            operator.push("+");
                        }
                        break;
                    }
                    case "-": {
                        if(operator.empty())
                            operator.push("-");
                        else {
                            if (operator.peek().equals("×"))
                                mult();
                            else if (operator.peek().equals("/"))
                                div();
                            operator.push("-");
                        }
                        break;
                    }
                    case "×": {
                        operator.push("×");
                        break;
                    }
                    case  "/": {
                        operator.push("/");
                        break;
                    }
                    case "(": {
                        operator.push("(");
                        break;
                    }
                    case ")": {
                        operator.push(")");
                        rParenthesis();
                        break;
                    }
                }
            }
        }
        rParenthesis();
        return operand.peek();
    }

    private void plus() {
        double n1 = operand.peek();
        operand.pop();
        double n2 = operand.peek();
        operand.pop();
        operator.pop();
        operand.push(n2+n1);
    }
    private void minus() {
        double n1 = operand.peek();
        operand.pop();
        double n2 = operand.peek();
        operand.pop();
        operator.pop();
        operand.push(n2-n1);
    }
    private void mult() {
        double n1 = operand.peek();
        operand.pop();
        double n2 = operand.peek();
        operand.pop();
        operator.pop();
        operand.push(n2*n1);
    }
    private void div() {
        double n1 = operand.peek();
        operand.pop();
        double n2 = operand.peek();
        operand.pop();
        operator.pop();
        operand.push(n2/n1);
    }

    private void rParenthesis() { //왼쪽 괄호가 나올때까지 혹은 식의 끝까지 계산
        while( !operator.empty() ) {
            if( operator.peek() != "(" ) {
                switch (operator.peek()) {
                    case "+": {
                        plus();
                        break;
                    }
                    case "-": {
                        minus();
                        break;
                    }
                    case "×": {
                        mult();
                        break;
                    }
                    case "/": {
                        div();
                        break;
                    }
                    case ")": {
                        operator.pop();
                        break;
                    }

                }
            }
            else
                operator.pop();
        }

    }

}


