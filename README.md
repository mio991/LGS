# Simple linear System Solver

## File Formats

### The .mat Format

Matricies can be stored in regular Text files complacent to the following Format:

- row count first as an Integer
- column count second also as an Integer
- Followed by row count times column count decimal numbers contained in the Matrix
- Everything separeted by whitespace characters like ' ' or '\\n'

### The .lgs Format

This Format essentialy specifys two .mat files concatenated and separated by a line break. Also in the concatenated file is the line break character not allowed in the single .mat parts. It's expected that the n\*n Coefficints Matrix stands first followed by an n\*1 Constants Vector.
