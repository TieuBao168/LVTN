#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>

#define Led_OnBoard 2

byte stt_led = LOW;
//wifi
#define ssid "P401"
#define pass "cutieubao"

//database
const char* pathGetCtr = "http://iotlogistics.000webhostapp.com/App/control/control.json";

String  payload,Pre_payload="";
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

int skip=0;
void Blink_led();
void Wifi_connect();
void GetCtr();
void CtrCMD(String payload);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  Wifi_connect();
}

void loop() {
  // put your main code here, to run repeatedly:
  GetCtr();
  delay(1000);
}

void Wifi_connect()
{
  delay(1000);
  WiFi.begin(ssid, pass);     //Connect to your WiFi router
  Serial.println("");

  Serial.print("Connecting");
  //Wait for connection
  while (WiFi.status() != WL_CONNECTED) 
  {
    Serial.print(".");
    Blink_led();
  }
  digitalWrite(Led_OnBoard, LOW);

  Serial.println("");
  Serial.println("Wifi connected successfull!!!");
  WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
  delay(1000);
}

void Blink_led()
{ 
  stt_led = !stt_led;
  digitalWrite(Led_OnBoard, stt_led);
  delay(250);
}

void GetCtr()
{
  http.begin(client, pathGetCtr);     //Specify request destination
  
  int httpCode = http.GET();            //Send the request
      //Get the response payload from server
  // Serial.println("ALOALO");
  // Serial.println(httpCode);
 
  if(httpCode == 200){
    payload = http.getString();
    http.end();  //Close connection
      if(payload != Pre_payload)
      {
        Serial.println(payload);    //Print request response payload-chuoi json
        Pre_payload = payload;
        if(skip){
          CtrCMD(payload);
        }
        skip=1;
      }
  }
}
void CtrCMD(String payload)
{
  String DVCtr,DVStt="";

  DynamicJsonDocument cmdJson(1024);
  DeserializationError error = deserializeJson(cmdJson, payload);  
  if (error)
  {
    Serial.print("deserializeJson() failed: ");
    Serial.println(error.c_str());
  }
  else
  {
    const char* name = cmdJson["Auto"];
    DVCtr = String(name);
    const char* TT = cmdJson["Handle"];
    DVStt = String(TT);
    Serial.println(DVCtr+'\t'+DVStt);
  }   


}