# TTO
Options
=======
required/requires|Short|Long|Arguments|Description
---|---|---|---|---
||`-h`|`--help`||Display help/usage information
|`required`|`-F`|`--File`|`file path`|Path with name of file
||`-T`|`--Table`||Print table of contents
||`-N`|`--NoSubSections`||Print without subsections
||`-S`|`--Section`|i.e. `1`, `II`, `1-2`, `1a-1b`|Index or range of Sections to print (works with table of contents)  
||`-C`|`--Chapter`|i.e. `1`, `II`, `1-2`, `1a-1b`|Index or range of Chapters to print
||`-A`|`--Article`|i.e. `1`, `II`, `1-2`, `1a-1b`|Index or range of Articles to print
|`--Article`|`-p`|`--Point`|i.e. `1`, `II`, `1-2`, `1a-1b`|Index or range of Points to print
|`--Article`|`-s`|`--SubPoint`|i.e. `1`, `II`, `1-2`, `1a-1b`|Index or range of SubPoints to print
|`--Article`|`-c`|`--Character`|i.e. `1`, `II`, `a-b`|Index or range of Charaters to print

**Range can only be given to the last of sections i.e. <span style="color:red">`-A 1-2 -p 1-2`</span> is wrong, `-A 1 -p 1-2` will work**
