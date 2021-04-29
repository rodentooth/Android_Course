<?php
//Connect to the DB

$conn = new PDO('mysql:host=' . "localhost" . ';dbname=' . "tappinggame" . '', "com", "emanuel610", array(
    PDO::ATTR_PERSISTENT => true,
    PDO::ATTR_ERRMODE => true,
    PDO::ERRMODE_EXCEPTION => true
));

