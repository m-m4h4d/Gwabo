// <?php
// $host = "localhost"; // e.g., "mysql.hostinger.com"
// $dbname = "u744990913_gwabo";
// $username = "u744990913_serge";
// $password = "1|lNKd|+";
//
// try {
//     // Create a connection
//     $conn = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
//     $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
//
//     // Query data
//     $stmt = $conn->prepare("SELECT * FROM products");
//     $stmt->execute();
//
//     // Fetch results
//     $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
//
//     // Return as JSON
//     echo json_encode($results);
// }
// catch(PDOException $e) {
//     echo "Error: " . $e->getMessage();
// }
//
// $conn = null;
//
$servername = "localhost"; // e.g., localhost
$username = "u744990913_serge";
$password = "1|lNKd|+";
$dbname = "u744990913_gwabo";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM products";
$result = $conn->query($sql);

$products = array();
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $products[] = $row;
    }
}
print_r($products);
header('Content-Type: application/json');
echo json_encode($products);
$conn->close();
?>
