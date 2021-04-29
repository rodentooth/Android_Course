<?php

require_once "connection.php";

$email = $_POST["email"];
$username = $_POST["username"];
$password = $_POST["password"];

$password=password_hash($password, PASSWORD_BCRYPT);


$stmt = $conn->prepare("SELECT username FROM user WHERE username LIKE :username");
$stmt->bindParam(':username', $username, PDO::PARAM_STR);
$stmt->execute();
$result = $stmt->fetchAll();

//check if there is a result
if (count($result) > 0) {
    //username is already gone!

    echo "ERROR: USERNAME NOT AVAILABLE";
    exit();
}else{

    $stmt = $conn->prepare("INSERT INTO user (username,password,email) VALUE  (:username,:password,:email)");
    $stmt->bindParam(':username', $username, PDO::PARAM_STR);
    $stmt->bindParam(':password', $password, PDO::PARAM_STR);
    $stmt->bindParam(':email', $email, PDO::PARAM_STR);
    $stmt->execute();
    $result = $stmt->fetchAll();
    if (!$stmt->execute()) {
        $result = $stmt->errorCode();
        echo "FAILURE! ::: " . $result . "" . $stmt->errorInfo()[2];
    }else{
        echo "1";
    }


}
