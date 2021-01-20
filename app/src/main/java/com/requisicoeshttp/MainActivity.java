package com.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MutableCallSite;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperarDados);
        textoResultado = findViewById(R.id.textViewResultados);

        botaoRecuperar.setOnClickListener( v -> {

            MyTask task = new MyTask();
            String moeda = "U";
            String urlApi =  "https://blockchain.info/tobtc?currency=" + moeda + "&value=500";
            String cep = "012345678";
            String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
            task.execute(urlApi);
            //task.execute(urlCep);
        });
    }

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //recuperar dados em bytes que o servidor devolveu
                inputStream = conexao.getInputStream();
                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader( inputStream );
                    //utilizado para ler os caracteres
                BufferedReader reader = new BufferedReader( inputStreamReader );
                buffer = new StringBuffer();
                String linha = "";

               while ( ( linha = reader.readLine( )) != null ) {
                    buffer.append( linha );
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

//            String logradouro = null;
//            String cep = null;
//            String complemento = null;
//            String bairro = null;
//            String localidade = null;
//            String uf = null;

            String objetoValor = null;
            String valorMoeda = null;
            String simbolo = null;

            try {
//                JSONObject jsonObject = new JSONObject(resultado);
//                logradouro = jsonObject.getString("logradouro");
//                cep = jsonObject.getString("cep");
//                bairro = jsonObject.getString("bairro");
//                localidade = jsonObject.getString("localidade");
//                complemento = jsonObject.getString("complemento");
//                uf = jsonObject.getString("uf");

                JSONObject jsonObject = new JSONObject(resultado);
                objetoValor = jsonObject.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(objetoValor);
                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //textoResultado.setText(resultado);
            //textoResultado.setText(logradouro + " / " + cep + " / " + bairro + " / " + localidade);
            textoResultado.setText(simbolo+ " " +valorMoeda);
        }
    }
}