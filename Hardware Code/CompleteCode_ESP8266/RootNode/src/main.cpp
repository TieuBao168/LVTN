#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
#include <LiquidCrystal_I2C.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>
#include <painlessMesh.h>
#include <string.h>

SoftwareSerial mySerial(D3,D4);
LiquidCrystal_I2C lcd(0x27,16,2); 

SoftwareSerial ss(14,15); // The serial connection to the GPS device  (rx,tx)

//Khai báo giao thức HTTP
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

float latitude , longitude;
int year , month , date, hour , minute , second;
String date_str , time_str , lat_str , lng_str;
int pm;

// Kết nối Wifi
#define ssid "P401"
#define pass "cutieubao"
const char* host = "http://iotlogistics.000webhostapp.com";
#define Led_OnBoard 2
byte stt_led = LOW;
void Wifi_connect();
void Blink_led();

// Đường dẫn file Back-end
const char* pathGetCtr = "http://iotlogistics.000webhostapp.com/App/control/control.json";

// Dùng để điều khiển Relay
int skip=0;
void GetCtr();
void CtrCMD(String payload);
String  payload,Pre_payload="";

//Setup mạng mesh
#define MESH_PREFIX     "IoTLogistic"
#define MESH_PASSWORD   "IoTLogistic2022"
#define MESH_PORT   5555
#define HOSTNAME "MeshNetwork" 
void receivedCallback(const uint32_t &from, const String &msg);
void newConnectionCallback(uint32_t nodeId);
void changedConnectionCallback();
void nodeTimeAdjustedCallback(int32_t offset);
void mesh_setup();
Scheduler userScheduler; 
painlessMesh mesh;

// String apiKeyValue = "iotgateway2021";

String Protocol, Device, Stt;
float Temp, Humi, Light_Lux;
String data_rx="";
uint32_t nodeID1, nodeID2 = 0;




void SendWebPage();
void relayon();
void relayoff();
void text();
void datajson(String ND,String DA, String TT_relay, String AS, String CD);
int TT_relay =1;
int bien = 0;
int Temperature=23;
int Humidity=78;
int anhsang=100;
int caidat=100;
int caidat1=100;
void senddata();
void capnhat();
void xuly();
void lcdd();
void uartt();
uint8_t str[]={};
int Te,Hu;

int last;

String json = "";
// const char* ssid = "Gia dinh la so 1"; 
// const char* password = "yeugiadinh"; 
// const char* host = "192.168.1.8";

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  
  mesh_setup();

  Wifi_connect();
  

}

void loop() {
  // put your main code here, to run repeatedly:

  // Serial.println("Again again");
  // delay(3000);

  GetCtr();
  mesh.update();

  client.print(String("GET https://iotlogistics.000webhostapp.com/connect.php?") + 
                          ("&temperature=") + Temperature +
                          ("&humidity=") + Humidity +
                          " HTTP/1.1\r\n" +
                 "Host: " + host + "\r\n" +
                 "Connection: close\r\n\r\n");
    unsigned long timeout = millis();
    while (client.available() == 0) {
        if (millis() - timeout > 1000) {
            Serial.println(">>> Client Timeout !");
            client.stop();
            return;
        }
    }

    // Read all the lines of the reply from server and print them to Serial
    while(client.available()) {
        String line = client.readStringUntil('\r');
        Serial.print(line);
        
    
}
  
  // delay(1000);
}

void receivedCallback(const uint32_t &from, const String &msg)
{
  Serial.printf("Mesh Network: Received from %u msg=%s\n", from, msg.c_str());
  String json = msg.c_str();
  DynamicJsonDocument doc(1024);
  DeserializationError error = deserializeJson(doc, json);
  if (error)
  {
    Serial.print("deserializeJson() failed: ");
    Serial.println(error.c_str());
  }
  else
  {
    const char* name = doc["NodeIDD"];
    Device = String(name);
    Temp = doc["Temp"];
    Humi =doc["Humi"];
    String x = Device + "," + String(Temp) + "," + String(Humi);
    Serial.println("data php : " +x);
  }
  
}
void newConnectionCallback(uint32_t nodeId) {
    Serial.printf("--> startHere: New Connection, nodeId = %u\n", nodeId);
    if(nodeID1 == 0){
      nodeID1 = nodeId;
    }
    else
    {
      nodeID2 = nodeId;
    }

}
void changedConnectionCallback() {
  Serial.printf("Changed connections\n");
}
void nodeTimeAdjustedCallback(int32_t offset) {
    Serial.printf("Adjusted time %u. Offset = %d\n", mesh.getNodeTime(),offset);
}
void mesh_setup()
{
  mesh.setDebugMsgTypes(ERROR | STARTUP | CONNECTION); // set before init() so that you can see startup messages
  // Channel set to 6. Make sure to use the same channel for your mesh and for you other
  // network (STATION_SSID)
  mesh.init(MESH_PREFIX, MESH_PASSWORD, MESH_PORT, WIFI_AP_STA, 6);
  mesh.onReceive(&receivedCallback);
  mesh.stationManual(ssid, pass);
  mesh.setHostname(HOSTNAME);
  mesh.onNewConnection(&newConnectionCallback);
  mesh.onChangedConnections(&changedConnectionCallback);
  mesh.onNodeTimeAdjusted(&nodeTimeAdjustedCallback);
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
    // Blink_led();
    delay(1000);
  }
  digitalWrite(Led_OnBoard, LOW);

  Serial.println("");
  Serial.println("Wifi connected successfull!!!");
  WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
  delay(1000);
}

// void Blink_led()
// { 
//   stt_led = !stt_led;
//   digitalWrite(Led_OnBoard, stt_led);
//   delay(250);
// }

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