// import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// User class to represent a user of the portfolio website
class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public User(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}

// Form class to represent the information entered by the user
class Form {
    private User user;
    private List<String> projects;

    public Form(User user) {
        this.user = user;
        this.projects = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void addProject(String project) {
        projects.add(project);
    }

    public List<String> getProjects() {
        return projects;
    }
}

// HTMLGenerator class to generate HTML content for the portfolio
class HTMLGenerator {
    public String generateHTML(User user, List<String> projects, String templateHTML) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body><h1>Portfolio</h1>");
        html.append("<p>Name: ").append(user.getName()).append("</p>");
        html.append("<p>Email: ").append(user.getEmail()).append("</p>");
        html.append("<p>Phone Number: ").append(user.getPhoneNumber()).append("</p>");
        html.append("<p>Address: ").append(user.getAddress()).append("</p>");
        html.append("<h2>Projects:</h2><ul>");
        for (String project : projects) {
            html.append("<li>").append(project).append("</li>");
        }
        html.append("</ul></body></html>");
        return html.toString();
    }
}

// PortfolioWebsite class to manage the portfolio generation process
class PortfolioWebsite {
    private Form form;
    private HTMLGenerator htmlGenerator;

    public PortfolioWebsite(Form form, HTMLGenerator htmlGenerator) {
        this.form = form;
        this.htmlGenerator = htmlGenerator;
    }

    public String generatePortfolio(String templateHTML) {
        User user = form.getUser();
        // Generate portfolio using user data and template HTML
        return htmlGenerator.generateHTML(user, form.getProjects(), templateHTML);
    }

    public void savePortfolioAsHTML(String portfolioHTML, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName + ".html");
            writer.write(portfolioHTML);
            writer.close();
            System.out.println("Portfolio saved as " + fileName + ".html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display options for template selection
        System.out.println("Choose a portfolio template:");
        System.out.println("1. Basic Portfolio");
        System.out.println("2. Detailed Portfolio");
        System.out.print("Enter your choice (1 or 2): ");
        int templateChoice = scanner.nextInt();

        // Create a user
        User user = getUserInput(scanner);

        // Create a form with user information
        Form form = getFormInput(scanner, user);

        // Create an HTMLGenerator instance
        HTMLGenerator htmlGenerator = new HTMLGenerator();

        // Create a PortfolioWebsite instance
        PortfolioWebsite portfolioWebsite = new PortfolioWebsite(form, htmlGenerator);

        // Generate portfolio HTML based on template choice
        String templateFileName = (templateChoice == 1) ? "basic_portfolio_template.html" : "detailed_portfolio_template.html";
        String portfolioHTML = portfolioWebsite.generatePortfolio(readTemplate(templateFileName));

        // Save portfolio as HTML file
        portfolioWebsite.savePortfolioAsHTML(portfolioHTML, "portfolio");
    }

    private static User getUserInput(Scanner scanner) {
        // Prompt user for input
        System.out.println("Enter your name:");
        String name = scanner.next();
        System.out.println("Enter your email:");
        String email = scanner.next();
        System.out.println("Enter your phone number:");
        String phoneNumber = scanner.next();
        System.out.println("Enter your address:");
        String address = scanner.next();
        
        return new User(name, email, phoneNumber, address);
    }

    private static Form getFormInput(Scanner scanner, User user) {
        // Create a form with user information
        Form form = new Form(user);

        // Prompt user to enter projects
        System.out.println("Enter your projects (one per line, type 'done' to finish):");
        String project = scanner.next();
        while (!project.equalsIgnoreCase("done")) {
            form.addProject(project);
            project = scanner.next();
        }

        return form;
    }

    private static String readTemplate(String fileName) {
        StringBuilder templateContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                templateContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return templateContent.toString();
    }
}
