import java.util.*;
public class chatbot {
    static String[][] classInfo = { // 2D list of classes and associated data.
            {"Physics", "Mr.S", "A", "1.11"},
            {"Math", "Ms.V", "B", "1.12"},
            {"English", "Mr.Rix", "C", "1.13"},
            {"PE", "Ms.Femke", "D", "1.14"},
            {"Dutch", "Mr.Brand", "E", "1.15"}};
    static String[][] clubInfo = { // 2D list of clubs that contains the club name and description.
            {"Robotics", "It's where you build robots."},
            {"Debate", "It's where you debate each other about meaningless things."}};
    static String[] suffixes = { // List of possible suffixes that fit the given data.
            " teaches ",
            " is at ",
            " is block ",
            " class is in room "};
    static String[] randomResponses = { // List of random responses.
            "I don't quite understand.",
            "Sorry can you say that again.",
            "I don't understand your question.",
            "Such a dumb question.",
            "Figure it out yourself."};
    public static String formatString(String original) { // Helper method that capitalizes and adds punctation (if needed).
        if (original.trim().substring(original.trim().length()-1) == ".") { // Checks if there is a period at the end of the original string.
            return original.substring(0, 1).toUpperCase() + original.substring(1) + " ";} // Adds a space at the end (to facilitate multiple responses).
        return original.substring(0, 1).toUpperCase() + original.substring(1) + ". ";} // Otherwise adds a period and a space.
    public static String randomResponse() { // Returns a random response from 'randomResponses' list.
        return randomResponses[(int) (Math.floor(Math.random()*randomResponses.length))];}
    public static boolean doesStatementContain(String statement, String[] substatement) { // Helper method that checks whether the statement contains
        for (int i  = 0; i < substatement.length; i++) { //                                   all substrings in 'substrings.'
            if (!statement.toLowerCase().contains(substatement[i].toLowerCase())) {return false;} // Returns false if the substring isn't found.
        }return true;} // If all the substrings are found, the method returns true.
    public static String[] returnSubjectInfo(String subjectName) {String[] info = null; // Helper method that returns the entire row of data
        for (int i  = 0; i < classInfo.length; i++) { //                                    about the given subject.
            if (classInfo[i][0].toLowerCase().equals(subjectName)) {info = classInfo[i];}} // Loops through each class and checks whether there is a match.
        if (info == null) {for  (int i  = 0; i < clubInfo.length; i++) { // If a class isn't found, finding a club is attempted.
                if (clubInfo[i][0].toLowerCase().equals(subjectName)) {info = clubInfo[i];}}} // Checks clubs by name.
        return info;} // Returns info, originally set to null meaning that the method will return null if no match is found.
    public static String returnInfoFromQuestion(String statement, String question, int index) { //                     Helper method that isolates given
        if (doesStatementContain(statement, new String[]{question})) { // Checks if statement contains the question.    data based on the type of question.
            String response;String[] splitStatement = statement.toLowerCase().split(" "); // Splits the statement around spaces into a list.
            String currentSubject = splitStatement[question.split(" ").length].replaceAll("\\p{Punct}", ""); // Removes punctuation.
            response = returnSubjectInfo(currentSubject)[index]; // Uses the method from before to return the correct field based on 'index.'
            switch (index) { //                                      (e.g. 1 –> teaches, 3 –> room)
                case 1: if (Arrays.asList(classInfo).contains(returnSubjectInfo(currentSubject))) { // Case where the requested field is teacher.
                            return formatString(response + suffixes[index - 1] + currentSubject);} // Returns the requested field, structured
                        else {return response;} // Case where requested field is description of a club.        using the suffixes list from before.
                case 2, 3: return formatString(currentSubject + suffixes[index] + response); // Cases where requested is block or room,
            }}return "";} // returns and empty string in case of an error.                               the sentence structure is the same.
    public static String bindQuestions(String statement, String[] questions, int index) {String response = ""; // Helper method that repeatedly
        for (int i  = 0; i < questions.length; i++) { //                                                           applies the method above.
            response = response + (returnInfoFromQuestion(statement, questions[i], index));} // Adds valid responses. This way many questions can be met
        return response;} //                                                                     with the same answer.
    public static String response(String statement) {String response = //                       Main method that returns a response with appropriate
                bindQuestions(statement, new String[] {"Who teaches", "What is"}, 1) + //  info. This can be easily done by adding them since
                bindQuestions(statement, new String[] {"What block is"}, 2) + //           they return an empty string if the question does not
                bindQuestions(statement, new String[] {"Where is"}, 3); //                 pertain to the requested field.
        if (response.equals("")) {response = randomResponse();}return response;} // If the response is empty, meaning info wasn't found or the question
    public static void main(String[] args) { //                                      isn't valid, then a random response is returned.
        System.out.println("Ask me questions about your classes!"); // Intro line that is displayed once in the beginning.
        Scanner input = new Scanner(System.in); // Initializes scanner to receive user input.
        while (true){ // Infinitely loops the conversation until user types 'bye' and then quits the program.
            String statement = input.nextLine(); // Reads the users input on the next line, to avoid confusion.
            if (!doesStatementContain(statement, new String[] {"bye"})) { // Checks if statement contains 'bye.'
                System.out.println(response(statement));} // Responds using the response method.
            else {System.out.println("Bye!");System.exit(0);}}}} // Quits the program if 'bye' is found in the statement.