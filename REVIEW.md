
---------- Names:

There is a class name which does not use the same naming convention as the other classes. The class Apigetter only uses a capital letter for the first letter, while there rest of the classes contain capital letters at the start of each word.

---------- Headers:

The code contains no header comments.

---------- Comments:

Almost no comments present. Only a handfull places contain comments and those comments do not always make the code more understandable.

For example the following comment:
// Handle item selection
This comment does only say when the code after this comment is reached, however it does not explain the functionality of the code.

---------- Layout:

The positioning of elements is not always consistent throughout all the code and optimized for readability. In the class ListView, the functions initializeData() and readData() are called at the end of the function onCreate(). It is expected that those functions are positions near onCreate(), however onCreateOptionsMenu() and onOptionsItemSelected() are positioned inbetween. Next time it would be better to position elements which belong together be positioned together. 

---

All classes contain no commented out code, except ListView. The class ListView consists 50% of commented out code, which should have been deleted.

---

There are still some System.out.println pieces of code used for debugging, which were forgotten to be removed. 
Examples of those are line 87, 96 and 139 of the InfoScreen class.

---------- Formatting:

Line breaks is used good in overall. Longer lines are most of the time broken up in multiple lines, however there is a exception on line 52 of the InfoScreen class. This line contains a very long line without line wrapping.

---

The inline spacing is not always consistend. Line 26 of the SearchResult class and line 229 ListView class, both contain for-loops, but have inconsistet spacing:

Line 26 of the SearchResult class:
	for (int i = 0; i < size;i++) {

Line 229 of the ListView class:
	for (int i=0;i<len;i++){

---

Spacing between arguments are not always consistent. 

For example lines 151-154 of the InfoScreen class contain a space after the comma's:
	editor.putString("titles", titlesJs.toString());
	editor.putString("posters", postersJs.toString());
	editor.putString("years", yearsJs.toString());
	editor.putString("ids", idsJs.toString());

This is however not the case on lines 209-212 in the ListView class.
	String titlesStr = sharedPref.getString("titles",null);
	String postersStr = sharedPref.getString("posters",null);
	String yearsStr = sharedPref.getString("years",null);
	String idsStr = sharedPref.getString("ids",null);	

---

Interline spacing is sometimes done incorrectly. There are cases where there are multiple empty lines between lines of code and there are some parts where the spacing is inconsistent. 

The end of the try-catch statement on lines 40-57 of the Apigetter class contains an empty line, however this is not the case for the try-catch statement on lines 65-78 of the same class.

------------ Flow:

The if-else statement on lines 89-156 contains the following statement in both the if-section and the else-section:

ArrayList titles = new ArrayList();
ArrayList posters = new ArrayList();
ArrayList years = new ArrayList();
ArrayList ids = new ArrayList();

This could be avoided by putting this code before the if-else statement.

------------ Decomposition:

The function onOptionsItemSelected() in the InfoScreen class consists of more than a 100 lines. This should be broken up in smaller parts.
