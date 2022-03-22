#include <Arduino.h>
#include <painlessMesh.h>
#include <WiFiManager.h>
#include <DHTesp.h>

#define MESH_PREFIX     "IoTLogistic"
#define MESH_PASSWORD   "IoTLogistic2022"
#define MESH_PORT   5555

DHTesp dht;

float humidity, temperature;
int LState;
//Pin Declaration
#define LED D5 //Relay1

Scheduler userScheduler; 
painlessMesh  mesh;
void sendMessage(); // Prototype so PlatformIO doesn't complain
Task taskSendMessage( TASK_SECOND * 1 , TASK_FOREVER, &sendMessage );

void sendMessage(){
  Serial.println();
  Serial.println("Start Sending....");

    // Serializing in JSON Format
  DynamicJsonDocument doc(1024);
  // float h = 50 + random(100)/10;
  // float t = 20 + random(100)/10;
  // int tt = random(2);
  // String stt = "OFF";
  // if (tt)
  // {
  //   stt = "ON";
  // }
  doc["Device"] = "Node 2";
  doc["Temp"] = temperature;
  doc["Humi"] = humidity;
  // doc["Stt"] = stt;
  String msg ;
  serializeJson(doc, msg);

  mesh.sendBroadcast( msg );
  Serial.println("Message from Node 2: ");
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

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  // Serial.println();
  // Serial.println("Status\tHumidity (%)\tTemperature (C)\t(F)\tHeatIndex (C)\t(F)");

  dht.setup(D3, DHTesp::DHT11);

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
  // it will run the user scheduler as well
  mesh.update();
  
  delay(dht.getMinimumSamplingPeriod());
  humidity = round(dht.getHumidity()*100)/100;
  temperature = roundf(dht.getTemperature()*100)/100;

  // Serial.print(dht.getStatusString());
  // Serial.print("\t");
  // Serial.print(humidity, 3);
  // Serial.print("\t\t");
  // Serial.print(temperature, 3);
  // Serial.print("\t\t");
  // Serial.print(dht.toFahrenheit(temperature), 1);
  // Serial.print("\t\t");
  // Serial.print(dht.computeHeatIndex(temperature, humidity, false), 1);
  // Serial.print("\t\t");
  // Serial.println(dht.computeHeatIndex(dht.toFahrenheit(temperature), humidity, true), 1);
}