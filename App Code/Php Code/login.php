<?php
$connect = mysqli_connect("localhost", "highalln_iot_project", "Hihomhinh99", "highalln_iot_project");  
// include 'config.php';
if (!$connect) {
 die("Connection failed: " . mysqli_connect_error());
}
$name = $_POST['name'];
$password = $_POST['password'];

//create a sql query to check email alread exist or not
$sql = "SELECT * FROM login1 WHERE name = '$name' AND password = '$password'";
$sql2 = "SELECT * FROM login2 WHERE name = '$name' AND password = '$password'";

$result = array();

$responce = mysqli_query($connect,$sql);
$responce2 = mysqli_query($connect,$sql2);

if(mysqli_num_rows($responce) === 1){
	$result['status'] = 'success1';
	echo json_encode($result);
	mysqli_close($connect);
}else if(mysqli_num_rows($responce2) === 1){
    $result['status'] = 'success2';
	echo json_encode($result);
	mysqli_close($connect);
}else{
	$result['status'] = 'error';
	echo json_encode($result);
	mysqli_close($connect);
}
?>