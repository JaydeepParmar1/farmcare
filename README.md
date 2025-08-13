# FarmCab 🚜

FarmCab is a mobile application built with **Kotlin + Jetpack Compose** to help farmers rent and lease agricultural equipment (like cultivators, rotavators, tractors) in a simple, affordable, and transparent way.  
Think of it as **Uber/Ola for farm equipment** — connecting those who need tools with those who own them.

---

## ✨ Features
- **Two user roles**:
  - **Equipment Owners**: List equipment with name, photo, description, price, and availability.
  - **Renters**: Browse and request available equipment.
- **Firebase Integration**:
  - **Authentication** (Sign up, Login, Email Verification)
  - **Realtime Database** for storing equipment details
  - **Firebase Storage** for storing images
- **Modern UI** using Jetpack Compose with a **green color palette** (`0xFF66BB6A` and related shades)
- **Grid-style home screen** with images on top and details below
- **Search & category filtering** (stored separately in Firebase for quick access)
- **Scalable structure** for adding real-time location matching in the future

---

## 📱 Tech Stack
- **Kotlin** with **Jetpack Compose** (UI)
- **Firebase Authentication** (email & password, email verification)
- **Firebase Realtime Database** (equipment listings)
- **Firebase Storage** (image hosting)
- **Coil** (image loading in Compose)
- **MVVM Architecture** (for state management and cleaner code)

---

## 📂 Firebase Structure
### Equipment Listings
