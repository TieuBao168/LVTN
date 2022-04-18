#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
#include <LiquidCrystal_I2C.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>
#include <painlessMesh.h>
#include <string.h>

// SoftwareSerial mySerial(D3,D4);
// LiquidCrystal_I2C lcd(0x27,16,2); 

SoftwareSerial ss(14,15); // The serial connection to the GPS device  (rx,tx)

//Khai báo giao thức HTTP
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

float latitude , longitude;
int year , month , date, hour , minute , second;
String date_str , time_str , lat_str , lng_str;
int pm;

// set pin numbers
#define Relay D2    // the number of the pushbutton pin
#define ledPin  D4       // the number of the LED pin

// Kết nối Wifi
#define ssid "VuPhuong 1"
#define pass "38115591"
const char* host = "192.168.1.13";

// byte stt_led = LOW;
void Wifi_connect();
void SendtoDB();
void ControlRelay();

// Đường dẫn file Back-end
const char* pathGetCtr = "http://luanvanlogistic.highallnight.com/app/control1/control1.json";

// Dùng để điều khiển Relay
void GetCtr();
void CtrMode(String payload);
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

String Protocol, Device, Stt, Mode;
int NumMode;
float Temp, Humi, Light_Lux;
String data_rx="";
uint32_t nodeID1, nodeID2 = 0;


void datajson(String ND,String DA, String TT_relay, String AS, String CD);

int Temperature=random(30);
int Humidity=random(100);

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
  
  // initialize the pushbutton pin as an input
  pinMode(Relay, OUTPUT);
  // initialize the LED pin as an output
  pinMode(ledPin, OUTPUT);

  
  mesh_setup();

  Wifi_connect();
  

}

void loop() {
  // put your main code here, to run repeatedly:

  // Serial.println("Again again");
  // delay(3000);
  
  GetCtr();
  mesh.update();
  SendtoDB();
  
  // delay(1000);
}

void SendtoDB(){
    client.print(String("GET http://luanvanlogistic.highallnight.com/connect.php?") + 
                          ("&nhiet_do=") + Temperature +
                          ("&do_am=") + Humidity +
                          " HTTP/1.1\r\n" +
                 "Host: " + host + "\r\n" +
                 "Connection: close\r\n\r\n");
    unsigned long timeout = millis();
    while (client.available() == 0) {
        if (millis() - timeout > 5000) {
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

  Serial.println("");
  Serial.println("Wifi connected successfull!!!");
  WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
  delay(1000);
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
        // Serial.println(payload);    //Print request response payload-chuoi json
        Pre_payload = payload;
        CtrMode(payload);
      }
  }
}

void CtrMode(String payload)
{
  DynamicJsonDocument cmdJson(1024);
  DeserializationError error = deserializeJson(cmdJson, payload);  
  if (error)
  {
    Serial.print("deserializeJson() failed: ");
    Serial.println(error.c_str());
  }
  else
  {
    const char* TT = cmdJson["Mode"];
    Mode = String(TT);
    Serial.println(Mode);

    ControlRelay();
  }   
  
}

void ControlRelay(){
  if (Mode == "ON" ){
      digitalWrite(Relay, HIGH);
      digitalWrite(ledPin, LOW);
    } else if(Mode == "OFF"){
      digitalWrite(Relay, LOW);
      digitalWrite(ledPin, HIGH);
    } else{
      NumMode = atoi(Mode.c_str());
      if(NumMode < 30){
        digitalWrite(Relay, HIGH);
        digitalWrite(ledPin, LOW);
      } else{
        digitalWrite(Relay, LOW);
        digitalWrite(ledPin, HIGH);
      }
    }
}