# CPSC 210: Function App (Fourier Series) Project:

## *About the project:*
**The goal of this project is to create a program that can take in a set of functions the user inputs, perform basic
operations involving them (addition, subtraction, products, quotients, and to some extent composition), and then 
compute the Fourier Series (odd, even and full range) for these functions on an interval specified by the user.**

This project is of particular interest to individuals who either aspire to become, or are currently involved in
science, especially engineers, mathematicians, and physicists, who make use of Fourier series in a variety of methods 
(solutions to separable Partial Differential Equations is a key example). This project is also useful for teachers and 
professors to aid students with visualization, along with providing said students a user-friendly and interactive 
program to explore how functions are represented through their Fourier series, and save functions they have previously 
worked with for future use. This project may also be of interest to a curious music theorist who is wanting to explore
acoustics and modes of vibration, which are closely related to Partial Differential Equations, and hence the Fourier 
Series. 

Being in the Physics and Mathematics combined honours program, and having been exposed to and used Fourier Series in a 
variety of my courses (Electricity and Magnetism, Quantum Mechanics, Partial Differential Equations, and Mathematical 
Analysis), I wanted to use the CPSC 210 course project as an avenue to explore this passion project, that I might not 
have worked on otherwise. I have also always been amazed at how significant visual aids can be at conveying mathematical
intuition, especially being an avid fan of the popular YouTube channel 3Blue1Brown. I feel that having an easy to 
approach and user-friendly program to explore Fourier series is something I would have found quite useful, and I also 
hope it inspires aspiring Physicists and Mathematics undergraduates who, like me, may not have much coding/programming 
experience to nevertheless create their own programs and further inspire younger students. 

## *Stretch Goals:*
Part of designing a project for CPSC 210 is understanding the feasibility of implementing certain functionality, and to
not underestimate the implementation of certain functionality. The initial objective was to create a Fourier Transform
visualization tool, which I felt would have been rather difficult to implement at a level I deemed satisfactory. I
therefore would like to have this as a stretch goal, and to aim for visualizing the Fourier Transform of a small subset
of functions for my final submission in Phase 4. For a more moderate stretch goal, I want to implement functionality 
to determine how many terms in a Fourier Series one would need to approximate a function in their worklist at a 
particular value to a specified degree of accuracy. I would also like to graph functions and parse function names in
LaTeX if possible.

## *User Stories:*
A requirement of the CPSC 210 Project is to consider user stories when designing our projects. Below is a list of user
stories that I feel may be relevant to my project, and what a user interacting with this program may think as they use
it:

- As a user, I want to be able to add multiple **functions** (X) to my analysis **worklist** (Y).
- As a user, I want to be able to view and modify functions already in my worklist.
- As a user, I want to be able to choose a function in my worklist, and compute the first n terms of its Fourier Series
on an interval, ideally of my choosing.
- As a user, I want to compare one function to another, and see how well it approximates the other within some
precision. Since the first n terms of a Fourier Series are also a function, this can be used to determine how accurate
the series is at approximating the function.
- As a user, I want to be able to save the current worklist, to access at a later time.
- As a user, I want to be able to load the last saved worklist, to interact with via the application.

## *Achieved Stretch Goals:*
- Adjusted the polynomial class to correctly represent the degree of a polynomial.

## *Acknowledgements of code used in the course:*
- The Phase 1 Console UI was heavily inspired by the provided CPSC 210 teller app, with its functionalities extended 
and modified for the purposes of this project. 
- The Phase 2 Persistence was heavily inspired by the provided CPSC 210 JsonSerializationDemo app, with its read write
features adjusted for the FunctionApp.

## *Instructions for Grader:*
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

*Additional information:*
The GUI is set up so that any invalid inputs are handled by default:
- For most function parameters, these are 0 (for example inputting abc as the coefficient in a polynomial produces 0).
- For integration, if any invalid parameters are entered for the interval, I default to outputting the integral of the
function on the interval [-1,1]. 
- For Fourier Series, if invalid inputs are entered, I default to computing the first 3 terms of the full
fourier series on [-1,1]. 
- In general, I do try and account for negative inputs when and where it can be done. So if a user enters -3 for the 
degree of a polynomial, this gets corrected to 3, as degree is normally understood to be positive. This was done to help
users in the case of typos.

## *Phase 4: Task 2:*
Launch the program
*Actions Done:*
- view the empty worklist
- load the preset worklist, with precision 1.0E-10, subintervals to 5000, and functions 1.0 + 1.0 * x^1 and 
1.0 * e^(1.0 * (x - 0.0)) added to the worklist
- viewed the now non-empty worklist
- remove 1.0 + 1.0 * x^1 from the worklist
- Select analyze ->  1.0 * e^(1.0 * (x - 0.0)) -> numerically integrate -> a = 0 and b = 5, should output e^5 - 1
Close the program.

*Log of Events expected (Dates are arbitrary):*
Wed Apr 12 19:10:35 PDT 2023
Viewed the worklist when it was empty.
Wed Apr 12 19:10:37 PDT 2023
Set precision to 1.0E-10.
Wed Apr 12 19:10:37 PDT 2023
Set number of subintervals to 5000.
Wed Apr 12 19:10:37 PDT 2023
Added 1.0 + 1.0 * x^1 to the worklist.
Wed Apr 12 19:10:37 PDT 2023
Added 1.0 * e^(1.0 * (x - 0.0)) to the worklist.
Wed Apr 12 19:10:42 PDT 2023
Viewed the worklist.
Wed Apr 12 19:10:48 PDT 2023
Removed 1.0 + 1.0 * x^1 to the worklist.
Wed Apr 12 19:10:59 PDT 2023
Integrated 1.0 * e^(1.0 * (x - 0.0)) from x = 0.0 to x = 5.0, yielding 147.4131591025774




