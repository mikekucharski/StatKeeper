StatKeeper
==========

This is an android application used to store baseball statistics.

## Errors when cloning?

Missing required source folder: 'gen'
  + Right click on project -> Properties -> Android -> Select a Project Build Target. Click OK. 
  + You might have to clean and refresh the project as well.

gen already exists but is not a source folder
  + 1. Right click on the project and go to "Properties"
  + 2. Select "Java Build Path" on the left
  + 3. Open Libraries "tab"
  + 4. Add an external JAR (android-sdk\platforms\android-currentversion)

## Where is the data stored?

Locally. The current version of the app stores the baseball stats only on the phone.

Advantage - You don't need a persistent internet connection to use the app.
Disadvantage - If you drop your phone in a lake, you lose all of your stats.

## Don't you also have a web application for tracking baseball statistics on here?

Yes, but currently the web application is independent of the app. In the future, I'd like to host the website and connect to the same database with this app.