# smart-dog-bed

Code for the [Smart Dog Bed project in Hackster.io](https://www.hackster.io/xrigau/smart-dog-bed-1c9737)

## IoT app (Android Things) - things

This is an android things app that reads data from a load cell sensor and stores the results in the Firebase Database.

<img width="301" alt="Firebase Database updating in real time" src="https://user-images.githubusercontent.com/1626673/32262460-11f27220-bed5-11e7-8447-2d37ff6eaf38.png">

This app can be installed by running `./gradlew things:installDebug` or by installing it directly from Android Studio.

## Companion app (Android) - mobile

This is a regular android app that fetches the results from the Firebase Database (created from the android things device) and displays them.

<img width="360" alt="Very simple demo app" src="https://user-images.githubusercontent.com/1626673/32262461-120a5a34-bed5-11e7-9437-f1a69968f286.png">

This app can be installed by running `./gradlew mobile:installDebug` or by installing it directly from Android Studio.

---

For more instructions follow the [instructions in the Hackster.io project page](https://www.hackster.io/xrigau/smart-dog-bed-1c9737)
