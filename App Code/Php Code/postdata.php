<?php
// Create connection
$connect = mysqli_connect("localhost", "highalln_iot_project", "Hihomhinh99", "highalln_iot_project");  
// Check connection
if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error);
} 
$api_key_value = "IoTLogistic";
 
$api_key = $Vehicle = $Table = $Lati = $Lngi = "";
$Temp = array();
$Humi = array();
date_default_timezone_set('Asia/Ho_Chi_Minh');
$timest = date('Y-m-d H:i:s'); 
 
var_dump($_POST);
 
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $api_key = test_input($_POST["api_key"]);
    if($api_key == $api_key_value) {
        $Table = test_input($_POST["Table"]);
        $Vehicle = test_input($_POST["Vehicle"]);
        if($Table == "location"){
            $Lati = test_input($_POST["Latitude"]);
            $Lngi = test_input($_POST["Longitude"]);
            $Table .= $Vehicle;

            $val = "(kinh_do, vi_do, created_at) VALUES ('$Lati', '$Lngi', '$timest')";
        } else if($Table == "data"){
            $Temp[0] = test_input($_POST["Temperature"]);
            $Humi[0] = test_input($_POST["Humidity"]);
            for ($i = 1; $i <= 4; $i++){
                $T = "Temperature"; $T .= $i;
                $H = "Humidity"; $H .= $i;
                $Temp[$i] = test_input($_POST[$T]);
                $Humi[$i] = test_input($_POST[$H]);
            }

            $Table .= $Vehicle;
            
            $val = " (nhiet_do, do_am, nhiet_do1, do_am1, nhiet_do2, do_am2, nhiet_do3, do_am3, nhiet_do4, do_am4, created_at)
            VALUES ('$Temp[0]', '$Humi[0]', '$Temp[1]', '$Humi[1]', '$Temp[2]', '$Humi[2]', '$Temp[3]', '$Humi[3]', '$Temp[4]', '$Humi[4]', '$timest')";
            
        } else{
            echo "Wrong table name";
        }
        
        $sql = "INSERT INTO ";
        $sql .= $Table;
        $sql .= $val;
        // mysqli_query($connect, $sql);
        
        if ($connect->query($sql) === TRUE) {
            echo "New record created successfully";
        } 
        else {
            echo "Error: " . $sql . "<br>" . $connect->error;
        }
    
        $connect->close();
    }
    else {
        echo "Wrong API Key provided.";
    }
}
else {
    echo "No data posted with HTTP POST.";
}
 
function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

