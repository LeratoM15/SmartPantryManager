# Smart Pantry Manager

Smart Pantry Manager is a Java Android application developed for Mobile App Development 700.

The application helps users reduce food waste by tracking ingredients currently available in their pantry and suggesting recipes that can be made using only those ingredients.

## Main Features

- Add pantry ingredients
- View pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- Store ingredient quantity, unit and optional expiry date
- Persistent local storage
- Suggested recipes based on pantry contents
- Strict recipe matching
- Ingredient quantity validation
- Basic unit conversion
- Singular/plural ingredient matching
- Recipe detail screen
- Settings screen
- Bottom navigation

## Strict Recipe Matching

A recipe is suggested only when every ingredient required by the recipe is available in the user's pantry in at least the required quantity.

For example, if a recipe requires:

- 50 g cereal
- 200 ml milk

and the pantry contains:

- 20 g cereal
- 1 L milk

the recipe will not be suggested because the cereal quantity is insufficient.

The matching logic also handles simple differences such as:

- egg / eggs
- tomato / tomatoes
- kg / g
- L / ml

## Database

The application uses SQLite through `SQLiteOpenHelper`.

SQLite was selected because Smart Pantry Manager is designed primarily as an offline, single-user Android application. It provides reliable persistent local storage without requiring an internet connection or external server.

The database stores:

- Pantry items
- Recipes
- Recipe ingredients

The application demonstrates full CRUD functionality for pantry items.

## Technologies

- Java
- Android Studio
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Custom RecyclerView Adapter
- Android Activities
- Intents
- SharedPreferences

## Requirements

- Android Studio
- Android SDK
- Java/JDK
- Minimum Android SDK: API 24

## How to Run

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Start an Android emulator or connect an Android device.
5. Run the `app` configuration.
6. Use the Pantry screen to add ingredients.
7. Open Suggested Recipes to view recipes that strictly match the pantry contents.

## Application Screens

The application contains the following main screens:

- Pantry List
- Add/Edit Ingredient
- Suggested Recipes
- Recipe Detail
- Settings

## Author

Mobile App Development 700 Practical Assignment