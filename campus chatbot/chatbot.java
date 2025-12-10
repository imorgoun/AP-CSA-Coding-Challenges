import java.util.*;
public class chatbot {
    static String[][] classInfo = {
            {"Physics", "Mr.S", "A", "1.11"},
            {"Math", "Ms.V", "B", "1.12"},
            {"English", "Mr.Rix", "C", "1.13"},
            {"PE", "Ms.Femke", "D", "1.14"},
            {"Dutch", "Mr.Brand", "E", "1.15"}};
    static String[][] clubInfo = {
            {"Robotics", "It's where you build robots."},
            {"Debate", "It's where you debate each other about meaningless things."}};
    static String[] suffixes = {
            " teaches ",
            " is at ",
            " is block ",
            " class is in room "};
    static String[] randomResponses = {
            "I don't quite understand.",
            "Sorry can you say that again.",
            "I don't understand your question.",
            "Such a dumb question.",
            "Figure it out yourself."};
    public static String formatString(String original) {
        if (original.trim().substring(original.trim().length()-1) == ".") {
            return original.substring(0, 1).toUpperCase() + original.substring(1) + " ";}
        return original.substring(0, 1).toUpperCase() + original.substring(1) + ". ";}
    public static String randomResponse() {
        return randomResponses[(int) (Math.floor(Math.random()*randomResponses.length))];}
    public static boolean doesStatementContain(String statement, String[] substatement) {
        for (int i  = 0; i < substatement.length; i++) {
            if (!statement.toLowerCase().contains(substatement[i].toLowerCase())) {return false;}
        }return true;}
    public static String[] returnSubjectInfo(String subjectName) {String[] info = null;
        for (int i  = 0; i < classInfo.length; i++) {
            if (classInfo[i][0].toLowerCase().equals(subjectName)) {info = classInfo[i];}}
        if (info == null) {for  (int i  = 0; i < clubInfo.length; i++) {
                if (clubInfo[i][0].toLowerCase().equals(subjectName)) {info = clubInfo[i];}}}
        return info;}
    public static String returnInfoFromQuestion(String statement, String question, int index) {
        if (doesStatementContain(statement, new String[]{question})) {
            String response;String[] splitStatement = statement.toLowerCase().split(" ");
            String currentSubject = splitStatement[splitStatement.length - 1].replaceAll("\\p{Punct}", "");
            response = returnSubjectInfo(currentSubject)[index];
            switch (index) {
                case 1: if (Arrays.asList(classInfo).contains(returnSubjectInfo(currentSubject))) {
                            return formatString(response + suffixes[index - 1] + currentSubject);}
                        else {return formatString(response);}
                case 2, 3: return formatString(currentSubject + suffixes[index] + response);
            }}return "";}
    public static String bindQuestions(String statement, String[] questions, int index) {String response = "";
        for (int i  = 0; i < questions.length; i++) {
            response = response + (returnInfoFromQuestion(statement, questions[i], index));}
        return response;}
    public static String response(String statement) {String response =
                bindQuestions(statement, new String[] {"Who teaches", "What is"}, 1) +
                bindQuestions(statement, new String[] {"What block is"}, 2) +
                bindQuestions(statement, new String[] {"Where is"}, 3);
        if (response.equals("")) {response = randomResponse();}return response;}
    public static void main(String[] args) {
        System.out.println("Ask me questions about your classes!");
        while (true){
            Scanner input = new Scanner(System.in); String statement = input.nextLine();
            System.out.println(response(statement));}}}