import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Observer pattern
interface HTMLGenerationListener {
    void onHTMLGenerated(String html);
}

class HTMLFileWriter {
    // Singleton pattern
    private static HTMLFileWriter instance;

    private HTMLFileWriter() {
    }

    public static HTMLFileWriter getInstance() {
        if (instance == null) {
            instance = new HTMLFileWriter();
        }
        return instance;
    }

    public void writeHTML(String html) {
        try {
            FileWriter writer = new FileWriter("portfolio.html");
            writer.write(html);
            writer.close();
            JOptionPane.showMessageDialog(null, "Portfolio HTML file generated successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing HTML file: " + e.getMessage());
        }
    }
}

// Factory method pattern
abstract class HTMLFactory {
    public abstract String generateHTML(String name, String email, String phone, String projectTitle, String projectDescription, String projectLink);

    public static HTMLFactory createFactory(String type) {
        if (type.equals("basic")) {
            return new BasicHTMLFactory();
        } else if (type.equals("fancy")) {
            return new FancyHTMLFactory();
        }
        return null;
    }
}

class BasicHTMLFactory extends HTMLFactory {
    @Override
    public String generateHTML(String name, String email, String phone, String projectTitle, String projectDescription, String projectLink) {
        try {
            String template = new String(Files.readAllBytes(Paths.get("basic_template.html")));
            return template.replace("{name}", name)
                    .replace("{email}", email)
                    .replace("{phone}", phone)
                    .replace("{projectTitle}", projectTitle)
                    .replace("{projectDescription}", projectDescription)
                    .replace("{projectLink}", projectLink);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

class FancyHTMLFactory extends HTMLFactory {
    @Override
    public String generateHTML(String name, String email, String phone, String projectTitle, String projectDescription, String projectLink) {
        try {
            String template = new String(Files.readAllBytes(Paths.get("fancy_template.html")));
            return template.replace("{name}", name)
                    .replace("{email}", email)
                    .replace("{phone}", phone)
                    .replace("{projectTitle}", projectTitle)
                    .replace("{projectDescription}", projectDescription)
                    .replace("{projectLink}", projectLink);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

public class Main extends JFrame {

    private JTextField nameField, emailField, phoneField, projectTitleField, projectDescriptionField, projectLinkField;

    private final List<HTMLGenerationListener> listeners = new ArrayList<>();

    public Main() {
        super("Portfolio Generator");

        // Create labels and text fields for user input
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);
        JLabel projectTitleLabel = new JLabel("Project Title:");
        projectTitleField = new JTextField(20);
        JLabel projectDescriptionLabel = new JLabel("Project Description:");
        projectDescriptionField = new JTextField(20);
        JLabel projectLinkLabel = new JLabel("Project Link:");
        projectLinkField = new JTextField(20);

        // Create radio buttons for HTML type selection
        JRadioButton basicRadio = new JRadioButton("Basic HTML");
        JRadioButton fancyRadio = new JRadioButton("Fancy HTML");
        ButtonGroup group = new ButtonGroup();
        group.add(basicRadio);
        group.add(fancyRadio);

        // Create button to generate portfolio HTML
        JButton generateButton = new JButton("Generate Portfolio");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String projectTitle = projectTitleField.getText();
                String projectDescription = projectDescriptionField.getText();
                String projectLink = projectLinkField.getText();

                // Factory method pattern
                HTMLFactory factory = HTMLFactory.createFactory(basicRadio.isSelected() ? "basic" : "fancy");
                String htmlContent = factory.generateHTML(name, email, phone, projectTitle, projectDescription, projectLink);

                // Write HTML content to a file
                HTMLFileWriter.getInstance().writeHTML(htmlContent);

                // Observer pattern
                notifyListeners(htmlContent);
            }
        });

        // Layout components using GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(phoneLabel, constraints);

        constraints.gridx = 1;
        panel.add(phoneField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(projectTitleLabel, constraints);

        constraints.gridx = 1;
        panel.add(projectTitleField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(projectDescriptionLabel, constraints);

        constraints.gridx = 1;
        panel.add(projectDescriptionField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(projectLinkLabel, constraints);

        constraints.gridx = 1;
        panel.add(projectLinkField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(basicRadio, constraints);

        constraints.gridy = 7;
        panel.add(fancyRadio, constraints);

        constraints.gridy = 8;
        panel.add(generateButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Observer pattern
    public void addHTMLGenerationListener(HTMLGenerationListener listener) {
        listeners.add(listener);
    }

    public void removeHTMLGenerationListener(HTMLGenerationListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(String html) {
        for (HTMLGenerationListener listener : listeners) {
            listener.onHTMLGenerated(html);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
                // Observer pattern
                main.addHTMLGenerationListener(new HTMLGenerationListener() {
                    @Override
                    public void onHTMLGenerated(String html) {
                        // Do something when HTML is generated
                    }
                });
                main.setVisible(true);
            }
        });
    }
}
