/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

package com.example.umidade_temperatura; // NOME DO PACOTE REFERENTE A CLASSE


//IMPORTAÇÃO DAS BIBLIOTECAS NECESSÁRIAS PARA EXECUÇÃO DO CÓDIGO

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UmidTempActivity extends ActionBarActivity implements OnClickListener {
	
	// DECLARAÇÃO DE VARIÁVEIS
	TextView tvUmidade, tvTemperatura;
	EditText et_Ip;
	ImageButton btConectar;
	String L, hostIp = "192.168.0.177";
	Handler mHandler;
	long lastPress;
	
	// MÉTODO QUE CRIA A PRIMEIRA TELA DA APLICAÇÃO
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO MÉTODO "telaIp"
	}
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	public void telaIp(){
		setContentView(R.layout.tela_ip);  // INICIALIZA A TELA
		et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
		
    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOTÃO
    	
    	if(btConectar.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
    		onClick(btConectar); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
    	}
    }
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	// MÉTODO "telaPrincipal"
	public void telaPrincipal(){
		setContentView(R.layout.activity_umid_temp); // INICIALIZA A TELA
		
		mHandler = new Handler(); // VARIÁVEL "mHandler" INICIALIZADA
        mHandler.post(mUpdate); // VARIÁVEL "mHandler" CHAMA O MÉTODO "mUpdate"
        
        tvUmidade = (TextView) findViewById(R.id.tvUmidade); // ESTANCIA O TEXTVIEW
     	tvTemperatura = (TextView) findViewById(R.id.tvTemperatura); // ESTANCIA O TEXTVIEW
	}
	// MÉTODO QUE EXECUTA A ATUALIZAÇÃO DO TEXTVIEW COM AS INFORMAÇÕES RECEBIDAS DO ARDUINO
	private Runnable mUpdate = new Runnable() {
    	public void run() {
    		arduinoStatus("http://"+hostIp+"/?L=1"); // CHAMA O MÉTODO "arduinoStatus" E PASSA O PARÂMETRO ENTRE "PARÊNTESES"
    		mHandler.postDelayed(this, 2000); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMAÇÃO (2 SEGUNDOS)
    	}
    };
 // MÉTODO "arduinoStatus"
    public void arduinoStatus(String urlArduino){
		
		String urlHost = urlArduino; // CRIA UMA STRING
		String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
		
		//INICIO DO TRY CATCH
		try{
			respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
			String resposta = respostaRetornada.toString(); // STRING "resposta"
			resposta = resposta.replaceAll("\\s+", "");
			
			String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"    
			
				tvUmidade.setText(b[0]+"%"); // O TEXTVIEW RECEBE O VALOR RETORNADO PELO ARDUINO NA POSIÇÃO 1(umidade) DO VETOR
				tvTemperatura.setText(b[1]+"º"); // O TEXTVIEW RECEBE O VALOR RETORNADO PELO ARDUINO NA POSIÇÃO 2(temperatura) DO VETOR			
		}
		catch(Exception erro){  // FUNÇÃO DE EXIBIÇÃO DO ERRO
		}// FIM DO TRY CATCH
	}
 // MÉTODO QUE VERIFICA O BOTÃO DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICAÇÃO SE PRESSIONADO 2 VEZES SEGUIDAS
    public void onBackPressed() {/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	    long currentTime = System.currentTimeMillis();
	    if(currentTime - lastPress > 5000){
	        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
	        lastPress = currentTime;
	        
	    }else{
	        super.onBackPressed();
	        android.os.Process.killProcess(android.os.Process.myPid());
	    }/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	}
	@Override
	public void onClick(View bt) { // MÉTODO QUE GERENCIA OS CLICK'S NOS BOTÕES
		if(bt == btConectar){  // SE BOTÃO CLICKADO
			if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
				Toast.makeText(getApplicationContext(), // FUNÇÃO TOAST
				"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
			}else{ // SENÃO 
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
			InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
		    Context.INPUT_METHOD_SERVICE);
		    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
			telaPrincipal(); // FAZ A CHAMADA DO MÉTODO "telaPrincipal"
			}	
		}			
	}
}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/