# Api Widgets

An api that stores and retrieves widgets.

## Building/Running the Project

### Pre-Requisites

1. Java 8

### Running in intellij

Project must be imported into intellij as a gradle project.

#### Building the Project

1. Building can be accomplished by clicking the hammer icon in intellij.

#### Running Tests

1. Right click on test package and select the run test option from the context menu

#### Running the API

1. Click the debug or play button in intellij with the ApiWidgetApplication config selected.  You can also open the
    ApiWidgetApplication.java file and there should be a play button straight left of the class declaration in the gutter.


### Running via terminal

Commands below can be executed in linux and mac with a properly configured JVM.  Windows users will need to use
the gradlew.bat instead of gradlew.

#### Building and running tests

```
./gradlew clean build
```

#### Running the API

```
./gradlew clean bootRun
```


