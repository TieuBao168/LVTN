#include <Arduino.h>
#include <SoftwareSerial.h>
#include <painlessMesh.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <string.h>


#define Led_OnBoard 2

//mesh network
#define MESH_PREFIX     "IoTLogistic"
#define MESH_PASSWORD   "IoTLogistic2022"
#define MESH_PORT   5555
#define HOSTNAME "MeshNetwork" 

//wifi
#define ssid "P401_5G"
#define pass "cutieubao"


String Protocol, Device, Stt;
float Temp, Humi, Light_Lux;
byte stt_led = LOW;
String data_rx="";
uint32_t nodeID1, nodeID2 = 0;

SoftwareSerial Serial_STM(D2,D3);//D2 = RX -- D3 = TX
Scheduler userScheduler; 
painlessMesh mesh;


void receivedCallback(const uint32_t &from, const String &msg);
void newConnectionCallback(uint32_t nodeId);
void changedConnectionCallback();
void nodeTimeAdjustedCallback(int32_t offset);
void mesh_setup();

void Wifi_connect();

void Blink_led();
void ClearVal();



void setup() {
  // put your setup code here, to run once:
  pinMode(Led_OnBoard, OUTPUT);
  pinMode(D2,INPUT);//RX
  pinMode(D3,OUTPUT);//TX
  Serial.begin(115200);
  Serial_STM.begin(115200);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  mesh_setup();
  //Wifi_connect();
  
  
}

void loop() {
  mesh.update();
  if(Serial_STM.available()>0)
  { 
    data_rx = Serial_STM.readString();
    Serial.println("Data from STM: " + data_rx);  
  }
  delay(1000);
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
    Protocol = "WIF";
    const char* name = doc["Device"];
    Device = String(name);
    const char* TT = doc["Stt"];
    Stt = String(TT);
    Temp = doc["Temp"];
    Humi =doc["Humi"];
    Light_Lux = doc["Light"];
    String x = Protocol + "," + Device + "," + String(Temp) + "," + String(Humi) + "," +String(Light_Lux) + "," + Stt;
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