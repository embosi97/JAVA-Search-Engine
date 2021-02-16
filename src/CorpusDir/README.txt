My Project has 5 Java files:

I used a Porter's Stemming Algorithm from this site 
https://tartarus.org/martin/PorterStemmer/java.txt

The SearchEngine.java class (Main), which will write to some of the command-line arguments or read from them
after parsing through them. Everything is done from here.

hashFunc.java (readHTML) is what parses the HTML files, removes the tags, special characters, untrue words
(such as those that contain digits) and loads the filtered words onto a data structure that counts the
number of instances of each word in each file (with the help of the dummy text file, which replaces
the invertedIndex file since resultOutput does what that was supposed to do). Other functions (such as printHash 
and printSearchWord) print onto the resultOutput file and queriesOutput file, respectfully. Search Word is asked from 
the console. If stemming is enabled by the user, it will stem all the words.

"Snippets" (which include stop-words to provide context) are also provided when a word is searched for in the GUI.

I have a single GUI class (Named GUI.java), that has a search bar, a search button, panes that display the results (all words) or search word 
results respectfully. There are buttons to switch between those 2 results. 

Lastly, the indexObject.java class is an object that contains the word itself (parsed from an HTML file), the
instances of said word, as well as the document it was found in. This class has modified toString() and 
compareTo() methods for presentable, sorted outputs. Outputs for resultsOutput and queriesOutput look like
this due to the object class's modified toString().

LINCOLN appears in /eclipse-workspace/335Project1/src/CorpusDir/folderdir/htmlFile8.html 545 times.

It deviates slightly from the instructions but I think it's still an efficient program and can be tweaked,
if necessary, if subsequent phases require it. 

Command line should look something like this:

// -CorpusDir /eclipse-workspace/335Project1/src/CorpusDir/folderdir
// -InvertedIndex /eclipse-workspace/335Project1/src/dummy.txt
// -StopList /eclipse-workspace/335Project1/src/english-stopwords.txt
// -Queries /eclipse-workspace/335Project1/src/searchword.txt
// -Results /eclipse-workspace/335Project1/src/results.txt
// -Fixed 3
// -Stem true
 
 
 I tested my project with 7 HTML files, I was afraid to use 200 as it could take a long time to run. 
 With 7 it was quite fast on an IDE (Eclipse), so maybe it wouldn't be that bad.
 
 I tried commenting a lot to explain my methods. 
 
 The dummy file is deleted after all is set and done.
 

 Thank you and have a happy holidays.