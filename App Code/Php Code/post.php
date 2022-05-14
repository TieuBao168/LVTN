<?php
$api_key_value = "IoTLogistic";
 
$api_key = $Vehicle = $Temp = $Humi = $Table = $Lati = $Lngi = "";
 
date_default_timezone_set('Asia/Ho_Chi_Minh');
$timest = date('Y-m-d H:i:s'); 
 
var_dump($_POST);
 
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $api_key = test_input($_POST["api_key"]);
    if($api_key == $api_key_value) {
        $Vehicle = test_input($_POST["Vehicle"]);
        $Temp = test_input($_POST["Temperature"]);
        $Humi = test_input($_POST["Humidity"]);
        $Lati = test_input($_POST["Latitude"]);
        $Lngi = test_input($_POST["Longitude"]);
    
        $Table = "iot_project";
        $Table .= $Vehicle;
        
        
        // Create connection
        $conn = mysqli_connect("localhost", "highalln_iot_project", "Hihomhinh99", "highalln_iot_project");  
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        } 
        $val = " (nhiet_do, do_am, kinh_do, vi_do, created_at)
        VALUES ('$Temp', '$Humi', '$Lati', '$Lngi', '$timest')";
        
        $sql = "INSERT INTO ";
        $sql .= $Table;
        $sql .= $val;
        // mysqli_query($conn, $sql);
        
        if ($conn->query($sql) === TRUE) {
            echo "New record created successfully";
        } 
        else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    
        $conn->close();
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