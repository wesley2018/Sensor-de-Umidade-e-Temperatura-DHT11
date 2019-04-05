//INCLUSÃO DAS BIBLIOTECAS NECESSÁRIAS PARA A EXECUÇÃO DO CÓDIGO
#include <SPI.h>
#include <Client.h>
#include <Ethernet.h>
#include <Server.h>
#include <Udp.h>
#include <dht.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; // NÃO PRECISA MEXER
byte ip[] = { 192, 168, 0, 100 };  // COLOQUE UMA FAIXA DE IP DISPONÍVEL DO SEU ROTEADOR. EX: 192.168.1.110  **** ISSO VARIA, NO MEU CASO É: 192.168.0.177
byte gateway[] = { 192, 168, 0, 1 }; // MUDE PARA O GATEWAY PADRÃO DO SEU ROTEADOR **** NO MEU CASO É O 192.168.0.1
byte subnet[] = { 255, 255, 255, 0 }; //COLOQUE O ENDEREÇO DA MASCARA DE REDE DO SEU ROTEADOR
EthernetServer server(80); //CASO OCORRA PROBLEMAS COM A PORTA 80, UTILIZE OUTRA (EX:8082,8089)
byte sampledata=50;

#define dht_dpin A2 //PINO DATA DO SENSOR DHT11 LIGADO NA PORTA ANALÓGICA A2
dht DHT; // DECLARAÇÃO DA VARIÁVEL DO TIPO DHT

String readString = String(30); //CRIA UMA STRING CHAMADA "readString"

String umidade; //CRIA UMA STRING CHAMADA "umidade"
String temperatura; //CRIA UMA STRING CHAMADA "temperatura"

void setup(){

  Ethernet.begin(mac, ip, gateway, subnet); // INICIALIZA A CONEXÃO ETHERNET
}
void loop(){

EthernetClient client = server.available(); // CRIA UMA VARIÁVEL CHAMADA client
  if (client) { // SE EXISTE CLIENTE
    while (client.connected()) { //ENQUANTO  EXISTIR CLIENTE CONECTADO
   if (client.available()) { // SE EXISTIR CLIENTE HABILITADO
    char c = client.read(); //CRIA A VARIÁVEL c

    if (readString.length() < 100) // SE O ARRAY FOR MENOR QUE 100
      {
        readString += c; // "readstring" VAI RECEBER OS CARACTERES LIDO
      }
        if (c == '\n') { // SE ENCONTRAR "\n" É O FINAL DO CABEÇALHO DA REQUISIÇÃO HTTP
          if (readString.indexOf("?") <0) //SE ENCONTRAR O CARACTER "?"
          {
          }
          else //SENÃO
        if(readString.indexOf("L=1") >0){ //SE ENCONTRAR O PARÂMETRO "L=1"
          DHT.read11(dht_dpin); // LÊ AS INFORMAÇÕES DO SENSOR
          umidade = String(DHT.humidity); //  STRING "umidade" RECEBE O VALOR MEDIDO
          temperatura = ","+String(DHT.temperature); // STRING "temperatura" RECEBE O VALOR MEDIDO
        }
          client.println("HTTP/1.1 200 OK"); // ESCREVE PARA O CLIENTE A VERSÃO DO HTTP
          client.println("Content-Type: text/html"); // ESCREVE PARA O CLIENTE O TIPO DE CONTEÚDO(texto/html)
          client.println();
          
          client.println(umidade); // RETORNA PARA O CLIENTE O VALOR DA UMIDADE
          client.println(temperatura); // RETORNA PARA O CLIENTE O VALOR DA TEMPERATURA
          readString="";
          client.stop(); // FINALIZA A REQUISIÇÃO HTTP
            }
          }
        }
      }
 }
