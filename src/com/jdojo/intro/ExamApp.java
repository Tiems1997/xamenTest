package com.jdojo.intro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.*;

// Classe principale qui hérite de Application, ce qui est requis pour une application JavaFX
public class ExamApp extends Application {

    // Champs pour entrer le matricule, afficher le résultat, et un bouton pour afficher les détails
    public TextField studentIdField;
    public Label outputLabel;
    public Button searchButton, showDetailsButton;
   
    // Point d'entrée pour démarrer l'interface graphique
    @Override
    public void start(Stage primaryStage) {
        // Définir le titre de la fenêtre principale (Stage)
        primaryStage.setTitle("Consultation de Résultats");

        // Champ de texte pour entrer le matricule
        studentIdField = new TextField();
        studentIdField.setPromptText("Entrez votre matricule"); // Placeholder

        // Bouton de recherche pour lancer la recherche du résultat
        searchButton = new Button("Rechercher");
        // Action liée au clic du bouton de recherche
        searchButton.setOnAction(e -> {
        	searchResult();
        });

        // Label pour afficher le résultat après la recherche
        outputLabel = new Label();

        // Bouton pour afficher les détails, caché par défaut
        showDetailsButton = new Button("Afficher les détails");
        showDetailsButton.setVisible(false); // Caché jusqu'à ce que le résultat soit "Succès"
        showDetailsButton.setOnAction(e -> showDetails()); // Action liée au clic pour afficher les détails


        // Disposition des éléments dans une boîte horizontale (HBox)
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(searchButton, showDetailsButton);
        HBox.setMargin(searchButton, new Insets(5, 10, 5, 0));
        HBox.setMargin(showDetailsButton, new Insets(5, 0, 5, 10));

        // Mettre le champ matricule dans une VBox
        VBox inputContainer = new VBox(10);
        inputContainer.getChildren().addAll(new Label("Matricule :"), studentIdField, buttonContainer, outputLabel);
        inputContainer.setPadding(new Insets(10));

        // Création d'une scène avec la VBox comme racine
        Scene scene = new Scene(inputContainer, 1200, 600); // 1200x600 est la taille de la fenêtre

        // Ajout de la scène à la fenêtre principale et affichage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour rechercher le résultat en fonction du matricule entré
    public void searchResult() {
        String studentId = studentIdField.getText(); // Récupérer le texte entré
        System.out.println("Recherche pour le matricule : " + studentId);

        // Connexion à la base de données
        try (Connection conx = DBconnection.getConnection()) {
            String sql = "SELECT etudiants.* FROM etudiants WHERE matricule = ?";
            PreparedStatement pstmt = conx.prepareStatement(sql);
            pstmt.setString(1, studentId); // Associer le matricule à la requête
            ResultSet resultSet = pstmt.executeQuery(); // Exécuter la requête

            if (resultSet.next()) { // Si un résultat est trouvé
                String result = resultSet.getString("resultat");
                System.out.println("Résultat trouvé : " + result);
                showDetailsButton.setVisible(true);
                
                Platform.runLater(() -> {
                    outputLabel.setText("Résultat : " + result);
                });

            } else { // Si aucun résultat n'est trouvé
                showDetailsButton.setVisible(false); // Cacher le bouton de détails
                System.out.println("Aucun résultat trouvé pour ce matricule.");
                
                Platform.runLater(() -> {
                    outputLabel.setText("Aucun résultat trouvé pour ce matricule.");
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                outputLabel.setText("Erreur lors de la recherche.");
            });
        }
    }

    // Méthode pour afficher les détails dans une nouvelle fenêtre (Stage)
    private void showDetails() {
        String studentId = studentIdField.getText(); // Récupérer le matricule entré

        // Connexion à la base de données pour récupérer les détails
        try (Connection conx = DBconnection.getConnection()) {
            String sql = "SELECT * FROM etudiants WHERE matricule = ?";
            PreparedStatement pstmt = conx.prepareStatement(sql);
            pstmt.setString(1, studentId); // Associer le matricule à la requête
            ResultSet resultSet = pstmt.executeQuery(); // Exécuter la requête

            if (resultSet.next()) { // Si un résultat est trouvé
                // Récupération des informations
                String name = resultSet.getString("nom");
                String surname = resultSet.getString("prenom");
                String birthDate = resultSet.getString("date_naissance");
                String school = resultSet.getString("ecole");
                float average = resultSet.getFloat("moyenne");

                // Création d'une nouvelle fenêtre pour afficher les détails
                Stage detailsStage = new Stage(); // Nouveau Stage (fenêtre)
                detailsStage.setTitle("Détails de l'étudiant");

                // Création des labels pour afficher les informations
                Label matriculeLabel = new Label("Matricule: " + studentId);
                Label nameLabel = new Label("Nom: " + name);
                Label surnameLabel = new Label("Prénom: " + surname);
                Label birthDateLabel = new Label("Date de naissance: " + birthDate);
                Label schoolLabel = new Label("École: " + school);
                Label averageLabel = new Label("Moyenne: " + average + " / 20");

                // Disposition des éléments dans une VBox
                VBox detailVBox = new VBox(10);
                detailVBox.getChildren().addAll(matriculeLabel, nameLabel, surnameLabel, birthDateLabel, schoolLabel, averageLabel);
                detailVBox.setPadding(new Insets(10)); // Ajouter des marges internes

                // Création d'une scène pour la nouvelle fenêtre et ajout des éléments
                Scene detailScene = new Scene(detailVBox, 800, 400);
                
                detailsStage.setScene(detailScene); // Ajouter la scène à la nouvelle fenêtre
                detailsStage.show(); // Afficher la nouvelle fenêtre
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Afficher les erreurs SQL dans la console
        }
    }
    
    // Méthode principale pour démarrer l'application
    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}
