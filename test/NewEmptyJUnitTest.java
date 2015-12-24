/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.anna.MainApp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author igor
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("BEFORE CLASS");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("AFTER CLASS");
    }
    
    @Before
    public void setUp() {
        System.out.println("BEFORE");
    }
    
    @After
    public void tearDown() {
        System.out.println("AFTER");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        try {
            System.out.println("TEST");
            MainApp ma = new MainApp();
            Stage stage = new Stage();
            ma.start(stage);
        } catch (Exception ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
