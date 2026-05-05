package com.example.k234111418;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {

    private TextView txtDisplay;
    private double firstValue = Double.NaN;
    private double secondValue;
    private String currentAction;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        txtDisplay = findViewById(R.id.txtDisplay);

        // Thiết lập sự kiện cho các nút số
        setNumericOnClickListener();
        // Thiết lập sự kiện cho các nút phép tính
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            if (txtDisplay.getText().toString().equals("0")) {
                txtDisplay.setText(b.getText());
            } else {
                txtDisplay.append(b.getText());
            }
        };

        int[] numericIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };
        for (int id : numericIds) {
            View view = findViewById(id);
            if (view != null) view.setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener() {
        // Phép tính cơ bản
        setupButton(R.id.btnAdd, v -> compute("+"));
        setupButton(R.id.btnSub, v -> compute("-"));
        setupButton(R.id.btnMul, v -> compute("*"));
        setupButton(R.id.btnDiv, v -> compute("/"));

        // Các nút chức năng đặc biệt
        setupButton(R.id.btnEq, v -> calculate());
        setupButton(R.id.btnC, v -> {
            firstValue = Double.NaN;
            txtDisplay.setText("0");
        });
        setupButton(R.id.btnCE, v -> txtDisplay.setText("0"));
        setupButton(R.id.btnDel, v -> {
            String text = txtDisplay.getText().toString();
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                txtDisplay.setText(text.isEmpty() ? "0" : text);
            }
        });

        // Phép tính khoa học cơ bản
        setupButton(R.id.btnSqrt, v -> {
            double val = Double.parseDouble(txtDisplay.getText().toString());
            txtDisplay.setText(decimalFormat.format(Math.sqrt(val)));
        });
        setupButton(R.id.btnSqr, v -> {
            double val = Double.parseDouble(txtDisplay.getText().toString());
            txtDisplay.setText(decimalFormat.format(val * val));
        });
        setupButton(R.id.btnInv, v -> {
            double val = Double.parseDouble(txtDisplay.getText().toString());
            txtDisplay.setText(decimalFormat.format(1 / val));
        });
        setupButton(R.id.btnPM, v -> {
            double val = Double.parseDouble(txtDisplay.getText().toString());
            txtDisplay.setText(decimalFormat.format(val * -1));
        });
    }

    private void setupButton(int id, View.OnClickListener listener) {
        View view = findViewById(id);
        if (view != null) view.setOnClickListener(listener);
    }

    private void compute(String action) {
        if (!Double.isNaN(firstValue)) {
            calculate();
        } else {
            firstValue = Double.parseDouble(txtDisplay.getText().toString());
        }
        currentAction = action;
        txtDisplay.setText("0");
    }

    private void calculate() {
        if (!Double.isNaN(firstValue)) {
            secondValue = Double.parseDouble(txtDisplay.getText().toString());
            switch (currentAction) {
                case "+": firstValue += secondValue; break;
                case "-": firstValue -= secondValue; break;
                case "*": firstValue *= secondValue; break;
                case "/": firstValue /= secondValue; break;
            }
            txtDisplay.setText(decimalFormat.format(firstValue));
            firstValue = Double.NaN;
            currentAction = null;
        }
    }
}
