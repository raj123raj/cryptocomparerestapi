<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cryptocurrency</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style type="text/css">
        .main-form, .profile-area {
            width: 500px;
        }
        .main-form {
            margin: 50px auto 0px;
        }
        .profile-area {
            margin: 10px auto;
        }
        .main-form section, .profile-area section {
            margin-bottom: 15px;
            background: #df5b47;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }
        .main-form section {
            padding: 30px;
        }
        .profile-area section {
            padding: 30px 30px 30px;
        }
        .profile-area section > div {
            text-align: center;
        }
        .main-form h3 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
            font-family: verdana;
        }
        .hideElement {
            display: none;
        }
    </style>
</head>
<body>
<div class="main-form" id="main-form">
    <section>
        <h5 class="text-center">Enter a Cryptocurrency that need to be compared </h5>
        <div class="form-group">
            <input id="fsym" type="text" class="form-control" placeholder="Enter a Cryptocurrency that need to be compared..." required="required">
        </div>
        <h5 class="text-center">Enter a comparison Cryptocurrency </h5>
        <div class="form-group">
            <input id="tsyms" type="text" class="form-control" placeholder="Enter a comparision Cryptocurrency ..." required="required">
        </div>
        <div class="form-group">
            <button onclick="loadData()" class="btn btn-primary btn-block">Compare Cryptocurrencies</button>
        </div>
    </section>
</div>
<div class="profile-area hideElement" id="profile-area">
    <section>
        <div id="loader" class="hideElement">
            <div class="spinner-border" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div id="profile" class="hideElement">
            <br><br>
            <p><strong>USD : <span id="USD"></span></strong></p>
            <p><strong>EUR : <span id="EUR"></span></strong></p>
            <p><strong>INR : <span id="INR"></span></strong></p>
        </div>
    </section>
</div>
</body>
<script>
    function loadData() {
        document.getElementById("profile-area").classList.remove("hideElement");
        document.getElementById("loader").classList.remove("hideElement");
        document.getElementById("profile").classList.add("hideElement");
        var fsym = document.getElementById("fsym").value;
        var tsyms = document.getElementById("tsyms").value;

        if(fsym != "" && fsym != null && tsyms != "" && tsyms != null) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var jsonResponse = JSON.parse(this.responseText);
                    document.getElementById("USD").innerHTML = jsonResponse.USD;
                    document.getElementById("EUR").innerHTML = jsonResponse.EUR;
                    document.getElementById("INR").innerHTML = jsonResponse.INR;
                    document.getElementById("loader").classList.add("hideElement");
                    document.getElementById("profile").classList.remove("hideElement");
                }
            };
            xhttp.open("GET", "getCryptocurrencyComparisionDetailsByName?fsym=" + fsym + "&tsyms=" + tsyms, true);
            
            xhttp.send();
            console.log("done");
        } else {
            console.log("Enter from and to cryptocurrency...")
        }
    }
</script>
</html>