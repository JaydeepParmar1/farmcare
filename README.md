# 🚜 FarmCab

> **Uber/Ola for farm equipment** — connecting farmers with equipment owners in a simple and affordable way.

FarmCab is an Android application built using **Kotlin + Jetpack Compose** that allows farmers to rent agricultural equipment like cultivators, rotavators, and tractors.

---

## ✨ Features

### 👥 User Roles

**🔹 Equipment Owners**
- List equipment with name, image, description, price, and availability  

**🔹 Renters**
- Browse equipment and send rental requests  

---

## 🔥 Core Functionalities

### 🔐 Authentication
- Sign Up / Login  
- Email Verification  

### ☁️ Firebase Integration
- Realtime Database (store equipment & bookings)  
- Firebase Storage (image upload)  

### 🎨 Modern UI
- Built with Jetpack Compose  
- Clean grid layout (image + details)  
- Green theme (`#66BB6A`)  

### 🔍 Search & Filtering
- Category-based filtering  
- Optimized for quick access  

### 📈 Scalable Design
- Ready for location-based matching (future scope)  

---

## 📱 Tech Stack

- **Kotlin + Jetpack Compose**  
- **Firebase Authentication**  
- **Firebase Realtime Database**  
- **Firebase Storage**  
- **Coil (Image Loading)**  
- **MVVM Architecture**  

---

## 📂 Firebase Database Structure

```plaintext
bookings/
    ownerId/
        bookingId/
            bookingDate
            equipmentId
            equipmentName
            ownerId
            ownerName
            ownerContact
            renterId
            renterName
            renterContact
            status        // pending | accepted | rejected | completed
            timestamp

equipmentListings/
    ownerId/
        equipmentId/
            name
            description
            category
            price
            imageUrl
            ownerId
            ownerName
            contactNumber
            equipmentID

categoryListings/
    categoryName/
        equipmentId/
            name
            description
            category
            price
            imageUrl
            ownerID
            ownerName
            contactNumber

users/
    userId/
        username
        email
        contact
