
# **Test Calculator** #

**Overview**
--

The Java program seeks to, given two equations, find an intersection between two given x values. The equations are limited to polynomials, rational functions, exponential functions, and any combination of the previous types. 

Many common operators, constants, and functions have not been defined. Unwritten constants include:

- pi
- phi and little phi
- imaginary unit i
  
Unwritten operators include :

- Modulus (%)
- Factorial (!)

Unwritten functions include:

- Trigonometric functions (sin, cos, tan, csc, sec, cot) and hyperbolic counterparts
- Logarithmic functions (log, log2, ln)
- Floor and ceiling 
- Custom piecewise functions
- Limits 
- Infinite sums and products
- nth-derivative (d/dx)
- Definite integrals (int_{}^{}()dx)
- Literally every function you could think of

Because of these limitations, several planes and forms have not been considered:

- Polar coordinates
- Spatial dimensions above the first two
- The complex plane
- Equations in parametric form
- Vectors.

**Input**
--

The program itself is in the main method within the Environment class. The output is in command line.


**Technical Stuff**
--


**Trees:**
A vast majority of this project banks on binary trees (some form of an AST, but I dont know what its actually called because I'm not working off of a source outside of one image I saw).
All equations are rewritten as trees and all equations are solved as trees. 

The equations are written as MathEqs, which extend from Tree\<T\>. MathEqs rely on MathChars that act as nodes. MathChars extend from Node\<T\>.

**Other Trees:** 
In the creation of the equation parser, I found a need to neatly organize nested expressions. As a result, I created the class called Tower. The Tower class creates what is essentially a non-binary tree, so rather than extend from Tree, it is standalone.

The Tower class differs from the Tree class because it does not explicitly talk about nodes as being children or parents, though the relationship certainly exists. A Tower is more focused about the relationship between layers of nesting and members of the same layer.

In the Tower class, I make mention to "floors." Floors are another way of thinking about nesting layers (Floors in a tower). 

Towers are created from one string and a pair of delimiters. As such, there is no way of adding nodes or removing nodes, nor would there be a simple and efficient way of doing this. 


 ---
Example: You create a tower using the string
"hello qqworldww" with delimiter pair "qq" and "ww". The Tower would look like
```
    FLOOR NUMBER 0: [hello ##[0]##]
    FLOOR NUMBER 1: [world]
```
This is interpreted as the first floor having "hello ", followed by whatever is at the 0th index on the floor above it. As a result of this, the string can be easily rebuilt by gathering information from higher floors, while also maintaining the nesting the user originally set.


**Exceptions:** In this same folder as this README contains two exception files. These exception files exist to more effectively communicate errors in user inputs. I did not write them well.

**Datatypes:** The eleven classes together use an absurd number of java libraries. Please don't take points off for my not building a custom ArrayList, HashMap, and RegEx classes. 

The Node and Tree classes were, if I am not mistaken, build as generic types. Inspired by the HashMap and ArrayList constructor syntax, I wanted to understand the angle bracket usage. As a result, the Node and Tree classes are generic (denoted by \<T\>), though they're most commonly used as Node\<String\> and Tree\<String\>

**Other Inconsistencies:**
In order to more closely line this project up with the originally assigned task, The Point and Line classes have been readded into this project. They have been modified to create an output that can be interpreted by the MathEq constructor, but lack the depth that the MathEq class can provide. As a result, the Line class can do nothing more than more efficiently provide information on the line's slope. 

**Notes**
--

**Functions:** 
Though not implemented because of time restraints, the groundwork for functions already exists. Their planned use would be to interpret the equation and replace them with Unicode characters outside of numbers 65 to 122 inclusive. Specific codes would represent specific functions.

**Non-functions:** Mostly a reference to circles, ellipses, and hyperbolas; they have multiple y values for each x value. I have not thought up of a more effective solution than brute forcing y values after brute forcing x values (would require four bounds and have an unacceptable time complexity).
