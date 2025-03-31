package com.example.farmcab

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Composable
fun ListEquipmentScreen() {
    var equipmentName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Image Pickers for Gallery and Camera
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            imageUri = saveBitmapToUri(context, bitmap)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("List Your Equipment", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Image Picker Section
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Tap to Select Image", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Buttons for Gallery and Camera
        Row {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Choose from Gallery")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { cameraLauncher.launch(null) }) {
                Text("Take a Photo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = equipmentName,
            onValueChange = { equipmentName = it },
            label = { Text("Equipment Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price per day (₹)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (equipmentName.isNotBlank() && price.isNotBlank() && description.isNotBlank() && imageUri != null) {
                    isLoading = true
                    uploadImageToFirebase(imageUri!!) { imageUrl ->
                        saveEquipmentToRealtimeDB(equipmentName, price, description, imageUrl, context) {
                            isLoading = false
                            equipmentName = ""
                            price = ""
                            description = ""
                            imageUri = null
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Uploading..." else "Submit")
        }
    }
}

// Function to convert Bitmap to URI
fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val filename = "temp_image_${System.currentTimeMillis()}.jpg"
    val file = File(context.cacheDir, filename)
    return try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        androidx.core.content.FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Function to upload image to Firebase Storage
fun uploadImageToFirebase(imageUri: Uri, onSuccess: (String) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference
    val fileRef = storageRef.child("equipmentImages/${UUID.randomUUID()}.jpg")

    fileRef.putFile(imageUri)
        .addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())  // Return Image URL
            }
        }
        .addOnFailureListener { e ->
            println("Image upload failed: $e")
        }
}

// Function to save equipment details to Realtime Database
fun saveEquipmentToRealtimeDB(name: String, price: String, description: String, imageUrl: String, context: Context, onComplete: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val ownerId = user.uid
        val dbRef = FirebaseDatabase.getInstance().getReference("equipmentListings").child(ownerId)

        val equipmentId = dbRef.push().key  // Generate unique ID for the listing

        if (equipmentId != null) {
            val equipment = mapOf(
                "ownerID" to ownerId,
                "name" to name,
                "description" to description,
                "price" to price.toInt(),
                "available" to true,
                "imageUrl" to imageUrl
            )

            dbRef.child(equipmentId).setValue(equipment)
                .addOnSuccessListener {
                    Toast.makeText(context, "Upload Successful!", Toast.LENGTH_SHORT).show()
                    onComplete()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    onComplete()
                }
        }
    } else {
        Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
        onComplete()
    }
}

//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.*
//import androidx.core.content.ContextCompat
//
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val cameraPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted ->
//            if (isGranted) {
//                Toast.makeText(this, "Camera permission granted!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Camera permission required!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // Check for camera permission
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//
//        setContent {
//            ListEquipmentScreen() // Launch your Compose UI
//        }
//    }
//}

