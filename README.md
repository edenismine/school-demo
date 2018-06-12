# school-demo
School system demo app that uses Bootiful Kotlin.

## Prerequisites

In order to build and run the project there a few things that need to be done:

1. Install [MongoDB](https://www.mongodb.com/download-center?jmp=nav#community). If you're using **ubuntu** you can follow [these instructions](https://docs.mongodb.com/master/tutorial/install-mongodb-on-ubuntu/).
2. Configure the `MONGODB_URI` system variable and set it to your local mongo server's URI, an example is shown below.

```sh
MONGODB_URI="mongodb://localhost:27017/school-demo"; <your build or run command>
```

## Building and running the program

Here are some one-liners to get you started:

Running the server is as easy as running the following command from the project's root folder:

```sh
./gradlew bootRun
```

Compiling the source code is equally easy:

```sh
./gradlew build
```