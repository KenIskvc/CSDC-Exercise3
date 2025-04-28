package at.ac.fhcampuswien.fhmdb;
import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class InfrastructureTest {

    private DatabaseManager dbManager;


    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager();
        dbManager.createConnectionSource();
    }

    @AfterEach
    void tearDown() {
        if (dbManager.getConnectionSource() != null) {
            dbManager.closeConnectionSource();
        }
    }

    @Test
    void testCreateConnectionSource() {
        ConnectionSource conn = dbManager.getConnectionSource();
        assertNotNull(conn, "ConnectionSource should not be null");
    }
    @Test
    void testCreateTables() {
        assertDoesNotThrow(() -> dbManager.createTables(), "Creating tables should not throw exceptions");
    }
}
