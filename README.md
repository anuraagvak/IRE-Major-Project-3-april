feature_words.java will extract the features for each of the 5 traits and writes them in 5 different file names which are created in the folder as a parameter .
feature_vectors.java will convert the users data into vector form and generates the training data  and test data  for each of the traits .  This  is given as an input to the svm tool
Compiling the codes :
javac -d feature_words.java feature_vectors.java 
executing :
java feature_words <path to the folder containing mypersonality_final.csv>
java feature_vectors <path to the folder containing mypersonality_final.csv>


After the features are extracted:

Now we have the train and test file for all the Traits separately.
go to the folder "svm"
For each trait i do:

./svm-train trainfile_i		//trainfile_i is the train file corresponding to the trait 'i'. Now a trainfile_i.model is generated.

./svm-predict testfile_i trainfile_i.model outputfile_i		//testfile_i is the test file corresponding to the trait 'i'. Now a 									//outputfile_i is generated which tells the accuracy!


The link to the codes repository is: https://github.com/anuraagvak/IRE-Major-Project-3-april
