/**
 * 
 */
/**
 * 
 */
module Examen {
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires  java.sql;
	requires junit;
	requires org.junit.jupiter.api;
	requires org.assertj.core;
	opens com.jdojo.intro to javafx.fxml;
	exports com.jdojo.intro;
	
}