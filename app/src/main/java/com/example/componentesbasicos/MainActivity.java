package com.example.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText tiPrincipioAtivo;
    private EditText etPosologia;
    private EditText etPeso;
    private EditText etconcen;
    private RadioButton rbPassaro;
    private RadioButton rbNPassaro;
    private RadioButton rbReptil;
    private RadioButton rbMaMetaAlto;
    private RadioButton rbMaMetabaixo;
    private RadioButton rbKg;
    private RadioButton rbg;
    private RadioGroup rg_grupo;
    private RadioGroup rge_g1;
    private RadioGroup rge_g2;
    private EditText etNomeCom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tiPrincipioAtivo = findViewById(R.id.et_PrincipioAtivo);
        etPosologia = findViewById(R.id.et_Posologia);
        etPeso = findViewById(R.id.et_peso);
        etNomeCom = findViewById(R.id.et_NomeCom);
        etconcen = findViewById(R.id.et_concentra);
        rbPassaro = findViewById(R.id.rb_Passaro);
        rbNPassaro = findViewById(R.id.rb_NPassaro);
        rbReptil = findViewById(R.id.rb_Reptil);
        rbMaMetaAlto = findViewById(R.id.rb_MaMetaAlto);
        rbMaMetabaixo = findViewById(R.id.rb_MaMetaBaixo);
        rbKg = findViewById(R.id.rb_kg);
        rbg = findViewById(R.id.rb_g);
        rg_grupo = findViewById(R.id.rg_Grupo);
        rge_g1 = findViewById(R.id.rg_g1);
        rge_g2 = findViewById(R.id.rg_g2);

        radioGrupConfig();

    }
    public void radioGrupConfig ( ){

        rge_g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_Passaro ||checkedId == R.id.rb_NPassaro ||checkedId == R.id.rb_Reptil ){
                    rge_g2.clearCheck();
                    rge_g1.check(checkedId);
               }
            }
        });
        rge_g2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_MaMetaAlto ||checkedId == R.id.rb_MaMetaBaixo){
                    rge_g1.clearCheck();
                    rge_g2.check(checkedId);
                }
            }
    });
    } // gambis pra melhorar o layout dos radiogroup, deve ter um jeito mais facil

    public void calcular(View view) {
        String principioAtivo = tiPrincipioAtivo.getText().toString();
        String nomeComercial = etNomeCom.getText().toString();
        String concentra = etconcen.getText().toString();
        int k = retornaK();
        double peso = retornaPeso();
        //int frequenciaModelo = 12; // puxado do bd que diz de quanto em quanto tempo, no caso a cada 12horas
        String[] bdPrinAtiv = {"doxiciclina", "enrofloxacino", "carprofeno", "Ceftriaxona"}; // fake bd. a ideia é buscar no banco de dados por esses principios ativos
        String[] bdNomeCom = {"doxvet", "enrodog", "carprocat", "dipirona", "doflex"};
        String[] especie = {"cachorro", "gato", "pato", "tartaruga"};
       // int posologia = 30;//fake posologia que vai ser puxada no bd. pode ter varias posologia com o mesmo tipo de medicamento, ex doxiciclina pra cachorro tem posologia 10 e pra pato 30
        System.out.println("test o valor da constante é " + k);
        System.out.println("test o valor do peso é " + peso);
        // tentando tratar entrada pra buscar no bd obs, n deu certo pq so aceita o primeiro verdadeiro,


        if ((principioAtivo.equals("") || principioAtivo == null) && (nomeComercial != "")) {
            String chave;
            chave = nomeComercial;
            for (int num1 = 0; num1 < bdNomeCom.length; num1++) {
                System.out.println("test buscando por " + chave);
                if (chave.equals(bdNomeCom[num1])) { // buscando no array de bdRemedios
                    System.out.println("test importante igual " + bdNomeCom[num1] + " na casa: " + num1);
                    num1 = bdNomeCom.length + 1;
                } else {
                    System.out.println("test importante diferente");
                }
            }
        } else if ((nomeComercial.equals("") || nomeComercial == null) || (principioAtivo != null && nomeComercial != null)) {
            String chave;
            chave = principioAtivo;
            for (int num1 = 0; num1 < bdPrinAtiv.length; num1++) {
                System.out.println("test buscando por " + chave);
                if (chave.equals(bdPrinAtiv[num1])) { // buscando no array de bdRemedios
                    System.out.println("test importante igual " + bdPrinAtiv[num1] + " na casa: " + num1);
                    num1 = bdPrinAtiv.length + 1;
                } else {
                    System.out.println("test importante diferente");
                }
            }
        } else if ((principioAtivo.equals("") || principioAtivo == null) && (nomeComercial.equals("") || nomeComercial == null)) {
            System.out.println("test Preencha Princio ativo ou nome comercial");
        }
        if (nomeComercial != null && (concentra == "" || concentra == null)) { // n sei pq mas concentra tem algum valor
                System.out.println("test Preencha a concentração" + concentra);
        }
        if (k == 0) {
            System.out.println("test Selecione um animal alvo");
            }

        }

        public void calculo (int k , double peso, double posologia, int concentra, int frequenciaModelo){

            double tbmAlvo;
            double tbmModelo;
            double doseTotal;
            double doseTotalMl;
            double posologiaAlvo;
            double frequenciaAlvo;
            double tmeAlvo;
            double tmeModelo;

            tbmModelo = 70*(Math.pow(10,0.75)); //  metabolismo do modelo = cao de 10kg
            tbmAlvo = k*(Math.pow(peso,0.75)); // metabolismo  alvo que quer medicar.
            doseTotal = ((posologia*10)/tbmModelo)*tbmAlvo; // dose total para o animal alvo
            posologiaAlvo = doseTotal/peso; // para saber a posologia do animal alvo caso queira colocar no BD.
            tmeModelo = tbmModelo/10;
            tmeAlvo = tbmAlvo/peso;

            System.out.println("Test o animal alvo vai receber: "+ doseTotal + "mg");
            System.out.println("test posologia para a animal alvo é: "+ posologiaAlvo );

            System.out.println("Test usando a concentração de 20mg/ml");

            frequenciaAlvo= (tmeModelo*frequenciaModelo)/tmeAlvo; // frequencia para medicação do amimal alvo
            doseTotalMl = doseTotal/concentra;
            System.out.println("Test vai receber: " + doseTotalMl + " ml");
            System.out.println("test medicar a cada: "+ frequenciaAlvo );
        }
        public int retornaK (){
        int k;
        if (rbPassaro.isChecked()){
            k = 129;
            return k;
        }else if(rbNPassaro.isChecked()) {
            k = 78;
            return k;
        }else if(rbReptil.isChecked()) {
            k = 10;
            return k;
        }else if(rbMaMetaAlto.isChecked()) {
            k = 70;
            return k;
        }else if (rbMaMetabaixo.isChecked()) {
            k = 49;
            return k;
        }else {
            k = 0;
            return k;
        }
        }
        public double retornaPeso (){
        double peso = Double.parseDouble(etPeso.getText().toString());
        if (rbg.isChecked()){
            peso = peso/1000;
            return peso;
        }else if (rbKg.isChecked()){
            return peso;
        }else {
            System.out.println("test Selecione um tipo de peso"); // precisa selecionar algo, não sei tratar.
            return peso;
        }
        }
}
