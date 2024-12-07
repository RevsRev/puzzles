![Build](https://github.com/RevsRev/advent-of-code/actions/workflows/maven.yml/badge.svg)
[![Lint](https://github.com/RevsRev/advent-of-code/actions/workflows/super-linter.yml/badge.svg)](https://github.com/marketplace/actions/super-linter)
![CodeQL](https://github.com/RevsRev/advent-of-code/actions/workflows/codeql.yml/badge.svg)
![JaCoCo](https://github.com/RevsRev/advent-of-code/actions/workflows/jacoco-badge.yml/badge.svg)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Advent of Code

A single project for all my advent of code solutions.

I am still yet to transfer all of my previous years' solutions to this project.

## Usage

Use 'maven' or the provided wrapper to build the project:
```shell
mvn clean package
```
Run the jar using:
```shell
java -jar target/advent-of-code-1.0-SNAPSHOT-jar-with-dependencies.jar
```

This will run all implemented solutions. Specific solutions can be run using additional command line options.

### Arguments

| Option Name   | Short Name | Argument       | Description                                                                                        |
|---------------|------------|----------------|----------------------------------------------------------------------------------------------------|
| help          | h          |                | Prints a help message                                                                              |
| problem       | p          | `yyyy:dd`      | Solve the problem identified by its year and day                                                   |
| problem-bound | pb         | `yyyy:dd`      | When used, solve all problems between `problem` and `problem-bound` (inclusive)                    |
| part          | pt         | `ONE` or `TWO` | Which part of a problem to solve. If omitted, both parts are solved                                |
| visualise     | v          |                | Visualise a problem (e.g. display a graph), if visualisation has been implemented for that problem |
| debug         | d          |                | Use debug logging                                                                                  |

e.g. to solve all problems between day 3 and 7 (inclusive) in 2024:
```shell
 java -jar target/advent-of-code-1.0-SNAPSHOT-jar-with-dependencies.jar -p 2024:07 -pb 2024:03
```
(Note: There is no requirement for `pb` to be less than `p`, specifying in either order is acceptable).

To display a pretty picture of the graph from 2023:20:
```shell
java -jar target/advent-of-code-1.0-SNAPSHOT-jar-with-dependencies.jar -p 2023:20 -v
```