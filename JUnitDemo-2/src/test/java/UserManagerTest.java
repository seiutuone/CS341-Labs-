import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;

    @BeforeAll
    public static void setupAll(){
        System.out.println("I should be printed before all the tests commence!");
    }

    @BeforeEach
    public void setup(){
        System.out.println("I am Instantiating the User Test Scripts!");
        userManager = new UserManager();
    }

    // Test null validations
    @Test
    @DisplayName("Should not create user when first name is null.")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser(null,"Prasad","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when last name is null.")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin",null,"9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number is null.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberNull() {
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin", "Prasad", null);
        });
    }

    // Test empty string validations
    @Test
    @DisplayName("Should not create user when first name is empty.")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsEmpty(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("","Prasad","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when last name is empty.")
    public void shouldThrowRuntimeExceptionWhenLastNameIsEmpty(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number is empty.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsEmpty(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","");
        });
    }

    // Test whitespace validations
    @Test
    @DisplayName("Should not create user when first name is only whitespace.")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsWhitespace(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("   ","Prasad","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when last name is only whitespace.")
    public void shouldThrowRuntimeExceptionWhenLastNameIsWhitespace(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","   ","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number is only whitespace.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsWhitespace(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","   ");
        });
    }

    // Test phone number format validation
    @Test
    @DisplayName("Should not create user when phone number doesn't start with 9.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberDoesntStartWith9(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","8998083");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number is wrong length.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsWrongLength(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","99980");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number contains non-digits.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberContainsNonDigits(){
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","999abc3");
        });
    }

    // Test successful user creation
    @Test
    @DisplayName("Should create user.")
    public void shouldCreateUser() {
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @Test
    @DisplayName("Phone number should start with 9.")
    public void shouldTestPhoneNumberFormat() {
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    // Test duplicate user prevention
    @Test
    @DisplayName("Should not create duplicate user.")
    public void shouldThrowRuntimeExceptionWhenUserAlreadyExists(){
        userManager.addUser("Shalvin","Prasad","9998083");
        assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin","Prasad","9998084");
        });
    }

    // Test multiple different users
    @Test
    @DisplayName("Should create multiple different users.")
    public void shouldCreateMultipleDifferentUsers() {
        userManager.addUser("Shalvin","Prasad","9998083");
        userManager.addUser("John","Doe","9998084");
        userManager.addUser("Jane","Smith","9998085");
        assertEquals(3,userManager.getAllUsers().size());
    }

    // Test same first name, different last name
    @Test
    @DisplayName("Should create users with same first name but different last name.")
    public void shouldCreateUsersWithSameFirstNameDifferentLastName() {
        userManager.addUser("Shalvin","Prasad","9998083");
        userManager.addUser("Shalvin","Kumar","9998084");
        assertEquals(2,userManager.getAllUsers().size());
    }

    // Test same last name, different first name
    @Test
    @DisplayName("Should create users with same last name but different first name.")
    public void shouldCreateUsersWithSameLastNameDifferentFirstName() {
        userManager.addUser("Shalvin","Prasad","9998083");
        userManager.addUser("Raj","Prasad","9998084");
        assertEquals(2,userManager.getAllUsers().size());
    }

    // Test getAllUsers returns empty collection initially
    @Test
    @DisplayName("Should return empty collection when no users exist.")
    public void shouldReturnEmptyCollectionWhenNoUsers() {
        assertTrue(userManager.getAllUsers().isEmpty());
        assertEquals(0,userManager.getAllUsers().size());
    }

    @Nested
    class RepeatedTests {
        @DisplayName("Repeat add user test 10 times.")
        @RepeatedTest(value = 10, name = "Repeating Test {currentRepetition} of {totalRepetitions}")
        public void shouldCreateUserMultipleTimes() {
            userManager.addUser("Shalvin", "Prasad", "9998083");
            assertFalse(userManager.getAllUsers().isEmpty());
            assertEquals(1, userManager.getAllUsers().size());
        }
    }

    @DisplayName("Add phones number from a value source.")
    @ParameterizedTest
    @ValueSource(strings = {"9998083","9998082","9998085"})
    public void shouldCreateUserUsingValueSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @DisplayName("Add phones number from a CSV source.")
    @ParameterizedTest
    @CsvSource({"9998083","9998082","9998085"})
    public void shouldCreateUserUsingCSVSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @DisplayName("Add phones number from a CSV File Source.")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldCreateUserUsingCSVFileSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @AfterEach
    public void afterEachTest(){
        System.out.println("Just completed a test!");
    }

    @AfterAll
    public static void weAreDoneNow(){
        System.out.println("This is the last thing to be printed!");
    }

    @Test
    @DisplayName("Test has been disabled!")
    @Disabled
    public void imNotWorking(){
        throw new RuntimeException("Test Should not be executed!");
    }

    @Test
    @DisplayName("We are going to Assume stuff here")
    public void shouldTestOnlyInDevEnvironment(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }
}