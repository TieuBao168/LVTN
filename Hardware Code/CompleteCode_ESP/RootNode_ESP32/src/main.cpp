#include <Arduino.h>
#include <HTTPClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <painlessMesh.h>
#include <string.h>
#include <TinyGPS++.h> // library for GPS module
#include <SoftwareSerial.h>

//Declare HTTP Protocol
HTTPClient http;    //Declare object of class HTTPClient
WiFiClient client;

String apiKeyValue = "IoTLogistic", Vehicle = "1";

TinyGPSPlus gps;  // The TinyGPS++ object
SoftwareSerial ss(3,1); // The serial connection to the GPS device  (Rx,Tx)

float latitude , longitude;
String lat_str , lng_str ;
// WiFiServer server(80);

int cnt = 0;
// int firsttime = 0;

String Protocol, Stt;
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

//Connect Wifi
#define ssid "Larcade"
#define pass "987654321"
const char* host = "192.168.1.13";

// byte stt_led = LOW;
void Wifi_connect();
void ControlRelay();

// Post data to MySQL
void SendtoDB();
void Post_to_DB();
void SaveValue();

// Đường dẫn file Back-end
const char* pathGetCtr = "http://luanvanlogistic.highallnight.com/app/control1/control1.json";
const char* pathWriteData = "http://luanvanlogistic.highallnight.com/app/postdata.php";

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

//test mesh
unsigned long previousTime = 0;
unsigned long currentTime = 0;
unsigned int count = 0, count1 = 0;;
unsigned int countRe = 0;
unsigned int countSend = 0;
void calculateRTT(unsigned int currentTime, unsigned int previousTime, unsigned int count);
void calculateRSSI(double RSSI, unsigned int count);
//test mesh

void sendMessage(){
  Serial.println();
  Serial.println("Start Sending....");

  previousTime = millis();
  Serial.printf("Send message successful: %d time\n", count1);
  if(count1 != 0){
  count1++;
  }
  Serial.printf("RSSI form Root: %d dBm\n", WiFi.RSSI());

  String x = "Begin";
  // x += mesh.getNodeId();
  mesh.sendBroadcast(x);
  // Serial.println(x);
  taskSendMessage.setInterval(TASK_SECOND * 5);
}

void setup() {
  Serial.begin(115200);
  ss.begin(9600);
  Serial.println();
  Serial.println("--------------------------------------------------------");
  Serial.println("----------------------Start here!-----------------------");
  
  

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

  // while (ss.available() > 0) //while data is available
  //   if (gps.encode(ss.read())) //read gps data
  //   {
  //     if (gps.location.isValid()) //check whether gps location is valid
  //     {
  //       latitude = gps.location.lat();
  //       lat_str = String(latitude , 6); // latitude location is stored in a string
  //       longitude = gps.location.lng();
  //       lng_str = String(longitude , 6); //longitude location is stored in a string
  //     }
  //     // Serial.println("Lat: " +lat_str+" | Lng: "+ lng_str);
  //     // delay(1000);
  //   }
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

void Post_to_DB()
{
  if(WiFi.status()== WL_CONNECTED)
    {
      String  postData;
      postData ="&api_key=" + apiKeyValue + "&Vehicle="+ Vehicle + "&Temperature=" + TemperatureAvg + "&Humidity=" + HumidityAvg + "&Latitude=" + lat_str + "&Longitude=" + lng_str;
            
      http.begin(client,pathWriteData);            //Specify request destination
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");                  //Specify content-type header
    
      int httpCode = http.POST(postData);   //Send the request
      // String payload = http.getString();    //Get the response payload
      
      if (httpCode == 200) 
      { 
        Serial.println("Values uploaded successfully."); 
        Serial.println(httpCode); 
        String webpage = http.getString();    // Get html webpage output and store it in a string
        Serial.println(webpage + "\n"); 
        Serial.println(postData);
        Serial.println("--------------------------------------------------------------------------------");
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
  // Serial.printf("Mesh Network: Received from %u msg=%s\n", from, msg.c_str());
  currentTime = millis();
  String json = msg.c_str();
  DynamicJsonDocument doc(1024);
  DeserializationError error = deserializeJson(doc, json);

  if(count1==0){
    count1=1;
  }

  
  Serial.printf("Receive message successful: %d time\n", count);
  Serial.printf("RTT Time %d is: ", count);
  count++;
  Serial.println(currentTime - previousTime);
  calculateRTT(currentTime, previousTime, count);
  // Serial.println();

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

    RSSI = doc["RSSI"];
    Serial.printf("RSSI form Leaf: %f\n", RSSI);
    calculateRSSI(RSSI, count);


    // SaveValue();
  }
}

void calculateRSSI(double RSSI, unsigned int count)
{
  // Serial.println("calculateRSSI");
  static double arrayRTT[100];
  // RTT thu count luu vao mang
  arrayRTT[count] = RSSI;
  // gan gia tri dau tien cho max va min
  static double max = arrayRTT[1];
  static double min = arrayRTT[1];
  // for loop through array

  if (arrayRTT[count] < min)
  {
    min = arrayRTT[count];
  }
  if (arrayRTT[count] >= max)
  {
    max = arrayRTT[count];
  }
  Serial.printf("min RSSI is: %f\n", min);
  Serial.printf("max RSSI is: %f\n", max);
  // tinh RTT trung binh
  double sumRTT = 0;
  for (size_t i = 0; i <= count; i++)
  {
    sumRTT += (double)arrayRTT[i];
  }
  double averageRTT = sumRTT / (double)(count);
  Serial.printf("Average RSSI is: %f\n", averageRTT);
}

// note la bien count chay tu 1
void calculateRTT(unsigned int currentTime, unsigned int previousTime, unsigned int count)
{
  // Serial.println("calculateRTT");
  // khoi tao gia tri RTT vong dau tien
  int roundTripTime = currentTime - previousTime;
  // array de luu RTT, 300 phan tu gui trong 5ph
  static unsigned int arrayRTT[100];
  // RTT thu count luu vao mang
  arrayRTT[count] = roundTripTime;

  // gan gia tri dau tien cho max va min
  static unsigned int max = arrayRTT[1];
  static unsigned int min = arrayRTT[1];
  // for loop through array

  if (arrayRTT[count] - min > 42949672)
  {
    min = arrayRTT[count];
  }
  if (arrayRTT[count] >= max)
  {
    max = arrayRTT[count];
  }
  Serial.printf("min RTT is: %d\n", min);
  Serial.printf("max RTT is: %d\n", max);
  // tinh RTT trung binh
  double sumRTT = 0;
  for (size_t i = 0; i <= count; i++)
  {
    sumRTT += (double)arrayRTT[i];
  }
  double averageRTT = sumRTT / (double)(count);
  Serial.printf("Average RTT is: %f \n", averageRTT);
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

    for(int i=0; i<4; i++){
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
    // SendtoDB();
    // GetGPS();
    Post_to_DB();
    cnt = 0;
    TemperatureAvg = 0;
    HumidityAvg = 0;
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