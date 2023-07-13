[![Java CI](https://github.com/Nianna/hyphenator/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Nianna/hyphenator/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-17-informational)
![GitHub](https://img.shields.io/github/license/nianna/hyphenator)

# hyphenator

Simple implementation of text hyphenation using approach described in Franklin Mark Liang's thesis "Word Hy-phen-a-tion by Com-put-er".

Hyphenation preserves the case of the original text and hyphenates words with non-alphabetic characters as long as they are placed only at the beginning or the end of each word.


## Requirements
To use this tool, you need to have a hyphenation pattern dictionary for the desired language which can be downloaded e.g. from [LibreOffice repositories](https://github.com/LibreOffice/dictionaries).
Find the file with "hyph" prefix e.g. hyph_pl_PL.dic for Polish language.

## Hyph dic file format
Hyph dictionaries contain metadata followed by a list of patterns. Make sure to pass only the patterns themselves when creating _Hyphenator_ instance.
```
UTF-8  <---- encoding info, use it to load file
LEFTHYPHENMIN 2  <------ minimium prefix length, the value can be passed to the Hyphenator in HyphenatorProperties
RIGHTHYPHENMIN 2  <------ minimium suffix length, the value can be passed to the Hyphenator in HyphenatorProperties
.ć8    <--- example pattern
.4ć3ć8
.ćł8
.2ć1ń8
```

## Example usage

### Using defaults
By default input text is automatically split into tokens. After hyphenation each word's prefix and suffix must be at least 2 characters long. Space is used as word separator and hyphen as syllables separator.
```
List<String> patterns = ... // load the patterns from hyph dictionary file
Hyphenator hyphenator = new Hyphenator(patterns);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

String hyphenatedText = result.read();
System.out.println(hyphenatedText); // prints "Test-ing (au-to-mat-ic) Hy-PHeN-AtioN by com-put-er!"
```

You can also hyphenate a single token:
```
HyphenatedToken result = hyphenator.hyphenateToken("Testing");
String hyphenatedText = result.read("-");
System.out.println(hyphenatedText); // prints "Test-ing"
System.out.println(result.hyphenIndexes()); // prints [4]
```

### Customizing minimum prefix and suffix lengths
To customize minimum prefix and suffix length pass custom _HyphenatorProperties_ when creating _Hyphenator_ instance.
```
List<String> patterns = ... // load the patterns from hyph dictionary file
HyphenatorProperties properties = new HyphenatorProperties(3, 4);
Hyphenator hyphenator = new Hyphenator(patterns, properties);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

String hyphenatedText = result.read();
System.out.println(hyphenatedText); // prints "Testing (auto-matic) HyPHeN-AtioN by com-puter!"
```

### Customizing input word separator
To customize the separator on which text is supposed to be split into tokens pass the _tokenSeparator_ argument to the _Hyphenator_.
```
List<String> patterns = ... // load the patterns from hyph dictionary file
Hyphenator hyphenator = new Hyphenator(patterns, new HyphenatorProperties(), "|");

HyphenatedText result = hyphenator.hyphenateText("Testing|(automatic)|HyPHeNAtioN|by|computer!");

String hyphenatedText = result.read();
System.out.println(hyphenatedText); // prints "Test-ing (au-to-mat-ic) Hy-PHeN-AtioN by com-put-er!"
```

### Customizing output
To customize the word or syllables separator used for creating hyphenated text pass arguments to _HyphenatedText::read_ method.
```
List<String> patterns = ... // load the patterns from hyph dictionary file
Hyphenator hyphenator = new Hyphenator(patterns);

HyphenatedText result = hyphenator.hyphenateText("Testing (automatic) HyPHeNAtioN by computer!");

String hyphenatedText = result.read("|", "_");
System.out.println(hyphenatedText); // prints "Test_ing|(au_to_mat_ic)|Hy_PHeN_AtioN|by|com_put_er!"
```
