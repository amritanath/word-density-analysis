# word-density-analysis

Interview ProjectL Word Density Analysis

Siyang Yao
12/08/2015     

OBJECTIVES:
---------------------------------------------------------------------------------------------------
Given any page (URL), be able to classify the page, and return a list of relevant topics.      

DESSIGN:
---------------------------------------------------------------------------------------------------
1. Parse url. In this part, I used Jsoup to parse url and get Document. Parse every word 
   from the Document and add them to the Word Trie(word trie will be introduced later). 
   jsoup is a Java library for working with real-world HTML. It provides a very convenient 
   API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.

2. Parse key words. Count the frequency of each key word. Key word might be one single word, or 
   might be consist of multiple words like Compact toaster. So I need to determine which words are 
   candidates. The idea is that the candidates should be the words after exclusing the stop words 
   and punctuation("?",",",".","!"......).
   Therefore, first, create stop words list from stopwords.txt. Then remove all punctuation 
   when parsing each word.
   In this project, return the top five most frequency of key words. 

DATA STRUCTURE:
---------------------------------------------------------------------------------------------------
I used the Trie and Node data structure to implement this system.

Word Node has three fields: 
value: it is the word value being stored. The type is String.
degree: it is the frequency of the word being stored
children: it is a list, which store the children Nodes of the current Node.

Word Trie has two main methods:
addWord(String word): add single word to this Trie
addWord(String[] word): add multiple words to this Trie.
Trie is a collection of NODEs stored in a tree sturcture. 

For example:
Adding these four keywords to the Trie 
{"Conair", "Conair Cuisinart", "Conair CPT-122", "Conair Cuisinart Toaster"} 
    
wordTrie.add({"Conair"});    // single word
wordTrie.add({"Conair", "Cuisinart"});  // multiple words
wordTrie.add({"Conair", "CPT-122"});
wordTrie.add({"Conair", "Cuisinart", "Toaster"});
    
Afer adding these word to the Trie, the Trie would look like:
    
["Conair", 4] --> ["Cuisinart", 2] --> ["Toaster", 1]
              --> ["CPT-122" 1] 
["CPT-122", 1]   
["Cuisinart" 2]
["Toaster", 1]

In "[..]", the first parameter is value, the second parameter is frequency. 
The arrows points the child Nodes.

If new keywords like {"Cuisinart Toaster"},  the Trie would look like:  
["Conair", 4] --> ["Cuisinart", 2] --> ["Toaster", 1]
              --> ["CPT-122" 1] 
["CPT-122", 1]   
["Cuisinart" 3] --> ["Toaster",2]
["Toaster", 2]
["compact",1]

So the result is:    
4 frequencies of "Conair"
3 frequencies of "Cuisinart"
2 frequencies of "Cuisinart Toaster"
2 frequencies of "Conair Cuisinart"
1 frequencies of "Conair CPT-122"
1 frequencies of "Conair Cuisinart Toaster"
1 frequencies of "CPT-122"
1 frequencies of "compact"

KEYWORD EXTRACTION
---------------------------------------------------------------------------------------------------
After getting the Document from the URL, the document is parsed entirely and put all words into the 
Scanner. Each time, one word is read out from Scanner. After determining the word is not stop word
and also it is candidate word, the word will be added the Word Trie.

For example:
the sentence is:

"Man behind NSA leaks says he did it to safeguard privacy and liberty"

The key word parsing would look like:

"Man": wordTrie.add("Man");
StopWord "behind";
"NSA": wordTrie.add("NSA");
"leaks": wordTrie.add("leaks");
StopWord "says": wordTrie.add("NSA","leaks");
StopWord "he" 
StopWord "did"
StopWord "it"
StopWord "to"
"safeguard": wordTrie.add("safeguard");
"privacy": wordTrie.add("privacy");
StopWord "and": wordTrie("safeguard","privacy");
"liberty": wordTrie.add("liberty");

And the Trie would look like:

["Man", 1]
["NSA", 1] --> ("leaks", 1)
["leaks", 1]
["safeguard", 1] --> ["privacy", 1]
["privacy", 1]


EXTERNAL LIBRARIES USED
---------------------------------------------------------------------------------------------------
jsoup - jsoup is a Java library for working with real-world HTML. It provides a very convenient API 
for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.
Reference: http://jsoup.org/

TEST CASES
--------------------------------------------------------------------------------
1. http://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster
2. http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/
3. http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/

There will be top five results.(the default is to return five results. It also can all the results)
for url 1, the result looks like:
keyword:[toaster]  fre: 39
keyword:[Toaster, Cuisinart]  fre: 33
keyword:[2-Slice]  fre: 27
keyword:[Stainless]  fre: 25
keyword:[toast, Amazon, bread]  fre: 24

for url 2, the result looks like:
keyword:[Outdoor]  fre: 10
keyword:[friend]  fre: 9
keyword:[Top]  fre: 8
keyword:[Ideas, REI, time, Blog]  fre: 5
keyword:[7, Holiday, Traditions, Gifts, Gift, Worthy, Friend, flat, great, Making]  fre: 4

for url 3, the result looks like:
keyword:[NSA]  fre: 16
keyword:[Snowden]  fre: 14
keyword:[government]  fre: 8
keyword:[WATCH, WATCHED, Replay, Videos]  fre: 6
keyword:[Guardian, worked, Hawaii]  fre: 5
keyword:[Act, Hong, told, Patriot, Kong, Obama, privacy, contractor, leaks]  fre: 4
