import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class SimpleCalculatorTest {

    private SimpleCalculator calculator;


    @BeforeEach
    public void setup(){
        SimpleCalculator calculator = new SimpleCalculator();
    }

    @Test
    @DisplayName("Should Add Two Number Correctly ")
    public void ShouldAddTwoNumbers(){
        assertEquals(5, calculator.add(2,3));
        assertEquals(-5, calculator.add(-2,-3));
        assertEquals(0, calculator.add(0,0));
    }


    @Test
    @DisplayName("Should Add Two Number Correctly ")
    public void ShouldSubtractTwoNumbers(){
        assertEquals(1, calculator.subtract(3,2));
        assertEquals(-1, calculator.subtract(1,2));
        assertEquals(0, calculator.subtract(0,0));
    }

    @Test
    public void ShouldCheckIfPositives(){
        assertTrue(calculator.isPositive(5));
        assertFalse(calculator.isPositive(-5));
    }

    @AfterEach
    public void AfterEachTest(){
        System.out.println("Calculator Test Completed.");
    }
}
