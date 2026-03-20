🚜 FarmCab

FarmCab is a mobile application built with Kotlin + Jetpack Compose to help farmers rent and lease agricultural equipment (like cultivators, rotavators, tractors) in a simple, affordable, and transparent way.
Think of it as Uber/Ola for farm equipment — connecting those who need tools with those who own them.

✨ Features
👥 Two User Roles

Equipment Owners:
List equipment with name, photo, description, price, and availability

Renters:
Browse and request available equipment

🔥 Core Features

Firebase Integration:

Authentication (Sign up, Login, Email Verification)

Realtime Database for storing equipment details

Firebase Storage for storing images

Modern UI using Jetpack Compose

Green color palette (0xFF66BB6A)

Grid-style home screen (image on top, details below)

Search & category filtering

Stored separately in Firebase for quick access

Scalable structure

Ready for future real-time location matching

📱 Tech Stack

Kotlin + Jetpack Compose (UI)

Firebase Authentication (Email & Password + Verification)

Firebase Realtime Database (Equipment Listings)

Firebase Storage (Image Hosting)

Coil (Image Loading in Compose)

MVVM Architecture (Clean state management)

📂 Firebase Database Structure
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
🧠 Database Design Highlights

Owner-based structure for efficient equipment and booking management

Category-based indexing for fast filtering

Denormalized data model for optimized read performance

Separate bookings node to handle rental workflow

🚀 Future Enhancements

📍 Location-based equipment matching

💬 In-app chat system

⭐ Ratings & reviews

💰 Bidding system

🔔 Push notifications
