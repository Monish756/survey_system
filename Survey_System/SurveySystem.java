import java.sql.*;
import java.util.Scanner;

public class SurveySystem {

    static final String URL = "jdbc:mysql://localhost:3306/survey_db";
    static final String USER = "root";
    static final String PASSWORD = "reshma@123";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== SURVEY SYSTEM =====");
            System.out.println("1. Add Question");
            System.out.println("2. View Questions");
            System.out.println("3. Answer Survey");
            System.out.println("4. View Responses");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addQuestion();
                case 2 -> viewQuestions();
                case 3 -> answerSurvey();
                case 4 -> viewResponses();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Add Question
    static void addQuestion() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            sc.nextLine();
            System.out.print("Enter Question: ");
            String q = sc.nextLine();

            String sql = "INSERT INTO questions (question_text) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, q);

            ps.executeUpdate();
            System.out.println("Question added!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // View Questions
    static void viewQuestions() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("question_text"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Answer Survey
    static void answerSurvey() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                int qid = rs.getInt("id");
                String question = rs.getString("question_text");

                System.out.println("\n" + question);
                sc.nextLine();
                String answer = sc.nextLine();

                String sql = "INSERT INTO responses (question_id, answer) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, qid);
                ps.setString(2, answer);

                ps.executeUpdate();
            }

            System.out.println("Survey completed!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // View Responses
    static void viewResponses() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String query = "SELECT q.question_text, r.answer " +
                    "FROM responses r JOIN questions q ON r.question_id = q.id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println("Q: " + rs.getString("question_text"));
                System.out.println("A: " + rs.getString("answer"));
                System.out.println("-------------------");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}