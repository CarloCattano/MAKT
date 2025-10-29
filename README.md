[![official JetBrains project](https://jb.gg/badges/official-plastic.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

## Kotlin Native Audio - cinterop with miniaudio library

[DISCLAIMER]: This is a project for _learning_ Kotlin Native cinterop capabilities, _Kotlin_ in general and the gradle build system.
This project is not intended for production use


## Dependencies:
- Miniaudio C needs to be build into a lib for your target platform.

```
git submodule update --init --recursive
cd deps/miniaudio
mkdir build
gcc -O2 -c -fPIC miniaudio.c -o build/miniaudio.o
ar rcs build/libminiaudio.a build/miniaudio.o
```

## Building the project
To build the project, simply run:

```bash
./gradlew build
```
## Running the project
To run the project, use:
```bash
./gradlew run
```

## TODO's

- [x] Tested and working on :penguin:
- [x] Setup gradle cinterop with miniaudio library
- [x] Play a simple audio file (wav, mp3, etc)
- [ ] Record audio from microphone
- [ ] Implement basic audio effects (volume, pan, etc)
- [ ] Create simple CLI application to demonstrate functionality
- [ ] Synthesize audio (sine wave, square wave, etc)
- [ ] Sample playback from memory buffer
- [ ] Write documentation

- [ ] Test on macOS :apple:
- [ ] Test on Windows :windows:

