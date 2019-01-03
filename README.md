# DoordashLite

The app allows user to browse a list of restaurants near Doordash HQ.

For the purpose of test app, the Doordash HQ is hardcoded to lat = 37.422740 and lng = -122.139956

The app uses Android Architecture Components and is based on MVVM without databinding. The app also uses Repository pattern to fetch the data from network and cache as seem fit.

The app does not uses persistent caching strategy(SQLite, ORMLite, Room) since the UI needs to show status of restaurant which cannot be outdated. Hence, the app won't work in offline mode and shows appropriate error if not connected to the internet.

The app uses in memory cache and stores the list of restaurants when fetched from the network for the first time. The list is maintained for 30 seconds and is configurable. After 30 seconds, the cache is cleared and the restaurants are fetched again. This allows the user to quickly navigate between the list of restaurants and restaurant details screen.

## Supporting screen size
The app should be able to run on all phones of different screen sizes. 

The app is not configured to run on tablets with screen size 7" to 10". It can be configured by creating different layout files for the tablet.

## Build Variants
The app has 3 different build variants:

1.`debug` - This serves as the dev build

2.`debugProguard` - This serves as the dev build as well but it builds the artifacts with proguard flag on. This way it allows the developer to compile and build the app with proguard and make sure that proguard doesn't throws any errors before creating a release build.

3.`release` - This serves as the release build and should be used to release the software on the play store.

# Dev Tools
The app is configured to support different tools:

1. `detekt` - This tool allows the developer to maintain the same code style throught the project.

2. `jacoco` - This tool can be used to run all the unit and instrumentation test cases and code coverage as well.

For example: *./gradlew clean assembleDebug detektCheck jacocoReport*

*Note: The app hasn't run these tools and made changes based on the suggestions given by the tool. The aim of integrating these tools was to just show that how easily these can be added to the deployment/CI tools and generate results automatically.*
