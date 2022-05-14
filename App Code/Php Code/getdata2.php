<?php   
$connect = mysqli_connect("localhost", "highalln_iot_project", "Hihomhinh99", "highalln_iot_project");   
$sql = "SELECT * FROM info2";
$sql1 = "SELECT * FROM iot_project2";    
$result = mysqli_query($connect, $sql);
$result1 = mysqli_query($connect, $sql1);  
$json_array = array();  
while($row = mysqli_fetch_array($result)){
    $id = $row['id'];
    $ten = $row['ten_tai_xe'];
    $dien_thoai = $row['dien_thoai'];
    $xuat_phat = $row['xuat_phat'];
    $dich_den = $row['dich_den'];
    $thoi_gian = $row['thoi_gian'];
    $kinh_do_xp = $row['kinh_do_xuat_phat'];
    $vi_do_xp = $row['vi_do_xuat_phat'];
    $kinh_do_dd = $row['kinh_do_dich_den'];
    $vi_do_dd = $row['vi_do_dich_den'];
	$json_array[] = array(
	    'ID'=> $id,
		'Ten'=> $ten, 
		'Dien Thoai'=> $dien_thoai,
		'Xuat phat' => $xuat_phat,
		'Dich den' => $dich_den,
		'Thoi gian' => $thoi_gian,
		'Kinh do xuat phat' => $kinh_do_xp,
		'Vi do xuat phat' => $vi_do_xp,
		'Kinh do dich den' => $kinh_do_dd,
		'Vi do dich den' => $vi_do_dd
	);
}

while($row = mysqli_fetch_array($result1)){
    $id = $row['id'];
    $nhiet_do = $row['nhiet_do'];
    $do_am = $row['do_am'];
    $kinh_do = $row['kinh_do'];
    $vi_do = $row['vi_do'];
    $thoi_gian_doc = $row['created_at'];
	$json_array[] = array(
	    'ID'=> $id,
		'Nhiet do'=> $nhiet_do,
		'Do am' => $do_am,
		'Kinh do'=> $kinh_do, 
		'Vi do'=> $vi_do,
		'Thoi gian doc' => $thoi_gian_doc
	);
}
echo json_encode($json_array)
 ?>
