## **Portfolio Generator**

## Overview

The portfolio generator application uses Java object oriented concepts and design principles and patterns to enable users to create visually appealing portfolios with modern UI design. The application allows users to input their personal information and project details through an intuitive web-based form. Upon submission, the application generates a dynamic HTML portfolio page based on the user's input, incorporating modern design and a contemporary layout. Users have the option to choose from multiple pre-designed templates to customise the appearance of their portfolios. 

## Features

- **User Input**: Collects user information such as name, email, phone number, project title, project description, and project link.
- **HTML Generation**: Utilizes the **Factory Method** pattern to generate HTML content based on selected style options (Basic or Fancy).
- **Color Customization**: Implements the **Strategy** pattern to allow users to choose from different color options (Default, Red, or Blue) for the portfolio.
- **HTML File Export**: Writes the generated HTML content to a file named "portfolio.html" in the current directory.


## Installation

To run the Portfolio Generator, ensure you have the following installed:

- Java Runtime Environment (JRE)
- Default Java Development Kit (JDK)
- Java Extension for Visual Studio Code

## Usage

1. Compile the Java code: `javac Main.java`
2. Execute the compiled code: `java Main`
3. Fill in the required details in the GUI.
4. Select HTML style and color options.
5. Click on "Generate Portfolio" to create the HTML file.



## License

This project is licensed under the [MIT License](LICENSE).
