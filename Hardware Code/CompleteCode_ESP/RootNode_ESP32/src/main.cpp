#include <Arduino.h>
#include <HTTPClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <painlessMesh.h>
#include <string.h>
#include <TinyGPS++.h> // library for GPS module
#include <SoftwareSerial.h>

// hw_timer_t* timer = NULL; //khơi tạo timer
// portMUX_TYPE timerMux = portMUX_INITIALIZER_UNLOCKED; 

//Declare HTTP Protocol
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

String apiKeyValue = "IoTLogistic", Vehicle = "2";

TinyGPSPlus gps;  // The TinyGPS++ object
SoftwareSerial ss(3,1); // The serial connection to the GPS device  (Rx,Tx)

float latitude , longitude;
String lat_str = "0.000000", lng_str = "0.000000";
// WiFiServer server(80);

int cnt = 0;
// int firsttime = 0;

float Temperature, Humidity, RSSI;
int Node;
String data_rx="";

int NumMode;
float TemperatureAvg, HumidityAvg = 0;
float TempArr[4], HumiArr[4];
String Mode;

// set pin numbers
#define Relay 2   // the number of the pushbutton pin
// #define ledPin  2       // the number of the LED pin
// Giá trị lần cuối cùng được cập nhật
unsigned long previousMillis = 0;  
const long interval = 1000; // giá trị delay (milliseconds)

//Connect Wifi
#define ssid "Larcade"
#define pass "987654321"
const char* host = "192.168.1.13";

// byte stt_led = LOW;
void Wifi_connect();
void ControlRelay();

// Post data to MySQL
void Post_to_DB(String Table);
void SaveValue();

// Đường dẫn file Back-end
const char* pathGetCtr = "http://luanvanlogistic.highallnight.com/app/control2/control2.json";
const char* LinkWriteData = "http://luanvanlogistic.highallnight.com/app/postdata.php";

// Control Relay
void GetCtr();
void CtrMode(String payload);
String  payload,Pre_payload="";

//Mesh network
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

  String x = "Begin";
  // x += mesh.getNodeId();
  mesh.sendBroadcast(x);
  // Serial.println(x);
  taskSendMessage.setInterval(TASK_SECOND * 10);
}

// hàm xử lý ngắt
// void IRAM_ATTR onTimer() {   
//   portENTER_CRITICAL_ISR(&timerMux); //vào chế độ tránh xung đột

//   // Post_to_DB("location");
//   Serial.println("Interrupt Successfully \n");

//   portEXIT_CRITICAL_ISR(&timerMux); // thoát 
// }

void setup() {
  Serial.begin(115200);
  ss.begin(9600);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  
  //   //khơit tạo timer với chu kì 1us vì thạch anh của ESP chạy 8MHz
  // timer = timerBegin(0, 80, true);
  // //khởi tạo hàm xử lý ngắt ngắt cho Timer
  // timerAttachInterrupt(timer, &onTimer, true);
  // //khởi tạo thời gian ngắt cho timer là 1s (1000000 us)
  // timerAlarmWrite(timer, 5000000, true);
  // //bắt đầu chạy timer
  // timerAlarmEnable(timer);

  // initialize the pushbutton pin as an input
  pinMode(Relay, OUTPUT);
  // initialize the LED pin as an output
  // pinMode(ledPin, OUTPUT);

  mesh_setup();

  Wifi_connect();
  // server.begin();


}

void loop() {
  // put your main code here, to run repeatedly:
  
  GetCtr();
  mesh.update();

  while (ss.available() > 0) //while data is available
    if (gps.encode(ss.read())) //read gps data
    {
      if (gps.location.isValid()) //check whether gps location is valid
      {
        latitude = gps.location.lat();
        lat_str = String(latitude , 6); // latitude location is stored in a string
        longitude = gps.location.lng();
        lng_str = String(longitude , 6); //longitude location is stored in a string
      }
      // Serial.println("Lat: " +lat_str+" | Lng: "+ lng_str);
      // delay(1000);
    }
  
  // khởi tạo một biến lưu giá trị hiện tại của Timer
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= 15*interval) {
    previousMillis = currentMillis;
    Post_to_DB("location");
  }
}

void Post_to_DB(String Table)
{
  if(WiFi.status()== WL_CONNECTED)
    {
      String  postData;
      if(Table == "location"){
        postData ="&api_key=" + apiKeyValue + "&Table="+ Table + "&Vehicle="+ Vehicle + 
        "&Latitude=" + lat_str + "&Longitude=" + lng_str;
      } else if(Table == "data"){
        postData ="&api_key=" + apiKeyValue + "&Table="+ Table + "&Vehicle="+ Vehicle + 
        "&Temperature=" + TemperatureAvg + "&Humidity=" + HumidityAvg + 
        "&Temperature1=" + TempArr[0] + "&Humidity1=" + HumiArr[0] +
        "&Temperature2=" + TempArr[1] + "&Humidity2=" + HumiArr[1] +
        "&Temperature3=" + TempArr[2] + "&Humidity3=" + HumiArr[2] +
        "&Temperature4=" + TempArr[3] + "&Humidity4=" + HumiArr[3];
      }
           
      http.begin(client, LinkWriteData); //Specify request destination
      http.addHeader("Content-Type", "application/x-www-form-urlencoded"); //Specify content-type header
    
      int httpCode = http.POST(postData); //Send the request
      // String payload = http.getString(); //Get the response payload
      
      if (httpCode == 200) 
      { 
        Serial.println("Values uploaded successfully. " + Table); 
        Serial.println(httpCode); 
        // String webpage = http.getString();    // Get html webpage output and store it in a string
        // Serial.println(webpage + "\n"); 
        // Serial.println(postData);
        // Serial.println("--------------------------------------------------------------------------------");
      }
      else 
      { 
        Serial.println(httpCode); 
        Serial.println("Failed to upload values. \n"); 
        return; 
      }
      http.end();
    }
    else{Serial.println("Connect Wifi Error!!!");}
  }

void receivedCallback(const uint32_t &from, const String &msg)
{
  Serial.println();
  Serial.print("Message = ");
  Serial.print(msg);
  String json = msg.c_str();
  DynamicJsonDocument doc(1024);
  DeserializationError error = deserializeJson(doc, json);

  if (error)
  {
    Serial.print("deserializeJson() failed: ");
    Serial.println(error.c_str());
    mesh.sendSingle(from, "Error");
  }
  else
  {
    Node = doc["Node"];
    Temperature = doc["Temperature"];
    Humidity = doc["Humidity"];
    // String x = String(Node) + "," + String(Temperature) + "," + String(Humidity);
    // Serial.println("data php : " +x);

    SaveValue();
  }
}

void SaveValue(){
  switch(Node){
    case 1:
      TempArr[0] = Temperature;
      HumiArr[0] = Humidity;
      break;
    case 2:
      TempArr[1] = Temperature;
      HumiArr[1] = Humidity;
      break;
    case 3:
      TempArr[2] = Temperature;
      HumiArr[2] = Humidity;
      break;
    case 4:
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

    for(int i=0; i<4; i++){
      if(TempArr[i]!=0){
        t += TempArr[i];
        a++;
      }
      if(HumiArr[i]!=0){
        h += HumiArr[i];
        b++;
      }
    } 
    if (a!=0){
      TemperatureAvg = (round((t/a)*100))/100;
    }
    if (b!=0){
      HumidityAvg = (round((h/b)*100))/100;
    }
    Post_to_DB("data");
    cnt = 0;
    TemperatureAvg = 0;
    HumidityAvg = 0;
    for(int i=0; i<4; i++){
      TempArr[i] = 0;
      HumiArr[i] = 0;
    }
  }
}

void newConnectionCallback(uint32_t nodeId) {
    // Serial.printf("--> startHere: New Connection, nodeId = %u\n", nodeId);
}

void changedConnectionCallback() {
  // Serial.printf("Changed connections\n");
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
  Serial.println(WiFi.localIP()); 
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
  if (Mode == "ON"){
      digitalWrite(Relay, HIGH);
      delay(200);
      // digitalWrite(ledPin, LOW);
    } else if(Mode == "OFF"){
      digitalWrite(Relay, LOW);
      delay(200);
      // digitalWrite(ledPin, HIGH);
    } else{ //tạo khoảng thời gian Offset 2 độ
      NumMode = atoi(Mode.c_str());
      if(NumMode < (30 + 2)){
        digitalWrite(Relay, HIGH);
        delay(200);
        // digitalWrite(ledPin, LOW);
      } else if(NumMode > (30 - 2)){
        digitalWrite(Relay, LOW);
        delay(200);
        // digitalWrite(ledPin, HIGH);
      }
    }
}