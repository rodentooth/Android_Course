<?php



require_once "connection.php";

$gamemode = $_POST["gamemode"];


switch ($gamemode){
    case "endless":
        $stmt = $conn->prepare("SELECT highscore,username FROM endless order by highscore desc limit 20");

        break;
    case "ftc":
        $stmt = $conn->prepare("SELECT highscore,username FROM ftc order by highscore desc limit 20");

        break;
}
$stmt->bindParam(':gamemode', $gamemode, PDO::PARAM_STR);
$stmt->execute();
$result = $stmt->fetchAll();


$out = array_values($result);
echo json_encode($out);
