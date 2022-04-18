#include <Arduino.h>
#include <HTTPClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <painlessMesh.h>
#include <string.h>

// SoftwareSerial mySerial(D3,D4);
// LiquidCrystal_I2C lcd(0x27,16,2); 

// SoftwareSerial ss(14,15); // The serial connection to the GPS device  (rx,tx)

//Khai báo giao thức HTTP
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

float latitude , longitude;
int year , month , date, hour , minute , second;
String date_str , time_str , lat_str , lng_str;
int pm;

int cnt =0;

// set pin numbers
#define Relay 2    // the number of the pushbutton pin
// #define ledPin  2       // the number of the LED pin

//wifi
#define ssid "Larcade"
#define pass "987654321"
const char* host = "192.168.1.13";

// byte stt_led = LOW;
void Wifi_connect();
void ControlRelay();

void SendtoDB();
void SaveValue();

// Đường dẫn file Back-end
const char* pathGetCtr = "http://luanvanlogistic.highallnight.com/app/control1/control1.json";

// Dùng để điều khiển Relay
void GetCtr();
void CtrMode(String payload);
String  payload,Pre_payload="";

//mesh network
Scheduler userScheduler; 
painlessMesh mesh;
#define MESH_PREFIX     "IoTLogistic"
#define MESH_PASSWORD   "IoTLogistic2022"
#define MESH_PORT   5555
#define HOSTNAME "MeshNetwork" 
void receivedCallback(const uint32_t &from, const String &msg);
void newConnectionCallback(uint32_t nodeId);
void changedConnectionCallback();
void nodeTimeAdjustedCallback(int32_t offset);
void mesh_setup();
//--------------------------------/*
void sendMessage(); // Prototype so PlatformIO doesn't complain
Task taskSendMessage( TASK_SECOND * 1 , TASK_FOREVER, &sendMessage );

void sendMessage(){
  Serial.println();
  Serial.println("Start Sending....");
  String x = "ok";
  // x += mesh.getNodeId();
  mesh.sendBroadcast( x );
  Serial.println(x);
  taskSendMessage.setInterval(TASK_SECOND * 5);
}

//-------------------------------
// String apiKeyValue = "iotgateway2021";

String Protocol, Stt;
float Temperature, Humidity;
int Node;
String data_rx="";
uint32_t nodeID1, nodeID2 = 0;

int NumMode;
float TemperatureAvg, HumidityAvg = 0;
float TempArr[4], HumiArr[4];
String Mode;

// void datajson(String ND,String DA, String TT_relay, String AS, String CD);

// int Temperature=random(30);
// int Humidity=random(100);

uint8_t str[]={};


void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  
  // initialize the pushbutton pin as an input
  pinMode(Relay, OUTPUT);
  // initialize the LED pin as an output
  // pinMode(ledPin, OUTPUT);

  // mesh_setup();
  // Wifi_connect();

}

void loop() {
  // put your main code here, to run repeatedly:

  // Serial.println("Again again");
  // delay(3000);
  
  // GetCtr();
  // mesh.update();
  
  // delay(1000);
}

void SendtoDB(){
  // Serial.println("Nhiet do: "+String(TemperatureAvg)+" || Do am: "+String(HumidityAvg));
    client.print(String("GET http://luanvanlogistic.highallnight.com/connect.php?") + 
                          ("&nhiet_do=") + TemperatureAvg +
                          ("&do_am=") + HumidityAvg +
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
    Node = doc["Node"];
    Temperature = doc["Temperature"];
    Humidity = doc["Humidity"];
    String x = String(Node) + "," + String(Temperature) + "," + String(Humidity);
    Serial.println("data php : " +x);

    SaveValue();

  }
  // SendtoDB();
}

void SaveValue(){
  switch(Node){
    case 2:
      TempArr[0] = Temperature;
      HumiArr[0] = Humidity;
      break;
    case 3:
      TempArr[1] = Temperature;
      HumiArr[1] = Humidity;
      break;
    case 4:
      TempArr[2] = Temperature;
      HumiArr[2] = Humidity;
      break;
    case 5:
      TempArr[3] = Temperature;
      HumiArr[3] = Humidity;
      break;
    default:
      break;
  }
  cnt++;
  if (cnt == 4){
    int a = 0, b = 0;
    float t = 0, h = 0; // Bien tam luu tong nhiet do, do am

    for(int i=0; i<4;i++){
      if(TempArr[i]!=0){
        t += TempArr[i];
        TempArr[i] = 0;
        a++;
      }
      if(HumiArr[i]!=0){
        h += HumiArr[i];
        HumiArr[i] = 0;
        b++;
      }
    } 
    if (a!=0){
      TemperatureAvg = (round((t/a)*100))/100;
    }
    if (b!=0){
      HumidityAvg = (round((h/b)*100))/100;
    }
    SendtoDB();
    cnt = 0;
    TemperatureAvg = 0;
    HumidityAvg = 0;
  }
}

void newConnectionCallback(uint32_t nodeId) {
    Serial.printf("--> startHere: New Connection, nodeId = %u\n", nodeId);
}
void changedConnectionCallback() {
  Serial.printf("Changed connections\n");
}
void nodeTimeAdjustedCallback(int32_t offset) {
    // Serial.printf("Adjusted time %u. Offset = %d\n", mesh.getNodeTime(),offset);
}
void mesh_setup()
{
  mesh.setDebugMsgTypes(ERROR | STARTUP | CONNECTION); // set before init() so that you can see startup messages
  // Channel set to 6. Make sure to use the same channel for your mesh and for you other
  // network (STATION_SSID)
  mesh.init(MESH_PREFIX, MESH_PASSWORD, &userScheduler, MESH_PORT, WIFI_AP_STA, 6);
  mesh.onReceive(&receivedCallback);
  mesh.stationManual(ssid, pass);
  mesh.setHostname(HOSTNAME);
  mesh.onNewConnection(&newConnectionCallback);
  mesh.onChangedConnections(&changedConnectionCallback);
  mesh.onNodeTimeAdjusted(&nodeTimeAdjustedCallback);
  userScheduler.addTask( taskSendMessage );
  taskSendMessage.enable();
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
      // digitalWrite(ledPin, LOW);
    } else if(Mode == "OFF"){
      digitalWrite(Relay, LOW);
      // digitalWrite(ledPin, HIGH);
    } else{
      NumMode = atoi(Mode.c_str());
      if(NumMode < 30){
        digitalWrite(Relay, HIGH);
        // digitalWrite(ledPin, LOW);
      } else{
        digitalWrite(Relay, LOW);
        // digitalWrite(ledPin, HIGH);
      }
    }
}