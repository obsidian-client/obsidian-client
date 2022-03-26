# Obsidian Client

## Initial Setup

Before you can start doing anything with the Obsidian Client source,
you have to run one of these commands to initialize ForgeGradle:

 - `./gradlew setupDecompWorkspace` For normal development environments, gives you nice deobfuscated Minecraft source to work with.

 - `./gradlew setupDevWorkspace` Like `setupDecompWorkspace`, but with obfuscated Minecraft source.

 - `./gradlew setupCiWorkspace` Bare minimum to build Obsidian Client, ideally for CI servers.

## Build & Run

To build Obsidian Client, run

`./gradlew jar`

or for production purposes you should use this command instead

`./gradlew reobfJar`.

Both commands will also automatically download all dependencies for you.

The output Java Archive will be in `build/libs/`.

To run Obsidian Client, there are two commands you can use:

 - For Vanilla Minecraft, use
    `./gradlew -b build.gradle runClient` or just `./gradlew runClient`
    (You can choose whatever you want, since `build.gradle` actually is the default buildscript).

 - For Minecraft Forge, use
    `./gradlew -b buildForge.gradle runClient`.

Both commands will download all dependencies, compile everything and run the game.

## Setup Development Environment

### IntelliJ Idea

 1. Open IntelliJ and import / open the project by selecting the `build.gradle` file.

 2. Now you have to generate the run configurations yourself using this command `./gradlew genIntellijRuns`.

### Eclipse

 1. Run this command `./gradlew eclipse` to generate the Eclipse project files.

 2. Now you can open the `eclipse` folder inside of Eclipse.

## Contributing

Feel free to contribute to this project by opening a pull request under the following rules:

 1. Write clean and organised code with Javadoc documentation.

 2. Try to adopt existing mechanisms, I don't want to end up 3 `ModuleManagers`.
    But if you want to improve existing mechanisms (like the `ModuleManager`), contact me: <alexander@obsidian-client.com>.

 3. Write code that fits perfectly into the existing one, so nothing looks out of place (especially important for the GUI's).
    I recommend you to take a look at other existing classes (with similar content) for refrence.
