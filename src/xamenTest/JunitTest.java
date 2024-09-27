package xamenTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jdojo.intro.DBconnection;
import com.jdojo.intro.ExamApp;

import javafx.application.Platform;

public class JunitTest {
	private static ExamApp app;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
        app = new ExamApp();
    }

  
   
    // Test JUnit 1: Vérifie si un résultat correct est renvoyé pour un matricule valide
    @Test
    public void testSearchResultValidMatricule() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("12345");
            app.searchResult();
            
            // Vérifie que le label de sortie contient "Succès" après la recherche
            assertEquals("Résultat : Succès", app.outputLabel.getText());
        });
        
    }

    // Test JUnit 2: Vérifie le comportement pour un matricule invalide
    @Test
    public void testSearchResultInvalidMatricule() {
        
        
        Platform.runLater(() -> {
        	ExamApp app = new ExamApp();
            app.studentIdField.setText("54321");
            app.searchResult();
            
            // Vérifie que le label de sortie indique qu'aucun résultat n'a été trouvé
            assertEquals("Aucun résultat trouvé pour ce matricule.", app.outputLabel.getText());
        });
    }

    // Test JUnit 3: Vérifie que le bouton "Afficher les détails" devient visible après une recherche réussie
    @Test
    public void testShowDetailsButtonVisible() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("12345");
            app.searchResult();
            
            // Vérifie que le bouton "Afficher les détails" est visible
            assertTrue(app.showDetailsButton.isVisible());
        });
        
    }

    // Test JUnit 4: Vérifie que le bouton "Afficher les détails" est caché si aucun résultat n'est trouvé
    @Test
    public void testShowDetailsButtonNotVisible() {
    	Platform.runLater(() -> {
    		ExamApp app = new ExamApp();
            app.studentIdField.setText("54321");
            app.searchResult();
            
            // Vérifie que le bouton "Afficher les détails" est caché
            assertFalse(app.showDetailsButton.isVisible());
        });
    }

    // Test JUnit 5: Vérifie le comportement en cas de problème de connexion à la base de données
    @Test
    public void testDatabaseConnectionFailure() throws SQLException {
        
        Platform.runLater(() -> {
        	try {
				DBconnection.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Utilise une URL invalide pour simuler un échec
            ExamApp app = new ExamApp();
            app.studentIdField.setText("12345");
            app.searchResult();
        });
    }
}