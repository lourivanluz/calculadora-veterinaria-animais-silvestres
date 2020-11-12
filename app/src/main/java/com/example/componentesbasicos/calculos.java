package com.example.componentesbasicos;


public class calculos {


    public String calculo (int Kalvo , double peso, double posologia, int kmodelo){
        double tbmAlvo;
        double tbmModelo;
        double doseTotal;
        double posologiaAlvo;


        tbmModelo = kmodelo*(Math.pow(10,0.75)); //  metabolismo do modelo = cao de 10kg
        tbmAlvo = Kalvo*(Math.pow(peso,0.75)); // metabolismo  alvo que quer medicar.
        doseTotal = ((posologia*10)/tbmModelo)*tbmAlvo; // dose total para o animal alvo
        posologiaAlvo = doseTotal/peso; // para saber a posologia do animal alvo caso queira colocar no BD.

        //antes de converter preciso tratar pra usar so 2 numeros depois do ponto.
        String resultado = "posologia é " + posologiaAlvo;
        return resultado;
    }
    public String calculo (int Kalvo , double peso, double posologia, int kmodelo, int frequencia){

        double tbmAlvo;
        double tbmModelo;
        double doseTotal;
        double posologiaAlvo;
        double frequenciaAlvo;
        double tmeAlvo;
        double tmeModelo;

        tbmModelo = kmodelo*(Math.pow(10,0.75)); //  metabolismo do modelo = cao de 10kg
        tbmAlvo = Kalvo*(Math.pow(peso,0.75)); // metabolismo  alvo que quer medicar.
        doseTotal = ((posologia*10)/tbmModelo)*tbmAlvo; // dose total para o animal alvo
        posologiaAlvo = doseTotal/peso; // para saber a posologia do animal alvo caso queira colocar no BD.
        tmeModelo = tbmModelo/10;
        tmeAlvo = tbmAlvo/peso;
        frequenciaAlvo= (tmeModelo*frequencia)/tmeAlvo;
        //antes de converter preciso tratar pra usar so 2 numeros depois do ponto.
        String resultado = ("Posologia para o alvo é: " + posologiaAlvo + " e a frequencia é " + frequenciaAlvo);
        return resultado;
    }
    public String calculo (int Kalvo , double peso, double posologia, int kmodelo, int frequencia, double concentra){

        double tbmAlvo;
        double tbmModelo;
        double doseTotal;
        double posologiaAlvo;
        double frequenciaAlvo;
        double tmeAlvo;
        double tmeModelo;

        tbmModelo = kmodelo*(Math.pow(10,0.75)); //  metabolismo do modelo = cao de 10kg
        tbmAlvo = Kalvo*(Math.pow(peso,0.75)); // metabolismo  alvo que quer medicar.
        doseTotal = ((posologia*10)/tbmModelo)*tbmAlvo; // dose total para o animal alvo
        posologiaAlvo = doseTotal/peso; // para saber a posologia do animal alvo caso queira colocar no BD.
        tmeModelo = tbmModelo/10;
        tmeAlvo = tbmAlvo/peso;
        frequenciaAlvo= (tmeModelo*frequencia)/tmeAlvo;
        double doseTotalMl = doseTotal/concentra;

        //antes de converter preciso tratar pra usar so 2 numeros depois do ponto.
        String resultado = ("Dose total em é: "+ doseTotalMl +" ml, posologia é " + posologiaAlvo + " e a frequencia é " + frequenciaAlvo);
        return resultado;

    }
    public String calculo (int Kalvo , double peso, double posologia, int kmodelo, double concentra) {

        double tbmAlvo;
        double tbmModelo;
        double doseTotal;
        double posologiaAlvo;

        tbmModelo = kmodelo * (Math.pow(10, 0.75)); //  metabolismo do modelo = cao de 10kg
        tbmAlvo = Kalvo * (Math.pow(peso, 0.75)); // metabolismo  alvo que quer medicar.
        doseTotal = ((posologia * 10) / tbmModelo) * tbmAlvo; // dose total para o animal alvo
        posologiaAlvo = doseTotal / peso; // para saber a posologia do animal alvo caso queira colocar no BD.
        double doseTotalMl = doseTotal / concentra;

        //antes de converter preciso tratar pra usar so 2 numeros depois do ponto.
        String resultado = ("Dose total em é: "+ doseTotalMl +" ml, posologia é " + posologiaAlvo);
        return resultado;
    }
}
