#include <Arduino.h>
#include <painlessMesh.h>
#include <WiFiManager.h>
#include <DHTesp.h>
#include <math.h>

#define MESH_PREFIX     "IoTLogistic"
#define MESH_PASSWORD   "IoTLogistic2022"
#define MESH_PORT   5555

DHTesp dht;

typedef struct{
  float humidity, temperature = 0;
} Data;

Data previous, recent;

int LState;
//Pin Declaration


//test mesh
unsigned long previousTime = 0;
unsigned int count = 0, count1 = 0;
//test mesh

Scheduler userScheduler; 
painlessMesh  mesh;
void sendMessage(uint32_t id); // Prototype so PlatformIO doesn't complain
//Task taskSendMessage( TASK_SECOND * 1 , TASK_FOREVER, &sendMessage );
void compareData();

void sendMessage(uint32_t id){
  Serial.println();
  // Serial.println("Start Sending....");

  // delay(dht.getMinimumSamplingPeriod());
  // recent.humidity = round(dht.getHumidity()*100)/100;
  // recent.temperature = roundf(dht.getTemperature()*100)/100;

    // Serializing in JSON Format
  DynamicJsonDocument doc(1024);

  doc["Node"] = 3;
  doc["Temperature"] = recent.temperature;
  doc["Humidity"] = recent.humidity;
  doc["RSSI"] = WiFi.RSSI();

  String msg ;
  serializeJson(doc, msg); 
  // uint32_t id = 3323046497; // ID of RootNode
  mesh.sendSingle(id, msg);
  // Serial.println("Message from Node Leaf: ");
  // Serial.println(msg);

  Serial.printf("Send message successful: %d time\n", count1);
  count1++;
  //taskSendMessage.setInterval(TASK_SECOND * 5);
}

void sendMessageWarn(){
  Serial.println();
  DynamicJsonDocument doc(1024);

  doc["Node"] = 3;
  doc["Temperature"] = recent.temperature;
  doc["Humidity"] = recent.humidity;
  doc["RSSI"] = WiFi.RSSI();

  String msg ;
  serializeJson(doc, msg); 
  // uint32_t id = 3323046497; // ID of RootNode
  mesh.sendBroadcast(msg);
}

// Needed for painless library
void receivedCallback( uint32_t from, String &msg ) {
  Serial.println();
  // Serial.print("Message = ");
  // Serial.print(msg);

  Serial.printf("Receive message successful: %d time", count);
  count++;

  if((msg == "Begin")||(msg == "Error")){
    sendMessage(from);
  } else if(msg == "Warn"){
    sendMessageWarn();
  }
}

void newConnectionCallback(uint32_t nodeId) {
  // Serial.printf("--> startHere: New Connection, nodeId = %u\n", nodeId);
}
void changedConnectionCallback() {
  // Serial.printf("Changed connections\n");
}
void nodeTimeAdjustedCallback(int32_t offset) {
  // Serial.printf("Adjusted time %u. Offset = %d\n", mesh.getNodeTime(), offset);
}

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  // Serial.println();
  // Serial.println("Status\tHumidity (%)\tTemperature (C)\t(F)\tHeatIndex (C)\t(F)");

  dht.setup(D3, DHTesp::DHT11);

  mesh.setDebugMsgTypes( ERROR | STARTUP );  
  mesh.init( MESH_PREFIX, MESH_PASSWORD, &userScheduler, MESH_PORT );
  mesh.onReceive(&receivedCallback);
  mesh.onNewConnection(&newConnectionCallback);
  mesh.onChangedConnections(&changedConnectionCallback);
  mesh.onNodeTimeAdjusted(&nodeTimeAdjustedCallback);
  // userScheduler.addTask( taskSendMessage );
  // taskSendMessage.enable();
}

void loop() {
  // put your main code here, to run repeatedly:
  // it will run the user scheduler as well
  compareData();
  mesh.update();
  
}

void compareData(){
  static int skip = 0;
  recent.humidity = round(dht.getHumidity()*100)/100;
  recent.temperature = round(dht.getTemperature()*100)/100;
  if((abs(recent.temperature - previous.temperature) > 2.0)||(abs(recent.humidity - previous.humidity) > 5.0)){
    if(skip == 1){
      mesh.sendBroadcast("Warn");
    }
    previous.temperature = recent.temperature;
    previous.humidity = recent.humidity;
    skip = 1;
  }
}