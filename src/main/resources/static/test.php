<?php
$nums = (int) $_GET['nums'];
$arr = ['Audi', 'BMW', 'Ford', 'Hyundai', 'Mazda', 'Mercedes-Benz', 'Toyota', 'Volkswagen'];
shuffle($arr);
exit(json_encode(array_slice($arr, 0, $nums)));
>