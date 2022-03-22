const char webpage[] PROGMEM = R"=====(

<!DOCTYPE html>
<html>
<head>
  <title>webserver</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <link rel="stylesheet" 
  type="text/css" href="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js">
  <link href="./css/style.css" rel="stylesheet"></link>
  <script type="text/javascript" src="./jquery/index.js"></script>
  <script
      src="https://code.jquery.com/jquery-3.6.0.min.js"
      integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
      crossorigin="anonymous">    
      </script>
    <style type="text/css">
      @import url('https://fonts.googleapis.com/css2?family=Chonburi&family=Cookie&family=Grenze:wght@600&display=swap');
      html, body {
  width: 100%;
  height: 100%;
  font-family: 'Chonburi', cursive;
  font-size: 25px;
  color: #222;
}
    </style>
</head>
<script type="text/javascript">
    function onrelay()
      { console.log(" bat relay")
        var xhttp = new XMLHttpRequest();
        xhttp.open('GET','/relayon',true);
        xhttp.send();
      }
     function offrelay()
      { console.log(" bat relay")
        var xhttp = new XMLHttpRequest();
        xhttp.open('GET','/relayoff',true);
        xhttp.send();
      }
    function send()
      {
        var x = document.getElementById("text").value;
        console.log("x="+x);

        var xhttp = new XMLHttpRequest();
        xhttp.open('GET','/x?namex='+x,true);
        xhttp.send();
        document.getElementById("text").value = "";
        //http://localhost/iot_project/x?namex="text"
      }
     function loaddata()
     {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
          if(this.readyState == 4 && this.status == 200)
            {
              var data = xhttp.responseText;
              console.log(data); 
              //ok 
              var datajson = JSON.parse(data);
             // var nhietdatajson.ND 
              //datajson.TTTB

              document.getElementById("nhietdo").innerHTML = datajson.ND;
              document.getElementById("doam").innerHTML = datajson.DA;
             // document.getElementById("anhsang").innerHTML = datajson.AS; 
              
              if(datajson.TTTB == 1)
                {
                   document.getElementById("trangthai").innerHTML = "ON";
                }
                else 
                   document.getElementById("trangthai").innerHTML = "OFF";                
            }
        }
        xhttp.open('GET','/loaddata',true);
        xhttp.send(); 

        setTimeout(function(){loaddata()},1000);
     }
  </script>
<body onload = "loaddata()">
  <div>

  <br>

  <label>CHỌN CHẾ ĐỘ</label>
  <br>
  
  <br>
  <div class="btn-group btn-group-toggle" data-toggle="buttons">
  <label class="btn btn-secondary active">
    <input id="bt1" type="radio" name="options" id="option1" autocomplete="off" checked> BẰNG TAY
  </label>
  <label class="btn btn-secondary">
    <input id="bt2" type="radio" name="options" id="option2" autocomplete="off"> TỰ ĐỘNG
  </label>
</div>
<br>
<br>
<div id="on"class="btn-group btn-group-toggle" data-toggle="buttons">
  <label class="btn btn-secondary active">
    <input onclick="onrelay()" type="radio" name="options" id="option1" autocomplete="off" checked> ON
  </label>
  <label class="btn btn-secondary">
    <input onclick="offrelay()" type="radio" name="options" id="option2" autocomplete="off"> OFF
  </label>
</div>
  <br>
  <div id="of">
  <label>CÀI ĐẶT NHIỆT ĐỘ</label>
  <input type="text" class="form-control" placeholder="chọn nhiệt độ cần cài đặt" aria-label="Username" aria-describedby="basic-addon1" id="text">
  <button onclick="send()" type="button" class="btn btn-secondary">CẬP NHẬT </button>
  </div>
  <br>
<br><br>
  <label>NHIỆT ĐỘ HIỆN TẠI : </label>
  <lable id="nhietdo"></lable>
  <br><br>
  <label>ĐỘ ẨM HIỆN TẠI : </label>
  <lable id="doam"></lable>
  <br><br>
  
  <label>TRẠNG THÁI THIẾT BỊ HIỆN TẠI : </label>
  <lable id="trangthai"><lable>

    <script type="text/javascript">
      document.getElementById("of").style.display = 'none';
      document.getElementById("on").style.display = 'none';
    </script>

    <script type="text/javascript">
    document.getElementById("bt1").onclick = function () {
      document.getElementById("on").style.display = 'block';
      document.getElementById("of").style.display = 'none';
            };
 
    document.getElementById("bt2").onclick = function () {
        document.getElementById("of").style.display = 'block';
        document.getElementById("on").style.display = 'none';
            };
  </script>
  </div>
</body>
</html>


)=====";
