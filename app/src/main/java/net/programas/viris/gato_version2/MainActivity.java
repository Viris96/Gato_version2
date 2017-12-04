package net.programas.viris.gato_version2;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnHistorial, btnAgain;
    EditText txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9;
    boolean buscador= false;
    EditText[] arreglos;
    int tamanioArreglo = 0, contador2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaComponentes();
    }

    public void inicializaComponentes() {
        this.txt1 = (EditText) findViewById(R.id.activity_main_txt1);
        this.txt2 = (EditText) findViewById(R.id.activity_main_txt2);
        this.txt3 = (EditText) findViewById(R.id.activity_main_txt3);
        this.txt4 = (EditText) findViewById(R.id.activity_main_txt4);
        this.txt5 = (EditText) findViewById(R.id.activity_main_txt5);
        this.txt6 = (EditText) findViewById(R.id.activity_main_txt6);
        this.txt7 = (EditText) findViewById(R.id.activity_main_txt7);
        this.txt8 = (EditText) findViewById(R.id.activity_main_txt8);
        this.txt9 = (EditText) findViewById(R.id.activity_main_txt9);
        this.btnHistorial = (Button) findViewById(R.id.activity_main_btnHistorial);
        this.btnAgain = (Button) findViewById(R.id.activity_main_btnJuegoNuevo);

        this.arreglos = new EditText[]{this.txt1, this.txt2, this.txt3, this.txt4, this.txt5, this.txt6, this.txt7, this.txt8, this.txt9};
        this.tamanioArreglo = this.arreglos.length;

        for (int i = 0; i < this.tamanioArreglo; i++) {
            arreglos[i].setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            if (buscador == false) {
                                marcador(view);
                                txts();
                            }
                            return true;
                        }
                    }
            );
        }
    }

    public void txts() {
        int[] cajas_llenas = new int[9];
        int tamanioCajas = 0;
        for (int i = 0; i < this.tamanioArreglo; i++) {
            if (!(this.arreglos[i].getText().toString().equals(""))) {
                cajas_llenas[i] = i + 1;
                tamanioCajas++;
            }
        }

        if (validuno(cajas_llenas) || validos(cajas_llenas) || valida_diagonal(cajas_llenas)) {
            SharedPreferences prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            if (this.contador2 % 2 != 0) {
                Toast.makeText(this, "Gana X", Toast.LENGTH_SHORT).show();
                int counterX = prefs.getInt("counterX", -1);
                if (counterX != -1) {
                    editor.putInt("counterX", ++counterX);
                    editor.apply();
                } else {
                    editor.putInt("counterX", 1);
                    editor.apply();
                }
            } else {
                Toast.makeText(this, "Gana O", Toast.LENGTH_SHORT).show();
                int counterO = prefs.getInt("counterO", -1);
                if (counterO != -1) {
                    editor.putInt("counterO", ++counterO);
                    editor.apply();
                } else {
                    editor.putInt("counterO", 1);
                    editor.apply();
                }
            }
            this.buscador = true;
        } else if (tamanioCajas == 9){
            Toast.makeText(this, "Empatee!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnHistorial_click(View sender) {
        SharedPreferences prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int counterX = prefs.getInt("counterX", 0);
        int counterO = prefs.getInt("counterO", 0);
        String record = "X: " + counterX + " ganados   |   O: " + counterO + " ganados";
        Toast.makeText(this, record, Toast.LENGTH_LONG).show();
    }

    public void btnJuegoNuevo_click(View sender) {
        for (int i = 0; i < this.tamanioArreglo; i++) {
            this.arreglos[i].setText("");
            this.arreglos[i].setBackgroundResource(R.color.colorAccent);
        }
        this.contador2 = 0;
        this.buscador = false;
    }

    static boolean Buscador(int[] filledBoxes, int numero) {
        for (int i = 0; i < filledBoxes.length; i++) {
            if (filledBoxes[i] == numero) {
                return true;
            }
        }
        return false;
    }

    public boolean validuno(int[] filledBoxes) {
        if (Buscador(filledBoxes, 1)) {
            if (txt1.getText().toString().equals(txt2.getText().toString()) && txt1.getText().toString().equals(txt3.getText().toString())) {
                return true;
            } else if (txt1.getText().toString().equals(txt4.getText().toString()) && txt1.getText().toString().equals(txt7.getText().toString())) {
                return true;
            } else if (txt1.getText().toString().equals(txt5.getText().toString()) && txt1.getText().toString().equals(txt9.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean validos(int[] filledBoxes) {
        if (Buscador(filledBoxes, 5)) {
            if (txt5.getText().toString().equals(txt2.getText().toString()) && txt5.getText().toString().equals(txt8.getText().toString())) {
                return true;
            } else if (txt5.getText().toString().equals(txt4.getText().toString()) && txt5.getText().toString().equals(txt6.getText().toString())) {
                return true;
            } else if (txt5.getText().toString().equals(txt3.getText().toString()) && txt5.getText().toString().equals(txt7.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean valida_diagonal(int[] filledBoxes) {
        if (Buscador(filledBoxes, 9)) {
            if (txt9.getText().toString().equals(txt3.getText().toString()) && txt9.getText().toString().equals(txt6.getText().toString())) {
                return true;
            } else if (txt9.getText().toString().equals(txt7.getText().toString()) && txt9.getText().toString().equals(txt8.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    public void marcador(View sender) {
        EditText txt = (EditText) sender;
        if (txt.getText().toString().equals("")) {
            if (this.contador2 % 2 == 0) {
                txt.setText("X");
            } else {
                txt.setText("O");
            }
            this.contador2++;
        }
    }

}

