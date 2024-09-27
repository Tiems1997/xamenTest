package xamenTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jdojo.intro.DBconnection;
import com.jdojo.intro.ExamApp;

import javafx.application.Platform;

public class AssertJTest{
	private static ExamApp app;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
        app = new ExamApp();
    }
    
	@Test
    public void testAssertJSearchResultValidMatricule() {
		Platform.runLater(() -> {
			ExamApp app = new ExamApp();
	        app.studentIdField.setText("12345");
	        app.searchResult();
	        
	        // Vérifie avec AssertJ que le label contient le texte attendu
	        assertThat(app.outputLabel.getText()).contains("Résultat : Succès");
        });
		
        
    }

    // Test AssertJ 2: Vérifie que le bouton "Afficher les détails" devient visible pour un matricule valide
    @Test
    public void testAssertJShowDetailsButtonVisible() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("12345");
            app.searchResult();
            
            // Vérifie avec AssertJ que le bouton est visible
            assertThat(app.showDetailsButton.isVisible()).isTrue();
        });
    	
        
    }

    // Test AssertJ 3: Vérifie que le label de sortie ne contient pas de résultat pour un matricule invalide
    @Test
    public void testAssertJSearchResultInvalidMatricule() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("54321");
            app.searchResult();
            
            // Vérifie avec AssertJ que le label affiche qu'aucun résultat n'a été trouvé
            assertThat(app.outputLabel.getText()).contains("Aucun résultat trouvé");
        });
    
        
    }

    // Test AssertJ 4: Vérifie que le bouton "Afficher les détails" reste caché pour un matricule invalide
    @Test
    public void testAssertJShowDetailsButtonNotVisible() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("54321");
            app.searchResult();
            
            // Vérifie avec AssertJ que le bouton reste caché
            assertThat(app.showDetailsButton.isVisible()).isFalse();
        });
    	
        
    }

    // Test AssertJ 5: Vérifie que le message d'erreur s'affiche correctement en cas d'échec de connexion à la base de données
    @Test
    public void testAssertJDatabaseConnectionFailure() {
    	Platform.runLater(() -> {
    		try {
    			DBconnection.getConnection();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} // Simule une erreur de connexion
            ExamApp app = new ExamApp();
            app.studentIdField.setText("12345");
            app.searchResult();
            
            // Vérifie avec AssertJ que le message d'erreur est affiché
            assertThat(app.outputLabel.getText()).contains("Erreur lors de la recherche");
        });
    	
        
    }

}