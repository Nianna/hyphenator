[![Java CI](https://github.com/Nianna/hyphenator/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Nianna/hyphenator/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-17-informational)
![GitHub](https://img.shields.io/github/license/nianna/hyphenator)

# hyphenator

Simple implementation of text hyphenation using pattern approach described in Franklin Mark Liang's thesis "Word Hy-phen-a-tion by Com-put-er".

_Hyphenator_ preserves the case of the original text and hyphenates words with non-alphabetic characters as long as they are not placed in the middle of the words.

## Requirements
To use this tool you need a list of hyphenation patterns for the desired language.
It can be downloaded from [TeX hyphenation repository](https://github.com/hyphenation/tex-hyphen/tree/master/hyph-utf8/tex/generic/hyph-utf8/patterns/txt).
Choose the *.pat.txt file.

Alternatively, you can download a dictionary file e.g. from [LibreOffice repositories](https://github.com/LibreOffice/dictionaries).
In this case choose file with "hyph" prefix e.g. hyph_pl_PL.dic for Polish language. 
Make sure to remove the tags at the beginning of the file and only pass the patterns themselves to the _Hyphenator_.
```
UTF-8  <---- encoding info, use it to load file
LEFTHYPHENMIN 2  <------ this value can be passed to the Hyphenator as minLeadingLength
RIGHTHYPHENMIN 2  <------ this value can be passed to the Hyphenator as minTralingLength
.ć8    <--- pattern
.4ć3ć8
.ćł8
.2ć1ń8
```

## Example usage

### Adding dependency
Library is published to maven central and can be added to your project in the standard way:
```
<dependencies>
  ...
  <dependency>
    <groupId>io.github.nianna</groupId>
    <artifactId>hyphenator</artifactId>
    <version>1.0.1</version>
  </dependency>

</dependencies>
```

### Hyphenation with default settings
Input text is automatically split into tokens. 
By default the first and last chunk after hyphenation must be at least 2 characters long.
Space is used as word separator and hyphen as syllables separator.
```java
List<String> patterns = ... // load the patterns from the patterns file
Hyphenator hyphenator = new Hyphenator(patterns);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

System.out.println(result.read()); // prints "Test-ing (au-to-mat-ic) Hy-PHeN-AtioN by com-put-er!"
```

You can also hyphenate a single token:
```java
HyphenatedToken result = hyphenator.hyphenateToken("Testing");

System.out.println(result.read("-")); // prints "Test-ing"
System.out.println(result.hyphenIndexes()); // prints [4]
```

### Customizing hyphenation
To skip some hyphens you can specify the following properties while creating _Hyphenator_ instance.
 * minLeadingLength (default: 2) - hyphen can be placed only after first _minLeadingLength_ characters 
 * minTrailingLength (default: 2) - hyphen can be placed only before last _minTrailingLength_ characters

```java
List<String> patterns = ... // load the patterns from the patterns file
HyphenatorProperties properties = new HyphenatorProperties(3, 4);
Hyphenator hyphenator = new Hyphenator(patterns, properties);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

System.out.println(result.read()); // prints "Testing (auto-matic) HyPHeN-AtioN by com-puter!"
```

### Customizing input word separator
To customize the separator on which text is supposed to be split into tokens pass the _tokenSeparator_ argument to the _Hyphenator_.
```java
List<String> patterns = ... // load the patterns from the patterns file
Hyphenator hyphenator = new Hyphenator(patterns, new HyphenatorProperties(), "|");

HyphenatedText result = hyphenator.hyphenateText("Testing|(automatic)|HyPHeNAtioN|by|computer!");

System.out.println(result.read()); // prints "Test-ing (au-to-mat-ic) Hy-PHeN-AtioN by com-put-er!"
```

### Customizing output
To customize the word or syllables separator used for creating hyphenated text pass arguments to _HyphenatedText::read_ method.
```java
List<String> patterns = ... // load the patterns from the patterns file
Hyphenator hyphenator = new Hyphenator(patterns);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

String hyphenatedText = result.read("|", "_");
System.out.println(hyphenatedText); // prints "Test_ing|(au_to_mat_ic)|Hy_PHeN_AtioN|by|com_put_er!"
```
