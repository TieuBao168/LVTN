   <?php
   
    $connect = mysqli_connect("localhost", "highalln_iot_project", "Hihomhinh99", "highalln_iot_project");  
    if (!$connect) {
    die("Connection failed: " . mysqli_connect_error());
    }
    $mode = test_input($_POST['Mode']);
    $insertdata = "INSERT INTO control2(nhiet_do) VALUES ('$mode')";
    mysqli_query($connect, $insertdata);
  
   $jsonString = file_get_contents("control2/control2.json");
   $data = json_decode($jsonString, true);
   

    $user='abcd_ef';
    
	if ($_SERVER["REQUEST_METHOD"] == "POST") {
	
// 	$data['Auto'] = test_input($_POST["Auto"]);
	$data['Mode'] = test_input($_POST["Mode"]);
   
	$newJsonString = json_encode($data);
	file_put_contents("control2/control2.json", $newJsonString);
	}
	
 function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
   ?>  