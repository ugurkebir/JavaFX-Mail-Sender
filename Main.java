package application;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws IOException {

		final String from = "purgoufr@gmail.com";
		final String username = "purgoufr@gmail.com";
		final String password = "mehmet123";

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", "username");
		props.put("mail.smtp.password", "password");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", true);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		Group root = new Group();
		Scene scene = new Scene(root, 310, 350);
		stage.setScene(scene);
		stage.setTitle("Mail Sender");

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(25);
		grid.setHgap(25);
		scene.setRoot(grid);

		// To Part
		TextField toadress = new TextField();
		toadress.setPromptText("To");
		toadress.setPrefColumnCount(20);
		GridPane.setConstraints(toadress, 0, 0);
		grid.getChildren().add(toadress);

		// Subject Part
		TextField subject = new TextField();
		GridPane.setConstraints(subject, 0, 1);
		grid.getChildren().add(subject);
		subject.setPromptText("Enter Subject");
		subject.setPrefColumnCount(20);
		subject.setPrefHeight(20);

		// Body Part
		TextArea body = new TextArea();
		body.setPrefRowCount(10);
		body.setPrefColumnCount(100);
		body.setWrapText(true);
		body.setPrefWidth(150);
		body.setPromptText("Some text");
		GridPane.setConstraints(body, 0, 2);
		String cssDefault = "";
		body.setText(cssDefault);
		grid.getChildren().add(body);

		// Button Part
		Button btn = new Button();
		grid.getChildren().add(btn);
		btn.setText("Send");
		GridPane.setConstraints(btn, 0, 4);
		GridPane.setHalignment(btn, HPos.RIGHT);

		stage.show();

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DropShadow shadow = new DropShadow();
				btn.setEffect(shadow);
				stage.setScene(scene);

				String text = toadress.getText();
				String text1 = subject.getText();
				String text2 = body.getText();
				stage.show();
				try {
					// Create a default MimeMessage object.
					Message message = new MimeMessage(session);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));

					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(text));

					// Set Subject: header field
					message.setSubject(text1);

					// Now set the actual message
					message.setText(text2);

					// Send message
					Transport.send(message);
					System.out.println("Sent message successfully.");
				} catch (MessagingException e) {
					System.out.println("Sent message failed.");
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
