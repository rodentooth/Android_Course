<?php


require_once "connection.php";

$gamemode = $_POST["gamemode"];
$highscore = $_POST["highscore"];
$username = $_POST["username"];


switch ($gamemode){
    case "endless":
        $stmt = $conn->prepare("Insert into endless (username,highscore) value (:username,:highscore) ON DUPLICATE KEY UPDATE highscore= :highscore");

        break;
        case "ftc":
        $stmt = $conn->prepare("Insert into ftc (username,highscore) value (:username,:highscore) ON DUPLICATE KEY UPDATE highscore= :highscore");

        break;
}
$stmt->bindParam(':username', $username, PDO::PARAM_STR);
$stmt->bindParam(':gamemode', $gamemode, PDO::PARAM_STR);
$stmt->bindParam(':highscore', $highscore, PDO::PARAM_STR);






if ($stmt->execute() == 1){
    echo "1";
}else{
    echo "0";
}
