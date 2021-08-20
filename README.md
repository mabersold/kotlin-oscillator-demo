# Kotlin Oscillator Demo
### by Mark Abersold
https://www.linkedin.com/in/markabersold/

## Description

A simple demonstration of sound synthesis using kotlin, running on the JVM.

## How to run

If you have gradle installed on your system, simply use the default build and run tasks.

    gradle build
    gradle run

If you do not have gradle installed, you can instead use gradlew or gradlew.bat (if you are on a Windows system) included in the root directory.

    gradlew build
    gradlew run

## How it works

The heart of the demo is the oscillator package, which includes the mathematical functions used to generate a signal. The oscillators will generate a series of doubles between the values of -1 and 1.

The song package contains data classes used to represent an actual song - in this case, the Super Mario Bros. theme music. It consists of the following:
* **Song**: A list of instruments and a beats per minute value.
* **Instrument**: Includes the type of oscillator this instrument will use, a list of phrases, a sequence that determines the order the phrases are played in, a decay value to determine how quickly the instrument should fade after a note is played, and (for square wave instruments) a pulse width value.
* **Phrase**: Not a data class since it also includes functions. Think of a phrase as several measures of a song. Its length is determined in the constructor in beats, and each beat has six positions before the next beat. Each position can contain a single note value.
* **Note**: An instruction to play a note at a specific pitch, volume, and stereo panning level.

The pcm package contains generators for songs, instruments, and notes. A song has a single song audio generator. The song audio generator has one instrument audio generator for each instrument. When playing the song, the song audio generator will request pcm data from each instrument audio generator. The instrument audio generators each contain an oscillator, and it uses the oscillator data combined with the volume, decay, and stereo panning to produce the correct sample sequence. It will continue through the song until it completes, and then terminate.

The note audio generator is used to generate audio for a single note, which you can call by passing in a command line argument of "1" when running.

I don't have any intention of continuing this project beyond this point, as I am satisfied with its progress and would like to move on to my next project. If you have any questions or comments, connect with me on LinkedIn.