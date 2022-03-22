#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include "web.h"
ESP8266WebServer server(80);
#include <SoftwareSerial.h>
SoftwareSerial mySerial(D3,D4);
//#include <WiFi.h>
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27,16,2); 

#include <TinyGPS++.h> // library for GPS module
TinyGPSPlus gps;  // The TinyGPS++ object
SoftwareSerial ss(14,15); // The serial connection to the GPS device  (rx,tx)

float latitude , longitude;
int year , month , date, hour , minute , second;
String date_str , time_str , lat_str , lng_str;
int pm;


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
const char* ssid = "Gia dinh la so 1"; 
const char* password = "yeugiadinh"; 
const char* host = "192.168.1.8";

void setup(){
  Wire.begin(D1,D2);               //Thiết lập chân kết nối I2C (SDA,SCL);
  lcd.begin();                      //Khởi tạo LCD
  lcd.clear();                     //Xóa màn hình
  lcd.backlight();                 //Bật đèn nền         
  
  lcd.setCursor(0,0);              //Đặt vị trí ở ô thứ 3 trên dòng 1
 /* lcd.print("Temperature");
  lcd.setCursor(12,0);              //Đặt vị trí ở ô thứ 3 trên dòng 1
  lcd.print(Temperature);
  lcd.setCursor(0,1);              //Đặt vị trí ở ô thứ 1 trên dòng 2
  lcd.print("Humidity: ");   //Ghi đoạn text "E-smart Channel!" 
  lcd.setCursor(12,1);              //Đặt vị trí ở ô thứ 1 trên dòng 2
  lcd.print(Humidity);   //Ghi đoạn text "E-smart Channel!" 
 */   mySerial.begin(9600);
//    ss.begin(9600);
  Serial.begin(9600);
  pinMode(D0, OUTPUT);
  pinMode(D5, INPUT);
  pinMode(D6, INPUT);
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("connecting to ");
    Serial.println(host);
  Serial.println(WiFi.localIP());

  server.on("/", [] {
    SendWebPage();
  });

  server.on("/relayon", [] {
    relayon();
  });
  server.on("/relayoff", [] {
    relayoff();
  });
  server.on("/x", [] {
    text();
  });

  server.on("/loaddata", [] {
    senddata();
  });
  
  server.begin();
  pinMode(D0, OUTPUT);
  digitalWrite(D0, LOW);  

  last = millis();
  
}
int receivev;
int dem=0,demm=0,dem2=0,demm2=0;
void loop()
{
  
  
  if(mySerial.available() > 1){//Read from  STM module and send to serial monitor
    String input = mySerial.readString();
    Serial.println(input);
        receivev =input.toInt();
        Temperature=receivev/100;
            //Serial.println(Temperature);
        Humidity=receivev%100;
           // Serial.println(Humidity);

  }
  delay(20);
  if(digitalRead(D6) == HIGH)
  {
    delay(90);
     if(digitalRead(D6) == HIGH)
     {
        dem2++;
        demm2=dem2%2;
     }
  }
  if(demm2==1){
  if(digitalRead(D5) == HIGH)
  {
    delay(90);
     if(digitalRead(D5) == HIGH)
     {
        dem++;
        demm=dem%2;
     }
  }
  
  if(demm==0)
  {
     digitalWrite(D0,LOW);
     TT_relay=0;
     Serial.println("tat");
  }
  else
  {
    digitalWrite(D0,HIGH);
    TT_relay=1;
    Serial.println("bat");

  }
  }
  
  lcd.setCursor(0,0);              //Đặt vị trí ở ô thứ 3 trên dòng 1
  lcd.print("Temperature: ");
  lcd.setCursor(13,0);              //Đặt vị trí ở ô thứ 3 trên dòng 1
  lcd.print(Temperature);
  lcd.setCursor(0,1);              //Đặt vị trí ở ô thứ 1 trên dòng 2
  lcd.print("Humidity: ");   //Ghi đoạn text "E-smart Channel!" 
  lcd.setCursor(13,1);              //Đặt vị trí ở ô thứ 1 trên dòng 2
  lcd.print(Humidity);   //Ghi đoạn text "E-smart Channel!" 
  lcd.display();
  
  server.handleClient();
 /*   lcdd();*/
  if(millis() - last > 10)
    {
      datajson(String(Temperature),String(Humidity),String(TT_relay),String(anhsang),String(caidat));
      last = millis();
    }
    

    // Use WiFiClient class to create TCP connections
    WiFiClient client;
    const int httpPort = 80;//5555
    if (!client.connect(host, httpPort)) {
        Serial.println("connection failed");
        return;
    }
    unsigned long timeout2 = millis();
    if (millis() - timeout2 > 0) {
    // This will send the request to the server
 client.print(String("GET http://localhost/iot_project/connect.php?") + 
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
    }
}   
void datajson(String ND,String DA,String TTTB, String AS,String CD)
{
  json = "{\"ND\":\"" + String(ND)+"\"," +
           "\"DA\":\"" + String(DA) + "\"," + 
          "\"TTTB\":\"" + String(TTTB) + "\"," +       
          "\"AS\":\"" + String(AS) + "\"," + 
          "\"CD\":\"" + String(CD) + "\"}";
}
void uartt()
{
  uint8_t str[]={};
  float Te,Hu;
  for(int i=1;i<4;i++){
    uint8_t  c = mySerial.read();
    str[i]=c;
    if(i==1){
      int x=str[i];
      if(x>10){
        i=0;
      }
      float z;
      z=(float)x/10.0;     
      Te = str[0]+z;
      if(Te>20){
        
        Temperature=Te;
      }
      
    }
    
     if(i==2){
      int x=str[i];
      float h;
      h=(float)x/10.0;      
      Hu = str[2]+h;
      if(Hu>40){
        
        Humidity=Hu;
      }
      
    }
    if(i==3)
    {
      int anhsang=str[i];
    }
  //  Serial.println(Temperature);
    //Serial.println(Humidity);
    }
}
void relayon()
{
  Serial.println("bat");
  
  TT_relay=1;
      digitalWrite(D0, HIGH);
      
      server.send(200, "text/html",webpage);
}

void relayoff()
{
  Serial.println("tat");
  
  TT_relay=0;
      digitalWrite(D0,LOW);
      
      server.send(200, "text/html",webpage);
}
/*int gps()
{
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
    }
    return 
}*/
void text()
{
  
  String text = server.arg("namex");
  bien = text.toInt();
  Serial.println(bien);
  
  if(bien > Temperature)
    {
      Serial.println("tang nhiet do");
      digitalWrite(D0, LOW);
      TT_relay=0;
    }    
    else
      {
        Serial.println("giam nhiet do bat may lanh");
        digitalWrite(D0,HIGH);
        TT_relay=1;
      } 
      
}

void senddata()
{
  server.send(200, "text/html",String(json));
}
/*
void lcdd()
{
  lcd.setCursor(10,0);
   lcd.print(Temperature);//
   lcd.setCursor(10,1);
   lcd.print("Humidity");
}
/*
void capnhat()
{
      nhietdo= nhietdo+1;
      delay(100);
      doam = doam +2;
}*/
void SendWebPage()
{
  server.send(200, "text/html",webpage);
}
