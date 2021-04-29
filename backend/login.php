<?php



require_once "connection.php";

$username = $_POST["username"];
$password = $_POST["password"];



$stmt = $conn->prepare("SELECT username,password FROM user WHERE username LIKE :username");
$stmt->bindParam(':username', $username, PDO::PARAM_STR);
$stmt->execute();
$result = $stmt->fetchAll();




if (password_verify($password, $result[0]["password"]) == 1){
    echo "1";
}else{
    echo "0";
}
