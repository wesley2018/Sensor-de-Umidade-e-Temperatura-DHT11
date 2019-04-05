/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

package com.example.umidade_temperatura; // NOME DO PACOTE REFERENTE A CLASSE


//IMPORTA��O DAS BIBLIOTECAS NECESS�RIAS PARA EXECU��O DO C�DIGO

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
	
	// DECLARA��O DE VARI�VEIS
	TextView tvUmidade, tvTemperatura;
	EditText et_Ip;
	ImageButton btConectar;
	String L, hostIp = "192.168.0.177";
	Handler mHandler;
	long lastPress;
	
	// M�TODO QUE CRIA A PRIMEIRA TELA DA APLICA��O
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO M�TODO "telaIp"
	}
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	public void telaIp(){
		setContentView(R.layout.tela_ip);  // INICIALIZA A TELA
		et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
		
    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
    	
    	if(btConectar.isPressed()){ // SE O BOT�O FOR PRESSIONADO
    		onClick(btConectar); // EXECUTA A FUN��O REFERENTE AO BOT�O
    	}
    }
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	// M�TODO "telaPrincipal"
	public void telaPrincipal(){
		setContentView(R.layout.activity_umid_temp); // INICIALIZA A TELA
		
		mHandler = new Handler(); // VARI�VEL "mHandler" INICIALIZADA
        mHandler.post(mUpdate); // VARI�VEL "mHandler" CHAMA O M�TODO "mUpdate"
        
        tvUmidade = (TextView) findViewById(R.id.tvUmidade); // ESTANCIA O TEXTVIEW
     	tvTemperatura = (TextView) findViewById(R.id.tvTemperatura); // ESTANCIA O TEXTVIEW
	}
	// M�TODO QUE EXECUTA A ATUALIZA��O DO TEXTVIEW COM AS INFORMA��ES RECEBIDAS DO ARDUINO
	private Runnable mUpdate = new Runnable() {
    	public void run() {
    		arduinoStatus("http://"+hostIp+"/?L=1"); // CHAMA O M�TODO "arduinoStatus" E PASSA O PAR�METRO ENTRE "PAR�NTESES"
    		mHandler.postDelayed(this, 2000); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMA��O (2 SEGUNDOS)
    	}
    };
 // M�TODO "arduinoStatus"
    public void arduinoStatus(String urlArduino){
		
		String urlHost = urlArduino; // CRIA UMA STRING
		String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
		
		//INICIO DO TRY CATCH
		try{
			respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
			String resposta = respostaRetornada.toString(); // STRING "resposta"
			resposta = resposta.replaceAll("\\s+", "");
			
			String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"    
			
				tvUmidade.setText(b[0]+"%"); // O TEXTVIEW RECEBE O VALOR RETORNADO PELO ARDUINO NA POSI��O 1(umidade) DO VETOR
				tvTemperatura.setText(b[1]+"�"); // O TEXTVIEW RECEBE O VALOR RETORNADO PELO ARDUINO NA POSI��O 2(temperatura) DO VETOR			
		}
		catch(Exception erro){  // FUN��O DE EXIBI��O DO ERRO
		}// FIM DO TRY CATCH
	}
 // M�TODO QUE VERIFICA O BOT�O DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICA��O SE PRESSIONADO 2 VEZES SEGUIDAS
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
	public void onClick(View bt) { // M�TODO QUE GERENCIA OS CLICK'S NOS BOT�ES
		if(bt == btConectar){  // SE BOT�O CLICKADO
			if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
				Toast.makeText(getApplicationContext(), // FUN��O TOAST
				"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
			}else{ // SEN�O 
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
			InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
		    Context.INPUT_METHOD_SERVICE);
		    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
			telaPrincipal(); // FAZ A CHAMADA DO M�TODO "telaPrincipal"
			}	
		}			
	}
}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/