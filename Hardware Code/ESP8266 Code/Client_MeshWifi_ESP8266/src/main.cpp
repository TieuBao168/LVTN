#include <Arduino.h>
#include <painlessMesh.h>

#define MESH_PREFIX     "IoTGateway"
#define MESH_PASSWORD   "IoTGateway2021"
#define MESH_PORT   5555

int LState;
//Pin Declaration
#define LED D5 //Relay1

Scheduler userScheduler; 
painlessMesh  mesh;
void sendMessage(); // Prototype so PlatformIO doesn't complain
Task taskSendMessage( TASK_SECOND * 1 , TASK_FOREVER, &sendMessage );

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  pinMode(LED, OUTPUT);

  mesh.setDebugMsgTypes( ERROR | STARTUP );  
  mesh.init( MESH_PREFIX, MESH_PASSWORD, &userScheduler, MESH_PORT );
  mesh.onReceive(&receivedCallback);
  mesh.onNewConnection(&newConnectionCallback);
  mesh.onChangedConnections(&changedConnectionCallback);
  mesh.onNodeTimeAdjusted(&nodeTimeAdjustedCallback);
  userScheduler.addTask( taskSendMessage );
  taskSendMessage.enable();
}

void loop() {
  // put your main code here, to run repeatedly:

}

void sendMessage(){
  Serial.println();
  Serial.println("Start Sending....");

    // Serializing in JSON Format
  DynamicJsonDocument doc(1024);
  float h = 50 + random(100)/10;
  float t = 20 + random(100)/10;
  int tt = random(2);
  String stt = "OFF";
  if (tt)
  {
    stt = "ON";
  }
  doc["Device"] = "DHTNode";
  doc["Temp"] = t;
  doc["Humi"] = h;
  doc["Stt"] = stt;
  String msg ;
  serializeJson(doc, msg);

  mesh.sendBroadcast( msg );
  Serial.println("Message from DHT node: ");
  Serial.println(msg);
  taskSendMessage.setInterval((TASK_SECOND * 1, TASK_SECOND * 10));
}

// Needed for painless library
void receivedCallback( uint32_t from, String &msg ) {
  Serial.println();
  Serial.print("Message = ");Serial.println(msg);
  String json;
  DynamicJsonDocument doc(1024);
  json = msg.c_str();
  DeserializationError error = deserializeJson(doc, json);
  if (error)
  {
    Serial.print("deserializeJson() failed: ");
    Serial.println(error.c_str());
  }

  LState = doc["Button"]; 
  digitalWrite(LED, LState);
}

void newConnectionCallback(uint32_t nodeId) {
  Serial.printf("--> startHere: New Connection, nodeId = %u\n", nodeId);
}
void changedConnectionCallback() {
  Serial.printf("Changed connections\n");
}
void nodeTimeAdjustedCallback(int32_t offset) {
  Serial.printf("Adjusted time %u. Offset = %d\n", mesh.getNodeTime(), offset);
}