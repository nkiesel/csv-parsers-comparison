# csv-parsers-comparison

-----

**Note:** This repo is forked from the original https://github.com/uniVocity/csv-parsers-comparison and changed in the following ways:

1. Only use JDK11
2. Upgrade all parsers to their latest stable version
3. Change download location for CSV file (original location no longer works)

-----

This project aims to compare all CSV parsers for Java in existence, or at least the ones that seem to work and are 
available to the general public. There are too many and the intention here is to help you decide which one is the best 
for you. Commercial parsers are welcome in the test. Please send us the details of your commercial parser and we will 
include the results. 

Currently, we are only testing parsing performance. As the input file, we will be using the 
famous [worldcitiespop.txt], which was made available 
for free by [Maxmind](http://www.maxmind.com). It contains more than 3 million rows, which should be sufficient for our test.

## Building and Running

Prerequisites: Git, wget, gunzip, GNU Patch, Apache Maven 3, and Java 11.

If you wish to reproduce our performance results:

```bash
$ git clone https://github.com/uniVocity/csv-parsers-comparison.git
$ cd csv-parsers-comparison
$ wget https://raw.githubusercontent.com/CODAIT/redrock/master/twitter-decahose/src/main/resources/Location/worldcitiespop.txt.gz 
$ gunzip worldcitiespop.txt.gz
$ git checkout src/
$ mvn clean package
$ java -jar target/csv-parsers-comparison-1.1-uber.jar .
```

NOTE: the `.` at the end of the last command, this tells Java the folder containing the `worldcitiespop.txt`. You can 
alternatively specify a path to any folder that contains a `worldcitiespop.txt` file.

Our test is very simple and involves just counting the number of rows read from the input file. The implementation using 
each parser is [here](./src/main/java/com/univocity/articles/csvcomparison/parser). 

### Important
 The input file is **not** [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant. We generate a compliant 
 version using the [HugeFileGenerator](./src/main/java/com/univocity/articles/csvcomparison/HugeFileGenerator.java) 
 class to test the parsers against a generated file with the same data, but enclosed within quotes and properly escaped.
 
It's important to notice that there's no such thing as a CSV standard and we do not recommend you to use parsers that 
follow the RFC strictly, as they will blow up in face of non-compliant inputs. The reality is: your parser must be 
ready to process crooked data instead of going belly up. In the end, your client is the one who tells you what you
must swallow, and in many circumstances it's not up to you to decide how your data is going to be generated.

We generate a RFC compliant version to give those sensitive parsers a chance to see how they perform.
Once again, we consider their usage risky.

## CSV Parsers

This is the list of all parsers currently tested.

| Parser                       | Version | Website                                                                                           |
|------------------------------|--------:|---------------------------------------------------------------------------------------------------|
| uniVocity-parsers' CsvParser |   2.9.1 | [www.univocity.com](http://www.univocity.com)                                                     |
| CSVeed                       |   0.7.3 | [csveed.org](http://csveed.org)                                                                   |
| Apache Commons CSV           |   1.9.0 | [commons.apache.org/proper/commons-csv](http://commons.apache.org/proper/commons-csv)             |
| flatpack                     |  4.0.18 | [flatpack.sourceforge.net](http://flatpack.sourceforge.net/)                                      |
| SuperCSV                     |   2.4.0 | [supercsv.sourceforge.net](http://supercsv.sourceforge.net/)                                      |
| jCSV                         |   1.4.0 | [code.google.com/p/jcsv](https://code.google.com/p/jcsv/)                                         |
| JavaCSV                      |     2.0 | [sourceforge.net/projects/javacsv](http://sourceforge.net/projects/javacsv)                       |
| esperio-csv                  |   8.8.0 | [www.espertech.com](http://www.espertech.com/)                                                    |
| way-io                       |   2.1.0 | [www.objectos.com.br](http://www.objectos.com.br/)                                                |
| OpenCSV                      |     5.6 | [opencsv.sourceforge.net](http://opencsv.sourceforge.net/)                                        |
| gj-csv                       |     1.0 | ?                                                                                                 |
| SimpleCSV                    |     2.1 | [github.com/quux00/simplecsv](https://github.com/quux00/simplecsv)                                |
| beanIO                       |   2.1.0 | [beanio.org](http://beanio.org/)                                                                  |
| jackson-dataformat-csv       |  2.3.13 | [github.com/FasterXML/jackson-dataformat-csv](http://github.com/FasterXML/jackson-dataformat-csv) |
| SimpleFlatMapper CSV parser  |   8.2.3 | [github.com/arnaudroger/SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper)        |
| OsterMiller Utils            | 1.07.00 | [ostermiller.org/utils/CSV.html](http://ostermiller.org/utils/CSV.html)                           |
| Product Collections          |   1.4.5 | [github.com/marklister/product-collections](https://github.com/marklister/product-collections)    |

## Statistics (updated 30th of June, 2022)

Results will vary depending on your setup and hardware, here is mine: 

 * CPU: Intel(R) Core(TM) i7-8665U CPU @ 1.90GHz
 * RAM: 32 GB
 * Storage: 1TB SSD drive
 * OS: Debian Linux 64-bit 
 * JDK: 11.0.15 64-bit (Linux)

*Note* [uniVocity-parsers](http://github.com/uniVocity/univocity-parsers/) provides an option to select the fields you
 are interested in, and our parsers will execute faster by not processing values that are not selected. It makes quite 
 a difference in performance but we removed this test as the other parsers don't have a similar feature.

### Processing 3,173,958 rows of non [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant input. No quoted values.

## JDK 11
| Parser                           | Average time | % Slower than best | Best time | Worst time |
|----------------------------------|-------------:|-------------------:|----------:|-----------:|
| SimpleFlatMapper CSV parser 	    |       943 ms |         Best time! |    928 ms |    990 ms  |
| uniVocity CSV parser 	        |       944 ms |                 0% |    876 ms |    1058 ms |
| Jackson CSV parser 	            |      1423 ms |                50% |   1254 ms |    1548 ms |
| Product Collections parser 	    |      1450 ms |                53% |   1388 ms |    1512 ms |
| Oster Miller CSV parser 	        |      1601 ms |                69% |   1562 ms |    1685 ms |
| JCSV Parser 	                    |      1624 ms |                72% |   1579 ms |    1670 ms |
| Java CSV Parser 	                |      1639 ms |                73% |   1573 ms |    1702 ms |
| Gen-Java CSV 	                |      1829 ms |                93% |   1781 ms |    1874 ms |
| OpenCSV 	                        |      1838 ms |                94% |   1776 ms |    1900 ms |
| Simple CSV parser 	            |      2054 ms |               117% |   1969 ms |    2166 ms |
| SuperCSV 	                    |      2127 ms |               125% |   2037 ms |    2258 ms |
| Apache Commons CSV 	            |      2455 ms |               160% |   2411 ms |    2497 ms |
| Way IO Parser 	                |      2460 ms |               160% |   2384 ms |    2568 ms |


### Processing 3,173,958 rows of [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant input. All values quoted.

**Note** this input file has all the values enclosed within quotes. We generated the input like this on purpose as 
the algorithm to process quotes is a bit different.

## JDK 11
| Parser                        | Average time | % Slower than best | Best time | Worst time |
|-------------------------------|-------------:|-------------------:|----------:|-----------:|
| SimpleFlatMapper CSV parser 	 |      1081 ms |         Best time! |   1029 ms |    1134 ms |
| uniVocity CSV parser 	     |      1142 ms |                 5% |   1106 ms |    1170 ms |
| Jackson CSV parser 	         |      1515 ms |                40% |   1472 ms |    1576 ms |
| Product Collections parser    |      1579 ms |                46% |   1525 ms |    1640 ms |
| Java CSV Parser 	             |      1989 ms |                83% |   1901 ms |    2206 ms |
| JCSV Parser 	                 |      2179 ms |               101% |   2099 ms |    2233 ms |
| SuperCSV 	                 |      2343 ms |               116% |   2268 ms |    2423 ms |
| Oster Miller CSV parser 	     |      2496 ms |               130% |   2425 ms |    2551 ms |
| OpenCSV 	                     |      2515 ms |               132% |   2337 ms |    2736 ms |
| Gen-Java CSV 	             |      2590 ms |               139% |   2501 ms |    2823 ms |
| Simple CSV parser 	         |      2750 ms |               154% |   2722 ms |    2802 ms |
| Apache Commons CSV 	         |      3012 ms |               178% |   2933 ms |    3112 ms |
| Way IO Parser 	             |      3062 ms |               183% |   2990 ms |    3169 ms |
| Esperio CSV parser 	         |      3556 ms |               228% |   3408 ms |    3733 ms |
| Bean IO Parser 	             |     3890 ms  |               259% |   3818 ms |    3951 ms |


## Reliability (updated 9th of October, 2017)

The following parsers were unable to process the [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant file
 [correctness.csv](./src/main/resources/correctness.csv). This test is executed using the class [CorrectnessComparison.java](./src/main/java/com/univocity/articles/csvcomparison/CorrectnessComparison.java)

| Parser            | Error                                                                                                                   |
|-------------------|:------------------------------------------------------------------------------------------------------------------------|
| CSVeed	  	     | CSVeed threw exception "Illegal state transition: Parsing symbol QUOTE_SYMBOL [34] in state INSIDE_FIELD"               |
| jCSV Parser       | JCSV Parser produced ["Year,Make,Model,Description,Price"] instead of ["Year", "Make", "Model", "Description", "Price"] |
| Simple CSV parser | Simple CSV parser threw exception "The separator, quote, and escape characters must be different!"                      |
| Way IO Parser     | Way IO Parser threw exception "Could not convert  to class java.lang.String"                                            |
| Gen-Java CSV      | Gen-Java CSV produced 7 rows instead of 6                                                                               |
| Flatpack			 | Flatpack produced 5 rows instead of 6                                                                                   |

The exact same errors have been reported 3 years ago when I last updated this page. Just avoid these parsers. 

## Conclusion

Currently, three parsers stand out as the fastest CSV parsers for Java:

*uniVocity-parsers*, *SimpleFlatMapper* and *Jackson CSV*. Keep in mind that *Simpleflatmapper* is a very simple 
implementation that does not provide any customization options. Results are affected by a simple change in the JDK version,
however these three parsers are always at the top.

We will keep working to improve the performance of our parsers, and will try to update the results of this benchmark
every time a new parser is added to the list.

Head on to the [uniVocity-parsers github page](http://github.com/uniVocity/univocity-parsers/) to get access to its
source code and documentation. Contributions are welcome.

#### Commercial support is available for your peace of mind. [Click here to learn more.](http://www.univocity.com/products/parsers-support)

[worldcitiespop.txt]: https://raw.githubusercontent.com/CODAIT/redrock/master/twitter-decahose/src/main/resources/Location/worldcitiespop.txt.gz
