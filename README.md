# Function App - Numeric Integration and Fourier Series Project:

## *About the project:*
**The goal of this project was to create a program that can take in a set of functions the user inputs, perform basic
operations involving them (addition, subtraction, products, quotients, and composition), and then 
compute their Fourier Series (odd, even and full range) on a symmetric real interval [-l, l] specified by the user.**

This project, begun initially as apart of coursework for CPSC 210 at UBC, is of particular interest to individuals who
either aspire to become, or are currently involved inscience, especially engineers, mathematicians, and physicists, who
make use of Fourier series in a variety of methods (solutions to separable Partial Differential Equations is a key 
example). This project is also useful for teachers and professors to aid students with visualization, along with providing
said students a user-friendly and interactive program to explore how functions are represented through their Fourier 
series, and save functions they have previously worked with for future use. This project may also be of interest to a 
curious music theorist who is wanting to explore acoustics and modes of vibration, which are closely related to 
Partial Differential Equations, where Fourier Series often turn up.

Being in the Physics and Mathematics combined honours program, and having been exposed to and used Fourier Series in a 
variety of my courses (Electricity and Magnetism, Quantum Mechanics, Partial Differential Equations, and Mathematical 
Analysis), I have always been amazed at how significant visual aids can be at conveying mathematical
intuition, especially being an avid fan of the popular YouTube channel 3Blue1Brown. I feel that having an easy to 
approach and user-friendly program to explore Fourier series is something I would have found quite useful, and I also 
hope it inspires aspiring Physicists and Mathematics undergraduates who, like me, may not have much coding/programming 
experience to nevertheless create their own programs and further inspire younger students. 


## *Instructions:*
*To use the program:*

- To view the worklist and functions inside, select the "View your Worklist" button.
- To add a function to the worklist, select the "Add a basic function to your worklist" button.
- To make more complicated functions, select the "Make more complicated functions" button.
- To remove functions from the worklist, select the "Remove functions from your worklist" button.
- To analyze the functions in the worklist, select the "Analyze (evaluate, integrate etc.) the function in your
worklist" button.
- To save your current worklist and settings, select the "Save your current worklist and settings" button.
- To load the previous worklist and settings, select the "Load the previously saved worklist and settings" button.
- To view or change settings, select the "View or change settings" option.

An image can be found at the start menu when the GUI is first run. The image is of a function that is continuous but
nowhere differentiable.

## *Additional information:*
The GUI is set up so that any invalid inputs are handled by default:
- For most function parameters, these are 0 (for example inputting abc as the coefficient in a polynomial produces 0).
- For integration, if any invalid parameters are entered for the interval, I default to outputting the integral of the
function on the interval [-1,1]. 
- For Fourier Series, if invalid inputs are entered, I default to computing the first 3 terms of the full
fourier series on [-1,1]. 
- In general, I do try and account for negative inputs when and where it can be done. So if a user enters -3 for the 
degree of a polynomial, this gets corrected to 3, as degree is normally understood to be positive. This was done to help
users in the case of typos.

## *Action items:*
- Improve documentation
- Add GUI for function visualization
- Explore more functionality
- Introduce LaTeX typesetting and parsing for function names
- Explore porting project over to Python potentially


## *Possible refactoring:*
- Change the left and right fields in the Function class to be a single field, parent, being of type List<Function>. 
This would avoid having to check for each field being null - an empty list signifies a child node, with two elements in
the list otherwise storing the left and right function respectively
- In terms of design, I would make an abstract class that handles function with 3 fields, and not just one for trig 
functions. This would allow for easier implementations of functions such as the Logarithm, which also essentially only 
take 3 key parameters.

## *Acknowledgements:*
- The Phase 1 Console UI was heavily inspired by the CPSC 210 teller app, with its functionalities extended
  and modified for the purposes of this project.
- The Phase 2 Persistence was heavily inspired by the CPSC 210 JsonSerializationDemo app, with its read write
  features adjusted for the FunctionApp.

