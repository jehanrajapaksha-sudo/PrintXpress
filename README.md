# PrintXpress

Android app for ordering custom print products such as business cards, posters, t-shirts, mugs, banners, flyers, and stickers.

Built as a Mobile Application Development project (ICBT, Semester 03).

## Features

- Shop print products and open a product detail page
- Customize an order (quantity, paper type, delivery option, custom text, and file upload)
- Register, log in, and manage a session
- View order history
- Browse a design gallery and save designs
- Save delivery addresses
- Profile screen with order history, saved designs, and addresses
- Local data stored with Room (`printXpress_db`)

## Requirements

- Android Studio
- JDK 11
- Android device or emulator running API 28 or higher

## How to run

1. Clone the repository:
   ```bash
   git clone https://github.com/jehanrajapaksha-sudo/PrintXpress.git
   ```
2. Open the project folder in Android Studio.
3. Let Gradle sync finish.
4. Run the app on an emulator or a connected Android device.

## Tech stack

- Java
- Android SDK (min 28, target 34)
- AndroidX AppCompat, Material, ConstraintLayout
- Room database
- Bottom navigation with fragments
