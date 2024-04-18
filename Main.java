// import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public String generateHTML(User user, List<String> projects) {
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

    public String generatePortfolio() {
        User user = form.getUser();
        // Generate portfolio using user data
        return htmlGenerator.generateHTML(user, form.getProjects());
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
        // Create a user
        User user = new User("John Doe", "john@example.com", "1234567890", "123 Main St");

        // Create a form with user information
        Form form = new Form(user);
        form.addProject("Project 1: Lorem Ipsum");
        form.addProject("Project 2: Dolor Sit Amet");

        // Create an HTMLGenerator instance
        HTMLGenerator htmlGenerator = new HTMLGenerator();

        // Create a PortfolioWebsite instance
        PortfolioWebsite portfolioWebsite = new PortfolioWebsite(form, htmlGenerator);

        // Generate portfolio HTML
        String portfolioHTML = portfolioWebsite.generatePortfolio();

        // Save portfolio as HTML file
        portfolioWebsite.savePortfolioAsHTML(portfolioHTML, "portfolio");
    }
}
