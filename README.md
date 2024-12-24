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

## Output

The program will output a nicely formatted table of solutions:

```shell
|------|------|--------------------|--------------------|--------------------|--------------------|--------------------------------------------------|
| Year | Day  | PartOne            | PartOneTime (ms)   | PartTwo            | PartTwoTime (ms)   | Error                                            |
|------|------|--------------------|--------------------|--------------------|--------------------|--------------------------------------------------|
| 2023 | 1    | 54338              | 7                  | 53389              | 20                 |                                                  |
| 2023 | 2    | 3035               | 5                  | 0                  | 0                  |                                                  |
| 2023 | 20   | 879834312          | 23                 | 0                  | 0                  |                                                  |
| 2024 | 1    | 2580760            | 7                  | 25358365           | 4                  |                                                  |
| 2024 | 2    | 356                | 2                  | 413                | 4                  |                                                  |
| 2024 | 3    | 184576302          | 4                  | 118173507          | 1                  |                                                  |
| 2024 | 4    | 2642               | 2                  | 1974               | 1                  |                                                  |
| 2024 | 5    | 7307               | 17                 | 4713               | 14                 |                                                  |
| 2024 | 6    | 4752               | 11                 | 1719               | 44                 |                                                  |
| 2024 | 7    | 1153997401072      | 22                 | 97902809384118     | 290                |                                                  |
| 2024 | 8    | 222                | 2                  | 884                | 0                  |                                                  |
| 2024 | 9    | 6154342787400      | 7                  | 6183632723350      | 43                 |                                                  |
| 2024 | 10   | 548                | 1                  | 1252               | 0                  |                                                  |
| 2024 | 11   | 203457             | 2                  | 241394363462435    | 44                 |                                                  |
| 2024 | 12   | 1457298            | 73                 | 921636             | 29                 |                                                  |
| 2024 | 13   | 26005              | 8                  | 105620095782547    | 2                  |                                                  |
| 2024 | 14   | 214109808          | 2                  | 7687               | 7                  |                                                  |
| 2024 | 15   | 1559280            | 5                  | 1576353            | 4                  |                                                  |
| 2024 | 16   | 89460              | 32                 | 504                | 24                 |                                                  |
| 2024 | 17   | -1                 | 2                  | 106086382266778    | 2                  |                                                  |
| 2024 | 18   | 312                | 12                 | 3038               | 3242               |                                                  |
| 2024 | 19   | 272                | 6                  | 1041529704688380   | 5                  |                                                  |
| 2024 | 20   | 1404               | 28                 | 1010981            | 197                |                                                  |
| 2024 | 21   | 176870             | 2                  | 223902935165512    | 3                  |                                                  |
| 2024 | 22   | 17960270302        | 13                 | 2042               | 1704               |                                                  |
|------|------|--------------------|--------------------|--------------------|--------------------|--------------------------------------------------|

```

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

To display a pretty picture of the graph from 2024:24:
```shell
java -jar target/advent-of-code-1.0-SNAPSHOT-jar-with-dependencies.jar -p 2023:20 -v
```
![2024:24](/img/2024-04-24:060440_cropped.png)