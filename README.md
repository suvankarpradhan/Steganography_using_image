# Steganography_using_image

This is a research oriented project based on Java.

## Table of contents

- [Overview](#overview)
  - [Features](#Features)
- [My process](#my-process)
  - [Built with](#built-with)
- [Author](#author)

## Overview

Steganography is the art of hiding the fact that communication is taking place, by hiding information in other information. In this project, a data hiding scheme by simple LSB substitution is proposed.

### Features

Users should be able to:

- Hide or conceal a text message under an image
- Decrypt or unhide the file from the image

## My process

*****LSB Method******
- Get the input cover image and message.
- Get Alpha, Red, Green, and Blue value of each pixel and store into array separatly.
- Take each character from message and convert into ASCII value.
- Divided total no of pixel by message length to store each character.
- Short the block of pixels in ascending order due to their difference with character.
- Select 1st pixel of shorted pixels.
- Shift the 1st 3 bit of message to pixel values over 3 bits to right.
- Select 2nd pixel of shorted pixels.
- Shift the 2nd 3 bit to message to pixel values over 3 bits to right.
- Select 3rd pixel of shorted pixels.
- Shift the rest 2 bit of message to pixel values over 2 bits to right.
- Recycle 3 to 11 until the character is not null.
- Store the select pixel index to file.
- Reconstruct the stego image from array.
- Take the generated password.

*****Message Retrieve procedures******
- Take stego image and keyfile and password as input.
- Check password is correct or not.
- If correct get the pixel index value from file.
- Collect the Least 3 bit, 3 bit, 2 bit from selected pixels respectively.
- Construct the word of message.
- Until the pop up value of file does not reach at the end so 4-5 simultaneously.

### Built with

- Java JDK(7)
- Swing

## Author

- Frontend Mentor - [@suvankarpradhan](https://www.frontendmentor.io/profile/suvankarpradhan)
- github - [@suvankarpradhan](https://github.com/suvankarpradhan)
