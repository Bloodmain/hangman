# Console version of game "Hangman"

The player tries to guess the hidden word by entering letters one at a time. 
The word is chosen by difficulty level and category that can be configured in the game menu. 
The number of attempts is limited, and for each incorrect guess, a part of the gallows and a figure of the hanged man are visualized.

## Some features:
 - Implementation of state machine that can be expanded with more state of the game
 - Smooth visualisation of hanged man (i.e. the number of his parts shown based on the ratio of incorrect answers to possible attempts)
 - Configurable category, difficulty (attempts and the words)
 - Realization of hints
